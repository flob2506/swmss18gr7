package com.group.tube;


import com.group.tube.Comparators.DateSortComparator;
import com.group.tube.Models.Course;
import com.group.tube.Models.Episodes;
import com.group.tube.utils.TestDataGenerator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

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
        Collections.sort(courses, new SemesterSortComperator());

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

    }
}
