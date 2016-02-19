package com.solutions.law.universityrouteplanner.Model;

import com.solutions.law.universityrouteplanner.Model.Graph.INode;
import com.solutions.law.universityrouteplanner.Model.PathFinding.PathFindingAlgorithm;
import com.solutions.law.universityrouteplanner.Model.Update.ModelState;
import com.solutions.law.universityrouteplanner.View.RoutePlannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class Model implements IModel {

    private PathFindingAlgorithm algorithm;
    private List<RoutePlannerListener> listeners;
    private INode startLoc;
    private INode endLoc;
    private List<INode> graph;
    private List<String> routeSelected;
    private String error;
    private String plane;

    public Model(List<INode> graph,PathFindingAlgorithm algorithm){
        this.algorithm=algorithm;
        this.graph=graph;
        listeners=new ArrayList<>();
        startLoc=null;
        endLoc=null;
        error=null;
        plane="Outside";
        algorithm.setUp(graph);
    }

    @Override
    public String getPlane(){
        return plane;
    }

    @Override
    public String getStart(){
        return startLoc.getName();
    }

    @Override
    public String getEnd(){
        return endLoc.getName();
    }
    @Override
    public void setPlane(String plane){
        this.plane=plane;
        alertAll();
    }

    @Override
    public void addListener(RoutePlannerListener newListener){
        listeners.add(newListener);
    }

    @Override
    public void startLoc(String start){
        Boolean changed=false;
        for(INode current:graph){
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

    @Override
    public void endLoc(String end){
        Boolean changed=false;
        for(INode current:graph){
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

    @Override
    public void newRoute(){
        if(startLoc==null||endLoc==null) {
            error = "Need a start and end destination to route between";
        }else if(startLoc.equals(endLoc)){
            error="Cannot route to and from the same location.";
        }else{
            routeSelected = algorithm.findRoute(startLoc, endLoc);
        }
        alertAll();
    }

    @Override
    public void setError(String error){
        this.error=error;
        if(error!=null){
            alertAll();
        }
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
            listener.update(new ModelState(startName,endName,routeSelected,error,plane));
        }
    }
}
