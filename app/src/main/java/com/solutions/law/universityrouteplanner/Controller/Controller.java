package com.solutions.law.universityrouteplanner.Controller;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;
import com.solutions.law.universityrouteplanner.Model.IModel;


import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class Controller implements IController {

    private IModel model;
    private List<Selectable> elements;
    private Location location;
    private List<Structure> structures;
    private Structure currentStructure;

    public Controller(IModel model,List<Selectable> elements,List<Structure> structures){
        this.model=model;
        this.elements=elements;
        location=Location.START;
        this.structures=structures;
        currentStructure=null;
    }

    @Override
    public void goInside() {
        if (currentStructure!=null) {
            currentStructure=null;
            model.setPlane("Outside");
        }else{
            if(location==Location.START){
                currentStructure=findStructure(model.getStart());
            }else{
                currentStructure=findStructure(model.getEnd());
            }
            model.setPlane(currentStructure.getName() + currentStructure.getLevel());
        }
        onCameraChange(model.getPosition());
    }

    @Override
    public void setLevel(int level){
        currentStructure.setLevel(level);
        model.setPlane(currentStructure.getName()+level);
    }

    @Override
    public void errorAccepted(){
        model.setError(null);
    }

    @Override
    public void focusOn(Location location){
        this.location=location;
    }

    @Override
    public void route(){
        model.newRoute();
    }

    @Override
    public void onPolygonClick(Polygon clicked){
        for(Selectable element:elements){
            if(element.sameShape(clicked)){
                if(location==Location.START) {
                    model.startLoc(element.getName());
                }else{
                    model.endLoc(element.getName());
                }
                return;
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        model.setPlane(marker.getTitle());
        return true;
    }

    @Override
    public void startUp(){
        model.start();
    }

    @Override
    public void onCameraChange(CameraPosition position) {
        if(currentStructure!=null){
            CameraPosition newPosition;
            float newZoom=position.zoom;
            Double newLng=position.target.longitude;
            Double newLat=position.target.latitude;
            if(position.zoom<currentStructure.getMinZoomAllowed()){
                newZoom=currentStructure.getMinZoomAllowed();
            }
            double halfRange=0.5*(360/(Math.pow(2,newZoom)));
            if(position.target.longitude<currentStructure.getMinLngAllowed()+halfRange){
                newLng=currentStructure.getMinLngAllowed()+halfRange;
            }
            if(position.target.longitude>currentStructure.getMaxLngAllowed()-halfRange){
                if(newLng.equals(currentStructure.getMinLngAllowed())){
                    newLng=position.target.longitude;
                }else {
                    newLng = currentStructure.getMaxLngAllowed()-halfRange;
                }
            }
            if(position.target.latitude<currentStructure.getMinLatAllowed()+halfRange){
                newLat=currentStructure.getMinLatAllowed()+halfRange;
            }
            if(position.target.latitude>currentStructure.getMaxLatAllowed()-halfRange){
                if(newLat.equals(currentStructure.getMinLatAllowed())){
                    newLat=position.target.latitude;
                }else {
                    newLat=currentStructure.getMaxLatAllowed()-halfRange;
                }
            }
            newPosition = new CameraPosition(new LatLng(newLat,newLng),newZoom,position.tilt,position.bearing);
            if(!newPosition.equals(position)) {
                model.setPosition(newPosition);
                return;
            }
        }
        model.setPosition(position);
    }

    private Structure findStructure(String name){
        for(Structure possible: structures){
            if(possible.getName().equals(name)){
                return possible;
            }
        }
        return null;
    }
}
