package com.solutions.law.universityrouteplanner.Model;

import com.google.android.gms.maps.model.LatLng;
import java.util.List;

/**
 * Created by kbb12 on 19/02/2016.
 */
public class RoutePlannerState {
    private String room;
    private String plane;
    private List<Midpoint> points;
    private Midpoint newPoint;

    public RoutePlannerState(String room,String plane,List<Midpoint> points,Midpoint newPoint){
        this.room=room;
        this.plane=plane;
        this.points=points;
        this.newPoint=newPoint;
    }

    public String getPlane(){
        return plane;
    }

    public String getRoom(){
        return room;
    }

    public List<Midpoint> getPoints(){
        return points;
    }

    public Midpoint getNewPoint(){
        return newPoint;
    }
}
