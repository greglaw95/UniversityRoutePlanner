package com.solutions.law.universityrouteplanner.StartUp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.SupportMapFragment;
import com.solutions.law.universityrouteplanner.Controller.Controller;
import com.solutions.law.universityrouteplanner.Controller.Selectable;
import com.solutions.law.universityrouteplanner.Controller.Structure;
import com.solutions.law.universityrouteplanner.Model.Model;
import com.solutions.law.universityrouteplanner.R;
import com.solutions.law.universityrouteplanner.View.EndPoint;
import com.solutions.law.universityrouteplanner.View.NodeThing;
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
        Model model = new Model(gb.getGraph());
        List<Structure> structures=new ArrayList<>();
        for(SelectableEndPoint current:endPoints){
            if(current.getPlane().equals("Outside")){
                structures.add(new Building(current));
            }
        }
        Controller controller = new Controller(model,new ArrayList<Selectable>(endPoints),steppingStones,structures);
        EditText textOne =(EditText) findViewById(R.id.locationOne);
        EditText textTwo =(EditText) findViewById(R.id.locationTwo);
        EditText textThree = (EditText) findViewById(R.id.weight);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button inOutButton = (Button) findViewById(R.id.inOutButton);
        List<NodeThing> nodes = new ArrayList<>();
        nodes.addAll(endPoints);
        nodes.addAll(steppingStones);
        View view = new View(controller,nodes,textOne,textTwo,textThree,addButton,inOutButton);
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
            if(steppingStones.get(i).getPlane().equals("Outside")) {
                for (int j = i + 1; j < steppingStones.size(); j++) {
                    if(steppingStones.get(j).getPlane().equals(steppingStones.get(i).getPlane())) {
                        links.add(approximateDuration(steppingStones.get(i), steppingStones.get(j)));
                    }
                }
            }
        }
    }

    private Link approximateDuration(SteppingStone one,SteppingStone two){
        Double duration=Math.abs(one.getPoint().latitude-two.getPoint().latitude);
        duration=duration+Math.abs(one.getPoint().longitude-two.getPoint().longitude);
        duration=duration*48093.36;
        return new Link(one.getName(),two.getName(),duration);
    }




}
