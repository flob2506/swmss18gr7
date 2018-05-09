package com.group.tube;


import com.group.tube.Comparators.SemesterSortComparator;
import com.group.tube.Models.Course;
import com.group.tube.utils.TestDataGenerator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class SortCoursesBySemesterUnitTest {
    private ArrayList<Course> courses;

    @Before
    public void setUp()
    {
        courses = TestDataGenerator.getRandomCourseList();
    }

    @Test
    public void verifyListSorted()
    {
        Collections.sort(courses, new SemesterSortComparator());

        Course prevCourse = null;
        for (Course course : courses) {
            if (prevCourse != null)
            {
                assertEquals(true, prevCourse.getSemesterYear() <= course.getSemesterYear());
                if (prevCourse.getSemesterYear() == course.getSemesterYear())
                    assert (!prevCourse.isWs());
            }
            prevCourse = course;
        }
    }

    @Test
    public void verifyFalseCourse()
    {
        Course courses = new Course(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        assertEquals(true, courses.isWs() && courses.getSemesterYear() == 0);
    }

    @Test
    public void testSemester()
    {
        int year = ThreadLocalRandom.current().nextInt(10, 19);
        boolean is_ws = ThreadLocalRandom.current().nextBoolean();
        Course course = new Course(UUID.randomUUID().toString(), TestDataGenerator.geCourseTitle(year, is_ws));
        assertEquals(is_ws, course.isWs());
        assertEquals((year + 2000), course.getSemesterYear());
    }

    @Test
    public void testSemesterMultiple()
    {
        Course course = new Course(UUID.randomUUID().toString(), "semester 16S 15S 17W");
        assertEquals(false, course.isWs());
        assertEquals(2016, course.getSemesterYear());
    }
}
