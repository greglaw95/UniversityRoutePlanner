package com.solutions.law.universityrouteplanner.Controller;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by kbb12 on 10/02/2016.
 */
public interface IController extends GoogleMap.OnPolygonClickListener,GoogleMap.OnCameraChangeListener {
    public enum Location{
        START,END
    }

    public void errorAccepted();
    
    public void focusOn(Location location);

    public void route();

    public void setLevel(int level);

    public void goInside();

    public void startUp();
}
