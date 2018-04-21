package com.group.tube.Models;

import java.util.ArrayList;

public class Course {
    public String id;
    public String course_title;
    public int semester_year = 0;
    public boolean is_ws = true;
    public ArrayList<Episodes> episodes = new ArrayList<>();

    public Course() {
    }

    public Course(String id, String course_title) {
        this.id = id;
        setCourseTitle(course_title);
    }

    public void setCourseTitle(String course_title)
    {
        this.course_title = course_title;

        this.semester_year = -;
        this.is_ws = is_ws;
    }
}

