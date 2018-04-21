package com.group.tube.Comparators;
import com.group.tube.Models.Course;

import java.util.Comparator;

public class SemesterSortComparator implements Comparator<Course> {
    @Override
    public int compare(Course course1, Course course2) {
        if(course1.semester_year == course2.semester_year &&
            course1.is_ws == course2.is_ws) {
            return 0;
        }
        if(course1.semester_year > course2.semester_year ||
                (course1.semester_year == course2.semester_year && course1.is_ws)) {
            return 1;
        }
        return -1;
    }
}