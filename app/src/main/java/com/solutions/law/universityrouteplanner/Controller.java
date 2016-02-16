package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;

import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class Controller implements IController {

    IModel model;
    List<EndPoint> elements;
    Location location;

    public Controller(IModel model,List<EndPoint> elements){
        this.model=model;
        this.elements=elements;
        location=Location.START;
    }

    @Override
    public void focusOn(Location location){
        this.location=location;
    }

    @Override
    public void route(){
        int i=5;
        int j=6;
        i=i+j;
        j=j+i;
    }

    @Override
    public void onCameraChange(CameraPosition position){
        //Instead of animating each time build new cameraPosition then animate to there
        //this will stop you missing it if they zoom and move out of bounds.
        CameraPosition newPosition;
        LatLng newLatLng =position.target;
        float newZoom=position.zoom;
        if(position.zoom<14) {
            newZoom=14;
        }
        if(position.target.longitude<-4.250000){
            newLatLng=new LatLng(newLatLng.latitude,-4.250000);
        }
        if(position.target.longitude>-4.235812){
            newLatLng=new LatLng(newLatLng.latitude,-4.235812);
        }
        if(position.target.latitude<55.859147){
            newLatLng=new LatLng(55.859147,newLatLng.longitude);
        }
        if(position.target.latitude>55.864932){
            newLatLng=new LatLng(55.864932,newLatLng.longitude);
        }
        newPosition = new CameraPosition(newLatLng,newZoom,position.tilt,position.bearing);
        model.moveTo(newPosition);
    }


    @Override
    public void onPolygonClick(Polygon clicked){
        for(EndPoint element:elements){
            if(element.sameShape(clicked)){
                if(location==Location.START) {
                    model.startLoc(element.getName());
                }else{
                    model.endLoc(element.getName());
                }
                location=Location.END;
                return;
            }
        }
    }

}
