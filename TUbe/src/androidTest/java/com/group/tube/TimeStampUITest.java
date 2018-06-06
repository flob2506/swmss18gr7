package com.group.tube;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.group.tube.Models.Episode;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class TimeStampUITest {

    @Rule
    public ActivityTestRule<EpisodesOverviewActivity> mActivityRule = new ActivityTestRule<>(EpisodesOverviewActivity.class);


    @Test
    public void checkIfTimeStampIsCorrect(){
        Episode epi = (Episode) mActivityRule.getActivity().listView.getItemAtPosition(0);
        assertTrue(epi.getTime, "00:00:00");
    }
}


