package com.solutions.law.universityrouteplanner.StartUp;

import com.solutions.law.universityrouteplanner.Model.Graph.IEdge;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;
import com.solutions.law.universityrouteplanner.Model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 15/02/2016.
 */
public class Node implements INode {
    String name;
    Boolean passThrough;
    List<IEdge> edges;

    public Node(String name,Boolean passThrough){
        this.passThrough=passThrough;
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

    @Override
    public boolean canPassThrough(){
        return passThrough;
    }
}
