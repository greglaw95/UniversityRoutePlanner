package com.solutions.law.universityrouteplanner.View.Adapters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.solutions.law.universityrouteplanner.Controller.IController;
import com.solutions.law.universityrouteplanner.View.View;

/**
 * Created by kbb12 on 11/03/2016.
 */
public class MarkerClickAdapter implements GoogleMap.OnMarkerClickListener {
    private View view;

    public MarkerClickAdapter(View view){
        this.view=view;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String fullPlane = marker.getTitle();
        view.setPlane(fullPlane);
        return false;
    }

}
