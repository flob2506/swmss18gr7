package com.group.tube;

import android.content.Context;
import android.content.Intent;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.webkit.WebView;

import com.group.tube.List.EpisodeTimeList;
import com.group.tube.List.WatchLaterList;
import com.group.tube.utils.LocalStorageUtils;
import com.group.tube.utils.TestDataGenerator;
import com.group.tube.utils.Utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import static com.group.tube.EpisodesOverviewActivity.EXTRA_EPISODE_ID;
import static com.group.tube.utils.LocalStorageUtils.FILE_NAME_WATCH_LATER_LIST;
import static java.lang.Thread.sleep;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SaveAndLoadEpisodeTimeTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class, false, false);

    private void overWriteWatchFile(Context context)
    {
        LocalStorageUtils.writeListToFile(context, new HashMap<String, Float>());
        EpisodeTimeList.getInstance().overwrite(new HashMap<String, Float>());
    }
    @Test
    public void videoStartsAfterTimeOut() throws InterruptedException {
        Intent intent = new Intent();
        String episodeId = TestDataGenerator.getEpisodeId();
        intent.putExtra(EXTRA_EPISODE_ID, episodeId);
        mActivityRule.launchActivity(intent);
        overWriteWatchFile(mActivityRule.getActivity());
        LocalStorageUtils.writeListToFile(mActivityRule.getActivity(), new HashMap<String, Float>());
        EpisodeTimeList.getInstance().overwrite(new HashMap<String, Float>());

        long acceptable_lower_margin = 300;
        long acceptable_top_margin = 1500;
        long play_time = 10000;
        //Wait for webView to load
        long max_waiting_time_in_milliseconds = 15000;
        long start_time = System.currentTimeMillis();
        max_waiting_time_in_milliseconds += start_time;

        while (!mActivityRule.getActivity().videoDidLoad) {
            if (max_waiting_time_in_milliseconds <= System.currentTimeMillis()) {
                fail("Waiting too long for webView to load");
            }
            sleep(1000);
        }
        sleep(play_time);
        Intent currentIntent = mActivityRule.getActivity().getIntent();
        mActivityRule.finishActivity();
        mActivityRule.launchActivity(currentIntent);
        sleep(1000);

        start_time = System.currentTimeMillis();
        max_waiting_time_in_milliseconds += start_time;

        while (!mActivityRule.getActivity().videoDidLoad) {
            if (max_waiting_time_in_milliseconds <= System.currentTimeMillis()) {
                fail("Waiting too long for webView to load");
            }
            sleep(1000);
        }
        sleep(play_time);
        currentIntent = mActivityRule.getActivity().getIntent();
        mActivityRule.finishActivity();
        mActivityRule.launchActivity(currentIntent);

        Map<String, Float> map = EpisodeTimeList.getInstance();
        Float time = map.get(episodeId) * 1000;

        assertTrue(time > 2 * (play_time - acceptable_lower_margin) && time < 2 * (play_time + acceptable_top_margin));
    }
}