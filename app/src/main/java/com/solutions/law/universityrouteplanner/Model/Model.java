package com.solutions.law.universityrouteplanner.Model;

import android.content.Context;
import android.os.Environment;

import com.google.android.gms.maps.model.LatLng;
import com.solutions.law.universityrouteplanner.View.RoutePlannerListener;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    private List<Midpoint> oldPoints;
    private Midpoint currentPoint;


    public Model(){
        String[] blah;
        listeners=new ArrayList<>();
        oldPoints=new ArrayList<>();
        File file =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"newMidpoints.txt");
        String line;
        try {
            if (file.exists()) {
                BufferedReader input = new BufferedReader(new FileReader(file));
                line = input.readLine();
                while (line != null) {
                    blah=line.split(",");
                    oldPoints.add(new Midpoint(blah[0],blah[1],new LatLng(Double.parseDouble(blah[2]),Double.parseDouble(blah[3]))));
                    line = input.readLine();
                }
            }
        }catch(IOException e){

        }
        currentPoint=new Midpoint("Default","Default",new LatLng(24,24));
    }

    @Override
    public void selectedPoint(String name){
        oldPoints.add(currentPoint);
        for(Midpoint current:oldPoints){
            if(current.getRoom().equals(name)){
                currentPoint=current;
            }
        }
        oldPoints.remove(currentPoint);
        alertAll();
    }

    public void addPoint(LatLng newLoc){
        currentPoint.setPoint(newLoc);
        alertAll();
    }


    public void select(){
        if(currentPoint==null){
            return;
        }
        if(!currentPoint.getRoom().equals("Default")) {
            oldPoints.add(currentPoint);
        }
        currentPoint=new Midpoint("Default","Default",new LatLng(24,24));
        try {
            File file =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"newMidpoints.txt");
            BufferedWriter outputStream = new BufferedWriter(new FileWriter(file));
            for(Midpoint current:oldPoints) {
                outputStream.write(current.getRoom()+","+current.getPlane()+","+current.getPoint().latitude+","+current.getPoint().longitude);
                outputStream.newLine();
            }
            outputStream.close();
            clear();
            alertAll();
        }catch(IOException e){
            String newString=e.getMessage();
        }
    }

    public void clear(){
        currentPoint=new Midpoint("Default","Default",new LatLng(24,24));
        select();
        alertAll();
    }

    public void currentRoom(String room){
        if(!room.equals(currentPoint.getRoom())) {
            currentPoint.setRoom(room);
            alertAll();
        }
    }

    public void currentPlane(String plane){
        if(!plane.equals(currentPoint.getPlane())) {
            currentPoint.setPlane(plane);
            alertAll();
        }
    }

    public void addListener(RoutePlannerListener listener){
        listeners.add(listener);
    }

    private void alertAll(){
        for(RoutePlannerListener listener:listeners){
            listener.update(new RoutePlannerState(currentPoint.getPlane(),currentPoint.getRoom(),oldPoints,currentPoint));
        }
    }

}
