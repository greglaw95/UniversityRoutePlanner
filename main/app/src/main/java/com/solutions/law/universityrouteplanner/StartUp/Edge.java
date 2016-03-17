package com.solutions.law.universityrouteplanner.StartUp;

import com.solutions.law.universityrouteplanner.Model.Graph.IEdge;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;

/**
 * Created by kbb12 on 15/02/2016.
 */
public class Edge implements IEdge {
    private INode nodeTwo;
    private double weight;

    public Edge(INode nodeTwo,double weight){
        this.nodeTwo=nodeTwo;
        this.weight=weight;
    }

    @Override
    public INode getOtherINode(){
        return nodeTwo;
    }

    @Override
    public double getWeight(){
        return weight;
    }
}
