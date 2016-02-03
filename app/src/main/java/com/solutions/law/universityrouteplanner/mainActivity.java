package com.solutions.law.universityrouteplanner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.BufferedReader;


public class mainActivity extends FragmentActivity implements GoogleMap.OnCameraChangeListener {

    private static final String filename="saveFile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor);
        FileLoader loader = new FileLoader(fileName);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync();//Put view in here so on the callback the view gets the map.
    }


    @Override
    public void onCameraChange(CameraPosition position){
        //Instead of animating each time build new cameraPosition then animate to there
        //this will stop you missing it if they zoom and move out of bounds.
        CameraPosition newPosition;
        LatLng newLatLng =position.target;
        float newZoom=position.zoom;
        if(position.zoom<16) {
            newZoom=16;
        }else if(position.zoom>17){
            newZoom=17;
        }
        if(position.target.longitude<-4.250000){
            newLatLng=new LatLng(newLatLng.latitude,-4.250000);
        }
        if(position.target.longitude>-4.235812){
            newLatLng=new LatLng(newLatLng.latitude,-4.235812);
        }
        if(position.target.latitude<55.859147){
            newLatLng=new LatLng(55.859147,newLatLng.longitude);
        }
        if(position.target.latitude>55.864932){
            newLatLng=new LatLng(55.864932,newLatLng.longitude);
        }
        newPosition = new CameraPosition(newLatLng,newZoom,position.tilt,position.bearing);
        if(!newPosition.equals(position)) {
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newPosition));
        }
    }
}
