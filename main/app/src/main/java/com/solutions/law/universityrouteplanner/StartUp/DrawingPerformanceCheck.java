package com.solutions.law.universityrouteplanner.StartUp;


import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Switch;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.solutions.law.universityrouteplanner.Controller.IController;
import com.solutions.law.universityrouteplanner.Controller.Structure;
import com.solutions.law.universityrouteplanner.Model.DijkstrasModel;
import com.solutions.law.universityrouteplanner.Model.Graph.INode;
import com.solutions.law.universityrouteplanner.Model.Update.ModelState;
import com.solutions.law.universityrouteplanner.R;
import com.solutions.law.universityrouteplanner.View.EndPoint;
import com.solutions.law.universityrouteplanner.View.MidPoint;
import com.solutions.law.universityrouteplanner.View.View;

import java.util.ArrayList;
import java.util.List;

public class DrawingPerformanceCheck {

    private View view;
    private int numberOfEndpoints;
    private boolean complete;

    public DrawingPerformanceCheck(MainActivity activity,int numberOfEndpoints){
        complete=false;
        this.numberOfEndpoints=numberOfEndpoints;
        DijkstrasModel model = new DijkstrasModel(new ArrayList<INode>());
        IController controller = new FakeController(this);
        AutoCompleteTextView textOne = (AutoCompleteTextView) activity.findViewById(R.id.locationOne);
        AutoCompleteTextView textTwo = (AutoCompleteTextView) activity.findViewById(R.id.locationTwo);
        Switch stairs = (Switch) activity.findViewById(R.id.stairs);
        Switch lifts = (Switch) activity.findViewById(R.id.lifts);
        Button directionsButton = (Button) activity.findViewById(R.id.directionsButton);
        Button inOutButton = (Button) activity.findViewById(R.id.inOutButton);
        List<SelectableEndPoint> endPoints=new ArrayList<>();
        List<LatLng> baseCoOrds = new ArrayList<>();
        baseCoOrds.add(new LatLng(55.861,-4.246));
        baseCoOrds.add(new LatLng(55.862,-4.246));
        baseCoOrds.add(new LatLng(55.861,-4.247));
        baseCoOrds.add(new LatLng(55.862,-4.247));
        List<LatLng> currentCoOrds;
        for(int i=0;i<numberOfEndpoints;i++){
            currentCoOrds=new ArrayList<>();
            for(LatLng latLng:baseCoOrds){
                currentCoOrds.add(new LatLng(latLng.latitude+(i/1000),latLng.longitude+(i/1000)));
            }
            endPoints.add(new SelectableEndPoint("endpoint"+i,currentCoOrds,"Outside"));
        }
        view = new View(controller, new ArrayList<EndPoint>(endPoints), new ArrayList<MidPoint>(), textOne, textTwo, directionsButton, inOutButton, activity.getFragmentManager(), activity, stairs, lifts);
        SupportMapFragment mapFragment = (SupportMapFragment) activity.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(view);
    }

    public void callBack(){
        ModelState ms = new ModelState(null,null,null,null,"Outside","",null);
        long startTime=System.currentTimeMillis();
        view.update(ms);
        long drawTime=System.currentTimeMillis()-startTime;
        Log.d("Testing","Time taken to draw "+numberOfEndpoints+" polygons: "+drawTime);
        complete=true;
    }
}
