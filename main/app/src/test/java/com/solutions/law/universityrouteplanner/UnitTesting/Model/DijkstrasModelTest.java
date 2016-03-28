package com.solutions.law.universityrouteplanner.UnitTesting.Model;

import com.solutions.law.universityrouteplanner.Controller.BasicCheck;
import com.solutions.law.universityrouteplanner.Controller.LiftCheck;
import com.solutions.law.universityrouteplanner.Controller.StairCheck;
import com.solutions.law.universityrouteplanner.Model.DijkstrasModel;
import com.solutions.law.universityrouteplanner.Model.Graph.IEdge;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;
import com.solutions.law.universityrouteplanner.Model.IModel;
import com.solutions.law.universityrouteplanner.Model.Update.RoutePlannerState;
import com.solutions.law.universityrouteplanner.View.RoutePlannerListener;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 28/03/2016.
 */
public class DijkstrasModelTest {

    @Test
    public void noRouteTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<INode> graph = new ArrayList<>();
        INode nodeOne = Mockito.mock(INode.class);
        Mockito.when(nodeOne.getName()).thenReturn("nodeOne");
        Mockito.when(nodeOne.canPassThrough()).thenReturn(false);
        Mockito.when(nodeOne.getIEdges()).thenReturn(new ArrayList<IEdge>());
        INode nodeTwo = Mockito.mock(INode.class);
        Mockito.when(nodeTwo.getName()).thenReturn("nodeTwo");
        Mockito.when(nodeTwo.canPassThrough()).thenReturn(false);
        Mockito.when(nodeTwo.getIEdges()).thenReturn(new ArrayList<IEdge>());
        graph.add(nodeOne);
        graph.add(nodeTwo);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.start(new BasicCheck());
        model.startLoc("nodeOne");
        model.endLoc("nodeTwo");
        model.addListener(listener);
        model.newRoute();
        Mockito.verify(listener).update(captor.capture());
        Assert.assertEquals(null,captor.getValue().getRouteSelected());
        Assert.assertNotNull(captor.getValue().getError());
    }

    @Test
    public void routeTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<INode> graph = new ArrayList<>();
        INode nodeOne = Mockito.mock(INode.class);
        Mockito.when(nodeOne.getName()).thenReturn("nodeOne");
        Mockito.when(nodeOne.canPassThrough()).thenReturn(false);
        INode nodeTwo = Mockito.mock(INode.class);
        Mockito.when(nodeTwo.getName()).thenReturn("nodeTwo");
        Mockito.when(nodeTwo.canPassThrough()).thenReturn(false);
        Mockito.when(nodeTwo.getIEdges()).thenReturn(new ArrayList<IEdge>());
        List<IEdge> oneEdges = new ArrayList<>();
        IEdge edge = Mockito.mock(IEdge.class);
        oneEdges.add(edge);
        Mockito.when(edge.getOtherINode()).thenReturn(nodeTwo);
        Mockito.when(edge.getWeight()).thenReturn(10.0);
        Mockito.when(nodeOne.getIEdges()).thenReturn(oneEdges);
        graph.add(nodeOne);
        graph.add(nodeTwo);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.start(new BasicCheck());
        model.startLoc("nodeOne");
        model.endLoc("nodeTwo");
        model.addListener(listener);
        model.newRoute();
        Mockito.verify(listener).update(captor.capture());
        List<String> expected =new ArrayList<>();
        expected.add("nodeOne");
        expected.add("nodeTwo");
        Assert.assertEquals(expected, captor.getValue().getRouteSelected());
        Assert.assertNull(captor.getValue().getError());
    }

    @Test
    public void shortestRouteTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<INode> graph = new ArrayList<>();
        INode nodeOne = Mockito.mock(INode.class);
        Mockito.when(nodeOne.getName()).thenReturn("nodeOne");
        Mockito.when(nodeOne.canPassThrough()).thenReturn(false);
        INode optionOne = Mockito.mock(INode.class);
        Mockito.when(optionOne.getName()).thenReturn("stairOption");
        Mockito.when(optionOne.canPassThrough()).thenReturn(true);
        INode optionTwo = Mockito.mock(INode.class);
        Mockito.when(optionTwo.getName()).thenReturn("liftOption");
        Mockito.when(optionTwo.canPassThrough()).thenReturn(true);
        INode nodeTwo = Mockito.mock(INode.class);
        Mockito.when(nodeTwo.getName()).thenReturn("nodeTwo");
        Mockito.when(nodeTwo.canPassThrough()).thenReturn(false);
        Mockito.when(nodeTwo.getIEdges()).thenReturn(new ArrayList<IEdge>());
        List<IEdge> oneEdges = new ArrayList<>();
        IEdge edgeOneToOne = Mockito.mock(IEdge.class);
        oneEdges.add(edgeOneToOne);
        Mockito.when(edgeOneToOne.getOtherINode()).thenReturn(optionOne);
        Mockito.when(edgeOneToOne.getWeight()).thenReturn(10.0);
        Mockito.when(nodeOne.getIEdges()).thenReturn(oneEdges);
        IEdge edgeOneToTwo = Mockito.mock(IEdge.class);
        oneEdges.add(edgeOneToTwo);
        Mockito.when(edgeOneToTwo.getOtherINode()).thenReturn(optionTwo);
        Mockito.when(edgeOneToTwo.getWeight()).thenReturn(11.0);
        Mockito.when(nodeOne.getIEdges()).thenReturn(oneEdges);
        List<IEdge> opOneEdges = new ArrayList<>();
        IEdge opOneToTwo = Mockito.mock(IEdge.class);
        Mockito.when(opOneToTwo.getOtherINode()).thenReturn(nodeTwo);
        Mockito.when(opOneToTwo.getWeight()).thenReturn(10.0);
        opOneEdges.add(opOneToTwo);
        Mockito.when(optionOne.getIEdges()).thenReturn(opOneEdges);
        List<IEdge> opTwoEdges = new ArrayList<>();
        IEdge opTwoToTwo = Mockito.mock(IEdge.class);
        Mockito.when(opTwoToTwo.getOtherINode()).thenReturn(nodeTwo);
        Mockito.when(opTwoToTwo.getWeight()).thenReturn(10.0);
        opTwoEdges.add(opTwoToTwo);
        Mockito.when(optionTwo.getIEdges()).thenReturn(opTwoEdges);
        //Need edges from the two options to node two then it should choose option one
        graph.add(nodeOne);
        graph.add(nodeTwo);
        graph.add(optionOne);
        graph.add(optionTwo);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.start(new BasicCheck());
        model.startLoc("nodeOne");
        model.endLoc("nodeTwo");
        model.addListener(listener);
        model.newRoute();
        Mockito.verify(listener).update(captor.capture());
        List<String> expected =new ArrayList<>();
        expected.add("nodeOne");
        expected.add("stairOption");
        expected.add("nodeTwo");
        Assert.assertEquals(expected, captor.getValue().getRouteSelected());
        Assert.assertNull(captor.getValue().getError());
    }

    @Test
    public void shortestRouteNoStairTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<INode> graph = new ArrayList<>();
        INode nodeOne = Mockito.mock(INode.class);
        Mockito.when(nodeOne.getName()).thenReturn("nodeOne");
        Mockito.when(nodeOne.canPassThrough()).thenReturn(false);
        INode optionOne = Mockito.mock(INode.class);
        Mockito.when(optionOne.getName()).thenReturn("stairOption");
        Mockito.when(optionOne.canPassThrough()).thenReturn(true);
        INode optionTwo = Mockito.mock(INode.class);
        Mockito.when(optionTwo.getName()).thenReturn("liftOption");
        Mockito.when(optionTwo.canPassThrough()).thenReturn(true);
        INode nodeTwo = Mockito.mock(INode.class);
        Mockito.when(nodeTwo.getName()).thenReturn("nodeTwo");
        Mockito.when(nodeTwo.canPassThrough()).thenReturn(false);
        Mockito.when(nodeTwo.getIEdges()).thenReturn(new ArrayList<IEdge>());
        List<IEdge> oneEdges = new ArrayList<>();
        IEdge edgeOneToOne = Mockito.mock(IEdge.class);
        oneEdges.add(edgeOneToOne);
        Mockito.when(edgeOneToOne.getOtherINode()).thenReturn(optionOne);
        Mockito.when(edgeOneToOne.getWeight()).thenReturn(10.0);
        Mockito.when(nodeOne.getIEdges()).thenReturn(oneEdges);
        IEdge edgeOneToTwo = Mockito.mock(IEdge.class);
        oneEdges.add(edgeOneToTwo);
        Mockito.when(edgeOneToTwo.getOtherINode()).thenReturn(optionTwo);
        Mockito.when(edgeOneToTwo.getWeight()).thenReturn(11.0);
        Mockito.when(nodeOne.getIEdges()).thenReturn(oneEdges);
        List<IEdge> opOneEdges = new ArrayList<>();
        IEdge opOneToTwo = Mockito.mock(IEdge.class);
        Mockito.when(opOneToTwo.getOtherINode()).thenReturn(nodeTwo);
        Mockito.when(opOneToTwo.getWeight()).thenReturn(10.0);
        opOneEdges.add(opOneToTwo);
        Mockito.when(optionOne.getIEdges()).thenReturn(opOneEdges);
        List<IEdge> opTwoEdges = new ArrayList<>();
        IEdge opTwoToTwo = Mockito.mock(IEdge.class);
        Mockito.when(opTwoToTwo.getOtherINode()).thenReturn(nodeTwo);
        Mockito.when(opTwoToTwo.getWeight()).thenReturn(10.0);
        opTwoEdges.add(opTwoToTwo);
        Mockito.when(optionTwo.getIEdges()).thenReturn(opTwoEdges);
        //Need edges from the two options to node two then it should choose option one
        graph.add(nodeOne);
        graph.add(nodeTwo);
        graph.add(optionOne);
        graph.add(optionTwo);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.start(new StairCheck());
        model.startLoc("nodeOne");
        model.endLoc("nodeTwo");
        model.addListener(listener);
        model.newRoute();
        Mockito.verify(listener).update(captor.capture());
        List<String> expected =new ArrayList<>();
        expected.add("nodeOne");
        expected.add("liftOption");
        expected.add("nodeTwo");
        Assert.assertEquals(expected, captor.getValue().getRouteSelected());
        Assert.assertNull(captor.getValue().getError());
    }

    @Test
    public void shortestRouteNoLiftTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<INode> graph = new ArrayList<>();
        INode nodeOne = Mockito.mock(INode.class);
        Mockito.when(nodeOne.getName()).thenReturn("nodeOne");
        Mockito.when(nodeOne.canPassThrough()).thenReturn(false);
        INode optionOne = Mockito.mock(INode.class);
        Mockito.when(optionOne.getName()).thenReturn("stairOption");
        Mockito.when(optionOne.canPassThrough()).thenReturn(true);
        INode optionTwo = Mockito.mock(INode.class);
        Mockito.when(optionTwo.getName()).thenReturn("liftOption");
        Mockito.when(optionTwo.canPassThrough()).thenReturn(true);
        INode nodeTwo = Mockito.mock(INode.class);
        Mockito.when(nodeTwo.getName()).thenReturn("nodeTwo");
        Mockito.when(nodeTwo.canPassThrough()).thenReturn(false);
        Mockito.when(nodeTwo.getIEdges()).thenReturn(new ArrayList<IEdge>());
        List<IEdge> oneEdges = new ArrayList<>();
        IEdge edgeOneToOne = Mockito.mock(IEdge.class);
        oneEdges.add(edgeOneToOne);
        Mockito.when(edgeOneToOne.getOtherINode()).thenReturn(optionOne);
        Mockito.when(edgeOneToOne.getWeight()).thenReturn(11.0);
        Mockito.when(nodeOne.getIEdges()).thenReturn(oneEdges);
        IEdge edgeOneToTwo = Mockito.mock(IEdge.class);
        oneEdges.add(edgeOneToTwo);
        Mockito.when(edgeOneToTwo.getOtherINode()).thenReturn(optionTwo);
        Mockito.when(edgeOneToTwo.getWeight()).thenReturn(10.0);
        Mockito.when(nodeOne.getIEdges()).thenReturn(oneEdges);
        List<IEdge> opOneEdges = new ArrayList<>();
        IEdge opOneToTwo = Mockito.mock(IEdge.class);
        Mockito.when(opOneToTwo.getOtherINode()).thenReturn(nodeTwo);
        Mockito.when(opOneToTwo.getWeight()).thenReturn(10.0);
        opOneEdges.add(opOneToTwo);
        Mockito.when(optionOne.getIEdges()).thenReturn(opOneEdges);
        List<IEdge> opTwoEdges = new ArrayList<>();
        IEdge opTwoToTwo = Mockito.mock(IEdge.class);
        Mockito.when(opTwoToTwo.getOtherINode()).thenReturn(nodeTwo);
        Mockito.when(opTwoToTwo.getWeight()).thenReturn(10.0);
        opTwoEdges.add(opTwoToTwo);
        Mockito.when(optionTwo.getIEdges()).thenReturn(opTwoEdges);
        //Need edges from the two options to node two then it should choose option one
        graph.add(nodeOne);
        graph.add(nodeTwo);
        graph.add(optionOne);
        graph.add(optionTwo);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.start(new LiftCheck());
        model.startLoc("nodeOne");
        model.endLoc("nodeTwo");
        model.addListener(listener);
        model.newRoute();
        Mockito.verify(listener).update(captor.capture());
        List<String> expected =new ArrayList<>();
        expected.add("nodeOne");
        expected.add("stairOption");
        expected.add("nodeTwo");
        Assert.assertEquals(expected, captor.getValue().getRouteSelected());
        Assert.assertNull(captor.getValue().getError());
    }
}
