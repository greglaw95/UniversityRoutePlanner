package com.solutions.law.universityrouteplanner.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kbb12 on 25/02/2016.
 */
public interface NodeThing {
    public String getPlane();

    public String getName();

    public void draw(GoogleMap gMap, int colour);

    public LatLng getPoint();
}