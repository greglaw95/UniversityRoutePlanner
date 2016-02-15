package com.solutions.law.universityrouteplanner;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;


public class MainActivity extends FragmentActivity{

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        FileReader read = new FileReader(this);
        Model model = new Model();
        Controller controller = new Controller(model,read.getElements());
        setContentView(R.layout.activity_outdoor);
        EditText textOne =(EditText) findViewById(R.id.locationOne);
        EditText textTwo =(EditText) findViewById(R.id.locationTwo);
        Button directionsButton = (Button) findViewById(R.id.directionsButton);
        View view = new View(controller,read.getElements(),textOne,textTwo,directionsButton);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(view);
        model.addListener(view);
    }


}
