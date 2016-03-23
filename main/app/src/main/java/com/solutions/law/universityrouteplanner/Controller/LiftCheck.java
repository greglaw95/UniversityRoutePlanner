package com.solutions.law.universityrouteplanner.Controller;

import com.solutions.law.universityrouteplanner.Model.Graph.INode;

/**
 * Created by kbb12 on 14/03/2016.
 */
public class LiftCheck implements NodeCheck {


    public LiftCheck(){

    }

    @Override
    public boolean canUse(INode node) {
        String name = node.getName().toUpperCase();
        return (!name.contains("LIFT"));
    }
}
