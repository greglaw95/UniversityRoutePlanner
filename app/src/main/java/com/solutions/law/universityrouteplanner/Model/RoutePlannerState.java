package com.solutions.law.universityrouteplanner.Model;

import com.google.android.gms.maps.model.LatLng;
import com.solutions.law.universityrouteplanner.View.RoutePlannerListener;

import java.util.List;

/**
 * Created by kbb12 on 19/02/2016.
 */
public class RoutePlannerState {
    private String room;
    private String plane;
    private List<LatLng> points;

    public RoutePlannerState(String room,String plane,List<LatLng> points){
        this.room=room;
        this.plane=plane;
        this.points=points;
    }

    public String getPlane(){
        return plane;
    }

    public String getRoom(){
        return room;
    }

    public List<LatLng> getPoints(){
        return points;
    }
}
