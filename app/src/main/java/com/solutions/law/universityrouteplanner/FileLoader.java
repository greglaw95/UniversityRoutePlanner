package com.solutions.law.universityrouteplanner;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by kbb12155 on 03/02/16.
 */
public class FileLoader {

    List<Element> elementList;
    public FileLoader(String fileName){
        String line;
        String[] lineParts;
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            line=br.readLine();
            while(line!=null){
                lineParts=line.split(":");
                elementList.add(new Element(lineParts));
            }
        }catch(FileNotFoundException e){
            Log.d("PANIC","COULDNT FIND FILE");//TODO
        }catch(IOException e){
            Log.d("PANIC","File badly formatted");//TODO
        }

    }
}
