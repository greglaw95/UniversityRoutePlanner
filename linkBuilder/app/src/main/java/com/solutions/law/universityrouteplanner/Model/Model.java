package com.solutions.law.universityrouteplanner.Model;

import android.os.Environment;
import android.util.Log;


import com.google.android.gms.maps.model.CameraPosition;
import com.solutions.law.universityrouteplanner.Model.Graph.Edge;
import com.solutions.law.universityrouteplanner.Model.Graph.IEdge;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;
import com.solutions.law.universityrouteplanner.Model.Graph.Node;
import com.solutions.law.universityrouteplanner.Model.Update.ModelState;
import com.solutions.law.universityrouteplanner.View.RoutePlannerListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class Model implements IModel {

    private List<RoutePlannerListener> listeners;
    private Node startLoc;
    private Node endLoc;
    private List<Node> graph;
    private List<String> connectedNodes;
    private String error;
    private String plane;
    private Double weight;
    private List<String> oldLinks;
    private CameraPosition position;

    public Model(List<Node> graph){
        oldLinks= new ArrayList<>();
        this.graph=graph;
        listeners=new ArrayList<>();
        startLoc=null;
        endLoc=null;
        error=null;
        weight=null;
        position=null;
        plane="Outside";
        connectedNodes= new ArrayList<>();
        File file =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"newLinks.txt");
        String line;
        Node first=null;
        Node second=null;
        try {
            if (file.exists()) {
                BufferedReader input = new BufferedReader(new FileReader(file));
                line = input.readLine();
                while (line != null) {
                    String[] parts=line.split(",");
                    for(Node current:graph){
                        if(current.getName().equals(parts[0])){
                            first=current;
                        }
                        if(current.getName().equals(parts[1])){
                            second=current;
                        }
                    }
                    first.addEdge(new Edge(second,Double.parseDouble(parts[2])));
                    second.addEdge(new Edge(first,Double.parseDouble(parts[2])));
                    oldLinks.add(line);
                    line = input.readLine();
                }
            }
        }catch(IOException e){

        }
    }

    @Override
    public void addLink() {
        startLoc.addEdge(new Edge(endLoc,weight));
        endLoc.addEdge(new Edge(startLoc,weight));
        connectedNodes.add(endLoc.getName());
        oldLinks.add(startLoc.getName() + "," + endLoc.getName() + "," + weight);
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "newlinks.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedWriter outputStream = new BufferedWriter(new FileWriter(file));
            for(String line:oldLinks){
                outputStream.write(line);
                outputStream.newLine();
            }
            outputStream.close();
        }catch (IOException e){
            Log.d("Just need a line","blah blah blah");
        }
    }

    @Override
    public void setPosition(CameraPosition position){
        this.position=position;
        alertAll();
    }

    @Override
    public String getStart(){
        return startLoc.getName();
    }

    @Override
    public String getEnd(){
        return endLoc.getName();
    }
    @Override
    public void setPlane(String plane){
        this.plane=plane;
        alertAll();
    }

    @Override
    public void addListener(RoutePlannerListener newListener){
        listeners.add(newListener);
    }

    @Override
    public void startLoc(String start){
        Boolean changed=false;
        for(Node current:graph){
            if(current.getName().equals(start)){
                if(!current.equals(startLoc)){
                    changed=true;
                    startLoc=current;
                }
            }
        }
        if(changed) {
            connectedNodes.clear();
            for(IEdge current:startLoc.getIEdges()){
                connectedNodes.add(current.getOtherINode().getName());
            }
            alertAll();
        }
    }

    @Override
    public void endLoc(String end){
        Boolean changed=false;
        for(Node current:graph){
            if(current.getName().equals(end)){
                if(!current.equals(endLoc)){
                    changed=true;
                    endLoc=current;
                }
            }
        }
        if(changed) {
            alertAll();
        }
    }


    @Override
    public CameraPosition getPosition() {
        return position;
    }

    private void alertAll(){
        String startName;
        String endName;
        if(startLoc==null){
            startName=null;
        }else{
            startName=startLoc.getName();
        }
        if(endLoc==null){
            endName=null;
        }else{
            endName=endLoc.getName();
        }
        for(RoutePlannerListener listener:listeners){
            listener.update(new ModelState(startName,endName,connectedNodes,error,plane,position));
        }
    }

    @Override
    public void start(){
        alertAll();
    }

    @Override
    public void setWeight(Double weight){
        this.weight=weight;
    }
}
