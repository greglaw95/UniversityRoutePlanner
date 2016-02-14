package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;

import java.util.List;

/**
 * Created by kbb12 on 10/02/2016.
 */
public class Element {
    private String name;
    private List<LatLng> coOrds;

    public Element(String name,List<LatLng> coOrds){
        this.name=name;
        this.coOrds=coOrds;
    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof Element)){
            return false;
        }
        Element otherElement = (Element) other;
        //The name of each element should be unique this
        //is a precondition on which the system is built
        //so it can be assumed to be true.
        return this.name.equals(otherElement.getName());
    }

    public String getName(){
        return name;
    }

    public List<LatLng> getCoOrds(){
        return coOrds;
    }

    public boolean sameShape(Polygon p){
        for(LatLng latLng:coOrds){
            if(!p.getPoints().contains(latLng)){
                return false;
            }
        }
        return true;
    }
}
