package com.solutions.law.universityrouteplanner.View.Adapters;

import android.view.MotionEvent;
import android.view.View;

import com.solutions.law.universityrouteplanner.Controller.IController;

/**
 * Created by kbb12 on 11/03/2016.
 */
public class TouchListenerAdapter implements View.OnTouchListener {
    private IController controller;
    private IController.Location focus;

    public TouchListenerAdapter(IController controller,IController.Location focus){
        this.controller=controller;
        this.focus=focus;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        controller.focusOn(focus);
        return false;
    }
}
