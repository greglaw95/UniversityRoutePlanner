package com.solutions.law.universityrouteplanner.StartUp;

import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.solutions.law.universityrouteplanner.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity{

    private List<Link> links;
    private List<SteppingStone> steppingStones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor);
        load();
        List<Link> newLinks = new ArrayList<>();
        SteppingStone one;
        SteppingStone two;
        for(Link link:links){
            one=find(link.getNodeOne());
            two=find(link.getNodeTwo());
            newLinks.add(new Link(link.getNodeOne(),link.getNodeTwo(),approximateDuration(one,two)));
        }
        save(newLinks);
    }

    private SteppingStone find(String name){
        for(SteppingStone current:steppingStones){
            if(current.getName().equals(name)){
                return current;
            }
        }
        return null;
    }

    private void load(){
        FileReader read = new FileReader(this);
        links = read.getLinks();
        steppingStones=read.getSteppingStones();
    }


    private Double approximateDuration(SteppingStone one,SteppingStone two){
        Double b=Math.abs(one.getPoint().latitude-two.getPoint().latitude);
        Double c=Math.abs(one.getPoint().longitude-two.getPoint().longitude);
        Double a=Math.sqrt(Math.pow(b, 2) + Math.pow(c, 2));
        Double duration=a*48093.36;
        return duration;
    }

    private void save(List<Link> links){
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "newlinks.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedWriter outputStream = new BufferedWriter(new FileWriter(file));
            for(Link link:links){
                outputStream.write(link.getNodeOne());
                outputStream.write(",");
                outputStream.write(link.getNodeTwo());
                outputStream.write(",");
                outputStream.write(Double.toString(link.getWeight()));
                outputStream.newLine();
            }
            outputStream.close();
        }catch (IOException e){
            Log.d("Just need a line", "blah blah blah");
        }
    }



}
