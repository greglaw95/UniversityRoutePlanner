package com.solutions.law.universityrouteplanner;

import com.solutions.law.universityrouteplanner.Controller.NodeCheck;
import com.solutions.law.universityrouteplanner.Controller.StairCheck;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by kbb12 on 23/03/2016.
 */
public class StairCheckTest {
    @Test
    public void unsuccessfulNodesJustPhrase() {
        NodeCheck check = new StairCheck();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.getName()).thenReturn("stair");
        Assert.assertFalse(check.canUse(node));
    }

    @Test
    public void unsuccessfulNodesPhraseStart() {
        NodeCheck check = new StairCheck();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.getName()).thenReturn("stairSomething");
        Assert.assertFalse(check.canUse(node));
    }

    @Test
    public void unsuccessfulNodesPhraseEnd() {
        NodeCheck check = new StairCheck();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.getName()).thenReturn("somethingStair");
        Assert.assertFalse(check.canUse(node));
    }

    @Test
    public void unsuccessfulNodesPhraseMiddle() {
        NodeCheck check = new StairCheck();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.getName()).thenReturn("somethingstairsomething");
        Assert.assertFalse(check.canUse(node));
    }

    @Test
    public void successfulNodesPassCheck() {
        NodeCheck check = new StairCheck();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.getName()).thenReturn("otherName");
        Assert.assertTrue(check.canUse(node));
    }
}
