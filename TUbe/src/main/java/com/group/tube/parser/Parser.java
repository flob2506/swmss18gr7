package com.group.tube.parser;
import com.group.tube.Models.Episodes;
import com.group.tube.Models.Course;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Parser {
    public void parseJSON (String json, Course course) throws JSONException {
        JSONObject firstString = new JSONObject(json);
        JSONObject searchResults = firstString.getJSONObject("search-results");
        JSONArray resultArray = searchResults.getJSONArray("result");
        JSONObject episodesObject = new JSONObject();
        for (int j = 0; j < resultArray.length(); j++) {
            episodesObject = resultArray.getJSONObject(j);

            JSONObject episodeMediapackage = episodesObject.getJSONObject("mediapackage");
            String seriesID = episodeMediapackage.getString("series");
            String seriesTitle = episodeMediapackage.getString("seriestitle");
            String episodeID = episodeMediapackage.getString("id");
            String episodeDate = episodeMediapackage.getString("start");
            String episodeTitle = episodeMediapackage.getString("title");
            JSONObject mediaObject = episodeMediapackage.getJSONObject("media");
            JSONArray trackArray = mediaObject.getJSONArray("track");
            JSONObject presenterObject = new JSONObject();
            JSONObject presentationObject = new JSONObject();
            for (int i = 0; i < trackArray.length(); i++)
            {
                if (i == 0)
                    presentationObject = trackArray.getJSONObject(i);
                if (i == 1)
                    presenterObject = trackArray.getJSONObject(i);
            }
            String presenterURL = presenterObject.getString("url");
            String presentationURL = presentationObject.getString("url");
            Episodes e = new Episodes();

            e.id = episodeID;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date = new Date();
            try {
                date = format.parse(episodeDate);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            e.episode_date = date;
            e.course_id = seriesID;
            e.episode_title = episodeTitle;
            e.presenter_url = presenterURL;
            e.presentation_url = presentationURL;

            course.episodes.add(e);
            if (j == 0)
            {
                course.id = seriesID;
                course.course_title = seriesTitle;
            }
        }
    }
}