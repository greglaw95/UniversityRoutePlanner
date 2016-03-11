package com.solutions.law.universityrouteplanner.View.Adapters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polygon;
import com.solutions.law.universityrouteplanner.Controller.IController;
import com.solutions.law.universityrouteplanner.View.EndPoint;

import java.util.List;

/**
 * Created by kbb12 on 11/03/2016.
 */
public class PolygonClickAdapter implements GoogleMap.OnPolygonClickListener {
    private List<EndPoint> endPoints;
    private IController controller;

    public PolygonClickAdapter(List<EndPoint> endPoints,IController controller){
        this.endPoints=endPoints;
        this.controller=controller;
    }

    @Override
    public void onPolygonClick(Polygon polygon) {
        for(EndPoint current:endPoints){
            if(current.sameShape(polygon)){
                controller.areaSelected(current.getName());
                return;
            }
        }
    }
}
