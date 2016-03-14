package com.solutions.law.universityrouteplanner.Controller;

import com.solutions.law.universityrouteplanner.Model.Graph.INode;

/**
 * Created by kbb12 on 13/03/2016.
 */
public class BasicCheck implements NodeCheck {

    public BasicCheck(){

    }

    @Override
    public boolean canUse(INode node) {
        return node.canPassThrough();
    }
}
