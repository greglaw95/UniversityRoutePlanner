package com.solutions.law.universityrouteplanner.StartUp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.SupportMapFragment;
import com.solutions.law.universityrouteplanner.Controller.Controller;
import com.solutions.law.universityrouteplanner.Model.Model;
import com.solutions.law.universityrouteplanner.R;
import com.solutions.law.universityrouteplanner.View.View;


public class MainActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor);
        Model model = new Model();
        Controller controller = new Controller(model);
        EditText plane =(EditText) findViewById(R.id.locationOne);
        EditText room =(EditText) findViewById(R.id.locationTwo);
        Button selectButton = (Button) findViewById(R.id.selectButton);
        Button clearButton = (Button) findViewById(R.id.clearButton);
        View view = new View(controller,plane,room,selectButton,clearButton);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(view);
        model.addListener(view);
    }
}
