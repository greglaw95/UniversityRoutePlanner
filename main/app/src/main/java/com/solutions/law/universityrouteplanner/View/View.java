package com.solutions.law.universityrouteplanner.View;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.solutions.law.universityrouteplanner.Controller.IController;
import com.solutions.law.universityrouteplanner.Model.Update.RoutePlannerState;
import com.solutions.law.universityrouteplanner.View.Adapters.DirectionsClickAdapter;
import com.solutions.law.universityrouteplanner.View.Adapters.InOutClickAdapter;
import com.solutions.law.universityrouteplanner.View.Adapters.IndoorStateChangeAdapter;
import com.solutions.law.universityrouteplanner.View.Adapters.MarkerClickAdapter;
import com.solutions.law.universityrouteplanner.View.Adapters.PolygonClickAdapter;
import com.solutions.law.universityrouteplanner.View.Adapters.TextWatcherAdapter;
import com.solutions.law.universityrouteplanner.View.Adapters.TouchListenerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class View implements OnMapReadyCallback, RoutePlannerListener {

    private final IController controller;
    private List<MidPoint> midPoints;
    private Map<String,List<EndPoint>> endPointByPlane;
    private GoogleMap gMap;
    private AutoCompleteTextView startPoint;
    private AutoCompleteTextView endPoint;
    private RoutePlannerState prevState;
    private ErrorMessageDialogFragment errorReporter;
    private FragmentManager supportFragmentManager;
    private CameraLimiter cameraLimiter;
    private CameraPosition currentPosition;
    private IndoorStateChangeAdapter indoorStateChangeAdapter;

    public View(IController control, List<EndPoint> endPoints, List<MidPoint> midPoints, AutoCompleteTextView startPoint, AutoCompleteTextView endPoint, Button directionsButton,Button inOutButton,FragmentManager fragmentManager,Activity context) {
        this.midPoints = midPoints;
        this.controller = control;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.supportFragmentManager=fragmentManager;
        errorReporter=new ErrorMessageDialogFragment();
        errorReporter.setController(controller);
        String[] options = new String[endPoints.size()];
        for(int i=0;i<endPoints.size();i++){
            options[i]=endPoints.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, options);
        this.startPoint.setAdapter(adapter);
        this.endPoint.setAdapter(adapter);
        this.startPoint.addTextChangedListener(new TextWatcherAdapter(controller, IController.Location.START));
        this.startPoint.setOnTouchListener(new TouchListenerAdapter(controller, IController.Location.START));
        this.endPoint.addTextChangedListener(new TextWatcherAdapter(controller, IController.Location.END));
        this.endPoint.setOnTouchListener(new TouchListenerAdapter(controller, IController.Location.END));
        directionsButton.setOnClickListener(new DirectionsClickAdapter(controller));
        inOutButton.setOnClickListener(new InOutClickAdapter(controller));
        prevState = null;
        setUpEndpointByPlane(endPoints);
        cameraLimiter= new CameraLimiter(controller,this);
        indoorStateChangeAdapter= new IndoorStateChangeAdapter(controller,this);
    }


    private void setUpEndpointByPlane(List<EndPoint> endPoints){
        endPointByPlane= new HashMap<>();
        for(EndPoint current:endPoints){
            if(!endPointByPlane.containsKey(current.getPlane())){
                endPointByPlane.put(current.getPlane(),new ArrayList<EndPoint>());
            }
            endPointByPlane.get(current.getPlane()).add(current);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.setBuildingsEnabled(true);
        gMap.setIndoorEnabled(false);
        LatLng centre = new LatLng(55.861903,-4.244082);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centre, 16));
        gMap.setOnIndoorStateChangeListener(indoorStateChangeAdapter);
        gMap.setOnMarkerClickListener(new MarkerClickAdapter(controller));
        gMap.setOnCameraChangeListener(cameraLimiter);
        controller.startUp();
        currentPosition=gMap.getCameraPosition();
    }

    @Override
    public void update(RoutePlannerState state) {
        boolean newStart;
        boolean newEnd;
        boolean newRoute;
        boolean newPlane;
        boolean newError;
        if (prevState!=null) {
            newStart = (prevState.getStartLoc() == null || !prevState.getStartLoc().equals(state.getStartLoc()));
            newEnd = (prevState.getEndLoc() == null || !prevState.getEndLoc().equals(state.getEndLoc()));
            newRoute = prevState.getRouteSelected() == null || !prevState.getRouteSelected().equals(state.getRouteSelected());
            newPlane = prevState.getPlane() == null || !prevState.getPlane().equals(state.getPlane());
            newError = prevState.getError() == null || !prevState.getError().equals(state.getError());
        }else {
            newStart= state.getStartLoc()!=null;
            newEnd=state.getEndLoc()!=null;
            newRoute=state.getRouteSelected()!=null;
            newPlane=state.getPlane()!=null;
            newError=state.getError()!=null;
        }
            if (newStart) {
                updateText(startPoint, state.getStartLoc());
            }
            if (newEnd) {
                updateText(endPoint, state.getEndLoc());
            }
            if (newStart || newEnd || newRoute || newPlane) {
                updateMapAdditions(state.getStartLoc(), state.getEndLoc(), state.getRouteSelected(), state.getPlane());
            }
            if(newPlane){
                updateMap(state.getPlane(), state.getLevel());
            }
            if(newError){
                updateError(state.getError());
            }
        prevState = state;
    }

    private void updateText(EditText box, String newText) {
        if (newText != null) {
            box.setText(newText.toCharArray(), 0, newText.length());
        }
    }

    private void updateMap(String plane,String level){
        boolean levelSet;
        gMap.setOnPolygonClickListener(new PolygonClickAdapter(endPointByPlane.get(plane),controller));
        if(plane.equals("Outside")){
            gMap.getUiSettings().setIndoorLevelPickerEnabled(false);
            gMap.setIndoorEnabled(false);
        }else {
            gMap.getUiSettings().setIndoorLevelPickerEnabled(true);
            indoorStateChangeAdapter.deactivate();
            gMap.setIndoorEnabled(true);
            levelSet=false;
            cameraLimiter.onCameraChange(currentPosition);
            while(levelSet==false){
                if(gMap.getFocusedBuilding()!=null) {
                    try {
                        levelSet=true;
                        gMap.getFocusedBuilding().getLevels().get(gMap.getFocusedBuilding().getLevels().size() - Integer.parseInt(level)).activate();
                    } catch (Exception e) {

                    }
                }
            }
            indoorStateChangeAdapter.activate();
        }
    }

    private void updateMapAdditions(String start, String end, List<String> routeSelected,String plane) {
        List<MidPoint> routeDisplay;
        gMap.clear();
        if(endPointByPlane.get(plane)!=null) {
            for (EndPoint current : endPointByPlane.get(plane)) {
                if ((start != null && current.getName().equals(start)) || (end != null && current.getName().equals(end))) {
                    gMap.addPolygon(new PolygonOptions().addAll(current.getCoOrds()).strokeColor(Color.RED).geodesic(true).clickable(true));
                } else {
                    gMap.addPolygon(new PolygonOptions().addAll(current.getCoOrds()).strokeColor(Color.BLUE).geodesic(true).clickable(true));
                }
            }
        }
        if (routeSelected == null) {
            return;
        }
        routeDisplay = new ArrayList<>();
        for (String nextStep : routeSelected) {
            for (MidPoint mp : midPoints) {
                if (mp.getName().equals(nextStep)) {
                    routeDisplay.add(mp);
                    break;
                }
            }
        }
        if (routeDisplay.size() > 1) {
            for (int i = 1; i < routeDisplay.size(); i++) {
                if(routeDisplay.get(i-1).getPlane().equals(plane)&&routeDisplay.get(i).getPlane().equals(plane)) {
                    drawLine(routeDisplay.get(i - 1), routeDisplay.get(i));
                }else if(routeDisplay.get(i-1).getPlane().equals(plane)){
                    gMap.addMarker(new MarkerOptions().position(routeDisplay.get(i-1).getCoOrd()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(routeDisplay.get(i).getPlane()));
                }else if(routeDisplay.get(i).getPlane().equals(plane)){
                    gMap.addMarker(new MarkerOptions().position(routeDisplay.get(i).getCoOrd()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title(routeDisplay.get(i-1).getPlane()));
                }
            }
        }
    }

    private void drawLine(MidPoint one, MidPoint two) {
        gMap.addPolyline(new PolylineOptions().add(one.getCoOrd(),two.getCoOrd()).width(5).color(Color.RED));
    }

    private void updateError(String error){
        if(error!=null) {
            errorReporter.setMessage(error);
            errorReporter.show(supportFragmentManager, "Error");
        }
    }

    public void setPosition(CameraPosition position){
        currentPosition=position;
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }

    public IndoorBuilding getFocusedBuilding(){
        return gMap.getFocusedBuilding();
    }

    public String getLevel(){
        return prevState.getLevel();
    }

}
