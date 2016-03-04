package com.solutions.law.universityrouteplanner.View;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


/**
 * Created by kbb12 on 15/02/2016.
 * Reference: Built bys studying the code at this address:
 * http://stackoverflow.com/questions/14444228/android-how-to-draw-route-directions-google-maps-api-v2-from-current-location-t
 * Although it has been changed dramatically to fit the exact purposes of this app some code segments may still be similar.
 */
public class OutdoorDirectionsFinder extends AsyncTask<Void,Void,Void> {
    private Document doc;
    private MidPoint start;
    private MidPoint end;
    private boolean done;

    public OutdoorDirectionsFinder(MidPoint start,MidPoint end){
        this.start=start;
        this.end=end;
        done=false;
    }


    @Override
    protected Void doInBackground(Void... params) {
        String[] status ={"status"};
        String stringUrl = "http://maps.googleapis.com/maps/api/directions/xml?"
                + "origin=" + start.getCoOrd().latitude + "," + start.getCoOrd().longitude
                + "&destination=" + end.getCoOrd().latitude + "," + end.getCoOrd().longitude
                + "&sensor=false&units=metric&mode=walking";
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            InputStream in = conn.getInputStream();
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = db.parse(in);
            Log.d("Just need a breakpoint","");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!findEventualNode(doc.getFirstChild(),status,0).getFirstChild().getNodeValue().equals("OK")){
            doInBackground();
        }
        done=true;
        return null;
    }

    public boolean isDone(){
        return done;
    }

    public List<LatLng[]> getRoute(){
        String[] toStepTags={"route","leg"};
        String[] toStartLatTags={"start_location","lat"};
        String[] toStartLngTags={"start_location","lng"};
        String[] toEndLatTags={"end_location","lat"};
        String[] toEndLngTags={"end_location","lng"};
        Node leg=findEventualNode(doc.getFirstChild(),toStepTags,0);
        List<Node> steps = new ArrayList<>();
        List<LatLng[]> routeStages=new ArrayList<>();
        LatLng[] latLngPair;
        for(int i=0;i<leg.getChildNodes().getLength();i++){
            if(leg.getChildNodes().item(i).getNodeName().equals("step")){
                steps.add(leg.getChildNodes().item(i));
            }
        }
        for(Node step:steps){
            latLngPair=new LatLng[2];
            latLngPair[0]=new LatLng(Double.parseDouble(findEventualNode(step, toStartLatTags, 0).getFirstChild().getNodeValue()), Double.parseDouble(findEventualNode(step, toStartLngTags, 0).getFirstChild().getNodeValue()));
            latLngPair[1]=new LatLng(Double.parseDouble(findEventualNode(step, toEndLatTags, 0).getFirstChild().getNodeValue()), Double.parseDouble(findEventualNode(step, toEndLngTags, 0).getFirstChild().getNodeValue()));
            routeStages.add(latLngPair);
        }
        return routeStages;
    }

    private Node findEventualNode(Node node,String[] tags,int index){
        if(tags.length>index) {
            for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                if (node.getChildNodes().item(i).getNodeName().equals(tags[index])) {
                    return findEventualNode(node.getChildNodes().item(i), tags, index + 1);
                }
            }
            Log.d("baj","sjdbf");
            throw new RuntimeException(doc.toString());
        }else{
            return node;
        }
    }
}
