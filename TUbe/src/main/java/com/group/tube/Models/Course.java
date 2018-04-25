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

    public void setCourseTitle(String course_title) {
        this.course_title = course_title;
        String pattern = "([0-9][0-9])([SW])";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher regexMatcher = regexPattern.matcher(course_title);
        if (regexMatcher.find()) {
            this.semester_year = Integer.parseInt(regexMatcher.group(1));
            this.is_ws = regexMatcher.group(2).equals("W");
        }
    }

    public String getSemesterString() {
        String return_string = is_ws ? "W" : "S";
        return_string += "S ";
        if (semester_year < 10) {
            return_string += "0";
        }
        return_string += Integer.toString(semester_year);
        return return_string;
    }
}

