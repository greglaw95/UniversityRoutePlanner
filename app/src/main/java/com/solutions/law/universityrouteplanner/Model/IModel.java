package com.solutions.law.universityrouteplanner.Model;

import com.google.android.gms.maps.model.CameraPosition;
import com.solutions.law.universityrouteplanner.View.RoutePlannerListener;

/**
 * Created by kbb12155 on 10/02/16.
 */
public interface IModel {
    public void start();
    public void addListener(RoutePlannerListener listener);
    public void startLoc(String item);
    public void endLoc(String item);
    public void newRoute();
    public void setError(String error);
    public void setPlane(String plane);
    public String getPlane();
    public String getStart();
    public String getEnd();
}
