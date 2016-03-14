package com.solutions.law.universityrouteplanner.Controller;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;

/**
 * Created by kbb12 on 10/02/2016.
 */
public interface IController {
    public enum Location{
        START,END
    }

    public void errorAccepted();
    
    public void focusOn(Location location);

    public void route();

    public void setLevel(String level);

    public void goInside();

    public void startUp();

    public void setStart(String newStart);

    public void setEnd(String newEnd);

    public void areaSelected(String areaName);

    public void setStructure(String structure);

    public Structure getStructure();

    public void useStairs(boolean stairUse);

    public void useLifts(boolean liftUse);
}
