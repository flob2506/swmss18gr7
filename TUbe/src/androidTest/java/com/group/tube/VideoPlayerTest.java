package com.group.tube;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.webkit.WebView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.group.tube.EpisodesOverviewActivity.EXTRA_EPISODE_ID;
import static java.lang.Thread.sleep;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class VideoPlayerTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class, false, false);

    @Test
    public void videoStartsAfterTimeOut() throws InterruptedException {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_EPISODE_ID, "9cc55b3c-89dd-4227-bade-6457afa50891");
        mActivityRule.launchActivity(intent);

        //Wait for webView to load
        long max_waiting_time_in_milliseconds = 5000;
        long start_time = System.currentTimeMillis();
        max_waiting_time_in_milliseconds += start_time;

        while (!mActivityRule.getActivity().videoDidLoad) {
            if (max_waiting_time_in_milliseconds <= System.currentTimeMillis()) {
                fail("Waiting too long for webView to load");
            }
            sleep(250);
        }

        //check if we got redirected
        final String[] originalURL = new String[1];
        final String[] URL = new String[1];
        mActivityRule.getActivity().findViewById(R.id.webview).post(new Runnable() {
            @Override
            public void run() {
                originalURL[0] = ((WebView)mActivityRule.getActivity().findViewById(R.id.webview)).getOriginalUrl();
                URL[0] = ((WebView)mActivityRule.getActivity().findViewById(R.id.webview)).getUrl();
            }
        });
        sleep(100);

        assertEquals("Got redirected, probably logging in doesn't work", originalURL[0], URL[0]);
    }
}