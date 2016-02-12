package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.model.CameraPosition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class Model implements IModel{

    CameraPosition position;

    private List<RoutePlannerListener> listeners;
    private List<String> userSelected;

    public Model(){
        position=null;
        listeners=new ArrayList<RoutePlannerListener>();
        userSelected=new ArrayList<String>();
    }

    public void addListener(RoutePlannerListener newListener){
        listeners.add(newListener);
    }

    public void moveTo(CameraPosition position){
        if(this.position==null||!roughlyEqual(this.position,position)){
            this.position=position;
            alertAll();
        }
    }

    public void userSelectItem(String item) {
        if (userSelected.contains(item)) {
            userSelected.remove(item);
        }else{
            userSelected.add(item);
        }
        alertAll();
    }

    private boolean roughlyEqual(CameraPosition position1,CameraPosition position2){
        boolean checkOne= (new BigDecimal(position1.zoom).setScale(4, RoundingMode.HALF_UP).floatValue()==new BigDecimal(position2.zoom).setScale(4, RoundingMode.HALF_UP).floatValue());
        boolean checkTwo= (new BigDecimal(position1.target.latitude).setScale(4, RoundingMode.HALF_UP).doubleValue()==new BigDecimal(position2.target.latitude).setScale(4, RoundingMode.HALF_UP).doubleValue());
        boolean checkThree= (new BigDecimal(position1.target.longitude).setScale(4, RoundingMode.HALF_UP).doubleValue()==new BigDecimal(position2.target.longitude).setScale(4, RoundingMode.HALF_UP).doubleValue());
        return checkOne&&checkTwo&&checkThree;
    }

    private void alertAll(){
        for(RoutePlannerListener listener:listeners){
            listener.update(new ModelState(position,userSelected));
        }
    }
}
