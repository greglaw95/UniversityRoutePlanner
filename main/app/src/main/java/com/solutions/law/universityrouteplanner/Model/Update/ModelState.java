package com.solutions.law.universityrouteplanner.Model.Update;

import java.util.List;

/**
 * Created by kbb12155 on 10/02/16.
 */
public class ModelState implements RoutePlannerState {
    private String startLoc;
    private String endLoc;
    private List<String> routeSelected;
    private String error;
    private String plane;
    private String level;
    private String focusOn;

    public ModelState(String startLoc,String endLoc,List<String> routeSelected,String error,String plane,String level,String focusOn){
        this.startLoc=startLoc;
        this.endLoc=endLoc;
        this.routeSelected=routeSelected;
        this.error=error;
        this.plane=plane;
        this.level=level;
        this.focusOn=focusOn;
    }

    @Override
    public String getError(){
        return error;
    }

    public String getStartLoc(){
        return startLoc;
    }

    public String getEndLoc(){
        return endLoc;
    }

    public List<String> getRouteSelected(){
        return routeSelected;
    }

    public String getPlane(){
        return plane+level;
    }

    public String getLevel(){
        return level;
    }

    public String getFocusOn(){
        return focusOn;
    }
}
