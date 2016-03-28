package com.solutions.law.universityrouteplanner.UnitTesting.Model;

import com.solutions.law.universityrouteplanner.Model.DijkstrasModel;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;
import com.solutions.law.universityrouteplanner.Model.IModel;
import com.solutions.law.universityrouteplanner.Model.Update.RoutePlannerState;
import com.solutions.law.universityrouteplanner.View.RoutePlannerListener;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 28/03/2016.
 */
public class ModelTest {

    @Test
    public void setStartTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<INode> graph = new ArrayList<>();
        INode nodeOne = Mockito.mock(INode.class);
        INode nodeTwo = Mockito.mock(INode.class);
        INode nodeThree = Mockito.mock(INode.class);
        Mockito.when(nodeOne.getName()).thenReturn("nameOne");
        Mockito.when(nodeTwo.getName()).thenReturn("nameTwo");
        Mockito.when(nodeThree.getName()).thenReturn("nameThree");
        graph.add(nodeOne);
        graph.add(nodeTwo);
        graph.add(nodeThree);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        model.startLoc("nameTwo");
        Mockito.verify(listener).update(captor.capture());
        Assert.assertEquals("nameTwo", captor.getValue().getStartLoc());
        Assert.assertEquals("nameTwo",model.getStart());
    }

    @Test
    public void setEndTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<INode> graph = new ArrayList<>();
        INode nodeOne = Mockito.mock(INode.class);
        INode nodeTwo = Mockito.mock(INode.class);
        INode nodeThree = Mockito.mock(INode.class);
        Mockito.when(nodeOne.getName()).thenReturn("nameOne");
        Mockito.when(nodeTwo.getName()).thenReturn("nameTwo");
        Mockito.when(nodeThree.getName()).thenReturn("nameThree");
        graph.add(nodeOne);
        graph.add(nodeTwo);
        graph.add(nodeThree);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        model.endLoc("nameThree");
        Mockito.verify(listener).update(captor.capture());
        Assert.assertEquals("nameThree", captor.getValue().getEndLoc());
        Assert.assertEquals("nameThree",model.getEnd());
    }

    @Test
    public void setErrorTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<INode> graph = new ArrayList<>();
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        model.setError("Error");
        Mockito.verify(listener).update(captor.capture());
        Assert.assertEquals("Error",captor.getValue().getError());
    }

    @Test
    public void setPlaneTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<INode> graph = new ArrayList<>();
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        model.setPlane("planeName", "6");
        Mockito.verify(listener).update(captor.capture());
        Assert.assertEquals("planeName6", captor.getValue().getPlane());
        Assert.assertEquals("6",captor.getValue().getLevel());
    }

}
