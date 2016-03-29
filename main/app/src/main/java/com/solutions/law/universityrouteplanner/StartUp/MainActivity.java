package com.solutions.law.universityrouteplanner.StartUp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Switch;

import com.google.android.gms.maps.SupportMapFragment;
import com.solutions.law.universityrouteplanner.Controller.Controller;
import com.solutions.law.universityrouteplanner.Controller.Structure;
import com.solutions.law.universityrouteplanner.Model.DijkstrasModel;
import com.solutions.law.universityrouteplanner.R;
import com.solutions.law.universityrouteplanner.View.EndPoint;
import com.solutions.law.universityrouteplanner.View.MidPoint;
import com.solutions.law.universityrouteplanner.View.View;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

    private List<Link> links;
    private List<SteppingStone> steppingStones;
    private List<SelectableEndPoint> endPoints;
    private Boolean testing=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor);
        if (testing) {
            for(int i=1;i<6;i++) {
                new DrawingPerformanceCheck(this,(int) Math.pow(10,i));
                Log.d("Testing",""+i);
            }
            new RoutingPerformanceCheck(this);
        } else {
            load();
            GraphBuilder gb = new GraphBuilder(endPoints, steppingStones, links);
            DijkstrasModel model = new DijkstrasModel(gb.getGraph());
            List<Structure> structures = new ArrayList<>();
            for (SelectableEndPoint current : endPoints) {
                if (current.getPlane().equals("Outside")) {
                    structures.add(new Building(current));
                }
            }
            Controller controller = new Controller(model, structures);
            AutoCompleteTextView textOne = (AutoCompleteTextView) findViewById(R.id.locationOne);
            AutoCompleteTextView textTwo = (AutoCompleteTextView) findViewById(R.id.locationTwo);
            Switch stairs = (Switch) findViewById(R.id.stairs);
            Switch lifts = (Switch) findViewById(R.id.lifts);
            Button directionsButton = (Button) findViewById(R.id.directionsButton);
            Button inOutButton = (Button) findViewById(R.id.inOutButton);
            View view = new View(controller, new ArrayList<EndPoint>(endPoints), new ArrayList<MidPoint>(steppingStones), textOne, textTwo, directionsButton, inOutButton, getFragmentManager(), this, stairs, lifts);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(view);
            model.addListener(view);
        }
    }

    private void load() {
        FileReader read = new FileReader(this);
        links = read.getLinks();
        steppingStones = read.getSteppingStones();
        endPoints = read.getEndPoints();
    }


}
