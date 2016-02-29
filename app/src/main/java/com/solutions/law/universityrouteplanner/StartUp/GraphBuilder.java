package com.solutions.law.universityrouteplanner.StartUp;

import com.solutions.law.universityrouteplanner.Model.Graph.Edge;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;
import com.solutions.law.universityrouteplanner.Model.Graph.Node;
import com.solutions.law.universityrouteplanner.View.EndPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 15/02/2016.
 */
public class GraphBuilder {

    private List<Node> nodes;

    public GraphBuilder(List<SelectableEndPoint> endPoints,List<SteppingStone> steppingStones,List<Link> links){
        nodes = new ArrayList<>();
        Node nodeOne=null;
        Node nodeTwo=null;
        for(EndPoint ep:endPoints){
            nodes.add(new Node(ep.getName(),false));
        }
        for(SteppingStone ss:steppingStones){
            nodes.add(new Node(ss.getName(),true));
        }
        for (Link link:links){
            for (Node current:nodes){
                if(current.getName().equals(link.getNodeOne())){
                    nodeOne=current;
                }
                if(current.getName().equals(link.getNodeTwo())){
                    nodeTwo=current;
                }
            }
            if(nodeOne==null||nodeTwo==null){
                throw new ImpossibleEdgeException(link);
            }
            nodeOne.addEdge(new Edge(nodeTwo,link.getWeight()));
            nodeTwo.addEdge(new Edge(nodeOne,link.getWeight()));
        }
    }

    public List<INode> getGraph(){
        List<INode> graph = new ArrayList(nodes);
        return graph;
    }
}
