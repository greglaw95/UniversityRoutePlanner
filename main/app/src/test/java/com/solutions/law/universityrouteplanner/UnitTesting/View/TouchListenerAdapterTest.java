package com.solutions.law.universityrouteplanner.UnitTesting.View;

import android.view.MotionEvent;
import android.view.View;

import com.solutions.law.universityrouteplanner.Controller.IController;
import com.solutions.law.universityrouteplanner.View.Adapters.LiftClickAdapter;
import com.solutions.law.universityrouteplanner.View.Adapters.TouchListenerAdapter;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by kbb12 on 28/03/2016.
 */
public class TouchListenerAdapterTest {
    @Test
    public void startTest(){
        IController controller = Mockito.mock(IController.class);
        View view = Mockito.mock(View.class);
        MotionEvent me = Mockito.mock(MotionEvent.class);
        View.OnTouchListener adapter = new TouchListenerAdapter(controller, IController.Location.START);
        adapter.onTouch(view,me);
        Mockito.verify(controller).focusOn(IController.Location.START);
    }

    @Test
    public void endTest(){
        IController controller = Mockito.mock(IController.class);
        View view = Mockito.mock(View.class);
        MotionEvent me = Mockito.mock(MotionEvent.class);
        View.OnTouchListener adapter = new TouchListenerAdapter(controller, IController.Location.END);
        adapter.onTouch(view,me);
        Mockito.verify(controller).focusOn(IController.Location.END);
    }
}
