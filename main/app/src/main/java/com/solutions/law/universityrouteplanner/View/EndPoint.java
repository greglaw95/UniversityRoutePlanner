package com.solutions.law.universityrouteplanner.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;

import java.util.List;

/**
 * Created by kbb12 on 17/02/2016.
 */
public interface EndPoint {
    public String getName();
    public List<LatLng> getCoOrds();
    public String getPlane();
    public LatLng getCentre();
    public boolean sameShape(Polygon p);
}
