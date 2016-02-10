package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.model.CameraPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class Model {

    CameraPosition position;

    private List<RoutePlannerListener> listeners;

    public Model(){
        position=null;
        listeners=new ArrayList<RoutePlannerListener>();
    }

    public void addListener(RoutePlannerListener newListener){
        listeners.add(newListener);
    }

    public void moveTo(CameraPosition position){
        this.position=position;
        alertAll();
    }

    private void alertAll(){
        for(RoutePlannerListener listener:listeners){
            listener.update(new ModelState(position));
        }
    }
}
