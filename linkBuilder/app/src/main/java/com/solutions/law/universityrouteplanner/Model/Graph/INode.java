package com.solutions.law.universityrouteplanner.Model.Graph;

import java.util.List;

/**
 * Created by kbb12 on 17/02/2016.
 */
public interface INode {
    public String getName();
    public List<IEdge> getIEdges();
}
