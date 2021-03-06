package com.solutions.law.universityrouteplanner.Model.Update;

import com.google.android.gms.maps.model.CameraPosition;

import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public interface RoutePlannerState {
    public String getStartLoc();
    public String getEndLoc();
    public List<String> getOtherConnections();
    public String getPlane();
    public CameraPosition getPosition();
    public Double getWeight();
}
