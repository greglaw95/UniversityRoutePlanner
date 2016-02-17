package com.solutions.law.universityrouteplanner.StartUp;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.solutions.law.universityrouteplanner.Controller.Selectable;
import com.solutions.law.universityrouteplanner.View.EndPoint;

import java.util.List;

/**
 * Created by kbb12 on 10/02/2016.
 */
public class SelectableEndPoint implements Selectable,EndPoint {
    private String name;
    private List<LatLng> coOrds;

    public SelectableEndPoint(String name, List<LatLng> coOrds){
        this.name=name;
        this.coOrds=coOrds;
    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof SelectableEndPoint)){
            return false;
        }
        SelectableEndPoint otherElement = (SelectableEndPoint) other;
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
