package com.solutions.law.universityrouteplanner.StartUp;

import com.google.android.gms.maps.model.LatLng;
import com.solutions.law.universityrouteplanner.View.MidPoint;

/**
 * Created by kbb12 on 15/02/2016.
 */
public class SteppingStone implements MidPoint {
    private String name;
    private LatLng coOrd;
    private String plane;

    public SteppingStone(String name,String plane,LatLng coOrd){
        this.name=name;
        this.coOrd=coOrd;
        this.plane=plane;
    }

    public String getPlane(){
        return plane;
    }

    public String getName() {
        return name;
    }

    public LatLng getCoOrd() {
        return coOrd;
    }

}
