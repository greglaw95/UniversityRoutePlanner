package com.solutions.law.universityrouteplanner.Controller;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by kbb12 on 10/02/2016.
 */
public interface IController extends GoogleMap.OnPolygonClickListener {
    public enum Location{
        START,END
    }
    public void focusOn(Location location);

    public void add();

    public void weight(String newWeight);

    public void switchPlane(String plane);

    public void goInside();

    public void startUp();
}
