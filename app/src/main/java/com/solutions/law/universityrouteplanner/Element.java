package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.model.LatLng;

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

    public String getName(){
        return name;
    }

    public List<LatLng> getCoOrds(){
        return coOrds;
    }
}
