package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.model.CameraPosition;

import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public interface RoutePlannerState {
    public CameraPosition getLocation();
    public String getStartLoc();
    public String getEndLoc();
    public List<String> getRouteSelected();
}
