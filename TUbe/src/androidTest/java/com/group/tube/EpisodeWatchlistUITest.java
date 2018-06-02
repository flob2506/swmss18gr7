package com.group.tube;

import android.support.design.widget.NavigationView;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class EpisodeWatchlistUITest {
    @Rule
    public ActivityTestRule<EpisodeWatchlistActivity> watchlistRule =
            new ActivityTestRule<>(EpisodeWatchlistActivity.class, false, false);

    private ActivityTestRule<EpisodesOverviewActivity> episodeOverviewRule =
            new ActivityTestRule<>(EpisodesOverviewActivity.class, false, false);

    private ActivityTestRule<CoursesOverviewActivity> courseOverviewRule =
            new ActivityTestRule<>(CoursesOverviewActivity.class, false, false);

    @Before
    public void setup() {
        // TODO: Reset file when feature is implemented
        //LocalStorageUtils.writeListToFile(mActivityRule.getActivity(), new LinkedHashSet<String>());
        //FavouriteList.getInstance().overwrite(new LinkedHashSet<String>());
    }

    @After
    public void tearDown() {
        // TODO: Reset file when feature is implemented
        //mActivityRule.launchActivity(null);
        //setup();
    }

    @Test
    public void verifyBasicSetup() {
        // toggling 3 courses
        watchlistRule.launchActivity(null);

        assertFalse(((NavigationView) watchlistRule.getActivity().findViewById(R.id.nav_view)).getMenu().getItem(0).isChecked());
        assertFalse(((NavigationView) watchlistRule.getActivity().findViewById(R.id.nav_view)).getMenu().getItem(1).isChecked());
        assertTrue(((NavigationView) watchlistRule.getActivity().findViewById(R.id.nav_view)).getMenu().getItem(2).isChecked());

        assertEquals("Watchlist", watchlistRule.getActivity().getTitle());
    }
}
