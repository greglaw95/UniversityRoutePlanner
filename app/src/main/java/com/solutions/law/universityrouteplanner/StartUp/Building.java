package com.solutions.law.universityrouteplanner.StartUp;

import com.google.android.gms.maps.model.LatLng;
import com.solutions.law.universityrouteplanner.Controller.Structure;


/**
 * Created by kbb12 on 27/02/2016.
 */
public class Building implements Structure {
    private String name;
    private Float minZoomAllowed;
    private Double minLatAllowed;
    private Double maxLatAllowed;
    private Double minLngAllowed;
    private Double maxLngAllowed;
    private int level;


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Building(SelectableEndPoint basis){
        this.name=basis.getName();
        this.level=1;

        this.minLatAllowed=Double.MAX_VALUE;
        this.maxLatAllowed=Double.MAX_VALUE*-1;
        this.minLngAllowed=Double.MAX_VALUE;
        this.maxLngAllowed=Double.MAX_VALUE*-1;
        for(LatLng current:basis.getCoOrds()){
            if(current.latitude<minLatAllowed){
                minLatAllowed=current.latitude;
            }
            if(current.latitude>maxLatAllowed){
                maxLatAllowed=current.latitude;
            }
            if(current.longitude<minLngAllowed){
                minLngAllowed=current.longitude;
            }
            if(current.longitude>maxLngAllowed){
                maxLngAllowed=current.longitude;
            }
        }
        this.minZoomAllowed=calcZoom();
    }

    private Float calcZoom(){
        Double latRange=Math.abs(maxLatAllowed-minLatAllowed);
        Double lngRange=Math.abs(maxLngAllowed-minLngAllowed);
        Double smallerRange;
        if(latRange>lngRange){
            smallerRange=lngRange;
        }else{
            smallerRange=latRange;
        }
        return logBaseTwo(360/smallerRange);
    }

    public float logBaseTwo(double number){
        return (float) (Math.log(number)/Math.log(2));
    }

    public String getName(){
        return name;
    }


    @Override
    public float getMinZoomAllowed() {
        return minZoomAllowed;
    }

    @Override
    public double getMinLatAllowed() {
        return minLatAllowed;
    }

    @Override
    public double getMaxLatAllowed() {
        return maxLatAllowed;
    }

    @Override
    public double getMinLngAllowed() {
        return minLngAllowed;
    }

    @Override
    public double getMaxLngAllowed() {
        return maxLngAllowed;
    }
}
