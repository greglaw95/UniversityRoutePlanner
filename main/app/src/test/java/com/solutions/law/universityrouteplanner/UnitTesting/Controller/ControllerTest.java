package com.solutions.law.universityrouteplanner.UnitTesting.Controller;

import com.solutions.law.universityrouteplanner.Controller.Controller;
import com.solutions.law.universityrouteplanner.Controller.IController;
import com.solutions.law.universityrouteplanner.Controller.NodeCheck;
import com.solutions.law.universityrouteplanner.Controller.Structure;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;
import com.solutions.law.universityrouteplanner.Model.IModel;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 26/03/2016.
 */
public class ControllerTest {
    @Test
    public void setStartTest(){
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = Mockito.mock(List.class);
        IController controller = new Controller(model,structures);
        controller.setStart("newStart");
        Mockito.verify(model).startLoc(captor.capture());
        Assert.assertEquals("newStart",captor.getValue());
    }

    @Test
    public void setEndTest(){
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = Mockito.mock(List.class);
        IController controller = new Controller(model,structures);
        controller.setEnd("newEnd");
        Mockito.verify(model).endLoc(captor.capture());
        Assert.assertEquals("newEnd",captor.getValue());
    }

    @Test
    public void errorAcceptedTest(){
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = Mockito.mock(List.class);
        IController controller = new Controller(model,structures);
        controller.errorAccepted();
        Mockito.verify(model).setError(null);
    }

    @Test
    public void goInsideTestOutsideWithNoStructureSelected(){
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        IController controller = new Controller(model,structures);
        controller.goInside();
        Mockito.verify(model).setError(captor.capture());
    }

    @Test
    public void goInsideTestOutsideWithStartStructureSelected(){
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        Structure startStruct = Mockito.mock(Structure.class);
        Mockito.when(startStruct.getName()).thenReturn("StartStructure");
        Structure endStruct = Mockito.mock(Structure.class);
        Mockito.when(endStruct.getName()).thenReturn("EndStructure");
        structures.add(startStruct);
        structures.add(endStruct);
        IController controller = new Controller(model,structures);
        Mockito.when(model.getStart()).thenReturn("StartStructure");
        Mockito.when(model.getEnd()).thenReturn("EndStructure");
        controller.focusOn(IController.Location.START);
        controller.goInside();
        Mockito.verify(model).setPlane("StartStructure", null);
    }

    @Test
    public void goInsideTestOutsideWithEndStructureSelected(){
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        Structure startStruct = Mockito.mock(Structure.class);
        Mockito.when(startStruct.getName()).thenReturn("StartStructure");
        Structure endStruct = Mockito.mock(Structure.class);
        Mockito.when(endStruct.getName()).thenReturn("EndStructure");
        structures.add(startStruct);
        structures.add(endStruct);
        IController controller = new Controller(model,structures);
        Mockito.when(model.getStart()).thenReturn("StartStructure");
        Mockito.when(model.getEnd()).thenReturn("EndStructure");
        controller.focusOn(IController.Location.END);
        controller.goInside();
        Mockito.verify(model).setPlane("EndStructure", null);
    }

    @Test
    public void goInsideTestInside(){
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        Structure startStruct = Mockito.mock(Structure.class);
        Mockito.when(startStruct.getName()).thenReturn("StartStructure");
        Structure endStruct = Mockito.mock(Structure.class);
        Mockito.when(endStruct.getName()).thenReturn("EndStructure");
        structures.add(startStruct);
        structures.add(endStruct);
        IController controller = new Controller(model,structures);
        Mockito.when(model.getStart()).thenReturn("StartStructure");
        Mockito.when(model.getEnd()).thenReturn("EndStructure");
        controller.focusOn(IController.Location.END);
        controller.goInside();
        controller.goInside();
        Mockito.verify(model).setPlane("EndStructure", null);
        Mockito.verify(model).setPlane("Outside","");
    }

    @Test
    public void setLevelTest(){
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        Structure startStruct = Mockito.mock(Structure.class);
        Mockito.when(startStruct.getName()).thenReturn("StartStructure");
        Structure endStruct = Mockito.mock(Structure.class);
        Mockito.when(endStruct.getName()).thenReturn("EndStructure");
        structures.add(startStruct);
        structures.add(endStruct);
        IController controller = new Controller(model,structures);
        Mockito.when(model.getStart()).thenReturn("StartStructure");
        Mockito.when(model.getEnd()).thenReturn("EndStructure");
        controller.focusOn(IController.Location.END);
        controller.goInside();
        Mockito.when(endStruct.getLevel()).thenReturn("4");
        controller.setLevel("4");
        Mockito.verify(model).setPlane("EndStructure", null);
        Mockito.verify(endStruct).setLevel("4");
        Mockito.verify(model).setPlane("EndStructure","4");
    }

    @Test
    public void routeTest(){
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        IController controller = new Controller(model,structures);
        controller.route();
        Mockito.verify(model).newRoute();
    }

    @Test
    public void startUpTest(){
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        IController controller = new Controller(model,structures);
        controller.startUp();
        Mockito.verify(model).start(Mockito.any(NodeCheck.class));
    }

    @Test
    public void avoidStairsTest(){
        ArgumentCaptor<NodeCheck> captor = ArgumentCaptor.forClass(NodeCheck.class);
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        IController controller = new Controller(model,structures);
        controller.startUp();
        Mockito.verify(model).start(captor.capture());
        controller.useStairs(false);
        NodeCheck check = captor.getValue();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.getName()).thenReturn("stair");
        Mockito.when(node.canPassThrough()).thenReturn(true);
        Assert.assertFalse(check.canUse(node));
    }

    @Test
    public void useStairsTest(){
        ArgumentCaptor<NodeCheck> captor = ArgumentCaptor.forClass(NodeCheck.class);
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        IController controller = new Controller(model,structures);
        controller.startUp();
        Mockito.verify(model).start(captor.capture());
        controller.useStairs(true);
        NodeCheck check = captor.getValue();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.getName()).thenReturn("stair");
        Mockito.when(node.canPassThrough()).thenReturn(true);
        Assert.assertTrue(check.canUse(node));
    }

    @Test
    public void avoidLiftsTest(){
        ArgumentCaptor<NodeCheck> captor = ArgumentCaptor.forClass(NodeCheck.class);
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        IController controller = new Controller(model,structures);
        controller.startUp();
        Mockito.verify(model).start(captor.capture());
        controller.useLifts(false);
        NodeCheck check = captor.getValue();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.getName()).thenReturn("lift");
        Mockito.when(node.canPassThrough()).thenReturn(true);
        Assert.assertFalse(check.canUse(node));
    }

    @Test
    public void useLiftsTest(){
        ArgumentCaptor<NodeCheck> captor = ArgumentCaptor.forClass(NodeCheck.class);
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        IController controller = new Controller(model,structures);
        controller.startUp();
        Mockito.verify(model).start(captor.capture());
        controller.useLifts(true);
        NodeCheck check = captor.getValue();
        INode node = Mockito.mock(INode.class);
        Mockito.when(node.getName()).thenReturn("lift");
        Mockito.when(node.canPassThrough()).thenReturn(true);
        Assert.assertTrue(check.canUse(node));
    }

    @Test
    public void startSelectedTest(){
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        IController controller = new Controller(model,structures);
        controller.focusOn(IController.Location.START);
        controller.areaSelected("name");
        Mockito.verify(model).startLoc("name");
    }

    @Test
    public void endSelectedTest(){
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        IController controller = new Controller(model,structures);
        controller.focusOn(IController.Location.END);
        controller.areaSelected("name");
        Mockito.verify(model).endLoc("name");
    }


    @Test
    public void setStructureNullTest(){
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        IController controller = new Controller(model,structures);
        controller.setStructure(null);
        Mockito.verify(model).setPlane("Outside", "");
        org.junit.Assert.assertEquals(null, controller.getStructure());
    }

    @Test
    public void setStructureTest(){
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        Structure expected = Mockito.mock(Structure.class);
        Structure notExpected = Mockito.mock(Structure.class);
        Mockito.when(expected.getName()).thenReturn("expected");
        Mockito.when(notExpected.getName()).thenReturn("notExpected");
        structures.add(expected);
        structures.add(notExpected);
        IController controller = new Controller(model,structures);
        controller.setStructure("expected");
        org.junit.Assert.assertEquals(expected, controller.getStructure());
    }

    @Test
    public void setStructureNotThereTest(){
        IModel model= Mockito.mock(IModel.class);
        List<Structure> structures = new ArrayList<>();
        Structure expected = Mockito.mock(Structure.class);
        Structure notExpected = Mockito.mock(Structure.class);
        Mockito.when(expected.getName()).thenReturn("expected");
        Mockito.when(notExpected.getName()).thenReturn("notExpected");
        structures.add(expected);
        structures.add(notExpected);
        IController controller = new Controller(model,structures);
        controller.setStructure("expected");
        controller.setStructure("otherName");
        org.junit.Assert.assertEquals(null,controller.getStructure());
    }

}
