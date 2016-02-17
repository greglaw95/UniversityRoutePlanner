package com.solutions.law.universityrouteplanner.Model.PathFinding;

import com.solutions.law.universityrouteplanner.Model.Graph.IEdge;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kbb12 on 16/02/2016.
 */
public class DijkstrasAlgorithm implements PathFindingAlgorithm {
    private List<INode> graph;


    public void setUp(List<INode> graph){
        this.graph=graph;
    }

    public List<String> findRoute(INode start,INode end){
        Set<INode> notDealtWith = new HashSet<>();
        Map<INode,Double> currentDistanceTo = new HashMap<>();
        Map<INode,INode> predecessor = new HashMap<>();
        INode toExamine;
        INode other;
        Double possibleNewDistance;
        if(start.equals(end)){
            throw new RuntimeException();//TODO
        }
        routeSetUp(notDealtWith, currentDistanceTo, start);
        toExamine=start;
        while(toExamine!=end){
            notDealtWith.remove(toExamine);
            for(IEdge edge:toExamine.getIEdges()){
                other=edge.getOtherINode();
                possibleNewDistance=currentDistanceTo.get(toExamine)+edge.getWeight();
                if(currentDistanceTo.get(other)>possibleNewDistance){
                    currentDistanceTo.put(other,possibleNewDistance);
                    predecessor.put(other,toExamine);
                }
            }
            toExamine=closestUnexaminedINode(currentDistanceTo, notDealtWith);
        }
        return parseRoute(predecessor,start,end);
    }

    private void routeSetUp(Set<INode> notDealtWith,Map<INode,Double> currentDistanceTo,INode start){
        notDealtWith.addAll(graph);
        for(INode current:graph){
            currentDistanceTo.put(current,Double.MAX_VALUE);
        }
        currentDistanceTo.put(start,0.0);
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

    private List<String> parseRoute(Map<INode,INode> predecessor,INode start,INode end){
        List<String> route = new ArrayList<>();
        INode nextINode;
        route.add(end.getName());
        nextINode=predecessor.get(end);
        while(nextINode!=start){
            route.add(nextINode.getName());
            nextINode=predecessor.get(nextINode);
        }
        route.add(nextINode.getName());
        Collections.reverse(route);
        return route;
    }
}
