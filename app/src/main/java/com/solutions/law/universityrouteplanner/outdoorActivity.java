package com.solutions.law.universityrouteplanner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;


public class outdoorActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnCameraChangeListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng centre = new LatLng(55.861903, -4.244082);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centre, 16));
        Polygon polygon = mMap.addPolygon(new PolygonOptions()
                .add(new LatLng(55.861830,-4.247068), new LatLng(55.861788,-4.246526), new LatLng(55.862038,-4.246440)
                        , new LatLng(55.861889,-4.245188), new LatLng(55.861053,-4.245488),new LatLng(55.861272,-4.247248))
                .strokeColor(Color.BLUE)
                .fillColor(Color.CYAN));
        mMap.getUiSettings().setIndoorLevelPickerEnabled(false);

        mMap.setIndoorEnabled(false);
        Paint paint = new Paint();
        paint.setTextSize(28);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText("Royal College") + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText("Royal College", 0, baseline, paint);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.861477, -4.246285))
                .icon(BitmapDescriptorFactory.fromBitmap(image)));
        mMap.setOnCameraChangeListener(this);

    }

    @Override
    public void onCameraChange(CameraPosition position){
        //Instead of animating each time build new cameraPosition then animate to there
        //this will stop you missing it if they zoom and move out of bounds.
        CameraPosition newPosition;
        LatLng newLatLng =position.target;
        float newZoom=position.zoom;
        if(position.zoom<16) {
            newZoom=16;
        }else if(position.zoom>17){
            newZoom=17;
        }
        if(position.target.longitude<-4.250000){
            newLatLng=new LatLng(position.target.latitude,-4.250000);
        }
        if(position.target.longitude>-4.235812){
            newLatLng=new LatLng(position.target.latitude,-4.235812);
        }
        newPosition = new CameraPosition(newLatLng,newZoom,position.tilt,position.bearing);
        if(!newPosition.equals(position)) {
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newPosition));
        }
    }
}
