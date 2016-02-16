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
    Map<LatLngPair,List<LatLngPair>> hiddenRoutes;
    int requestsLeftToReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        FileReader read = new FileReader(this);
        links = read.getLinks();
        steppingStones=read.getSteppingStones();
        endPoints=read.getEndPoints();
        hiddenRoutes=new HashMap<>();
        requestsLeftToReturn=factorial(steppingStones.size() - 1);
        for (int i = 0; i < steppingStones.size() - 1; i++) {
            for (int j = i + 1; j < steppingStones.size(); j++) {
                new OutdoorDirectionsFinder(steppingStones.get(i), steppingStones.get(j), this).execute();
            }
        }
    }

    public synchronized void finished(OutdoorDirectionsFinder directions){
        links.add(new Link(directions.getStart().getName(),directions.getEnd().getName(),directions.getDuration()));
        hiddenRoutes.put(directions.getBothEnds(),directions.getRoute());
        requestsLeftToReturn--;
        if(requestsLeftToReturn<1){
            finishCreation();
        }
    }

    private void finishCreation(){
        Log.d("Just need a break","");
        GraphBuilder gb = new GraphBuilder(endPoints,steppingStones,links);
        Model model = new Model();
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

    public int factorial(int start){
        if(start==1){
            return 1;
        }else{
            return start*factorial(start-1);
        }
    }


}
