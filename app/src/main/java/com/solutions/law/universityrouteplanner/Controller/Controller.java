package com.solutions.law.universityrouteplanner.Controller;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;
import com.solutions.law.universityrouteplanner.Model.IModel;
import com.solutions.law.universityrouteplanner.StartUp.SteppingStone;


import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class Controller implements IController {

    private IModel model;
    private List<Selectable> elements;
    private List<SteppingStone> stones;
    private List<Structure> structures;
    private Location location;
    private Structure building;

    public Controller(IModel model,List<Selectable> elements,List<SteppingStone> stones,List<Structure> structures){
        this.model=model;
        this.elements=elements;
        this.stones=stones;
        this.structures = structures;
        location=Location.START;
        building=null;
    }

    public void setLevel(int level){
        building.setLevel(level);
        model.setPlane(building.getName()+level);
    }

    @Override
    public void goInside() {
        if (building!=null) {
            building=null;
            model.setPlane("Outside");
        }else{
            if(location==Location.START){
                building=findStructure(model.getStart());
            }else{
                building=findStructure(model.getEnd());
            }
            model.setPlane(building.getName()+building.getLevel());
        }
        onCameraChange(model.getPosition());
    }

    @Override
    public void weight(String weight){
        try{
            model.setWeight(Double.parseDouble(weight));
        }catch(Exception e){

        }

    }

    @Override
    public void focusOn(Location location){
        this.location=location;
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
    public boolean onMarkerClick(Marker clicked){
        for(SteppingStone element:stones){
            if(element.getPoint().equals(clicked.getPosition())){
                if(location==Location.START) {
                    model.startLoc(element.getName());
                }else{
                    model.endLoc(element.getName());
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void startUp(){
        model.start();
    }

    @Override
    public void add(){
        model.addLink();
    }

    @Override
    public void onCameraChange(CameraPosition position) {
        if(building!=null){
            CameraPosition newPosition;
            float newZoom=position.zoom;
            Double newLng=position.target.longitude;
            Double newLat=position.target.latitude;
            if(position.zoom<building.getMinZoomAllowed()){
                newZoom=building.getMinZoomAllowed();
            }
            double halfRange=0.5*(360/(Math.pow(2,newZoom)));
            if(position.target.longitude<building.getMinLngAllowed()+halfRange){
                newLng=building.getMinLngAllowed()+halfRange;
            }
            if(position.target.longitude>building.getMaxLngAllowed()-halfRange){
                if(newLng.equals(building.getMinLngAllowed())){
                    newLng=position.target.longitude;
                }else {
                    newLng = building.getMaxLngAllowed()-halfRange;
                }
            }
            if(position.target.latitude<building.getMinLatAllowed()+halfRange){
                newLat=building.getMinLatAllowed()+halfRange;
            }
            if(position.target.latitude>building.getMaxLatAllowed()-halfRange){
                if(newLat.equals(building.getMinLatAllowed())){
                    newLat=position.target.latitude;
                }else {
                    newLat=building.getMaxLatAllowed()-halfRange;
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
