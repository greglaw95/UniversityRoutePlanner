package com.solutions.law.universityrouteplanner.Model.Graph;

/**
 * Created by kbb12 on 17/02/2016.
 */
public interface IEdge {

    public INode getOtherINode();
    public double getWeight();
}
