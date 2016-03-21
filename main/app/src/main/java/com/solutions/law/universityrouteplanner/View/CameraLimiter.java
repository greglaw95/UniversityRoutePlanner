package com.solutions.law.universityrouteplanner.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.solutions.law.universityrouteplanner.Controller.IController;
import com.solutions.law.universityrouteplanner.Controller.Structure;
import com.solutions.law.universityrouteplanner.View.View;

/**
 * Created by kbb12 on 11/03/2016.
 */
public class CameraLimiter implements GoogleMap.OnCameraChangeListener {
    private IController controller;
    private View view;

    public CameraLimiter(IController controller,View view){
        this.controller=controller;
        this.view =view;
    }


    @Override
    public void onCameraChange(CameraPosition position) {
        Structure currentStructure = controller.getStructure();
        CameraPosition newPosition=position;
        if(currentStructure!=null){
            float newZoom=position.zoom;
            Double newLng=position.target.longitude;
            Double newLat=position.target.latitude;
            if(position.zoom<currentStructure.getMinZoomAllowed()){
                newZoom=currentStructure.getMinZoomAllowed();
            }
            double halfRange=0.5*(360/(Math.pow(2,newZoom)));
            if(position.target.longitude<currentStructure.getMinLngAllowed()+halfRange){
                newLng=currentStructure.getMinLngAllowed()+halfRange;
            }
            if(position.target.longitude>currentStructure.getMaxLngAllowed()-halfRange){
                if(newLng.equals(currentStructure.getMinLngAllowed())){
                    newLng=position.target.longitude;
                }else {
                    newLng = currentStructure.getMaxLngAllowed()-halfRange;
                }
            }
            if(position.target.latitude<currentStructure.getMinLatAllowed()+halfRange){
                newLat=currentStructure.getMinLatAllowed()+halfRange;
            }
            if(position.target.latitude>currentStructure.getMaxLatAllowed()-halfRange){
                if(newLat.equals(currentStructure.getMinLatAllowed())){
                    newLat=position.target.latitude;
                }else {
                    newLat=currentStructure.getMaxLatAllowed()-halfRange;
                }
            }
            newPosition = new CameraPosition(new LatLng(newLat,newLng),newZoom,position.tilt,position.bearing);
        }
        if(newPosition!=null) {
            view.setPosition(newPosition);
        }
    }
}
