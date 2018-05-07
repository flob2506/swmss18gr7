package com.group.tube.parser;

import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Parser {

    /**
     * This method parses all courses from the provided json-string and saves them in the provided
     * ArrayList.
     *
     * @param jsonString String containing the courses
     * @param courses    ArrayList to store the courses in
     * @throws ParseException Either the json is null or empty or a JSONException occurred
     */
    public void parseAllCourses(String jsonString, ArrayList<Course> courses) throws ParseException {
        if (jsonString == null || jsonString.isEmpty()) {
            throw new ParseException("JSONString is null or empty", 0);
        }

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonCourse = jsonArray.getJSONObject(i);

                Course newCourse = new Course();

                newCourse.setId(jsonCourse.getString("identifier"));
                newCourse.setCourseTitle(jsonCourse.getString("title"));

                courses.add(newCourse);
            }
        } catch (JSONException e) {
            throw new ParseException("JSONException occurred when parsing", 0);
        }
    }

    /**
     * This method parses all episodes from the provided json-string and saves them in the provided
     * ArrayList.
     *
     * @param jsonString String containing the episodes
     * @param episodes   ArrayList to store the episodes in
     * @throws ParseException Either the json is null or empty or a JSONException occurred
     **/
    public void parseEpisodesOfCourse(String jsonString, ArrayList<Episode> episodes) throws ParseException {
        if (jsonString == null || jsonString.isEmpty()) {
            throw new ParseException("JSONString is null or empty", 0);
        }

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonEpisode = jsonArray.getJSONObject(i);

                Episode newEpisode = new Episode();

                newEpisode.setId(jsonEpisode.getString("identifier"));
                newEpisode.setEpisodeTitle(jsonEpisode.getString("title"));

                String dateString = jsonEpisode.getString("created");
                Date dummyDate = new Date();
                newEpisode.setDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(jsonEpisode.getString("created")));

                episodes.add(newEpisode);
            }
        } catch (JSONException e) {
            throw new ParseException("JSONException occurred when parsing", 0);
        }
    }
}