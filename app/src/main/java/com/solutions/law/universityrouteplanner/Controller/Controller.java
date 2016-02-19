package com.solutions.law.universityrouteplanner.Controller;

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

    public Controller(IModel model,List<Selectable> elements){
        this.model=model;
        this.elements=elements;
        location=Location.START;
    }

    @Override
    public void goInside() {
        if (model.getPlane() != "Outside") {
            model.setPlane("Outside");
        }else{
            if(location==Location.START){
                model.setPlane(model.getStart()+"1");
            }else{
                model.setPlane(model.getEnd()+"1");
            }
        }
    }

    @Override
    public void switchPlane(String plane){
        model.setPlane(plane);
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

}
