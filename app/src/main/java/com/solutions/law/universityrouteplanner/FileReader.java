package com.solutions.law.universityrouteplanner;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kbb12 on 10/02/2016.
 */
public class FileReader {

    List<Element> elements;

    public FileReader(Context context){
        Iterator<String> linePieces;
        StringBuilder name;
        List<LatLng> coOrds;
        int limit;
        elements = new ArrayList<Element>();
        try {
            Resources res = context.getResources();
            AssetManager assetManager = res.getAssets();
            InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open("Elements.txt"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line=bufferedReader.readLine();
            while(line!=null){
                name=new StringBuilder();
                coOrds=new ArrayList<LatLng>();
                linePieces= Arrays.asList(line.split(" ")).iterator();
                limit=Integer.parseInt(linePieces.next());
                for(int i=0;i<limit;i++){
                    name.append(linePieces.next());
                }
                limit=Integer.parseInt(linePieces.next());
                for(int i=0;i<limit;i++){
                    coOrds.add(new LatLng(Double.parseDouble(linePieces.next()),Double.parseDouble(linePieces.next())));
                }

                elements.add(new Element(name.toString(),coOrds));
                line=bufferedReader.readLine();
            }
        }catch (FileNotFoundException e){
            Log.e("Hey",e.toString());
        }catch (IOException e){
            Log.e("Hey",e.toString());
        }
    }

    public List<Element> getElements(){
        Log.e("Just need a line to","Stick a breakpoint on");
        return elements;
    }

}
