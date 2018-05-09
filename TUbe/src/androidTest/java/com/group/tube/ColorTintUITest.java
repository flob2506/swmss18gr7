package com.group.tube;

import android.graphics.Color;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Window;

import com.group.tube.utils.Utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ColorTintUITest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);



    @Test
    public void ColorNotNull() throws InterruptedException {


        int colour =  Utils.getColor(mActivityRule.getActivity());
        assert(colour != 0);
    }

    @Test
    public void CheckColor() throws InterruptedException {


        int colour =  Utils.getColor(mActivityRule.getActivity());
        assert(colour == -1829557);
    }



}