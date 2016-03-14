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
    private Location location;
    private List<Structure> structures;
    private Structure currentStructure;
    private CompositeCheck checks;
    private NodeCheck stairCheck;
    private NodeCheck liftCheck;

    public Controller(IModel model,List<Structure> structures){
        this.model=model;
        location=Location.START;
        this.structures=structures;
        currentStructure=null;
        checks=new CompositeCheck();
        stairCheck=new StairCheck();
        liftCheck= new LiftCheck();
        checks.addCheck(new BasicCheck());
    }

    @Override
    public void goInside() {
        if (currentStructure!=null) {
            currentStructure=null;
            model.setPlane("Outside","");
        }else{
            if(location==Location.START){
                currentStructure=findStructure(model.getStart());
            }else{
                currentStructure=findStructure(model.getEnd());
            }
            if(currentStructure==null){
                model.setError("You can not move inside the selected area.");
            }else {
                model.setPlane(currentStructure.getName(), currentStructure.getLevel());
            }
        }
    }

    @Override
    public void setLevel(String level){
        if(currentStructure!=null) {
            currentStructure.setLevel(level);
            model.setPlane(currentStructure.getName(), currentStructure.getLevel());
        }
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
    public void areaSelected(String areaName){
        if(location==Location.START) {
            model.startLoc(areaName);
        }else{
            model.endLoc(areaName);
        }
    }

    @Override
    public void setStructure(String structureName) {
        if(structureName==null){
            currentStructure=null;
            model.setPlane("Outside", "");
            return;
        }
        currentStructure=findStructure(structureName);
    }

    @Override
    public void startUp(){
        model.start(checks);
    }

    private Structure findStructure(String name){
        for(Structure possible: structures){
            if(possible.getName().equals(name)){
                return possible;
            }
        }
        return null;
    }

    public void setStart(String start){
        if(!start.equals(model.getStart())){
            model.startLoc(start);
        }
    }

    @Override
    public Structure getStructure(){
        return currentStructure;
    }

    @Override
    public void setEnd(String end){
        if(!end.equals(model.getEnd())){
            model.endLoc(end);
        }
    }

    @Override
    public void useStairs(boolean stairUse){
        if(stairUse){
            checks.removeCheck(stairCheck);
        }else{
            checks.addCheck(stairCheck);
        }
        model.setCheck(checks);
    }

    @Override
    public void useLifts(boolean liftUse){
        if(liftUse){
            checks.removeCheck(liftCheck);
        }else{
            checks.addCheck(liftCheck);
        }
    }
}
