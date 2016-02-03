package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by kbb12155 on 03/02/16.
 */
public class Element {
    String elementName;
    List<LatLng> mapCoOrds;
    public Element(String[] elementInfo){
        elementName = elementInfo[0];
        for(int i=0;i<elementInfo.length;i=i+2){
            mapCoOrds.add(new LatLng(Integer.parseInt(elementInfo[i]),Integer.parseInt(elementInfo[i+1])));
        }
    }
}
