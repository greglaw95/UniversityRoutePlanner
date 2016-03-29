package com.solutions.law.universityrouteplanner.SubSystemTesting;

import com.google.android.gms.maps.model.LatLng;
import com.solutions.law.universityrouteplanner.Controller.Controller;
import com.solutions.law.universityrouteplanner.Controller.IController;
import com.solutions.law.universityrouteplanner.Controller.Structure;
import com.solutions.law.universityrouteplanner.Model.DijkstrasModel;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;
import com.solutions.law.universityrouteplanner.Model.IModel;
import com.solutions.law.universityrouteplanner.Model.Update.RoutePlannerState;
import com.solutions.law.universityrouteplanner.StartUp.Building;
import com.solutions.law.universityrouteplanner.StartUp.Edge;
import com.solutions.law.universityrouteplanner.StartUp.Node;
import com.solutions.law.universityrouteplanner.StartUp.SelectableEndPoint;
import com.solutions.law.universityrouteplanner.View.RoutePlannerListener;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 29/03/2016.
 */
public class ControllerModel {

    @Test
    public void setGetStructureTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<Structure> structures=new ArrayList<>();
        Structure structureOne = new Building(new SelectableEndPoint("structureOne",new ArrayList<LatLng>(),"planeName"));
        structures.add(structureOne);
        List<INode> graph = new ArrayList<>();
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        IController controller = new Controller(model,structures);
        controller.setStructure("structureOne");
        Assert.assertEquals(structureOne, controller.getStructure());
    }

    @Test
    public void setGetStructureNotThereTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<Structure> structures=new ArrayList<>();
        Structure structureOne = new Building(new SelectableEndPoint("structureOne",new ArrayList<LatLng>(),"planeName"));
        structures.add(structureOne);
        List<INode> graph = new ArrayList<>();
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        IController controller = new Controller(model,structures);
        controller.setStructure("structureTwo");
        Assert.assertEquals(null, controller.getStructure());
    }

    @Test
    public void setMaintainLevelTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<Structure> structures=new ArrayList<>();
        Structure structureOne = new Building(new SelectableEndPoint("structureOne",new ArrayList<LatLng>(),"planeName"));
        structures.add(structureOne);
        List<INode> graph = new ArrayList<>();
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        IController controller = new Controller(model,structures);
        controller.setStructure("structureOne");
        Assert.assertEquals(structureOne, controller.getStructure());
        controller.setLevel("6");
        controller.setStructure("structureTwo");
        controller.setLevel("4");
        controller.setStructure("structureOne");
        Mockito.verify(listener).update(captor.capture());
        List<String> expected = new ArrayList<>();
        expected.add("structureOne6");
        expected.add("structureTwo4");
        expected.add("structureOne6");
        for(int i=0;i<captor.getAllValues().size();i++){
            Assert.assertEquals(expected.get(i),captor.getAllValues().get(i).getPlane());
        }
    }

    @Test
    public void setStartTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<Structure> structures=new ArrayList<>();
        List<INode> graph = new ArrayList<>();
        INode nodeOne = new Node("nodeOne",false);
        INode nodeTwo = new Node("nodeTwo",false);
        graph.add(nodeOne);
        graph.add(nodeTwo);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        IController controller = new Controller(model,structures);
        controller.setStart("nodeOne");
        Mockito.verify(listener).update(captor.capture());
        Assert.assertEquals("nodeOne", captor.getValue().getStartLoc());
    }

    @Test
    public void setEndTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<Structure> structures=new ArrayList<>();
        List<INode> graph = new ArrayList<>();
        INode nodeOne = new Node("nodeOne",false);
        INode nodeTwo = new Node("nodeTwo",false);
        graph.add(nodeOne);
        graph.add(nodeTwo);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        IController controller = new Controller(model,structures);
        controller.setEnd("nodeOne");
        Mockito.verify(listener).update(captor.capture());
        Assert.assertEquals("nodeOne",captor.getValue().getEndLoc());
    }

    @Test
    public void errorAcceptedTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<Structure> structures=new ArrayList<>();
        List<INode> graph = new ArrayList<>();
        INode nodeOne = new Node("nodeOne",false);
        INode nodeTwo = new Node("nodeTwo",false);
        graph.add(nodeOne);
        graph.add(nodeTwo);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        IController controller = new Controller(model,structures);
        controller.setStart("nodeOne");
        controller.route();
        controller.errorAccepted();
        Mockito.verify(listener,Mockito.times(2)).update(captor.capture());
        List<String> expected = new ArrayList<>();
        expected.add(null);
        expected.add("The end location is not recognised.");
        for(int i=0;i<captor.getAllValues().size();i++){
            Assert.assertEquals(expected.get(i), captor.getAllValues().get(i).getError());
        }
    }

    @Test
    public void startUpTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<Structure> structures=new ArrayList<>();
        List<INode> graph = new ArrayList<>();
        INode nodeOne = new Node("nodeOne",false);
        INode nodeTwo = new Node("nodeTwo",false);
        graph.add(nodeOne);
        graph.add(nodeTwo);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        IController controller = new Controller(model,structures);
        controller.startUp();
        Mockito.verify(listener).update(captor.capture());
    }

    @Test
    public void moveInsideTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<Structure> structures=new ArrayList<>();
        Structure structureOne = new Building(new SelectableEndPoint("structureOne",new ArrayList<LatLng>(),"planeName"));
        structures.add(structureOne);
        List<INode> graph = new ArrayList<>();
        INode nodeOne = new Node("structureOne",false);
        INode nodeTwo = new Node("nodeTwo",false);
        graph.add(nodeOne);
        graph.add(nodeTwo);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        IController controller = new Controller(model,structures);
        controller.setStart("structureOne");
        controller.focusOn(IController.Location.START);
        controller.goInside();
        controller.goInside();
        Mockito.verify(listener,Mockito.times(3)).update(captor.capture());
        List<String> expected = new ArrayList<>();
        expected.add("Outside");
        expected.add("structureOne1");
        expected.add("Outside");
        for(int i=0;i<captor.getAllValues().size();i++){
            Assert.assertEquals(expected.get(i), captor.getAllValues().get(i).getPlane());
        }
    }

    @Test
    public void areaSelectedTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<Structure> structures=new ArrayList<>();
        Structure structureOne = new Building(new SelectableEndPoint("structureOne",new ArrayList<LatLng>(),"planeName"));
        structures.add(structureOne);
        List<INode> graph = new ArrayList<>();
        INode nodeOne = new Node("structureOne",false);
        INode nodeTwo = new Node("nodeTwo",false);
        graph.add(nodeOne);
        graph.add(nodeTwo);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        IController controller = new Controller(model,structures);
        controller.focusOn(IController.Location.START);
        controller.areaSelected("structureOne");
        controller.focusOn(IController.Location.END);
        controller.areaSelected("nodeTwo");
        Mockito.verify(listener,Mockito.times(2)).update(captor.capture());
        Assert.assertEquals("structureOne", captor.getValue().getStartLoc());
        Assert.assertEquals("nodeTwo", captor.getValue().getEndLoc());
    }

    @Test
    public void routeTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<Structure> structures=new ArrayList<>();
        Structure structureOne = new Building(new SelectableEndPoint("structureOne",new ArrayList<LatLng>(),"planeName"));
        structures.add(structureOne);
        List<INode> graph = new ArrayList<>();
        Node nodeOne = new Node("nodeOne",false);
        Node nodeTwo = new Node("nodeTwo",true);
        Node nodeThree = new Node("nodeThree",true);
        Node nodeFour = new Node("nodeFour",false);
        nodeOne.addEdge(new Edge(nodeTwo,10));
        nodeOne.addEdge(new Edge(nodeThree,11));
        nodeTwo.addEdge(new Edge(nodeThree,4));
        nodeTwo.addEdge(new Edge(nodeFour,5));
        nodeThree.addEdge(new Edge(nodeFour,5));
        graph.add(nodeOne);
        graph.add(nodeTwo);
        graph.add(nodeThree);
        graph.add(nodeFour);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        IController controller = new Controller(model,structures);
        controller.startUp();
        controller.setStart("nodeOne");
        controller.setEnd("nodeFour");
        controller.route();
        Mockito.verify(listener,Mockito.times(4)).update(captor.capture());
        List<String> expected = new ArrayList<>();
        expected.add("nodeOne");
        expected.add("nodeTwo");
        expected.add("nodeFour");
        for(int i=0;i<captor.getValue().getRouteSelected().size();i++){
            Assert.assertEquals(expected.get(i), captor.getValue().getRouteSelected().get(i));
        }
    }

    @Test
    public void useStairsTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<Structure> structures=new ArrayList<>();
        Structure structureOne = new Building(new SelectableEndPoint("structureOne",new ArrayList<LatLng>(),"planeName"));
        structures.add(structureOne);
        List<INode> graph = new ArrayList<>();
        Node nodeOne = new Node("nodeOne",false);
        Node nodeTwo = new Node("nodeTwoStair",true);
        Node nodeThree = new Node("nodeThree",true);
        Node nodeFour = new Node("nodeFour",false);
        nodeOne.addEdge(new Edge(nodeTwo,10));
        nodeOne.addEdge(new Edge(nodeThree,11));
        nodeTwo.addEdge(new Edge(nodeThree,4));
        nodeTwo.addEdge(new Edge(nodeFour,5));
        nodeThree.addEdge(new Edge(nodeFour,5));
        graph.add(nodeOne);
        graph.add(nodeTwo);
        graph.add(nodeThree);
        graph.add(nodeFour);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        IController controller = new Controller(model,structures);
        controller.startUp();
        controller.setStart("nodeOne");
        controller.setEnd("nodeFour");
        controller.useStairs(false);
        controller.route();
        Mockito.verify(listener,Mockito.times(4)).update(captor.capture());
        List<String> expected = new ArrayList<>();
        expected.add("nodeOne");
        expected.add("nodeThree");
        expected.add("nodeFour");
        for(int i=0;i<captor.getValue().getRouteSelected().size();i++){
            Assert.assertEquals(expected.get(i), captor.getValue().getRouteSelected().get(i));
        }
    }

    @Test
    public void useLiftsTest(){
        ArgumentCaptor<RoutePlannerState> captor = ArgumentCaptor.forClass(RoutePlannerState.class);
        List<Structure> structures=new ArrayList<>();
        Structure structureOne = new Building(new SelectableEndPoint("structureOne",new ArrayList<LatLng>(),"planeName"));
        structures.add(structureOne);
        List<INode> graph = new ArrayList<>();
        Node nodeOne = new Node("nodeOne",false);
        Node nodeTwo = new Node("nodeTwoLift",true);
        Node nodeThree = new Node("nodeThree",true);
        Node nodeFour = new Node("nodeFour",false);
        nodeOne.addEdge(new Edge(nodeTwo,10));
        nodeOne.addEdge(new Edge(nodeThree,11));
        nodeTwo.addEdge(new Edge(nodeThree,4));
        nodeTwo.addEdge(new Edge(nodeFour,5));
        nodeThree.addEdge(new Edge(nodeFour,5));
        graph.add(nodeOne);
        graph.add(nodeTwo);
        graph.add(nodeThree);
        graph.add(nodeFour);
        RoutePlannerListener listener = Mockito.mock(RoutePlannerListener.class);
        IModel model = new DijkstrasModel(graph);
        model.addListener(listener);
        IController controller = new Controller(model,structures);
        controller.startUp();
        controller.setStart("nodeOne");
        controller.setEnd("nodeFour");
        controller.useLifts(false);
        controller.route();
        Mockito.verify(listener,Mockito.times(4)).update(captor.capture());
        List<String> expected = new ArrayList<>();
        expected.add("nodeOne");
        expected.add("nodeThree");
        expected.add("nodeFour");
        for(int i=0;i<captor.getValue().getRouteSelected().size();i++){
            Assert.assertEquals(expected.get(i), captor.getValue().getRouteSelected().get(i));
        }
    }

    /*

    public void useStairs(boolean stairUse);

    public void useLifts(boolean liftUse);
*/
}
