package com.group.tube.parser;

import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Parser {
    public void parseAllCourses(String json, ArrayList<Course> courses) throws JSONException, IllegalArgumentException {
        if(json == null ||json.isEmpty()){
            throw new IllegalArgumentException();
        }

        JSONArray jsonArray = new JSONArray(json);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonCourse = jsonArray.getJSONObject(i);

            Course newCourse = new Course();

           newCourse.setId(jsonCourse.getString("identifier"));
            newCourse.setCourseTitle(jsonCourse.getString("title"));

            courses.add(newCourse);
        }
    }

    public void parseEpisodesOfCourse(String jsonResponse, ArrayList<Episode> episodes) throws JSONException, IllegalArgumentException {
        if(jsonResponse == null || jsonResponse.isEmpty()){
            throw new IllegalArgumentException();
        }

        JSONArray jsonArray = new JSONArray(jsonResponse);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonEpisode = jsonArray.getJSONObject(i);

            Episode newEpisode = new Episode();

            newEpisode.setId(jsonEpisode.getString("identifier"));
            newEpisode.setEpisodeTitle(jsonEpisode.getString("title"));

            //TODO: parse date
            String dateString = jsonEpisode.getString("created");
            Date dummyDate = new Date();
            newEpisode.setDate(dummyDate);

            episodes.add(newEpisode);
        }
    }
}