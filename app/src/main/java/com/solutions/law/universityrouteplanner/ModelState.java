package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.model.CameraPosition;

import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class ModelState implements RoutePlannerState {
    CameraPosition position;
    String startLoc;
    String endLoc;
    List<String> routeSelected;

    public ModelState(CameraPosition position,String startLoc,String endLoc,List<String> routeSelected){
        this.position=position;
        this.startLoc=startLoc;
        this.endLoc=endLoc;
        this.routeSelected=routeSelected;
    }

    public CameraPosition getLocation(){
        return position;
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
}
