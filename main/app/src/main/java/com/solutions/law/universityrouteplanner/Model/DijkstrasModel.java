package com.solutions.law.universityrouteplanner.Model;

import com.solutions.law.universityrouteplanner.Controller.NodeCheck;
import com.solutions.law.universityrouteplanner.Model.Graph.IEdge;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kbb12 on 16/02/2016.
 */
public class DijkstrasModel extends Model {
    
    public DijkstrasModel(List<INode> graph){
        super(graph);
    }
    
    
    public Map<INode,INode> findRoute(){
        Set<INode> notDealtWith = new HashSet<>();
        Map<INode,Double> currentDistanceTo = new HashMap<>();
        Map<INode,INode> predecessor = new HashMap<>();
        INode toExamine;
        INode other;
        Double possibleNewDistance;
        routeSetUp(notDealtWith, currentDistanceTo, startLoc);
        toExamine=startLoc;
        while(toExamine!=endLoc){
            notDealtWith.remove(toExamine);
            if(check.canUse(toExamine)||toExamine.equals(startLoc)) {
                for (IEdge edge : toExamine.getIEdges()) {
                    other = edge.getOtherINode();
                    possibleNewDistance = currentDistanceTo.get(toExamine) + edge.getWeight();
                    if (currentDistanceTo.get(other) > possibleNewDistance) {
                        currentDistanceTo.put(other, possibleNewDistance);
                        predecessor.put(other, toExamine);
                    }
                }
            }
            toExamine=closestUnexaminedINode(currentDistanceTo, notDealtWith);
        }
        return predecessor;
    }

    private void routeSetUp(Set<INode> notDealtWith,Map<INode,Double> currentDistanceTo,INode startLoc){
        notDealtWith.addAll(graph);
        for(INode current:graph){
            currentDistanceTo.put(current,Double.MAX_VALUE);
        }
        currentDistanceTo.put(startLoc,0.0);
    }


    private INode closestUnexaminedINode(Map<INode,Double> currentDistanceTo,Set<INode> notDealtWith){
        List<INode> unexamined = new ArrayList<>(notDealtWith);
        INode lowest=unexamined.get(0);
        for(int i=1;i<unexamined.size();i++){
            if(currentDistanceTo.get(unexamined.get(i))<currentDistanceTo.get(lowest)){
                lowest=unexamined.get(i);
            }
        }
        return lowest;
    }
    
}
