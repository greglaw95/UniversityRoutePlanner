package com.solutions.law.universityrouteplanner;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class View implements OnMapReadyCallback,RoutePlannerListener {

    IController controller;
    List<Element> elements;
    GoogleMap gMap;
    RoutePlannerState prevState;

    public View(IController controller,List<Element> elements){
        this.elements=elements;
        this.controller=controller;
        prevState=null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.setOnCameraChangeListener(controller);
        for(Element current:elements){
            gMap.addPolygon(new PolygonOptions().addAll(current.getCoOrds()).strokeColor(Color.BLUE).fillColor(Color.CYAN));
        }
    }

    @Override
    public void update(RoutePlannerState state){
        if(prevState==null){
            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(state.getLocation()));
        }else{
            if(prevState.getLocation()!=state.getLocation()){
                gMap.animateCamera(CameraUpdateFactory.newCameraPosition(state.getLocation()));
            }
        }
        prevState=state;
    }
}
