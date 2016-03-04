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
    private List<LatLng> points;
    private Context context;
    private List<String> entries;


    public Model(Context context){
        this.context=context;
        listeners=new ArrayList<>();
        points=new ArrayList<>();
        entries=new ArrayList<>();
        File file =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"newEndpoints.txt");
        String line;
        try {
            if (file.exists()) {
                BufferedReader input = new BufferedReader(new FileReader(file));
                line = input.readLine();
                while (line != null) {
                    entries.add(line);
                    line = input.readLine();
                }
            }
        }catch(IOException e){

        }
    }

    public void addPoint(LatLng newPoint){
        points.add(newPoint);
        alertAll();
    }


    public void select(){
        StringBuilder line = new StringBuilder();
        line.append(room);
        line.append(",");
        line.append(plane);
        line.append(",");
        line.append(points.size());
        for(LatLng current:points){
            line.append(",");
            line.append(current.latitude);
            line.append(",");
            line.append(current.longitude);
        }
        entries.add(line.toString());
        try {
            File file =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"newEndpoints.txt");
            BufferedWriter outputStream = new BufferedWriter(new FileWriter(file));
            for(String current:entries) {
                outputStream.write(current);
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
        points.clear();
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
            listener.update(new RoutePlannerState(plane,room,points));
        }
    }

}
