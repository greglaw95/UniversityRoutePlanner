package com.solutions.law.universityrouteplanner.Model;

import com.solutions.law.universityrouteplanner.Controller.NodeCheck;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;
import com.solutions.law.universityrouteplanner.Model.Update.ModelState;
import com.solutions.law.universityrouteplanner.View.RoutePlannerListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by kbb12155 on 10/02/16.
 */
public abstract class Model implements IModel {

    private List<RoutePlannerListener> listeners;
    protected INode startLoc;
    protected INode endLoc;
    protected List<INode> graph;
    protected NodeCheck check;
    private List<String> routeSelected;
    private String error;
    private String plane;
    private String level;

    public Model(List<INode> graph){
        this.graph=graph;
        listeners=new ArrayList<>();
        startLoc=null;
        endLoc=null;
        error=null;
        plane="Outside";
        level="";
    }


    @Override
    public void setCheck(NodeCheck check){
        this.check=check;
    }

    @Override
    public String getStart(){
        if(startLoc!=null) {
            return startLoc.getName();
        }
        return null;
    }

    @Override
    public String getEnd(){
        if(endLoc!=null) {
            return endLoc.getName();
        }
        return null;
    }

    @Override
    public void setPlane(String plane,String level){
        this.level=level;
        this.plane=plane;
        alertAll(null);
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
            alertAll(startLoc.getName());
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
            alertAll(endLoc.getName());
        }
    }

    @Override
    public void newRoute(){
        if(endsValid()){
            Map<INode,INode> predecessors=findRoute();
            try {
                parseRoute(predecessors);
                alertAll(startLoc.getName());
                return;
            }catch (RouteNotAvailableException e){
                error="There is not a valid route between these points with these settings.";
            }
        }
        alertAll(null);
    }

    private final boolean endsValid(){
        if(startLoc==null) {
            error = "The start location is not recognised.";
        }else if(endLoc==null){
            error="The end location is not recognised.";
        }else if(startLoc.equals(endLoc)){
            error="Cannot route to and from the same location.";
        }else{
            return true;
        }
        return false;
    }

    protected abstract Map<INode,INode> findRoute();

    private final void parseRoute(Map<INode,INode> predecessor) throws RouteNotAvailableException{
        List<String> route = new ArrayList<>();
        INode nextINode;
        route.add(endLoc.getName());
        nextINode=predecessor.get(endLoc);
        while(nextINode!=startLoc){
            if(nextINode==null){
                throw new RouteNotAvailableException();
            }
            route.add(nextINode.getName());
            nextINode=predecessor.get(nextINode);
        }
        route.add(nextINode.getName());
        Collections.reverse(route);
        routeSelected=route;
    }

    @Override
    public void setError(String error){
        this.error=error;
        if(error!=null){
            alertAll(null);
        }
    }


    private void alertAll(String focusOn){
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
            listener.update(new ModelState(startName,endName,routeSelected,error,plane,level,focusOn));
        }
    }

    @Override
    public void start(NodeCheck check){
        this.check=check;
        alertAll(null);
    }
}
