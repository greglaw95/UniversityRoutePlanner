package com.solutions.law.universityrouteplanner.Model.Update;

import com.google.android.gms.maps.model.CameraPosition;

import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public interface RoutePlannerState {
    public String getStartLoc();
    public String getEndLoc();
    public List<String> getRouteSelected();
    public String getError();
    public String getPlane();
    public String getLevel();
    public String getFocusOn();
}
