package com.solutions.law.universityrouteplanner.StartUp;

import com.solutions.law.universityrouteplanner.Controller.IController;
import com.solutions.law.universityrouteplanner.Controller.Structure;

/**
 * Created by kbb12 on 21/03/2016.
 */
public class FakeController implements IController {

    private DrawingPerformanceCheck check;

    public FakeController(DrawingPerformanceCheck check){
        this.check=check;
    }

    @Override
    public void errorAccepted() {

    }

    @Override
    public void focusOn(Location location) {

    }

    @Override
    public void route() {

    }

    @Override
    public void setLevel(String level) {

    }

    @Override
    public void goInside() {

    }

    @Override
    public void startUp() {
        check.callBack();
    }

    @Override
    public void setStart(String newStart) {

    }

    @Override
    public void setEnd(String newEnd) {

    }

    @Override
    public void areaSelected(String areaName) {

    }

    @Override
    public void setStructure(String structure) {

    }

    @Override
    public Structure getStructure() {
        return null;
    }

    @Override
    public void useStairs(boolean stairUse) {

    }

    @Override
    public void useLifts(boolean liftUse) {

    }
}
