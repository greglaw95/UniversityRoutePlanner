package com.solutions.law.universityrouteplanner.Model.Update;

import com.google.android.gms.maps.model.CameraPosition;

import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class ModelState implements RoutePlannerState {
    private String startLoc;
    private String endLoc;
    private List<String> routeSelected;
    private String error;
    private String plane;
    private CameraPosition position;

    public ModelState(String startLoc,String endLoc,List<String> routeSelected,String error,String plane,CameraPosition position){
        this.startLoc=startLoc;
        this.endLoc=endLoc;
        this.routeSelected=routeSelected;
        this.error=error;
        this.plane=plane;
        this.position=position;
    }

    @Override
    public CameraPosition getPosition() {
        return position;
    }

    @Override
    public String getError(){
        return error;
    }

    public String getStartLoc(){
        return startLoc;
    }

    public String getEndLoc(){
        return endLoc;
    }

    public List<String> getRouteSelected(){
        return routeSelected;
    }

    public String getPlane(){
        return plane;
    }
}
