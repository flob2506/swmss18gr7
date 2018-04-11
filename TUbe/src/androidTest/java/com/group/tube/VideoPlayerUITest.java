package com.group.tube;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.VideoView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class VideoPlayerUITest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void videoStartsAfterTimeOut() throws InterruptedException {
        sleep(8000);
        //onView(withId(R.id.videoView)).perform(click());
        assertTrue(((VideoView)mActivityRule.getActivity().findViewById(R.id.videoView)).isPlaying());
    }
}