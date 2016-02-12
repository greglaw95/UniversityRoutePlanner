package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.model.CameraPosition;

import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class ModelState implements RoutePlannerState {
    CameraPosition position;
    List<String> userSelected;

    public ModelState(CameraPosition position,List<String> userSelected){
        this.position=position;
        this.userSelected=userSelected;
    }

    public CameraPosition getLocation(){
        return position;
    }

    public List<String> getUserSelected(){
        return userSelected;
    }
}
