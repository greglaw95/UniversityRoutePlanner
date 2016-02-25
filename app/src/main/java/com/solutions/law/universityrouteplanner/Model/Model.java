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

    private String plane;
    private String room;
    private List<RoutePlannerListener> listeners;
    private List<Midpoint> oldPoints;
    private Midpoint newPoint;


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
    }

    public void addPoint(LatLng newLoc){
        newPoint= new Midpoint(room,plane,newLoc);
        alertAll();
    }


    public void select(){
        oldPoints.add(newPoint.copy());
        newPoint=null;
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
        newPoint=null;
        alertAll();
    }

    public void currentRoom(String room){
        this.room=room;
    }

    public void currentPlane(String plane){
        this.plane=plane;
    }

    public void addListener(RoutePlannerListener listener){
        listeners.add(listener);
    }

    private void alertAll(){
        for(RoutePlannerListener listener:listeners){
            listener.update(new RoutePlannerState(plane,room,oldPoints,newPoint));
        }
    }

}
