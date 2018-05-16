package com.group.tube;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.group.tube.Models.Course;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.group.tube.CoursesOverviewActivity.EXTRA_COURSE_OBJECT;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EpisodeOverviewUITest {
    @Rule
    public ActivityTestRule<EpisodesOverviewActivity> episodesOverviewActivityTestRule =
            new ActivityTestRule<>(EpisodesOverviewActivity.class, false, false);

    @Test
    public void titleSetTest(){
        final Intent intent = new Intent();
        Bundle bundle = new Bundle();

        Course course = new Course();
        String courseTitle = "143.700 14S Architekturtheorie heute";
        course.setCourseTitle(courseTitle);

        bundle.putSerializable(EXTRA_COURSE_OBJECT, course);
        intent.putExtras(bundle);

        episodesOverviewActivityTestRule.launchActivity(intent);

        assertEquals(courseTitle, episodesOverviewActivityTestRule.getActivity().getTitle());
    }

    @Test
    public void imageViewIsShown() {
        final Intent intent = new Intent();
        intent.putExtra(EXTRA_MESSAGE, "e1ad5c57-2e78-4933-99cb-645ab5b865c5");

        episodesOverviewActivityTestRule.launchActivity(intent);

        onView(withId(R.id.imageViewThumbnailEpisode)).check(matches(isDisplayed()));
    }

    @Test
    public void imageViewHasChanged() {
        final Intent intent = new Intent();
        intent.putExtra(EXTRA_MESSAGE, "e1ad5c57-2e78-4933-99cb-645ab5b865c5");

        episodesOverviewActivityTestRule.launchActivity(intent);

        onView(withId(R.id.imageViewThumbnailEpisode)).check(matches(isDisplayed()));

        ImageView firstImageView = episodesOverviewActivityTestRule.getActivity().findViewById(R.id.imageViewThumbnailEpisode);
        assertNotSame(firstImageView.getDrawable(), ContextCompat.getDrawable(getContext(), android.R.drawable.presence_video_busy));
    }
}
