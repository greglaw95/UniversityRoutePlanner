package com.solutions.law.universityrouteplanner.Controller;

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
    private Location location;
    private String building;
    private Integer level;

    public Controller(IModel model,List<Selectable> elements,List<SteppingStone> stones){
        this.model=model;
        this.elements=elements;
        this.stones=stones;
        location=Location.START;
        building=null;
        level=null;
    }

    public void setLevel(int level){
        this.level=level;
        model.setPlane(building+level);
    }

    @Override
    public void goInside() {
        if (model.getPlane() != "Outside") {
            building=null;
            level=null;
            model.setPlane("Outside");
        }else{
            if(location==Location.START){
                building=model.getStart();
                level=1;
            }else{
                building=model.getEnd();
                level=1;
            }
            model.setPlane(building+level);
        }
    }

    @Override
    public void weight(String weight){
        model.setWeight(Double.parseDouble(weight));
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

}
