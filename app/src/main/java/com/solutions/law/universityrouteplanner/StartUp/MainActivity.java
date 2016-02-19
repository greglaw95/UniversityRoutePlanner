package com.solutions.law.universityrouteplanner.StartUp;

import android.support.v4.app.FragmentActivity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.SupportMapFragment;
import com.solutions.law.universityrouteplanner.Controller.Controller;
import com.solutions.law.universityrouteplanner.Controller.Selectable;
import com.solutions.law.universityrouteplanner.Model.Model;
import com.solutions.law.universityrouteplanner.Model.PathFinding.DijkstrasAlgorithm;
import com.solutions.law.universityrouteplanner.R;
import com.solutions.law.universityrouteplanner.View.EndPoint;
import com.solutions.law.universityrouteplanner.View.MidPoint;
import com.solutions.law.universityrouteplanner.View.View;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity{

    private List<Link> links;
    private List<SteppingStone> steppingStones;
    private List<SelectableEndPoint> endPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor);
        load();
        addOutsideLinks();
        GraphBuilder gb = new GraphBuilder(endPoints,steppingStones,links);
        Model model = new Model(gb.getGraph(),new DijkstrasAlgorithm());
        Controller controller = new Controller(model,new ArrayList<Selectable>(endPoints));
        EditText textOne =(EditText) findViewById(R.id.locationOne);
        EditText textTwo =(EditText) findViewById(R.id.locationTwo);
        Button directionsButton = (Button) findViewById(R.id.directionsButton);
        Button inOutButton = (Button) findViewById(R.id.inOutButton);
        View view = new View(controller,new ArrayList<EndPoint>(endPoints),new ArrayList<MidPoint>(steppingStones),textOne,textTwo,directionsButton,inOutButton,getFragmentManager());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(view);
        model.addListener(view);
    }

    private void load(){
        FileReader read = new FileReader(this);
        links = read.getLinks();
        steppingStones=read.getSteppingStones();
        endPoints=read.getEndPoints();
    }

    private void addOutsideLinks(){
        for (int i = 0; i < steppingStones.size() - 1; i++) {
            for (int j = i + 1; j < steppingStones.size(); j++) {
                links.add(approximateDuration(steppingStones.get(i),steppingStones.get(j)));
            }
        }
    }

    private Link approximateDuration(SteppingStone one,SteppingStone two){
        Double duration=Math.abs(one.getCoOrd().latitude-two.getCoOrd().latitude);
        duration=duration+Math.abs(one.getCoOrd().longitude-two.getCoOrd().longitude);
        duration=duration*48093.36;
        return new Link(one.getName(),two.getName(),duration);
    }




}