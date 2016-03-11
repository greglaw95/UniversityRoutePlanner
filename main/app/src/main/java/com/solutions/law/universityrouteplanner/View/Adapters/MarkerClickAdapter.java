package com.solutions.law.universityrouteplanner.View.Adapters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.solutions.law.universityrouteplanner.Controller.IController;

/**
 * Created by kbb12 on 11/03/2016.
 */
public class MarkerClickAdapter implements GoogleMap.OnMarkerClickListener {
    private IController controller;

    public MarkerClickAdapter(IController controller){
        this.controller=controller;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String fullPlane = marker.getTitle();
        int lastChar=findLastChar(fullPlane);
        if(lastChar<fullPlane.length()-1) {
            String structure = fullPlane.substring(0,lastChar+1);
            String level = fullPlane.substring(lastChar + 1);
            controller.setStructure(structure);
            controller.setLevel(level);
        }else{
            controller.setStructure(null);
        }
        return false;
    }

    private int findLastChar(String title){
        char[] blah = title.toCharArray();
        int lastCharAt=0;
        for(int i=0;i<blah.length;i++){
            if(!Character.isDigit(blah[i])){
                lastCharAt=i;
            }
        }
        return lastCharAt;
    }
}
