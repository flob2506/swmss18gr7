package com.group.tube.networking;

import android.content.res.Resources;

import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.R;
import com.group.tube.parser.Parser;
import com.group.tube.utils.TestDataGenerator;

import java.text.ParseException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.client.cache.Resource;

public class NetworkConnector {
    public NetworkTask networkTask;
    public static final String TUBE_URL = "https://tube-test.tugraz.at/";

    public NetworkConnector() {
        this.networkTask = new NetworkTask();
    }

    public void loadAllCourses(final AsyncResponse<ArrayList<Course>> responseHandler) {
        this.networkTask.setResponseHandler(new AsyncResponse<String>() {
            @Override
            public void processFinish(String jsonResponse) {
                ArrayList<Course> courses = new ArrayList<>();
                Parser parser = new Parser();
                try {
                    parser.parseAllCourses(jsonResponse, courses);
                    responseHandler.processFinish(courses);
                } catch (ParseException e) {
                    responseHandler.processFinish(TestDataGenerator.getRandomCourseList());
                    //responseHandler.handleProcessException(e);
                }
            }

            @Override
            public void handleProcessException(Exception e) {
                //responseHandler.handleProcessException(e);
                responseHandler.processFinish(TestDataGenerator.getRandomCourseList());
            }
        });

        this.networkTask.execute(TUBE_URL + "api/series");
    }


    public void loadEpisodesOfCourse(final AsyncResponse<ArrayList<Episode>> responseHandler, final String courseID) {
        this.networkTask.setResponseHandler(new AsyncResponse<String>() {
            @Override
            public void processFinish(String response) {
                ArrayList<Episode> episodes = new ArrayList<>();
                Parser parser = new Parser();
                try {
                    parser.parseEpisodesOfCourse(response, episodes);
                    responseHandler.processFinish(episodes);
                } catch (ParseException e) {
                    //responseHandler.handleProcessException(e);
                    responseHandler.processFinish(TestDataGenerator.getRandomEpisodeList());
                }
            }

            @Override
            public void handleProcessException(Exception e) {
                //responseHandler.handleProcessException(e);
                responseHandler.processFinish(TestDataGenerator.getRandomEpisodeList());
            }
        });
        this.networkTask.execute(TUBE_URL + "api/events/?filter=series:" + courseID);
    }


    /**
     * Gives back the same episode from tube.tugraz.at every time.
     *
     * @deprecated use the new video player instead.
     */
    @Deprecated
    public void loadEpisode(final AsyncResponse<Episode> responseHandler, String episodeId) {
        this.networkTask.setResponseHandler(new AsyncResponse<String>() {
            @Override
            public void processFinish(String jsonResponse) {
                Episode episode = new Episode();
                episode.setPresentationUrl("https://tube.tugraz.at/static/mh_default_org/engage-player/876d2ecf-11df-4b69-9bc2-376bf719c759/d32e06cf-3fb1-4150-b790-e95162af31ae/track.mp4");
                responseHandler.processFinish(episode);
            }

            @Override
            public void handleProcessException(Exception e) {
                responseHandler.handleProcessException(e);
            }
        });
    }
}

