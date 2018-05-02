package com.group.tube;


import com.group.tube.Models.Course;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.parser.Parser;

import org.json.JSONException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class LoadCoursesUnitTtest {
    @Test
    public void verifyCoursesLoaded() throws Exception
    {
        final ArrayList<Course> courses = new ArrayList<>();
        final Parser p = new Parser();
        p.parseJSON(COURSES_JSON, courses);
        assertTrue(courses.size() == COURSE_COUNT);
        assertTrue(courses.get(0).getId().equals(FIRST_COURSE_ID));
        assertTrue(courses.get(0).getCourseTitle().equals(FIRST_COURSE_TITLE));
    }

    @Test(expected = JSONException.class)
    public void verifyCoursesJSONError() throws Exception
    {
        final ArrayList<Course> courses = new ArrayList<>();
        final Parser p = new Parser();
        p.parseJSON("asdfökjl", courses);
    }

    @Test(expected = JSONException.class)
    public void verifyCoursesJSONErrorIDMissing() throws Exception
    {
        final ArrayList<Course> courses = new ArrayList<>();
        final Parser p = new Parser();
        p.parseJSON(COURSES_JSON_WITHOUT_ID, courses);
    }

    @Test
    public void verifyCoursesLoadedFromHttp() throws Exception
    {
        final CountDownLatch signal = new CountDownLatch(1);
        final NetworkConnector networkConnector = new NetworkConnector();

        networkConnector.loadCourses(new AsyncResponse<ArrayList<Course>>(){
            @Override
            public void processFinish(ArrayList<Course> courses){
                assertTrue(courses.size() > 0);
                boolean hasESP = false;
                boolean hasShittyESP = false;
                for(Course course : courses) {
                    hasESP |= course.getCourseTitle().contains(ESP_TITLE);
                    hasShittyESP |= course.getCourseTitle().contains(ESP_TITLE_ERROR);
                }
                assertTrue(hasESP);
                assertFalse(hasShittyESP);
                signal.countDown();
            }
        });

        signal.await();
    }

    private static final String ESP_TITLE = "Einführung in die strukturierte Programmierung";
    private static final String ESP_TITLE_ERROR = ESP_TITLE + "boi";

    private static final int COURSE_COUNT = 2;
    private static final String FIRST_COURSE_ID = "a8b53a40-ca6d-42d5-a018-a1c3a445c0b6";
    private static final String FIRST_COURSE_TITLE = "16W Arbeitsplatzsicherheit";
    private static final String COURSES_JSON = "{\n"+
            "    \"catalogs\": [\n"+
            "        {\n"+
            "            \"http://purl.org/dc/terms/\": {\n"+
            "                \"identifier\": [\n"+
            "                    {\n"+
            "                        \"value\": \""+FIRST_COURSE_ID+"\"\n"+
            "                    }\n"+
            "                ],\n"+
            "                \"created\": [\n"+
            "                    {\n"+
            "                        \"type\": \"dcterms:W3CDTF\",\n"+
            "                        \"value\": \"2017-11-09T08:36Z\"\n"+
            "                    }\n"+
            "                ],\n"+
            "                \"title\": [\n"+
            "                    {\n"+
            "                        \"value\": \""+FIRST_COURSE_TITLE+"\"\n"+
            "                    }\n"+
            "                ]\n"+
            "            },\n"+
            "            \"http://www.opencastproject.org/matterhorn/\": {\n"+
            "                \"annotation\": [\n"+
            "                    {\n"+
            "                        \"value\": \"false\"\n"+
            "                    }\n"+
            "                ]\n"+
            "            }\n"+
            "        },\n"+
            "        {\n"+
            "            \"http://purl.org/dc/terms/\": {\n"+
            "                \"identifier\": [\n"+
            "                    {\n"+
            "                        \"value\": \"f2392482-bc0b-40b3-b77a-9633f40f5ec7\"\n"+
            "                    }\n"+
            "                ],\n"+
            "                \"creator\": [\n"+
            "                    {\n"+
            "                        \"value\": \"Gethmann D\"\n"+
            "                    }\n"+
            "                ],\n"+
            "                \"title\": [\n"+
            "                    {\n"+
            "                        \"value\": \"[CO]143.403 Ringvorlesung Doktorat Architektur\"\n"+
            "                    }\n"+
            "                ]\n"+
            "            },\n"+
            "            \"http://www.opencastproject.org/matterhorn/\": {\n"+
            "                \"annotation\": [\n"+
            "                    {\n"+
            "                        \"value\": \"false\"\n"+
            "                    }\n"+
            "                ]\n"+
            "            }\n"+
            "        }\n"+
            "    ]\n"+
            "}";

    private static final String COURSES_JSON_WITHOUT_ID = "{\n"+
            "    \"catalogs\": [\n"+
            "        {\n"+
            "            \"http://purl.org/dc/terms/\": {\n"+
            "                \"identifier\": [\n"+
            "                ],\n"+
            "                \"created\": [\n"+
            "                    {\n"+
            "                        \"type\": \"dcterms:W3CDTF\",\n"+
            "                        \"value\": \"2017-11-09T08:36Z\"\n"+
            "                    }\n"+
            "                ],\n"+
            "                \"title\": [\n"+
            "                    {\n"+
            "                        \"value\": \""+FIRST_COURSE_TITLE+"\"\n"+
            "                    }\n"+
            "                ]\n"+
            "            },\n"+
            "            \"http://www.opencastproject.org/matterhorn/\": {\n"+
            "                \"annotation\": [\n"+
            "                    {\n"+
            "                        \"value\": \"false\"\n"+
            "                    }\n"+
            "                ]\n"+
            "            }\n"+
            "        },\n"+
            "        {\n"+
            "            \"http://purl.org/dc/terms/\": {\n"+
            "                \"identifier\": [\n"+
            "                    {\n"+
            "                        \"value\": \"f2392482-bc0b-40b3-b77a-9633f40f5ec7\"\n"+
            "                    }\n"+
            "                ],\n"+
            "                \"creator\": [\n"+
            "                    {\n"+
            "                        \"value\": \"Gethmann D\"\n"+
            "                    }\n"+
            "                ],\n"+
            "                \"title\": [\n"+
            "                    {\n"+
            "                        \"value\": \"[CO]143.403 Ringvorlesung Doktorat Architektur\"\n"+
            "                    }\n"+
            "                ]\n"+
            "            },\n"+
            "            \"http://www.opencastproject.org/matterhorn/\": {\n"+
            "                \"annotation\": [\n"+
            "                    {\n"+
            "                        \"value\": \"false\"\n"+
            "                    }\n"+
            "                ]\n"+
            "            }\n"+
            "        }\n"+
            "    ]\n"+
            "}";
}
