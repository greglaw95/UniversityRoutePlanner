package com.solutions.law.universityrouteplanner.Model;

import android.os.Environment;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.solutions.law.universityrouteplanner.Model.Graph.IEdge;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;
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
    private INode startLoc;
    private INode endLoc;
    private List<INode> graph;
    private List<String> connectedNodes;
    private String error;
    private String plane;
    private Double weight;
    private List<String> oldLinks;

    public Model(List<INode> graph){
        oldLinks= new ArrayList<>();
        this.graph=graph;
        listeners=new ArrayList<>();
        startLoc=null;
        endLoc=null;
        error=null;
        weight=null;
        plane="Outside";
        connectedNodes= new ArrayList<>();
        File file =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"newLinks.txt");
        String line;
        try {
            if (file.exists()) {
                BufferedReader input = new BufferedReader(new FileReader(file));
                line = input.readLine();
                while (line != null) {
                    oldLinks.add(line);
                    line = input.readLine();
                }
            }
        }catch(IOException e){

        }
    }

    @Override
    public void addLink() {
        oldLinks.add(startLoc.getName()+","+endLoc.getName()+","+weight);
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
    public String getPlane(){
        return plane;
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
        for(INode current:graph){
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
        for(INode current:graph){
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
            listener.update(new ModelState(startName,endName,connectedNodes,error,plane));
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
