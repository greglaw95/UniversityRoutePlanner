package com.solutions.law.universityrouteplanner.UnitTesting.Controller;

import com.solutions.law.universityrouteplanner.Controller.BasicCheck;
import com.solutions.law.universityrouteplanner.Controller.NodeCheck;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class BasicCheckTest {
    @Test
    public void successfulNodesPassCheck() {
        NodeCheck check = new BasicCheck();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.canPassThrough()).thenReturn(true);
        Assert.assertTrue(check.canUse(node));
    }

    @Test
    public void unsuccessfulNodesFailCheck() {
        NodeCheck check = new BasicCheck();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.canPassThrough()).thenReturn(false);
        Assert.assertFalse(check.canUse(node));
    }
}