package com.group.tube;

import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.parser.Parser;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class ParserUnitTest {

    @Test
    public void parseAllCourses_working() throws ParseException {
        Parser parser = new Parser();
        ArrayList<Course> courses = new ArrayList<>();
        String allCourses_working = "[{\"identifier\":\"e1ad5c57-2e78-4933-99cb-645ab5b865c5\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2017-07-14T10:24:23Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[\"Wagner A\"],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"143.700 14S Architekturtheorie heute\"},{\"identifier\":\"88e0b9d4-e127-44c4-8cb6-23d10330dfec\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2017-07-14T10:28:08Z\",\"subjects\":[\"c-o-u-r-s-e l-i-v-e\"],\"organizers\":[\"Slany W\"],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"716.010 14W Programmieren 0\"},{\"identifier\":\"71f252d4-7453-4d75-b642-ed19c1dfcc74\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2017-07-06T15:58:38Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"141.532 14S Architekturgeschichte 1\"},{\"identifier\":\"bcfa6470-b7c8-4d85-8678-89c6844b1660\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2017-12-18T17:15:20Z\",\"subjects\":[\"e-v-e-n-t\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"Event Series\"},{\"identifier\":\"20327447-89c2-467d-b66e-8403089cdd2b\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-02-21T13:09:04Z\",\"subjects\":[\"l-i-v-e\"],\"organizers\":[],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"TU Graz Public\"},{\"identifier\":\"f74fa407-cce9-445d-b59d-1520c4145c82\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-02-21T13:09:51Z\",\"subjects\":[\"l-i-v-e\"],\"organizers\":[],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"TU Graz Campus\"},{\"identifier\":\"c9d478ef-4b8e-4c08-aed3-4c1980fcc268\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2017-09-15T09:59:06Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"NATM Engineer 2017 [Module 1]\"},{\"identifier\":\"6eaa3d1b-13f3-400f-aae8-bfb700ecb2c6\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-03-12T15:01:29Z\",\"subjects\":[\"l-i-v-e\"],\"organizers\":[],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"HS i13\"},{\"identifier\":\"b73fd584-317a-40d6-a3c1-86e6f9257ff8\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-03-22T13:38:09Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"[CO]123.432 18S New Series After Update\"},{\"identifier\":\"39088ea0-9fb4-4eb5-abf2-f7c75e644dae\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2017-07-14T10:26:25Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"[CO]143.534 14W Kunst- und Kulturwissenschaften\"},{\"identifier\":\"94de0288-0643-43c2-8e48-f20ed46952ac\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:46:30Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[\"Organizer O\"],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"234.567 18S anotherCourse\"},{\"identifier\":\"be327289-d653-49f2-a14a-45736771ad1d\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:53:00Z\",\"subjects\":[\"c-o-u-r-s-e l-i-v-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"DEF.234 18S aLiveCourse\"},{\"identifier\":\"88c97553-3ea1-4c62-91ac-96d7efc2eaf2\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:51:32Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"ABC.123 18S yetAnotherCourse\"},{\"identifier\":\"90748844-e2da-4da7-809c-7a4d404d2ef6\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:50:09Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"456.789 18S yetAnotherCourse\"},{\"identifier\":\"e81c413c-cc5c-461a-8cec-5a488e421cc0\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:49:34Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"345.678 18S yetAnotherCourse\"},{\"identifier\":\"f607dfe9-d3a4-4c30-a190-b2c58ee84205\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2017-11-07T10:33:58Z\",\"subjects\":[\"c-o-u-r-s-e l-i-v-e\"],\"organizers\":[\"Safran C\"],\"publishers\":[\"TU Graz\"],\"contributors\":[],\"title\":\"INB.03001UF 17W Einführung in die strukturierte Programmierung\"},{\"identifier\":\"d5a8992f-d5d7-451f-9b24-c9fe50311a01\",\"creator\":\"Opencast Project Administrator\",\"created\":\"2018-02-06T08:48:00Z\",\"subjects\":[\"c-o-u-r-s-e l-i-v-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"123.456 18S Empty Series\"},{\"identifier\":\"bfb7fe6a-5ef7-41a4-a0d0-9c6e98fdb3aa\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:54:49Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"CDE.567 18S yetAnotherCourse\"},{\"identifier\":\"1c85def2-138f-4083-bb6c-db70c4fb3b40\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:55:33Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"EFG.789 18S yetAnotherCourse\"},{\"identifier\":\"92ad9ee6-3a5d-4796-b0d5-b47355df9c7e\",\"creator\":\"Ypatios Grigoriadis\",\"created\":\"2018-04-05T06:54:17Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"BCD.456UF 18S aNawiCourse\"},{\"identifier\":\"3dc0cbbe-b622-498f-ba44-9473e57b3134\",\"creator\":\"Opencast Project Administrator\",\"created\":\"2018-04-05T06:50:40Z\",\"subjects\":[\"c-o-u-r-s-e\"],\"organizers\":[],\"publishers\":[],\"contributors\":[],\"title\":\"012.345 18S yetAnotherCourse\"}]";

        parser.parseAllCourses(allCourses_working, courses);

        assertEquals("143.700 14S Architekturtheorie heute", courses.get(0).getCourseTitle());
        assertEquals(2014, courses.get(2).getSemesterYear());
        assertEquals(true, courses.get(7).isWs());
    }

    @Test(expected = ParseException.class)
    public void parseAllCourses_nonsense() throws ParseException {
        Parser parser = new Parser();
        ArrayList<Course> courses = new ArrayList<>();
        String allCourses_nonsense = "nonsense";

        try {
            parser.parseAllCourses(allCourses_nonsense, courses);
        } catch (ParseException e) {
            assertEquals("JSONException occurred when parsing", e.getMessage());
            throw e;
        }
    }

    @Test(expected = ParseException.class)
    public void parseAllCourses_null() throws ParseException {
        Parser parser = new Parser();
        ArrayList<Course> courses = new ArrayList<>();
        String allCourses_null = "";

        try {
            parser.parseAllCourses(allCourses_null, courses);
        } catch (ParseException e) {
            assertEquals("JSONString is null or empty", e.getMessage());
            throw e;
        }
    }


    @Test
    public void parseEpisodesOfCourse_working() throws ParseException {
        Parser parser = new Parser();
        ArrayList<Episode> episodes = new ArrayList<>();
        String episodesOfCourse_working = "[{\"identifier\":\"05035dc4-3938-485d-8ea8-5081b9d6bc57\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-04-07T06:00:00Z\",\"subjects\":[],\"start\":\"2014-04-07T06:00:00Z\",\"description\":\"\",\"title\":\"7. April 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":31,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]},{\"identifier\":\"23e9d34e-8d0f-4da1-9000-e5ced77ee3ae\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-06-16T06:00:00Z\",\"subjects\":[],\"start\":\"2014-06-16T06:00:00Z\",\"description\":\"\",\"title\":\"16. Juni 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":31,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]},{\"identifier\":\"d28fed90-9def-4f6d-b451-34ef58e67c09\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-03-10T06:00:00Z\",\"subjects\":[],\"start\":\"2014-03-10T06:00:00Z\",\"description\":\"\",\"title\":\"10. März 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":34,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]},{\"identifier\":\"f9338064-4099-4c75-a674-06741b198347\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-05-26T06:00:00Z\",\"subjects\":[],\"start\":\"2014-05-26T06:00:00Z\",\"description\":\"\",\"title\":\"26. Mai 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":30,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]},{\"identifier\":\"b9217e1d-f5c5-47a3-87db-0cc63f0e8143\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-06-02T06:00:00Z\",\"subjects\":[],\"start\":\"2014-06-02T06:00:00Z\",\"description\":\"\",\"title\":\"2. Juni 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":32,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]},{\"identifier\":\"efa1061f-f420-4e5b-888a-a1addadd054c\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-06-30T06:00:00Z\",\"subjects\":[],\"start\":\"2014-06-30T06:00:00Z\",\"description\":\"\",\"title\":\"30. Juni 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":30,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]},{\"identifier\":\"aa4ed4a9-71e1-47de-ae31-2d437827a123\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-06-23T06:00:00Z\",\"subjects\":[],\"start\":\"2014-06-23T06:00:00Z\",\"description\":\"\",\"title\":\"23. Juni 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":31,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]},{\"identifier\":\"9a74fbf4-1c64-49b7-ba53-5a274da37ca9\",\"creator\":\"Ypatios Grigoriadis\",\"presenter\":[\"Wagner A\"],\"created\":\"2014-05-05T06:00:00Z\",\"subjects\":[],\"start\":\"2014-05-05T06:00:00Z\",\"description\":\"\",\"title\":\"5. Mai 2014 [MP4]\",\"processing_state\":\"SUCCEEDED\",\"archive_version\":31,\"contributor\":[],\"has_previews\":true,\"location\":\"\",\"publication_status\":[\"internal\",\"engage-player\"]}]";

        parser.parseEpisodesOfCourse(episodesOfCourse_working, episodes);


        assertEquals("05035dc4-3938-485d-8ea8-5081b9d6bc57", episodes.get(0).getId());
        assertEquals("7. April 2014 [MP4]", episodes.get(0).getEpisodeTitle());
        assertEquals(new SimpleDateFormat("yyyyMMddhhmmss").parse("201405260600000"), episodes.get(3).getDate());
    }

    @Test(expected = ParseException.class)
    public void parseEpisodesOfCourse_nonsense() throws ParseException {
        Parser parser = new Parser();
        ArrayList<Episode> episodes = new ArrayList<>();
        String episodesOfCourse_nonsense = "nonsense";
        try {
            parser.parseEpisodesOfCourse(episodesOfCourse_nonsense, episodes);
        } catch (ParseException e) {
            assertEquals("JSONException occurred when parsing", e.getMessage());
            throw e;
        }
    }

    @Test(expected = ParseException.class)
    public void parseEpisodesOfCourse_null() throws ParseException {
        Parser parser = new Parser();
        ArrayList<Episode> episodes = new ArrayList<>();
        String episodesOfCourse_null = "";

        try {
            parser.parseEpisodesOfCourse(episodesOfCourse_null, episodes);
        } catch (ParseException e) {
            assertEquals("JSONString is null or empty", e.getMessage());
            throw e;
        }
    }

    @Test
    public void parseMediaOfEpisodeTest() throws ParseException {
        Parser parser = new Parser();
        String result = parser.parseMediaOfEpisode(mediaResponse);

        String expectedURL = "https://tube-test.tugraz.at/static/mh_default_org/api/0ebba909-dfcb-4bb1-b809-c49a518a8c40/253952ee-109d-462b-a0b9-1f0d8950ddf8/coverimage.png";

        assertEquals(expectedURL, result);
    }

    private final String mediaResponse = "[{\"metadata\":[],\"attachments\":[],\"channel\":\"engage-player\",\"id\":\"86d1b382-a8f5-40a2-bbbf-df4aad8e06d1\",\"media\":[],\"mediatype\":\"text\\/html\",\"url\":\"https:\\/\\/tube-test.tugraz.at\\/paella\\/ui\\/watch.html?id=0ebba909-dfcb-4bb1-b809-c49a518a8c40\"},{\"metadata\":[{\"flavor\":\"dublincore\\/episode\",\"size\":-1,\"checksum\":\"\",\"id\":\"d22b183c-3e2c-40bd-8489-58b9d1c6c681\",\"mediatype\":\"text\\/xml\",\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/fea4cd0d-6c8e-4e3a-a17a-ae9d0b571734\\/dublincore.xml\",\"tags\":[\"archive\",\"engage-download\"]},{\"flavor\":\"dublincore\\/series\",\"size\":-1,\"checksum\":\"9162f6b3f10b762581e984f7fccd6f52 (md5)\",\"id\":\"5f14fe84-9c29-4a10-9cbe-298d018e1d56\",\"mediatype\":\"text\\/xml\",\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/6c0d49aa-4e7f-4dae-a25b-ecbf94471b33\\/dublincore.xml\",\"tags\":[\"archive\",\"engage-download\"]}],\"attachments\":[{\"flavor\":\"presenter\\/timeline+preview\",\"ref\":\"track:track-4\",\"size\":0,\"checksum\":\"\",\"id\":\"c573438e-ec26-4ffe-91b9-1c5c65c3b672\",\"mediatype\":\"image\\/png\",\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/b1671c43-bb9b-4b1f-968a-385821f55a00\\/bunny_7e21b5f6_e970_4eeb_a417_7a4be6010a85_timelinepreviews.png\",\"tags\":[\"engage-download\"]},{\"flavor\":\"presenter\\/search+preview\",\"ref\":\"track:track-4\",\"size\":0,\"checksum\":\"\",\"id\":\"a45d4753-6676-4622-808a-2df354a78af3\",\"mediatype\":\"image\\/jpeg\",\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/attachment-5\\/bunny_1_000s_search.jpg\",\"tags\":[\"engage-download\"]},{\"flavor\":\"presenter\\/player+preview\",\"ref\":\"\",\"size\":0,\"checksum\":\"\",\"id\":\"b4628a32-11ee-4c93-aa63-25cadfa9cafa\",\"mediatype\":\"image\\/png\",\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/253952ee-109d-462b-a0b9-1f0d8950ddf8\\/coverimage.png\",\"tags\":[\"archive\",\"engage-download\"]}],\"channel\":\"api\",\"id\":\"88d30de0-b799-4b3b-a59f-679a4563d5fb\",\"media\":[{\"has_audio\":true,\"framerate\":25.0,\"description\":\"\",\"bitrate\":329857.0,\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/16ddf942-8746-475b-99ec-76c94ee7f3e6\\/bunny.mp4\",\"has_video\":true,\"tags\":[\"360p-quality\",\"engage-download\",\"engage-streaming\"],\"flavor\":\"presenter\\/delivery\",\"duration\":71600,\"size\":-1,\"framecount\":1790,\"checksum\":\"cf9afc05248bcae9b18ce291284e0948 (md5)\",\"width\":640,\"id\":\"7cd8b9b5-5021-45c3-b006-97c8791cac02\",\"mediatype\":\"video\\/mp4\",\"height\":360},{\"has_audio\":true,\"framerate\":25.0,\"description\":\"\",\"bitrate\":790676.0,\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/0e51e9fc-011b-4ade-a540-c91390e0d4c0\\/bunny.mp4\",\"has_video\":true,\"tags\":[\"480p-quality\",\"engage-download\",\"engage-streaming\"],\"flavor\":\"presenter\\/delivery\",\"duration\":71600,\"size\":-1,\"framecount\":1790,\"checksum\":\"5549495db09a83c31bfb66e5e0fc5bcc (md5)\",\"width\":854,\"id\":\"b6a8bcad-a6f2-4f48-9235-ac72356938ae\",\"mediatype\":\"video\\/mp4\",\"height\":480},{\"has_audio\":true,\"framerate\":25.0,\"description\":\"\",\"bitrate\":1211168.0,\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/33c3ebcd-278c-4bb9-bc10-2ef9df32a6c0\\/bunny.mp4\",\"has_video\":true,\"tags\":[\"720p-quality\",\"engage-download\",\"engage-streaming\"],\"flavor\":\"presenter\\/delivery\",\"duration\":71600,\"size\":-1,\"framecount\":1790,\"checksum\":\"ede14a8dca414e3007a8e58990b73e08 (md5)\",\"width\":1280,\"id\":\"eee1b944-ca13-4aed-a732-e67562db533a\",\"mediatype\":\"video\\/mp4\",\"height\":720}],\"mediatype\":\"application\\/json\",\"url\":\"http:\\/\\/localhost:8080\\/api\\/events\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\"}]";
}
