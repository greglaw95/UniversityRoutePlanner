package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.model.CameraPosition;

import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class ModelState implements RoutePlannerState {
    CameraPosition position;
    List<String> userSelected;
    String startLoc;
    String endLoc;

    public ModelState(CameraPosition position,String startLoc,String endLoc){
        this.position=position;
        this.startLoc=startLoc;
        this.endLoc=endLoc;
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
}
