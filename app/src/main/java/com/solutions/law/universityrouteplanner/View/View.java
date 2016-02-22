package com.solutions.law.universityrouteplanner.View;

import android.graphics.Color;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.solutions.law.universityrouteplanner.Controller.IController;
import com.solutions.law.universityrouteplanner.Model.RoutePlannerState;


/**
 * Created by kbb12155 on 10/02/16.
 */
public class View implements OnMapReadyCallback, RoutePlannerListener {

    private final IController controller;
    private GoogleMap gMap;

    public View(IController control, EditText plane, EditText room, Button selectButton,Button clearButton) {
        this.controller = control;
        plane.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                controller.setPlane(v.getText());
                return false;
            }
        });
        room.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                controller.setRoom(v.getText());
                return false;
            }
        });
        selectButton.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(android.view.View v, MotionEvent event) {
                controller.select();
                return false;
            }
        });
        clearButton.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(android.view.View v, MotionEvent event) {
                controller.clear();
                return false;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.setBuildingsEnabled(true);
        LatLng centre = new LatLng(55.861903,-4.244082);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centre,16));
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                controller.addPoint(latLng);
            }
        });
    }

    @Override
    public void update(RoutePlannerState state) {
        gMap.clear();
        if(state.getPoints().size()>0) {
            gMap.addPolygon(new PolygonOptions().addAll(state.getPoints()).strokeColor(Color.BLUE).fillColor(Color.CYAN).geodesic(true).clickable(true));
        }
    }


}
