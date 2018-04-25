package com.group.tube;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;
import android.widget.VideoView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BackButtonUITest {


    @Rule

    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    private  boolean waitForViewSwitch(long max_waiting_time_in_milliseconds) {
        long start_time = System.currentTimeMillis();
        max_waiting_time_in_milliseconds += start_time;
        //mActivityRule.getActivity().findViewById(R.id.BackButton).callOnClick();

        while ((mActivityRule.getActivity().findViewById(R.id.listViewEpisodes)).isActivated()) {
            if (max_waiting_time_in_milliseconds <= System.currentTimeMillis()) {
                return false;
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }



    @Test
    public void UiSwitch() throws InterruptedException {
        boolean success = waitForViewSwitch(20000);
        assertTrue(success);
    }


}