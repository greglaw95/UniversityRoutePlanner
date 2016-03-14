package com.solutions.law.universityrouteplanner.Controller;

import com.solutions.law.universityrouteplanner.Model.Graph.INode;

/**
 * Created by kbb12 on 12/03/2016.
 */
public interface NodeCheck {
    public boolean canUse(INode node);
}
