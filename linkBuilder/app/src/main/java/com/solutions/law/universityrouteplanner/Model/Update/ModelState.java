package com.solutions.law.universityrouteplanner.Model.Update;

import com.google.android.gms.maps.model.CameraPosition;

import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class ModelState implements RoutePlannerState {
    private String startLoc;
    private String endLoc;
    private List<String> otherConnections;
    private String error;
    private String plane;
    private CameraPosition position;
    private Double weight;

    @Override
    public Double getWeight() {
        return weight;
    }

    public ModelState(String startLoc,String endLoc,List<String> otherConnections,String error,String plane,CameraPosition position,Double weight){
        this.startLoc=startLoc;
        this.endLoc=endLoc;
        this.otherConnections =otherConnections;
        this.error=error;
        this.plane=plane;
        this.position=position;
        this.weight=weight;
    }

    public CameraPosition getPosition() {
        return position;
    }

    public String getStartLoc(){
        return startLoc;
    }

    public String getEndLoc(){
        return endLoc;
    }

    public List<String> getOtherConnections(){
        return otherConnections;
    }

    public String getPlane(){
        return plane;
    }
}
