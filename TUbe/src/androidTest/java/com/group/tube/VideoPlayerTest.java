package com.group.tube;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class VideoPlayerTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    private  boolean waitForVideoToLoad(long max_waiting_time_in_milliseconds) {
        long start_time = System.currentTimeMillis();
        max_waiting_time_in_milliseconds += start_time;
        while (!mActivityRule.getActivity().videoDidLoad) {
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
    public void videoStartsAfterTimeOut() throws InterruptedException {
        boolean success = waitForVideoToLoad(20000);
        assertTrue(success);
    }


}