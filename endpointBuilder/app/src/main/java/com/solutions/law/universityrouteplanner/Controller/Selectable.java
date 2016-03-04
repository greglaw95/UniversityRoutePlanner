package com.solutions.law.universityrouteplanner.Controller;

import com.google.android.gms.maps.model.Polygon;

/**
 * Created by kbb12 on 17/02/2016.
 */
public interface Selectable {
    public boolean sameShape(Polygon p);
    public String getName();
}
