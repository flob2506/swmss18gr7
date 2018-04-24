package com.group.tube.Models;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Course {
    public String id;
    public Semester semester;
    public int semester_year;

    public String course_title;
    public int semester_year = 0;
    public boolean is_ws = true;
    public ArrayList<Episodes> episodes = new ArrayList<>();

    //TODO: Rework this constructor
    public Course() {
    }

    //TODO: Rework this constructor
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

    public Course(String id, Semester semester, int semester_year, String course_title, String course_number, ArrayList<Episodes> episodes) {
        this.id = id;
        this.semester = semester;
        this.semester_year = semester_year;
        this.course_title = course_title;
        this.course_number = course_number;
        this.episodes = episodes;
    }

    public String getSemesterString() {
        String semester_string;
        if (semester == Semester.WS) {
            semester_string = "WS";
        } else {
            semester_string = "SS";
        }
        if (semester_year < 10) {
            semester_string += "0";
        }
        semester_string += Integer.toString(semester_year);
        return semester_string;
    }
}

