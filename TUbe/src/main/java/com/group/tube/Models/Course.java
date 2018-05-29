package com.group.tube.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Course implements Serializable {

    public final static int uninitializedSemesterYear = 0;

    private String id;
    private String courseTitle;

    private int semesterYear = uninitializedSemesterYear;


    private boolean isWs = true;
    private boolean isFavorite = false;


    public Course() {
    }

    public Course(String id, String courseTitle) {
        this.id = id;
        setCourseTitle(courseTitle);
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
        String pattern = "([0-9][0-9])([SW])";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher regexMatcher = regexPattern.matcher(courseTitle);
        if (regexMatcher.find()) {
            this.semesterYear = 2000 + Integer.parseInt(regexMatcher.group(1));
            this.isWs = regexMatcher.group(2).equals("W");
        }
    }

    public String getSemesterString() {
        String returnString = isWs ? "W" : "S";
        returnString += "S ";
        if (semesterYear < 10) {
            returnString += "0";
        }
        returnString += Integer.toString(semesterYear % 100);
        return returnString;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getId() {
        return id;
    }

    public int getSemesterYear() {
        return semesterYear;
    }

    public boolean isWs() {
        return isWs;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}

