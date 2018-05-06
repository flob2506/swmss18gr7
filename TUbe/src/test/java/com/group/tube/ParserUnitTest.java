package com.group.tube;

import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.parser.Parser;

import org.json.JSONException;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ParserUnitTest {

    @Test
    public void parseAllCourses_working() {
        Parser parser = new Parser();
        ArrayList<Course> courses = new ArrayList<>();
        String allCourses_working = "[{\"identifier\":\"e1ad5c57-2e78-4933-99cb-645ab5b865c5\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2017-07-14T10:24:23Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[\"Wagner A\"],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"143.700 14S Architekturtheorie heute\"},{\"identifier\":\"88e0b9d4-e127-44c4-8cb6-23d10330dfec\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2017-07-14T10:28:08Z\",\"subjects\":[\"c-o-u-r-s-e l-i-v-e\"],\"organizers\":[\"Slany W\"],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"716.010 14W Programmieren 0\"},{\"identifier\":\"71f252d4-7453-4d75-b642-ed19c1dfcc74\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2017-07-06T15:58:38Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"141.532 14S Architekturgeschichte 1\"},{\"identifier\":\"bcfa6470-b7c8-4d85-8678-89c6844b1660\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2017-12-18T17:15:20Z\",\"subjects\":[\"e-v-e-n-t\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"Event Series\"},{\"identifier\":\"20327447-89c2-467d-b66e-8403089cdd2b\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-02-21T13:09:04Z\",\"subjects\":[\"l-i-v-e\"],\"organizers\":[],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"TU Graz Public\"},{\"identifier\":\"f74fa407-cce9-445d-b59d-1520c4145c82\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-02-21T13:09:51Z\",\"subjects\":[\"l-i-v-e\"],\"organizers\":[],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"TU Graz Campus\"},{\"identifier\":\"c9d478ef-4b8e-4c08-aed3-4c1980fcc268\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2017-09-15T09:59:06Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"NATM Engineer 2017 [Module 1]\"},{\"identifier\":\"6eaa3d1b-13f3-400f-aae8-bfb700ecb2c6\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-03-12T15:01:29Z\",\"subjects\":[\"l-i-v-e\"],\"organizers\":[],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"HS i13\"},{\"identifier\":\"b73fd584-317a-40d6-a3c1-86e6f9257ff8\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-03-22T13:38:09Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"[CO]123.432 18S New Series After Update\"},{\"identifier\":\"39088ea0-9fb4-4eb5-abf2-f7c75e644dae\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2017-07-14T10:26:25Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"[CO]143.534 14W Kunst- und Kulturwissenschaften\"},{\"identifier\":\"94de0288-0643-43c2-8e48-f20ed46952ac\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:46:30Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[\"Organizer O\"],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"234.567 18S anotherCourse\"},{\"identifier\":\"be327289-d653-49f2-a14a-45736771ad1d\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:53:00Z\",\"subjects\":[\"c-o-u-r-s-e l-i-v-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"DEF.234 18S aLiveCourse\"},{\"identifier\":\"88c97553-3ea1-4c62-91ac-96d7efc2eaf2\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:51:32Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"ABC.123 18S yetAnotherCourse\"},{\"identifier\":\"90748844-e2da-4da7-809c-7a4d404d2ef6\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:50:09Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"456.789 18S yetAnotherCourse\"},{\"identifier\":\"e81c413c-cc5c-461a-8cec-5a488e421cc0\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:49:34Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"345.678 18S yetAnotherCourse\"},{\"identifier\":\"f607dfe9-d3a4-4c30-a190-b2c58ee84205\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2017-11-07T10:33:58Z\",\"subjects\":[\"c-o-u-r-s-e l-i-v-e\"],\"organizers\":[\"Safran C\"],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"INB.03001UF 17W Einführung in die strukturierte Programmierung\"},{\"identifier\":\"d5a8992f-d5d7-451f-9b24-c9fe50311a01\",\"creator\":\"Opencast Project Administrator\",\"created\":\"2018-02-06T08:48:00Z\",\"subjects\":[\"c-o-u-r-s-e l-i-v-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"123.456 18S Empty Series\"},{\"identifier\":\"bfb7fe6a-5ef7-41a4-a0d0-9c6e98fdb3aa\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:54:49Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"CDE.567 18S yetAnotherCourse\"},{\"identifier\":\"1c85def2-138f-4083-bb6c-db70c4fb3b40\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:55:33Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"EFG.789 18S yetAnotherCourse\"},{\"identifier\":\"92ad9ee6-3a5d-4796-b0d5-b47355df9c7e\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:54:17Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"BCD.456UF 18S aNawiCourse\"},{\"identifier\":\"3dc0cbbe-b622-498f-ba44-9473e57b3134\",\"creator\":\"Opencast Project Administrator\",\"created\":\"2018-04-05T06:50:40Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"012.345 18S yetAnotherCourse\"}]";

        try {
            parser.parseAllCourses(allCourses_working, courses);
        } catch (JSONException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        assertEquals("143.700 14S Architekturtheorie heute", courses.get(0).getCourseTitle());
        assertEquals(14, courses.get(2).getSemesterYear());
        assertEquals(true, courses.get(7).isWs());
    }

    @Test(expected = JSONException.class)
    public void parseAllCourses_nonsense() throws JSONException {
        Parser parser = new Parser();
        ArrayList<Course> courses = new ArrayList<>();
        String allCourses_nonsense = "nonsense";

        parser.parseAllCourses(allCourses_nonsense, courses);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseAllCourses_null() throws JSONException {
        Parser parser = new Parser();
        ArrayList<Course> courses = new ArrayList<>();
        String allCourses_null = "";
        parser.parseAllCourses(allCourses_null, courses);
    }


    @Test
    public void parseEpisodesOfCourse_working() throws ParseException {
        Parser parser = new Parser();
        ArrayList<Episode> episodes = new ArrayList<>();
        String episodesOfCourse_working = "[{\"identifier\":\"05035dc4-3938-485d-8ea8-5081b9d6bc57\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-04-07T06:00:00Z\",\"subjects\":[],\"start\":\"2014-04-07T06:00:00Z\",\"description\":\"\",\"title\":\"7. April 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":31,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]},{\"identifier\":\"23e9d34e-8d0f-4da1-9000-e5ced77ee3ae\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-06-16T06:00:00Z\",\"subjects\":[],\"start\":\"2014-06-16T06:00:00Z\",\"description\":\"\",\"title\":\"16. Juni 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":31,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]},{\"identifier\":\"d28fed90-9def-4f6d-b451-34ef58e67c09\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-03-10T06:00:00Z\",\"subjects\":[],\"start\":\"2014-03-10T06:00:00Z\",\"description\":\"\",\"title\":\"10. März 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":34,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]},{\"identifier\":\"f9338064-4099-4c75-a674-06741b198347\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-05-26T06:00:00Z\",\"subjects\":[],\"start\":\"2014-05-26T06:00:00Z\",\"description\":\"\",\"title\":\"26. Mai 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":30,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]},{\"identifier\":\"b9217e1d-f5c5-47a3-87db-0cc63f0e8143\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-06-02T06:00:00Z\",\"subjects\":[],\"start\":\"2014-06-02T06:00:00Z\",\"description\":\"\",\"title\":\"2. Juni 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":32,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]},{\"identifier\":\"efa1061f-f420-4e5b-888a-a1addadd054c\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-06-30T06:00:00Z\",\"subjects\":[],\"start\":\"2014-06-30T06:00:00Z\",\"description\":\"\",\"title\":\"30. Juni 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":30,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]},{\"identifier\":\"aa4ed4a9-71e1-47de-ae31-2d437827a123\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-06-23T06:00:00Z\",\"subjects\":[],\"start\":\"2014-06-23T06:00:00Z\",\"description\":\"\",\"title\":\"23. Juni 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":31,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]},{\"identifier\":\"9a74fbf4-1c64-49b7-ba53-5a274da37ca9\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-05-05T06:00:00Z\",\"subjects\":[],\"start\":\"2014-05-05T06:00:00Z\",\"description\":\"\",\"title\":\"5. Mai 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":31,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]}]";

        try {
            parser.parseEpisodesOfCourse(episodesOfCourse_working, episodes);
        } catch (JSONException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        assertEquals("05035dc4-3938-485d-8ea8-5081b9d6bc57", episodes.get(0).getId());
        assertEquals("7. April 2014 [MP4]", episodes.get(0).getEpisodeTitle());
        assertEquals(new SimpleDateFormat("yyyyMMddhhmmss").parse("201405260600000"), episodes.get(3).getDate());
    }

    @Test(expected = JSONException.class)
    public void parseEpisodesOfCourse_nonsense() throws JSONException {
        Parser parser = new Parser();
        ArrayList<Episode> episodes = new ArrayList<>();
        String episodesOfCourse_nonsense = "nonsense";
        parser.parseEpisodesOfCourse(episodesOfCourse_nonsense, episodes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseEpisodesOfCourse_null() throws JSONException {
        Parser parser = new Parser();
        ArrayList<Episode> episodes = new ArrayList<>();
        String episodesOfCourse_null = "";

        parser.parseEpisodesOfCourse(episodesOfCourse_null, episodes);
    }
}
