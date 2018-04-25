package com.group.tube;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.VideoView;

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
public class VideoPlayerUITest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    private  boolean waitForVideoToPlay(long max_waiting_time_in_milliseconds) {
        long start_time = System.currentTimeMillis();
        max_waiting_time_in_milliseconds += start_time;
        while (!((VideoView) mActivityRule.getActivity().findViewById(R.id.videoView)).isPlaying()) {
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
        boolean success = waitForVideoToPlay(20000);
        assertTrue(success);
    }

    @Test
    public void videoPause() throws InterruptedException {
        boolean success = waitForVideoToPlay(20000);
        ((VideoView)mActivityRule.getActivity().findViewById(R.id.videoView)).pause();
        assertTrue(success && !((VideoView)mActivityRule.getActivity().findViewById(R.id.videoView)).isPlaying());
    }

}