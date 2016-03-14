package com.solutions.law.universityrouteplanner.Controller;

import com.solutions.law.universityrouteplanner.Model.Graph.INode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 14/03/2016.
 */
public class CompositeCheck implements NodeCheck {

    private List<NodeCheck> checks;

    public CompositeCheck(){
        checks=new ArrayList<>();
    }

    public void addCheck(NodeCheck newCheck){
        checks.add(newCheck);
    }

    public void removeCheck(NodeCheck check){
        checks.remove(check);
    }

    @Override
    public boolean canUse(INode node) {
        for(NodeCheck current:checks){
            if(!current.canUse(node)){
                return false;
            }
        }
        return true;
    }
}
