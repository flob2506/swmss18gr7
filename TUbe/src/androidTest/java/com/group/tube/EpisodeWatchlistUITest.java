package com.group.tube;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.DataInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.View;
import android.widget.ListView;

import com.group.tube.List.WatchLaterList;
import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.utils.LocalStorageUtils;
import com.group.tube.utils.TestDataGenerator;
import com.group.tube.utils.Utils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.group.tube.CoursesOverviewActivity.EXTRA_COURSE_OBJECT;
import static com.group.tube.EpisodesOverviewActivity.EXTRA_EPISODE_ID;
import static com.group.tube.utils.LocalStorageUtils.FILE_NAME_WATCH_LATER_LIST;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.anything;

public class EpisodeWatchlistUITest {
    @Rule
    public ActivityTestRule<EpisodeWatchlistActivity> watchlistRule =
            new ActivityTestRule<>(EpisodeWatchlistActivity.class, false, false);

    @Rule
    public ActivityTestRule<EpisodesOverviewActivity> episodeOverviewRule =
            new ActivityTestRule<>(EpisodesOverviewActivity.class, false, false);

    private void overWriteWatchFile(Context context)
    {
        LocalStorageUtils.writeListToFile(context, new LinkedHashSet<String>(), FILE_NAME_WATCH_LATER_LIST);
        WatchLaterList.getInstance().overwrite(new LinkedHashSet<String>());
    }



    @After
    public void tearDown() {
        //overWriteWatchFile(watchlistRule.getActivity());
    }

    @Test
    public void verifyBasicSetup() {

        watchlistRule.launchActivity(null);
        assertFalse(((NavigationView) watchlistRule.getActivity().findViewById(R.id.nav_view)).getMenu().getItem(0).isChecked());
        assertFalse(((NavigationView) watchlistRule.getActivity().findViewById(R.id.nav_view)).getMenu().getItem(1).isChecked());
        assertTrue(((NavigationView) watchlistRule.getActivity().findViewById(R.id.nav_view)).getMenu().getItem(2).isChecked());

        assertEquals("Watchlist", watchlistRule.getActivity().getTitle());
    }

    @Test
    public void watchLaterEpisodeSaved() throws InterruptedException {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        Course course = TestDataGenerator.getCourse();
        bundle.putSerializable(EXTRA_COURSE_OBJECT, course);
        intent.putExtras(bundle);
        episodeOverviewRule.launchActivity(intent);
        overWriteWatchFile(episodeOverviewRule.getActivity());

        DataInteraction interaction = onData(anything()).inAdapterView(withId(R.id.listViewEpisodes)).onChildView(withId(R.id.imageViewEpisodeMore));
        interaction.atPosition(0).perform(click());
        onView(withId(R.id.imageViewEpisodeWatchLater)).perform(click());
        interaction.atPosition(1).perform(click());
        onView(withId(R.id.imageViewEpisodeWatchLater)).perform(click());

        watchlistRule.launchActivity(null);

        onView(withId(R.id.listViewEpisodes)).check(matches(withWatchLater()));
    }

    @Test
    public void navigationToVideo() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        Course course = TestDataGenerator.getCourse();
        bundle.putSerializable(EXTRA_COURSE_OBJECT, course);
        intent.putExtras(bundle);
        episodeOverviewRule.launchActivity(intent);
        overWriteWatchFile(episodeOverviewRule.getActivity());


        onData(anything()).inAdapterView(withId(R.id.listViewEpisodes)).onChildView(withId(R.id.imageViewEpisodeMore)).atPosition(1).perform(click());
        ListView listView = episodeOverviewRule.getActivity().findViewById(R.id.listViewEpisodes);
        Episode episode = (Episode)listView.getItemAtPosition(1);

        onView(withId(R.id.imageViewEpisodeWatchLater)).perform(click());

        watchlistRule.launchActivity(null);

        onData(anything()).inAdapterView(withId(R.id.listViewEpisodes)).atPosition(0).perform(click());

        Intent actualIntent = getActivityInstance().getIntent();

        assertEquals(episode.getId(), actualIntent.getExtras().get(EXTRA_EPISODE_ID));
    }

    // adapted from FavoriteButtonTest
    private static Matcher<View> withWatchLater() {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(final View view) {
                ListView list = ((ListView) view);
                ArrayList<String> checkedEpisodes = new ArrayList<>();
                for (int i = 0; i < list.getCount(); i++) {
                    Episode episode = (Episode) list.getItemAtPosition(i);
                    View child = Utils.getChildViewOfListView(list, i);
                    if (child == null) {
                        // views not yet inflated (not scrolled to)
                        continue;
                    }
                    checkedEpisodes.add(episode.getId());
                }
                return WatchLaterList.getInstance().containsAll(checkedEpisodes) && checkedEpisodes.containsAll(WatchLaterList.getInstance());
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("List doesn't have watchlater episodes");
            }
        };
    }

    private Activity getActivityInstance() {
        final Activity[] currentActivity = {null};

        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                Iterator<Activity> it = resumedActivity.iterator();
                currentActivity[0] = it.next();
            }
        });

        return currentActivity[0];
    }



}
