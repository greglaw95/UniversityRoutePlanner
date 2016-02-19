package com.solutions.law.universityrouteplanner.View;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
    private ErrorMessageDialogFragment errorReporter;
    private FragmentManager supportFragmentManager;

    public View(IController control, List<EndPoint> endPoints, List<MidPoint> midPoints, EditText startPoint, EditText endPoint, Button directionsButton,Button inOutButton,FragmentManager fragmentManager) {
        this.endPoints = endPoints;
        this.midPoints = midPoints;
        this.controller = control;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.supportFragmentManager=fragmentManager;
        errorReporter=new ErrorMessageDialogFragment();
        errorReporter.setController(controller);
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
        inOutButton.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(android.view.View v, MotionEvent event) {
                controller.goInside();
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
            updateMap(state.getStartLoc(),state.getEndLoc(),state.getRouteSelected(),state);
            updateError(state.getError());
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
                updateMap(state.getStartLoc(), state.getEndLoc(), state.getRouteSelected(),state);
            }
            if(prevState.getError()==null||!prevState.getError().equals(state.getError())){
                updateError(state.getError());
            }
        }
        prevState = state;
    }

    private void updateText(EditText box, String newText) {
        if (newText != null) {
            box.setText(newText.toCharArray(), 0, newText.length());
        }
    }

    private void updateMap(String start, String end, List<String> routeSelected,RoutePlannerState state) {
        List<MidPoint> routeDisplay;
        gMap.clear();
        if(state.getPlane().equals("Outside")){
            gMap.setBuildingsEnabled(false);
            gMap.setIndoorEnabled(false);
            gMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        }else{
            gMap.setBuildingsEnabled(true);
            gMap.setIndoorEnabled(true);
            gMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        }
        for (EndPoint current : endPoints) {
            if(current.getPlane().equals(state.getPlane())) {
                if(gMap.getCameraPosition().zoom>17) {
                    drawName(current);
                }
                if ((start != null && current.getName().equals(start)) || (end != null && current.getName().equals(end))) {
                    gMap.addPolygon(new PolygonOptions().addAll(current.getCoOrds()).strokeColor(Color.RED).fillColor(Color.MAGENTA).geodesic(true).clickable(true));
                } else {
                    gMap.addPolygon(new PolygonOptions().addAll(current.getCoOrds()).strokeColor(Color.BLUE).fillColor(Color.CYAN).geodesic(true).clickable(true));
                }
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
                if(routeDisplay.get(i-1).getPlane().equals(state.getPlane())&&routeDisplay.get(i).getPlane().equals(state.getPlane())) {
                    drawLine(routeDisplay.get(i - 1), routeDisplay.get(i));
                }else if(routeDisplay.get(i-1).getPlane().equals(state.getPlane())){
                    //TODO add marker to let user move to routeDisplay(i) plane maybe make it green
                }else if(routeDisplay.get(i).getPlane().equals(state.getPlane())){
                    //TODO add marker to let user move to routeDisplay(i-1) plane maybe make it red
                }
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

    private void drawName(EndPoint location){
        Paint paint = new Paint();
        paint.setTextSize(28);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(location.getName()) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(location.getName(), 0, baseline, paint);
        gMap.addMarker(new MarkerOptions()
                .position(location.getCentre())
                .icon(BitmapDescriptorFactory.fromBitmap(image)));
    }

    private void updateError(String error){
        if(error!=null) {
            errorReporter.setMessage(error);
            errorReporter.show(supportFragmentManager, "Error");
        }
    }

}
