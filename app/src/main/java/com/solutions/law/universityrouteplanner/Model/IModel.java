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
    public void setPlane(String plane);
    public void addLink();
    public void setWeight(Double weight);
    public String getStart();
    public String getEnd();
    public String getPlane();
}
