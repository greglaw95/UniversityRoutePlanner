package com.solutions.law.universityrouteplanner.View;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.solutions.law.universityrouteplanner.Controller.IController;
import com.solutions.law.universityrouteplanner.Model.Midpoint;
import com.solutions.law.universityrouteplanner.Model.RoutePlannerState;


/**
 * Created by kbb12155 on 10/02/16.
 */
public class View implements OnMapReadyCallback, RoutePlannerListener {

    private final IController controller;
    private GoogleMap gMap;
    private EditText plane;
    private EditText room;

    public View(IController control, EditText plane, EditText room, final Button selectButton,Button clearButton) {
        this.controller = control;
        this.plane=plane;
        this.room=room;
        plane.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                controller.setPlane(s.toString());
            }
        });
        room.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                controller.setRoom(s.toString());
            }
        });
        selectButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                controller.select();
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
        plane.getText().clear();
        plane.getText().append(state.getPlane());
        room.setText(state.getNewPoint().getRoom());
        gMap.clear();
        for(Midpoint point:state.getPoints()) {
            gMap.addMarker(new MarkerOptions().position(point.getPoint()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title(point.getRoom()));
        }
        if(!state.getPoints().contains(state.getNewPoint())){
            gMap.addMarker(new MarkerOptions().position(state.getNewPoint().getPoint()));
        }
    }


}
