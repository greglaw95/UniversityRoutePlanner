package com.solutions.law.universityrouteplanner.View;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.solutions.law.universityrouteplanner.Controller.IController;
import com.solutions.law.universityrouteplanner.Model.Update.RoutePlannerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class View implements OnMapReadyCallback, RoutePlannerListener {

    private final IController controller;
    private List<MidPoint> midPoints;
    private Map<String,List<EndPoint>> endPointByPlane;
    private GoogleMap gMap;
    private AutoCompleteTextView startPoint;
    private AutoCompleteTextView endPoint;
    private RoutePlannerState prevState;
    private ErrorMessageDialogFragment errorReporter;
    private FragmentManager supportFragmentManager;

    public View(IController control, List<EndPoint> endPoints, List<MidPoint> midPoints, AutoCompleteTextView startPoint, AutoCompleteTextView endPoint, Button directionsButton,Button inOutButton,FragmentManager fragmentManager,Activity context) {
        this.midPoints = midPoints;
        this.controller = control;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.supportFragmentManager=fragmentManager;
        errorReporter=new ErrorMessageDialogFragment();
        errorReporter.setController(controller);
        String[] options = new String[endPoints.size()];
        for(int i=0;i<endPoints.size();i++){
            options[i]=endPoints.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, options);
        this.startPoint.setAdapter(adapter);
        this.endPoint.setAdapter(adapter);
        this.startPoint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                controller.setStart(s.toString());
            }
        });
        this.startPoint.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(android.view.View v, MotionEvent event) {
                controller.focusOn(IController.Location.START);
                return false;
            }
        });
        this.endPoint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                controller.setEnd(s.toString());
            }
        });
        this.endPoint.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(android.view.View v, MotionEvent event) {
                controller.focusOn(IController.Location.END);
                return false;
            }
        });
        directionsButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                controller.route();
            }
        });
        inOutButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                controller.goInside();
            }
        });
        prevState = null;
        setUpEndpointByPlane(endPoints);
    }


    private void setUpEndpointByPlane(List<EndPoint> endPoints){
        endPointByPlane= new HashMap<>();
        for(EndPoint current:endPoints){
            if(!endPointByPlane.containsKey(current.getPlane())){
                endPointByPlane.put(current.getPlane(),new ArrayList<EndPoint>());
            }
            endPointByPlane.get(current.getPlane()).add(current);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.setBuildingsEnabled(true);
        gMap.setIndoorEnabled(false);
        LatLng centre = new LatLng(55.861903,-4.244082);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centre, 16));
        gMap.setOnIndoorStateChangeListener(new GoogleMap.OnIndoorStateChangeListener() {
            @Override
            public void onIndoorBuildingFocused() {
                try {
                    gMap.getFocusedBuilding().getLevels().get(gMap.getFocusedBuilding().getLevels().size() - Integer.parseInt(prevState.getLevel())).activate();
                } catch (Exception e) {
                    //Wrong building focused but this doesn't matter it will be the right one next time around. It's just google maps panning over other buildings as it moves
                    //to the selected building.
                }
            }

            @Override
            public void onIndoorLevelActivated(IndoorBuilding indoorBuilding) {
                    controller.setLevel(Integer.toString(indoorBuilding.getLevels().size() - indoorBuilding.getActiveLevelIndex()));
            }
        });
        gMap.setOnMarkerClickListener(controller);
        gMap.setOnPolygonClickListener(controller);
        gMap.setOnCameraChangeListener(controller);
        controller.startUp();
    }

    @Override
    public void update(RoutePlannerState state) {
        if (prevState!=null) {
            Boolean startDifferent=(prevState.getStartLoc()==null||!prevState.getStartLoc().equals(state.getStartLoc()));
            Boolean endDifferent=(prevState.getEndLoc()==null||!prevState.getEndLoc().equals(state.getEndLoc()));
            Boolean newRoute=prevState.getRouteSelected()==null || !prevState.getRouteSelected().equals(state.getRouteSelected());
            Boolean differentPlane=prevState.getPlane()==null || !prevState.getPlane().equals(state.getPlane());
            Boolean newPosition=(prevState.getPosition()==null||!prevState.getPosition().equals(state.getPosition()));
            Boolean newError=prevState.getError()==null||!prevState.getError().equals(state.getError());
            if (startDifferent) {
                updateText(startPoint, state.getStartLoc());
            }
            if (endDifferent) {
                updateText(endPoint, state.getEndLoc());
            }
            if (startDifferent || endDifferent || newRoute || differentPlane) {
                updateMapAdditions(state.getStartLoc(), state.getEndLoc(), state.getRouteSelected(),state.getPlane());
            }
            if((state.getPosition()!=null)&&(newPosition||differentPlane)){
                updateMap(state.getPosition(),state.getPlane(),state.getLevel());
            }
            if(newError){
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

    private void updateMap(CameraPosition position,String plane,String level){
        if(plane.equals("Outside")){
            gMap.getUiSettings().setIndoorLevelPickerEnabled(false);
            gMap.setIndoorEnabled(false);
        }else{
            gMap.getUiSettings().setIndoorLevelPickerEnabled(true);
            gMap.setIndoorEnabled(true);
        }
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        if(gMap.getFocusedBuilding()!=null) {
            try {
                gMap.getFocusedBuilding().getLevels().get(gMap.getFocusedBuilding().getLevels().size() - Integer.parseInt(level)).activate();
            } catch (Exception e) {

            }
        }
    }

    private void updateMapAdditions(String start, String end, List<String> routeSelected,String plane) {
        List<MidPoint> routeDisplay;
        gMap.clear();
        if(endPointByPlane.get(plane)!=null) {
            for (EndPoint current : endPointByPlane.get(plane)) {
                if ((start != null && current.getName().equals(start)) || (end != null && current.getName().equals(end))) {
                    gMap.addPolygon(new PolygonOptions().addAll(current.getCoOrds()).strokeColor(Color.RED).geodesic(true).clickable(true));
                } else {
                    gMap.addPolygon(new PolygonOptions().addAll(current.getCoOrds()).strokeColor(Color.BLUE).geodesic(true).clickable(true));
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
                if(routeDisplay.get(i-1).getPlane().equals(plane)&&routeDisplay.get(i).getPlane().equals(plane)) {
                    drawLine(routeDisplay.get(i - 1), routeDisplay.get(i));
                }else if(routeDisplay.get(i-1).getPlane().equals(plane)){
                    gMap.addMarker(new MarkerOptions().position(routeDisplay.get(i-1).getCoOrd()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(routeDisplay.get(i).getPlane()));
                }else if(routeDisplay.get(i).getPlane().equals(plane)){
                    gMap.addMarker(new MarkerOptions().position(routeDisplay.get(i).getCoOrd()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title(routeDisplay.get(i-1).getPlane()));
                }
            }
        }
    }

    private void drawLine(MidPoint one, MidPoint two) {
        gMap.addPolyline(new PolylineOptions().add(one.getCoOrd(),two.getCoOrd()).width(5).color(Color.RED));
    }

    private void updateError(String error){
        if(error!=null) {
            errorReporter.setMessage(error);
            errorReporter.show(supportFragmentManager, "Error");
        }
    }

}
