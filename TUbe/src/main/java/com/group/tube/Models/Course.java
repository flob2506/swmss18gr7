package com.group.tube.Models;

import java.util.ArrayList;

public class Course {
    public String id;
    public String course_title;
    public ArrayList<Episodes> episodes = new ArrayList<>();

    public Course() {

    }

    public Course(String id, String course_title) {
        this.id = id;
        this.course_title = course_title;
    }
}

