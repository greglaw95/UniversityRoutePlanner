package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.GoogleMap;
import android.view.View.OnTouchListener;

/**
 * Created by kbb12 on 10/02/2016.
 */
public interface IController extends GoogleMap.OnCameraChangeListener,GoogleMap.OnPolygonClickListener {
    public enum Location{
        START,END
    }

    public void focusOn(Location location);
}
