package com.group.tube.utils;

import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Date;
import java.util.UUID;

public final class TestDataGenerator {

    public static Course getCourse() {
        String courseTitle = "143.700 18S SEP";
        String courseID = "b69911cc-9c04-4e21-9053-93c068283d5f";
        return new Course(courseID, courseTitle);
    }
    public static String getEpisodeId() {
        // SEP VO
        return "dbd281f3-6072-4e34-bb3a-3a124b37fa83";
    }

    public static ArrayList<Episode> getRandomEpisodeList() {
        ArrayList<Episode> episodes = new ArrayList<>();

        episodes.add(new Episode("bla", "bla", "bla", "bla", "bla", getRandomDate(), "00:00:00"));

        int numOfEpisodes = ThreadLocalRandom.current().nextInt(10, 20);
        for (int i = 0; i < numOfEpisodes; i++) {
            episodes.add(new Episode("1ace56be-eb47-4150-97c1-9e285f34e5de&limit=12 - " + i,"Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at", getRandomDate(), "00:00:00"));
        }


        return episodes;

    }

    public static ArrayList<Course> getRandomCourseList()
    {
        ArrayList<Course> courses = new ArrayList<>();
        int numOfCourses = ThreadLocalRandom.current().nextInt(10, 20);
        for (int i = 0; i < numOfCourses; i++) {
            courses.add(new Course("i bims die ID " + UUID.randomUUID().toString(), getRandomCourseTitle()));
        }

        return courses;
    }

    public static String getRandomCourseTitle()
    {
        return geCourseTitle(ThreadLocalRandom.current().nextInt(10, 19),
                    ThreadLocalRandom.current().nextBoolean());
    }
    public static String geCourseTitle(int year, boolean is_ws)
    {
        String s = Integer.toString(year);
        String x = is_ws ? "W": "S";

        return  "Kurstitel vom Semester " + s + x;
    }

    public static Date getRandomDate()
    {
         return new Date( ThreadLocalRandom.current().nextInt(100, 119), // java shit adds 1900
            ThreadLocalRandom.current().nextInt(1, 13),
            ThreadLocalRandom.current().nextInt(1, 29),
            ThreadLocalRandom.current().nextInt(0, 25),
            ThreadLocalRandom.current().nextInt(0, 61),
            ThreadLocalRandom.current().nextInt(0, 61)
        );
    }
}
