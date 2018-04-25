package com.group.tube.Models;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String pattern = "([0-9][0-9])([SW])";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher regexMatcher = regexPattern.matcher(course_title);
        if (regexMatcher.find()) {
            this.semester_year = Integer.parseInt(regexMatcher.group(1)) + 2000;
            this.is_ws = regexMatcher.group(2).equals("W");
        }
    }
}

