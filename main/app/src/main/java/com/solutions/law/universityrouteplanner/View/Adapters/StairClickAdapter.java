package com.solutions.law.universityrouteplanner.View.Adapters;

import android.view.View;

import com.solutions.law.universityrouteplanner.Controller.IController;

/**
 * Created by kbb12 on 14/03/2016.
 */
public class StairClickAdapter implements View.OnClickListener {

    private IController controller;
    private boolean stairUse;

    public StairClickAdapter(IController controller){
        this.controller=controller;
        stairUse=true;
    }

    @Override
    public void onClick(View v) {
        stairUse=!stairUse;
        controller.useStairs(stairUse);
    }
}
