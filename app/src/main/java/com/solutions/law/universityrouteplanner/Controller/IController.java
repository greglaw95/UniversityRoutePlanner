package com.solutions.law.universityrouteplanner.Controller;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kbb12 on 10/02/2016.
 */
public interface IController extends GoogleMap.OnMarkerClickListener  {
    public void setPlane(String newPlane);
    public void setRoom(String newRoom);
    public void select();
    public void clear();
    public void addPoint(LatLng point);
}
