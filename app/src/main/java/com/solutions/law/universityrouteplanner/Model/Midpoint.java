package com.solutions.law.universityrouteplanner.Model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kbb12 on 25/02/2016.
 */
public class Midpoint {
    private String room;
    private String plane;
    private LatLng point;

    public Midpoint(String room,String plane,LatLng point){
        this.room=room;
        this.plane=plane;
        this.point=point;
    }

    public LatLng getPoint() {
        return point;
    }

    public String getPlane() {
        return plane;
    }

    public String getRoom() {
        return room;
    }

    public Midpoint copy(){
        return new Midpoint(room,plane,new LatLng(point.latitude,point.longitude));
    }
}
