package com.solutions.law.universityrouteplanner;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by kbb12155 on 03/02/16.
 */
public interface IMapState {
    boolean getRouting();
    List<EID> getUserSelect();
    List<EID> getRouteSelect();
    String getTextBoxOne();
    String getTextBoxTwo();
    String getError();
    Location getLocation();
}
