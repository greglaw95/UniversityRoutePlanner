package com.solutions.law.universityrouteplanner.Controller;

import com.solutions.law.universityrouteplanner.Model.Graph.INode;

/**
 * Created by kbb12 on 14/03/2016.
 */
public class StairCheck implements NodeCheck {

    public StairCheck(){

    }

    @Override
    public boolean canUse(INode node) {
        return (!node.getName().contains("STAIR"));
    }
}
