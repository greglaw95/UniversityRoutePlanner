package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.model.CameraPosition;

/**
 * Created by kbb12155 on 10/02/16.
 */
public interface IModel {
    public void moveTo(CameraPosition position);
    public void userSelectItem(String item);
}
