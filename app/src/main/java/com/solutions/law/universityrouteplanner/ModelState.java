package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.model.CameraPosition;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class ModelState implements RoutePlannerState {
    CameraPosition position;
    public ModelState(CameraPosition position){
        this.position=position;
    }

    public CameraPosition getLocation(){
        return position;
    }
}
