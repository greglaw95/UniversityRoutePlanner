package com.solutions.law.universityrouteplanner.UnitTesting.Controller;

import com.solutions.law.universityrouteplanner.Controller.LiftCheck;
import com.solutions.law.universityrouteplanner.Controller.NodeCheck;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;


public class LiftCheckTest{
    @Test
    public void unsuccessfulNodesJustPhrase() {
        NodeCheck check = new LiftCheck();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.getName()).thenReturn("lift");
        Assert.assertFalse(check.canUse(node));
    }

    @Test
    public void unsuccessfulNodesPhraseStart() {
        NodeCheck check = new LiftCheck();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.getName()).thenReturn("liftSomething");
        Assert.assertFalse(check.canUse(node));
    }

    @Test
    public void unsuccessfulNodesPhraseEnd() {
        NodeCheck check = new LiftCheck();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.getName()).thenReturn("somethingLift");
        Assert.assertFalse(check.canUse(node));
    }

    @Test
    public void unsuccessfulNodesPhraseMiddle() {
        NodeCheck check = new LiftCheck();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.getName()).thenReturn("somethingliftsomething");
        Assert.assertFalse(check.canUse(node));
    }

    @Test
    public void successfulNodesFailCheck() {
        NodeCheck check = new LiftCheck();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.getName()).thenReturn("otherName");
        Assert.assertTrue(check.canUse(node));
    }

}