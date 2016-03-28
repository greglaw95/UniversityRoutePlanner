package com.solutions.law.universityrouteplanner.UnitTesting.View;

import android.view.View;

import com.solutions.law.universityrouteplanner.Controller.IController;
import com.solutions.law.universityrouteplanner.View.Adapters.InOutClickAdapter;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by kbb12 on 28/03/2016.
 */
public class InOutClickAdapterTest {
    @Test
    public void onlyTest(){
        IController controller = Mockito.mock(IController.class);
        View view = Mockito.mock(View.class);
        View.OnClickListener adapter = new InOutClickAdapter(controller);
        adapter.onClick(view);
        Mockito.verify(controller).goInside();
    }
}
