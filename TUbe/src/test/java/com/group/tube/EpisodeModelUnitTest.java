package com.group.tube;

import com.group.tube.Models.Episode;

import org.junit.Test;

import java.util.Date;

import static com.group.tube.utils.TestDataGenerator.getRandomDate;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class EpisodeModelUnitTest {
    @Test
    public void checkConstructorOfEpisodeModel() throws Exception {
        String id = "1";
        String courseTitle = "this course";
        String episodeTitle = "this episode";
        String presenterUrl = "url";
        String presentationUrl = "presenter url";
        String time = "00:00:00";
        Date date = getRandomDate();

        Episode episode = new Episode(id, courseTitle, episodeTitle, presenterUrl,
                presentationUrl, date, time);

        assertEquals(id, episode.getId(), id);
        assertEquals(courseTitle, episode.getCourseTitle());
        assertEquals(episodeTitle, episode.getEpisodeTitle());
        assertEquals(presenterUrl, episode.getPresenterUrl());
        assertEquals(presentationUrl, episode.getPresentationUrl());
        assertEquals(date, episode.getDate());
    }

    @Test
    public void checkGetterAndSetter() throws Exception {

        Episode episode = new Episode("1", "course", "episode",
                "presenter url", "presentation url", getRandomDate(), "00:00:00");


        String id = "2";
        String courseTitle = "new course";
        String courseId = "10";
        String episodeTitle = "new episode";
        String presenterUrl = "new url";
        String presentationUrl = "new presenter url";
        Date date = getRandomDate();

        episode.setId(id);
        episode.setCourseTitle(courseTitle);
        episode.setEpisodeTitle(episodeTitle);
        episode.setPresenterUrl(presenterUrl);
        episode.setPresentationUrl(presentationUrl);
        episode.setDate(date);
        episode.setCourseId(courseId);

        assertEquals(id, episode.getId());
        assertEquals(courseTitle, episode.getCourseTitle());
        assertEquals(courseId, episode.getCourseId());
        assertEquals(episodeTitle, episode.getEpisodeTitle());
        assertEquals(presenterUrl, episode.getPresenterUrl());
        assertEquals(presentationUrl, episode.getPresentationUrl());
        assertEquals(date, episode.getDate());

    }
}