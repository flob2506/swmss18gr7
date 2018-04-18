package com.group.tube;

import android.provider.MediaStore;
import android.support.test.espresso.Espresso;
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

    @Test
    public void videoStartsAfterTimeOut() throws InterruptedException {
        sleep(8000);
        assertTrue(((VideoView)mActivityRule.getActivity().findViewById(R.id.videoView)).isPlaying());
    }

    @Test
    public void videoPause() throws InterruptedException {
        sleep(8000);
        ((VideoView)mActivityRule.getActivity().findViewById(R.id.videoView)).pause();
        assertFalse(((VideoView)mActivityRule.getActivity().findViewById(R.id.videoView)).isPlaying());
    }

}