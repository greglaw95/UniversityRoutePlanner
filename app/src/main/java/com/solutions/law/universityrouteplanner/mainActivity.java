package com.solutions.law.universityrouteplanner;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends FragmentActivity{

    private GoogleMap mMap;
    List<Link> links;
    List<SteppingStone> steppingStones;
    List<EndPoint> endPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        FileReader read = new FileReader(this);
        links = read.getLinks();
        steppingStones=read.getSteppingStones();
        endPoints=read.getEndPoints();
        for (int i = 0; i < steppingStones.size() - 1; i++) {
            for (int j = i + 1; j < steppingStones.size(); j++) {
                links.add(approximateDuration(steppingStones.get(i),steppingStones.get(j)));
            }
        }
        GraphBuilder gb = new GraphBuilder(endPoints,steppingStones,links);
        Model model = new Model(gb.getGraph(),new DijkstrasAlgorithm());
        Controller controller = new Controller(model,endPoints);
        setContentView(R.layout.activity_outdoor);
        EditText textOne =(EditText) findViewById(R.id.locationOne);
        EditText textTwo =(EditText) findViewById(R.id.locationTwo);
        Button directionsButton = (Button) findViewById(R.id.directionsButton);
        View view = new View(controller,endPoints,steppingStones,textOne,textTwo,directionsButton);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(view);
        model.addListener(view);
    }

    private Link approximateDuration(SteppingStone one,SteppingStone two){
        Double duration=Math.abs(one.getCoOrd().latitude-two.getCoOrd().latitude);
        duration=duration+Math.abs(one.getCoOrd().longitude-two.getCoOrd().longitude);
        duration=duration*48093.36;
        return new Link(one.getName(),two.getName(),duration);
    }




}
