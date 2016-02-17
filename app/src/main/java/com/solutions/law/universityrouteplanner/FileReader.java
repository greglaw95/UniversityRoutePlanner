package com.solutions.law.universityrouteplanner;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kbb12 on 10/02/2016.
 */
public class FileReader {

    List<EndPoint> endPoints;
    List<SteppingStone> steppingStones;
    List<Link> links;

    public FileReader(Context context){
        Iterator<String> linePieces;
        StringBuilder name;
        List<LatLng> coOrds;
        int limit;
        endPoints = new ArrayList<EndPoint>();
        steppingStones=new ArrayList<>();
        links = new ArrayList<>();
        try {
            Resources res = context.getResources();
            AssetManager assetManager = res.getAssets();
            InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open("EndPoints.txt"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line=bufferedReader.readLine();
            while(line!=null){
                name=new StringBuilder();
                coOrds=new ArrayList<LatLng>();
                linePieces= Arrays.asList(line.split(" ")).iterator();
                limit=Integer.parseInt(linePieces.next());
                for(int i=0;i<limit;i++){
                    if(i!=0){
                        name.append(" ");
                    }
                    name.append(linePieces.next());
                }
                limit=Integer.parseInt(linePieces.next());
                for(int i=0;i<limit;i++){
                    coOrds.add(new LatLng(Double.parseDouble(linePieces.next()),Double.parseDouble(linePieces.next())));
                }

                endPoints.add(new EndPoint(name.toString(),coOrds));
                line=bufferedReader.readLine();
            }
            inputStreamReader = new InputStreamReader(assetManager.open("SteppingStones.txt"));
            bufferedReader = new BufferedReader(inputStreamReader);
            line=bufferedReader.readLine();
            while(line!=null) {
                linePieces = Arrays.asList(line.split(" ")).iterator();
                steppingStones.add(new SteppingStone(linePieces.next(), new LatLng(Double.parseDouble(linePieces.next()), Double.parseDouble(linePieces.next()))));
                line = bufferedReader.readLine();
            }
            inputStreamReader = new InputStreamReader(assetManager.open("Links.txt"));
            bufferedReader = new BufferedReader(inputStreamReader);
            line=bufferedReader.readLine();
            while(line!=null) {
                linePieces = Arrays.asList(line.split(",")).iterator();
                links.add(new Link(linePieces.next(),linePieces.next(),Double.parseDouble(linePieces.next())));
                line = bufferedReader.readLine();
            }
        }catch (FileNotFoundException e){
            Log.e("Hey",e.toString());
        }catch (IOException e){
            Log.e("Hey",e.toString());
        }
    }

    public List<EndPoint> getEndPoints(){
        return endPoints;
    }

    public List<SteppingStone> getSteppingStones(){
        return steppingStones;
    }

    public List<Link> getLinks() {
        return links;
    }
}
