package com.solutions.law.universityrouteplanner.StartUp;

import android.content.Context;
import android.util.Log;

import com.solutions.law.universityrouteplanner.Controller.BasicCheck;
import com.solutions.law.universityrouteplanner.Controller.CompositeCheck;
import com.solutions.law.universityrouteplanner.Controller.LiftCheck;
import com.solutions.law.universityrouteplanner.Controller.NodeCheck;
import com.solutions.law.universityrouteplanner.Controller.StairCheck;
import com.solutions.law.universityrouteplanner.Model.DijkstrasModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12 on 21/03/2016.
 */
public class RoutingPerformanceCheck {
    private List<Link> links;
    private List<SteppingStone> steppingStones;
    private List<SelectableEndPoint> endPoints;
    private GraphBuilder gb;
    private DijkstrasModel model;

    public RoutingPerformanceCheck(Context context){
        FileReader read = new FileReader(context);
        links = read.getLinks();
        steppingStones=read.getSteppingStones();
        endPoints=read.getEndPoints();
        Log.d("Testing", "Basic Check");
        NodeCheck check=new BasicCheck();
        testOne(check);
        testTwo(check);
        testThree(check);
        Log.d("Testing", "Basic Check+No Stairs");
        CompositeCheck compCheck=new CompositeCheck();
        compCheck.addCheck(new BasicCheck());
        compCheck.addCheck(new StairCheck());
        testOne(compCheck);
        testTwo(compCheck);
        testThree(compCheck);
        Log.d("Testing", "Basic Check+No Lifts");
        compCheck=new CompositeCheck();
        compCheck.addCheck(new BasicCheck());
        compCheck.addCheck(new LiftCheck());
        testOne(compCheck);
        testTwo(compCheck);
        testThree(compCheck);
        Log.d("Testing", "Basic Check+No Lifts+No Stairs");
        compCheck=new CompositeCheck();
        compCheck.addCheck(new BasicCheck());
        compCheck.addCheck(new StairCheck());
        compCheck.addCheck(new LiftCheck());
        testOne(compCheck);
        testTwo(compCheck);
        testThree(compCheck);
    }

    public void testOne(NodeCheck check){
        Log.e("Testing", "Test Two starting");
        gb=new GraphBuilder(endPoints,steppingStones,links);
        model=new DijkstrasModel(gb.getGraph());
        model.setCheck(check);
        Log.d("Testing", "Full graph results: No. of EndPoints: " + endPoints.size() + " No. of Midpoints: " + steppingStones.size() + " No. of Links: " + links.size());
        Log.d("Testing","Average Time To Find Route: " + mainTestBody(endPoints, steppingStones));
    }

    public void testTwo(NodeCheck check){
        List<SelectableEndPoint> removedEndPoints = new ArrayList<>();
        List<SelectableEndPoint> shorterEndPoints= new ArrayList<>();
        List<SteppingStone> removedSteppingStones= new ArrayList<>();
        List<SteppingStone> shorterSteppingStones=new ArrayList<>();
        List<Link> shorterLinks = new ArrayList<>(links);
        for(SelectableEndPoint endPoint:endPoints){
            if(endPoint.getPlane().contains("Livingstone")){
                removedEndPoints.add(endPoint);
            }else{
                shorterEndPoints.add(endPoint);
            }
        }
        for(SteppingStone steppingStone:steppingStones){
            if(steppingStone.getPlane().contains("Livingstone")){
                removedSteppingStones.add(steppingStone);
            }else{
                shorterSteppingStones.add(steppingStone);
            }
        }
        tidyLinks(removedEndPoints,removedSteppingStones,shorterLinks);
        gb=new GraphBuilder(shorterEndPoints,shorterSteppingStones,shorterLinks);
        model=new DijkstrasModel(gb.getGraph());
        model.setCheck(check);
        Log.d("Testing", "Without LT results: No. of EndPoints: " + shorterEndPoints.size() + " No. of Midpoints: " + shorterSteppingStones.size() + " No. of Links: " + shorterLinks.size());
        Log.d("Testing","Average Time To Find Route: " + mainTestBody(shorterEndPoints, shorterSteppingStones));
    }

    public void testThree(NodeCheck check){
        List<SelectableEndPoint> removedEndPoints = new ArrayList<>();
        List<SelectableEndPoint> shorterEndPoints= new ArrayList<>();
        List<SteppingStone> removedSteppingStones= new ArrayList<>();
        List<SteppingStone> shorterSteppingStones=new ArrayList<>();
        List<Link> shorterLinks = new ArrayList<>(links);
        for(SelectableEndPoint endPoint:endPoints){
            if(endPoint.getPlane().equals("Royal College4")){
                removedEndPoints.add(endPoint);
            }else{
                shorterEndPoints.add(endPoint);
            }
        }
        for(SteppingStone steppingStone:steppingStones){
            if(steppingStone.getPlane().equals("Royal College4")){
                removedSteppingStones.add(steppingStone);
            }else{
                shorterSteppingStones.add(steppingStone);
            }
        }
        tidyLinks(removedEndPoints,removedSteppingStones,shorterLinks);
        gb=new GraphBuilder(shorterEndPoints,shorterSteppingStones,shorterLinks);
        model=new DijkstrasModel(gb.getGraph());
        model.setCheck(check);
        Log.d("Testing", "Without RC2 results: No. of EndPoints: " + shorterEndPoints.size() + " No. of Midpoints: " + shorterSteppingStones.size() + " No. of Links: " + shorterLinks.size());
        Log.d("Testing","Average Time To Find Route: " + mainTestBody(shorterEndPoints, shorterSteppingStones));
    }

    public long mainTestBody(List<SelectableEndPoint> currentEndPoints,List<SteppingStone> currentSteppingStones){
        long totalTime=0;
        long startTime;
        long routeTime;
        int checks=0;
        for(SelectableEndPoint endPoint:currentEndPoints){
            model.startLoc(endPoint.getName());
            for(SelectableEndPoint endPoint1:currentEndPoints){
                if(!endPoint.equals(endPoint1)){
                    model.endLoc(endPoint1.getName());
                    startTime=System.currentTimeMillis();
                    model.findRoute();
                    routeTime=(System.currentTimeMillis()-startTime);
                    totalTime=totalTime+routeTime;
                    checks++;
                    //Log.v("Testing","Current Route Time: "+routeTime+" Check Number: "+checks);
                }
            }
            for(SteppingStone steppingStone:currentSteppingStones){
                model.endLoc(steppingStone.getName());
                startTime=System.currentTimeMillis();
                model.findRoute();
                routeTime=(System.currentTimeMillis()-startTime);
                totalTime=totalTime+routeTime;
                checks++;
                //Log.v("Testing","Current Route Time: "+routeTime+" Check Number: "+checks);
            }
        }
        for(SteppingStone steppingStone:currentSteppingStones){
            model.startLoc(steppingStone.getName());
            for(SteppingStone steppingStone1:currentSteppingStones){
                if(!steppingStone.equals(steppingStone1)){
                    model.endLoc(steppingStone1.getName());
                    startTime=System.currentTimeMillis();
                    model.findRoute();
                    routeTime=(System.currentTimeMillis()-startTime);
                    totalTime=totalTime+routeTime;
                    checks++;
                    //Log.v("Testing","Current Route Time: "+routeTime+" Check Number: "+checks);
                }
            }
            for(SelectableEndPoint endPoint:currentEndPoints){
                model.endLoc(endPoint.getName());
                startTime=System.currentTimeMillis();
                model.findRoute();
                routeTime=(System.currentTimeMillis()-startTime);
                totalTime=totalTime+routeTime;
                checks++;
                //Log.v("Testing","Current Route Time: "+routeTime+" Check Number: "+checks);
            }
        }
        return totalTime/checks;
    }

    private void tidyLinks(List<SelectableEndPoint> removedEndPoints,List<SteppingStone> removedSteppingStones,List<Link> shorterLinks){
        List<Link> removedLinks=new ArrayList<>();
        for(SelectableEndPoint endPoint:removedEndPoints){
            for(Link link:shorterLinks){
                if(link.getNodeOne().equals(endPoint.getName())||link.getNodeTwo().equals(endPoint.getName())){
                    removedLinks.add(link);
                }
            }
        }
        for(SteppingStone steppingStone:removedSteppingStones){
            for(Link link:shorterLinks){
                if(link.getNodeOne().equals(steppingStone.getName())||link.getNodeTwo().equals(steppingStone.getName())){
                    removedLinks.add(link);
                }
            }
        }
        shorterLinks.removeAll(removedLinks);
    }

}
