package com.solutions.law.universityrouteplanner.StartUp;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by kbb12 on 15/02/2016.
 */
public class SteppingStone {
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

    public LatLng getPoint() {
        return coOrd;
    }

    public void draw(GoogleMap gMap,int colour){
        float finalColour;
        if(colour== Color.BLUE){
            finalColour=BitmapDescriptorFactory.HUE_BLUE;
        }else{
            finalColour=BitmapDescriptorFactory.HUE_RED;
        }
        gMap.addMarker(new MarkerOptions().position(getPoint()).icon(BitmapDescriptorFactory.defaultMarker(finalColour)));
    }

}
