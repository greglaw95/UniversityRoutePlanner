package com.solutions.law.universityrouteplanner.View.Adapters;

import android.view.View;

import com.solutions.law.universityrouteplanner.Controller.IController;

/**
 * Created by kbb12 on 11/03/2016.
 */
public class InOutClickAdapter implements View.OnClickListener {
    private IController controller;

    public InOutClickAdapter(IController controller){
        this.controller=controller;
    }

    @Override
    public void onClick(View v) {
        controller.goInside();
    }
}
