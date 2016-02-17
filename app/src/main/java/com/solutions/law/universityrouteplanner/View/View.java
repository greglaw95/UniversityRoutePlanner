package com.solutions.law.universityrouteplanner.View;

import android.graphics.Color;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.solutions.law.universityrouteplanner.Controller.IController;
import com.solutions.law.universityrouteplanner.Model.Update.RoutePlannerState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class View implements OnMapReadyCallback, RoutePlannerListener {

    private final IController controller;
    private List<EndPoint> endPoints;
    private List<MidPoint> midPoints;
    private GoogleMap gMap;
    private EditText startPoint;
    private EditText endPoint;
    private RoutePlannerState prevState;

    public View(IController control, List<EndPoint> endPoints, List<MidPoint> midPoints, EditText startPoint, EditText endPoint, Button directionsButton) {
        this.endPoints = endPoints;
        this.midPoints = midPoints;
        this.controller = control;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startPoint.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(android.view.View v, MotionEvent event) {
                controller.focusOn(IController.Location.START);
                return false;
            }
        });
        this.endPoint.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(android.view.View v, MotionEvent event) {
                controller.focusOn(IController.Location.END);
                return false;
            }
        });
        directionsButton.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(android.view.View v, MotionEvent event) {
                controller.route();
                return false;
            }
        });
        prevState = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        for (EndPoint current : endPoints) {
            gMap.addPolygon(new PolygonOptions().addAll(current.getCoOrds()).strokeColor(Color.BLUE).fillColor(Color.CYAN).geodesic(true).clickable(true));
        }
        gMap.setBuildingsEnabled(false);
        gMap.setOnPolygonClickListener(controller);
    }

    @Override
    public void update(RoutePlannerState state) {
        Boolean earlyChange = false;
        if (prevState == null) {
            updateText(startPoint, state.getStartLoc());
            updateText(endPoint, state.getEndLoc());
            updateMap(state.getStartLoc(),state.getEndLoc(),state.getRouteSelected());
        } else {
            if (prevState.getStartLoc()==null||!prevState.getStartLoc().equals(state.getStartLoc())) {
                updateText(startPoint, state.getStartLoc());
                earlyChange = true;
            }
            if (prevState.getEndLoc()==null||!prevState.getEndLoc().equals(state.getEndLoc())) {
                updateText(endPoint, state.getEndLoc());
                earlyChange = true;
            }
            if (earlyChange || prevState.getRouteSelected()==null || !prevState.getRouteSelected().equals(state.getRouteSelected())) {
                updateMap(state.getStartLoc(), state.getEndLoc(), state.getRouteSelected());
            }
        }
        prevState = state;
    }

    private void updateText(EditText box, String newText) {
        if (newText != null) {
            box.setText(newText.toCharArray(), 0, newText.length());
        }
    }

    private void updateMap(String start, String end, List<String> routeSelected) {
        List<MidPoint> routeDisplay;
        gMap.clear();
        gMap.setBuildingsEnabled(false);
        for (EndPoint current : endPoints) {
            if ((start != null && current.getName().equals(start)) || (end != null && current.getName().equals(end))) {
                gMap.addPolygon(new PolygonOptions().addAll(current.getCoOrds()).strokeColor(Color.RED).fillColor(Color.MAGENTA).geodesic(true).clickable(true));
            } else {
                gMap.addPolygon(new PolygonOptions().addAll(current.getCoOrds()).strokeColor(Color.BLUE).fillColor(Color.CYAN).geodesic(true).clickable(true));
            }
        }
        if (routeSelected == null) {
            return;
        }
        routeDisplay = new ArrayList<>();
        for (String nextStep : routeSelected) {
            for (MidPoint mp : midPoints) {
                if (mp.getName().equals(nextStep)) {
                    routeDisplay.add(mp);
                    break;
                }
            }
        }
        if (routeDisplay.size() > 1) {
            for (int i = 1; i < routeDisplay.size(); i++) {
                drawLine(routeDisplay.get(i - 1), routeDisplay.get(i));
            }
        }
    }

    private void drawLine(MidPoint one, MidPoint two) {
        OutdoorDirectionsFinder odf = new OutdoorDirectionsFinder(one, two);
        odf.execute();
        while (!odf.isDone()) ;
        List<LatLng[]> route = odf.getRoute();
        for (int i = 0; i < route.size(); i++) {
            gMap.addPolyline(new PolylineOptions().add(route.get(i)[0], route.get(i)[1]).width(5).color(Color.RED));
        }
    }


}
