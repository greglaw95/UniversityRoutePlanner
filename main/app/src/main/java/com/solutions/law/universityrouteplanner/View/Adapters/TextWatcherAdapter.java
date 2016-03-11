package com.solutions.law.universityrouteplanner.View.Adapters;

import android.text.Editable;
import android.text.TextWatcher;

import com.solutions.law.universityrouteplanner.Controller.IController;

/**
 * Created by kbb12 on 11/03/2016.
 */
public class TextWatcherAdapter implements TextWatcher {
    private IController controller;
    private IController.Location focus;
    public TextWatcherAdapter(IController controller,IController.Location focus){
        this.controller=controller;
        this.focus=focus;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        switch(focus){
            case START:
                controller.setStart(s.toString());
                break;
            case END:
                controller.setEnd(s.toString());
                break;
        }
    }

}
