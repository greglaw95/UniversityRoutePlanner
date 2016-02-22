package com.solutions.law.universityrouteplanner.Model;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.solutions.law.universityrouteplanner.View.RoutePlannerListener;

/**
 * Created by kbb12155 on 10/02/16.
 */
public interface IModel {
    public void addListener(RoutePlannerListener listener);
    public void currentPlane(String item);
    public void currentRoom(String item);
    public void select();
    public void clear();
    public void addPoint(LatLng newPoint);
}
