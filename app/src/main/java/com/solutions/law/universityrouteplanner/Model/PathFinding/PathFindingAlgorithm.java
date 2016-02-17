package com.solutions.law.universityrouteplanner.Model.PathFinding;

import com.solutions.law.universityrouteplanner.Model.Graph.INode;
import com.solutions.law.universityrouteplanner.Model.Graph.Node;

import java.util.List;

/**
 * Created by kbb12 on 16/02/2016.
 */
public interface PathFindingAlgorithm {
    public void setUp(List<INode> graph);
    public List<String> findRoute(INode start,INode dest);
}
