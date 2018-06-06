package com.group.tube.Comparators;
import com.group.tube.Models.Course;

import java.util.Comparator;

public class SemesterSortComparator implements Comparator<Course> {
    @Override
    public int compare(Course course1, Course course2) {
        if(course1.getSemesterYear() == course2.getSemesterYear() &&
            course1.isWs() == course2.isWs()) {
            return 0;
        }
        if(course1.getSemesterYear() > course2.getSemesterYear() ||
                (course1.getSemesterYear() == course2.getSemesterYear() && course1.isWs())) {
            return 1;
        }
        return -1;
    }
}