package com.solutions.law.universityrouteplanner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;
import java.util.Map;

/**
 * Created by kbb12155 on 03/02/16.
 */
public class View implements OnMapReadyCallback {
    Map<EID,List<LatLng>> elementLocations;
    GoogleMap gMap;

    public View(Map<EID,List<LatLng>> elementLocations){
        this.elementLocations=elementLocations;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng centre = new LatLng(55.861903, -4.244082);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centre, 16));
        /*
            *Make this the controller.
            *gMap.setOnCameraChangeListener(this);
         */
    }

}
