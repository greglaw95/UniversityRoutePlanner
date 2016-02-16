package com.solutions.law.universityrouteplanner;

import android.graphics.Color;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class View implements OnMapReadyCallback,RoutePlannerListener {

    final IController controller;
    List<EndPoint> endPoints;
    List<SteppingStone> steppingStones;
    GoogleMap gMap;
    EditText startPoint;
    EditText endPoint;
    Button directionsButton;
    RoutePlannerState prevState;
    Polygon p;

    public View(IController control,List<EndPoint> endPoints,List<SteppingStone> steppingStones,EditText startPoint,EditText endPoint,Button directionsButton){
        this.endPoints = endPoints;
        this.steppingStones=steppingStones;
        this.controller=control;
        this.startPoint=startPoint;
        this.endPoint=endPoint;
        this.directionsButton=directionsButton;
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
        this.directionsButton.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(android.view.View v, MotionEvent event) {
                controller.route();
                return false;
            }
        });
        prevState=null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.setOnCameraChangeListener(controller);
        for(EndPoint current: endPoints){
            gMap.addPolygon(new PolygonOptions().addAll(current.getCoOrds()).strokeColor(Color.BLUE).fillColor(Color.CYAN).geodesic(true).clickable(true));
        }
        gMap.setBuildingsEnabled(false);
        gMap.setOnPolygonClickListener(controller);
    }

    @Override
    public void update(RoutePlannerState state){
        if(prevState==null){
            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(state.getLocation()));
        }else{
            if(!(prevState.getEndLoc()==state.getEndLoc()&&prevState.getStartLoc()==state.getStartLoc())){
                gMap.clear();
                gMap.setBuildingsEnabled(false);
                gMap.animateCamera(CameraUpdateFactory.newCameraPosition(state.getLocation()));
                for(EndPoint current: endPoints){
                    if(current.getName().equals(state.getEndLoc())||current.getName().equals(state.getStartLoc())){
                        gMap.addPolygon(new PolygonOptions().addAll(current.getCoOrds()).strokeColor(Color.RED).fillColor(Color.MAGENTA).geodesic(true).clickable(true));
                    }else {
                        gMap.addPolygon(new PolygonOptions().addAll(current.getCoOrds()).strokeColor(Color.BLUE).fillColor(Color.CYAN).geodesic(true).clickable(true));
                    }
                }
            }
            if(state.getStartLoc()!=null){
                startPoint.setText(state.getStartLoc().toCharArray(),0,state.getStartLoc().length());
            }
            if(state.getEndLoc()!=null){
                endPoint.setText(state.getEndLoc().toCharArray(),0,state.getEndLoc().length());
            }
        }
        prevState=state;
    }

    private boolean equalLists(List<String> listOne,List<String> listTwo){
        for(String elementOne:listOne){
            if(!listTwo.contains(elementOne)){
                return false;
            }
        }
        for(String elementTwo:listTwo){
            if(!listOne.contains(elementTwo)){
                return false;
            }
        }
        return true;
    }
}
