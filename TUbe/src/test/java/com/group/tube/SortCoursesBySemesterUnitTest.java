package com.group.tube;


import com.group.tube.Comparators.SemesterSortComparator;
import com.group.tube.Models.Course;
import com.group.tube.Models.Episodes;
import com.group.tube.utils.TestDataGenerator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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
                assert(prevCourse.semester_year <= course.semester_year);
                if (prevCourse.semester_year == course.semester_year)
                    assert (!prevCourse.is_ws);
            }
            prevCourse = course;
        }
    }

    @Test
    public void verifyFalseCourse()
    {
        Course courses = new Course(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        assert(courses.is_ws && courses.semester_year == 0);
    }

    @Test
    public void testSemester()
    {
        int year = ThreadLocalRandom.current().nextInt(10, 19);
        boolean is_ws = ThreadLocalRandom.current().nextBoolean();
        Course course = new Course(UUID.randomUUID().toString(), TestDataGenerator.geCourseTitle(year, is_ws));
        assert(is_ws == course.is_ws);
        assert((year + 2000) == course.semester_year);
    }

    @Test
    public void testSemesterMultiple()
    {
        Course course = new Course(UUID.randomUUID().toString(), "semester 16S 15S 17W");
        assert(!course.is_ws);
        assert(2016 == course.semester_year);
    }
}
