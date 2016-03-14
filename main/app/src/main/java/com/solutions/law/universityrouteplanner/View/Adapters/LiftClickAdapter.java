package com.solutions.law.universityrouteplanner.View.Adapters;

import android.view.View;

import com.solutions.law.universityrouteplanner.Controller.IController;

/**
 * Created by kbb12 on 14/03/2016.
 */
public class LiftClickAdapter implements View.OnClickListener {

    private IController controller;
    private boolean liftUse;

    public LiftClickAdapter(IController controller){
        this.controller=controller;
        liftUse =true;
    }

    @Override
    public void onClick(View v) {
        liftUse =!liftUse;
        controller.useLifts(liftUse);
    }
}
