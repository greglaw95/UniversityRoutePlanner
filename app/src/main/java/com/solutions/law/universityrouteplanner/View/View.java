package com.solutions.law.universityrouteplanner.View;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.IndoorBuilding;
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
    private List<NodeThing> points;
    private GoogleMap gMap;
    private EditText startPoint;
    private EditText endPoint;
    private EditText weight;
    private RoutePlannerState prevState;

    public View(IController control, List<NodeThing> points,EditText startPoint, EditText endPoint, EditText weight, Button addButton,Button inOut) {
        this.points = points;
        this.controller = control;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.weight=weight;
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
        this.weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                controller.weight(s.toString());
            }
        });
        addButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                controller.add();
            }
        });
        inOut.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                controller.goInside();
            }
        });
        prevState = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.setBuildingsEnabled(false);
        gMap.setOnMarkerClickListener(controller);
        gMap.setOnIndoorStateChangeListener(new GoogleMap.OnIndoorStateChangeListener() {
            @Override
            public void onIndoorBuildingFocused() {

            }

            @Override
            public void onIndoorLevelActivated(IndoorBuilding indoorBuilding) {
                controller.setLevel(indoorBuilding.getActiveLevelIndex());
            }
        });
        gMap.setOnPolygonClickListener(controller);
        controller.startUp();
    }

    @Override
    public void update(RoutePlannerState state) {
        Boolean earlyChange = false;
        if (prevState == null) {
            updateText(startPoint, state.getStartLoc());
            updateText(endPoint, state.getEndLoc());
            updateMap(state.getStartLoc(), state.getEndLoc(), state.getOtherConnections(), state.getPlane());
        } else {
            if (prevState.getStartLoc()==null||!prevState.getStartLoc().equals(state.getStartLoc())) {
                updateText(startPoint, state.getStartLoc());
                earlyChange = true;
            }
            if (prevState.getEndLoc()==null||!prevState.getEndLoc().equals(state.getEndLoc())) {
                updateText(endPoint, state.getEndLoc());
                earlyChange = true;
            }
            if (earlyChange || prevState.getOtherConnections()==null || !prevState.getOtherConnections().equals(state.getOtherConnections()) ||prevState.getPlane()==null||!prevState.getPlane().equals(state.getPlane())) {
                updateMap(state.getStartLoc(), state.getEndLoc(), state.getOtherConnections(), state.getPlane());
            }
        }
        prevState = state;
    }

    private void updateText(EditText box, String newText) {
        if (newText != null) {
            box.setText(newText.toCharArray(), 0, newText.length());
        }
    }

    private void updateMap(String selectedOne, String selectedTwo, List<String> otherConnections,String plane) {
        NodeThing centre=null;
        List<NodeThing> others = new ArrayList<>();
        gMap.clear();
        gMap.setOnPolygonClickListener(controller);
        if(plane.equals("Outside")){
            gMap.setBuildingsEnabled(false);
            gMap.setIndoorEnabled(false);
            gMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        }else{
            gMap.setBuildingsEnabled(true);
            gMap.setIndoorEnabled(true);
            gMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        }
        for (NodeThing current : points) {
            if(current.getPlane().equals(plane)) {
                if ((selectedOne != null && current.getName().equals(selectedOne))){
                    centre=current;
                    current.draw(gMap,Color.BLUE);
                }else if (selectedTwo != null && current.getName().equals(selectedTwo)) {
                    current.draw(gMap,Color.BLUE);
                    possibleAdd(current,others,otherConnections);
                } else {
                    current.draw(gMap,Color.RED);
                    possibleAdd(current,others,otherConnections);
                }
            }
        }
        if(centre==null){
            return;
        }
        for(NodeThing current:others){
            gMap.addPolyline(new PolylineOptions().add(centre.getPoint()).add(current.getPoint()));
        }
    }

    public void possibleAdd(NodeThing pos,List<NodeThing> addTo,List<String> criteria){
        if(!criteria.contains(pos.getName())){
            return;
        }
        addTo.add(pos);
    }

}
