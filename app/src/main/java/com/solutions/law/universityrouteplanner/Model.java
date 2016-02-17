package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.model.CameraPosition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class Model implements IModel{

    private CameraPosition position;
    private PathFindingAlgorithm algorithm;
    private List<RoutePlannerListener> listeners;
    private Node startLoc;
    private Node endLoc;
    private List<Node> graph;
    private List<String> routeSelected;

    public Model(List<Node> graph,PathFindingAlgorithm algorithm){
        this.algorithm=algorithm;
        this.graph=graph;
        position=null;
        listeners=new ArrayList<RoutePlannerListener>();
        startLoc=null;
        endLoc=null;
        algorithm.setUp(graph);
    }

    public void addListener(RoutePlannerListener newListener){
        listeners.add(newListener);
    }

    public void moveTo(CameraPosition position){
        if(this.position==null||!roughlyEqual(this.position,position)){
            this.position=position;
            alertAll();
        }
    }

    public void startLoc(String start){
        Boolean changed=false;
        for(Node current:graph){
            if(current.getName().equals(start)){
                if(!current.equals(startLoc)){
                    changed=true;
                    startLoc=current;
                }
            }
        }
        if(changed) {
            alertAll();
        }
    }

    public void endLoc(String end){
        Boolean changed=false;
        for(Node current:graph){
            if(current.getName().equals(end)){
                if(!current.equals(endLoc)){
                    changed=true;
                    endLoc=current;
                }
            }
        }
        if(changed) {
            alertAll();
        }
    }


    //DOn't like this ridiculously coupled replace with strategy design pattern??
    public void newRoute(){
        routeSelected =algorithm.findRoute(startLoc,endLoc);
        alertAll();
    }

    private boolean roughlyEqual(CameraPosition position1,CameraPosition position2){
        boolean checkOne= (new BigDecimal(position1.zoom).setScale(4, RoundingMode.HALF_UP).floatValue()==new BigDecimal(position2.zoom).setScale(4, RoundingMode.HALF_UP).floatValue());
        boolean checkTwo= (new BigDecimal(position1.target.latitude).setScale(4, RoundingMode.HALF_UP).doubleValue()==new BigDecimal(position2.target.latitude).setScale(4, RoundingMode.HALF_UP).doubleValue());
        boolean checkThree= (new BigDecimal(position1.target.longitude).setScale(4, RoundingMode.HALF_UP).doubleValue()==new BigDecimal(position2.target.longitude).setScale(4, RoundingMode.HALF_UP).doubleValue());
        return checkOne&&checkTwo&&checkThree;
    }

    private void alertAll(){
        String startName;
        String endName;
        if(startLoc==null){
            startName=null;
        }else{
            startName=startLoc.getName();
        }
        if(endLoc==null){
            endName=null;
        }else{
            endName=endLoc.getName();
        }
        for(RoutePlannerListener listener:listeners){
            listener.update(new ModelState(position,startName,endName,routeSelected));
        }
    }
}
