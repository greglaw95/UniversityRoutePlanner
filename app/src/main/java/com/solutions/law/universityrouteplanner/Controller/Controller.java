package com.solutions.law.universityrouteplanner.Controller;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.solutions.law.universityrouteplanner.Model.IModel;


import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class Controller implements IController {

    private IModel model;

    public Controller(IModel model){
        this.model=model;
    }

    @Override
    public void setRoom(String room){
        model.currentRoom(room);
    }

    @Override
    public void setPlane(String newPlane) {
        model.currentPlane(newPlane);
    }

    @Override
    public void select(){
        model.select();
    }

    @Override
    public void clear(){
        model.clear();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        model.selectedPoint(marker.getTitle(),marker.getPosition());
        return true;
    }

    @Override
    public void addPoint(LatLng newPoint){
        model.addPoint(newPoint);
    }
}
