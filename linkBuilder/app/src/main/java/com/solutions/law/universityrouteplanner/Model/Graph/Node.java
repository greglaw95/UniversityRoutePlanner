package com.solutions.law.universityrouteplanner.Model.Graph;

import com.solutions.law.universityrouteplanner.Model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 15/02/2016.
 */
public class Node implements INode {
    String name;
    List<IEdge> edges;

    public Node(String name){
        this.name=name;
        edges=new ArrayList<>();
    }

    @Override
    public String getName(){
        return name;
    }

    public void addEdge(IEdge edge){
        edges.add(edge);
    }

    @Override
    public List<IEdge> getIEdges(){
        return edges;
    }
}
