package com.solutions.law.universityrouteplanner.View.Adapters;

import android.graphics.drawable.Icon;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.solutions.law.universityrouteplanner.Controller.IController;
import com.solutions.law.universityrouteplanner.View.View;

/**
 * Created by kbb12 on 12/03/2016.
 */
public class IndoorStateChangeAdapter implements GoogleMap.OnIndoorStateChangeListener {
    private IController controller;
    private View view;
    private boolean active;

    public IndoorStateChangeAdapter(IController controller,View view){
        this.controller=controller;
        this.view=view;
        active=true;
    }

    public void activate(){
        active=true;
    }

    public void deactivate(){
        active=false;
    }


    @Override
    public void onIndoorBuildingFocused() {
        try {
            view.getFocusedBuilding().getLevels().get(view.getFocusedBuilding().getLevels().size() - Integer.parseInt(view.getLevel())).activate();
        } catch (Exception e) {
            //Wrong building focused but this doesn't matter it will be the right one next time around. It's just google maps panning over other buildings as it moves
            //to the selected building.
        }
    }

    @Override
    public void onIndoorLevelActivated(IndoorBuilding indoorBuilding) {
        if(active&&indoorBuilding.equals(view.getFocusedBuilding())) {
            controller.setLevel(Integer.toString(indoorBuilding.getLevels().size() - indoorBuilding.getActiveLevelIndex()));
        }
    }
}
