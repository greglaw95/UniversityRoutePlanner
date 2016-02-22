package com.solutions.law.universityrouteplanner.Model;

import android.content.Context;
import android.os.Environment;
import android.provider.Settings;

import com.google.android.gms.maps.model.LatLng;
import com.solutions.law.universityrouteplanner.View.RoutePlannerListener;


import java.io.File;
import java.io.FileOutputStream;
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


    public Model(Context context){
        this.context=context;
        listeners=new ArrayList<>();
        points=new ArrayList<>();
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
        try {
            FileOutputStream outputStream = openFileOutput("Filename.txt",context.MODE_WORLD_WRITEABLE);
            outputStream.write(line.toString().getBytes());
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
