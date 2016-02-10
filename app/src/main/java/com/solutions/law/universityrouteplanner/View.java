package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class View implements OnMapReadyCallback,RoutePlannerListener {

    IController controller;
    GoogleMap gMap;
    RoutePlannerState prevState;

    public View(IController controller){
        this.controller=controller;
        prevState=null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.setOnCameraChangeListener(controller);
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
