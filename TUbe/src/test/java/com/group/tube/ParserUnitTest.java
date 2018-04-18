package com.group.tube;

import android.preference.PreferenceActivity;

import com.group.tube.parser.Parser;
import com.group.tube.Models.Episodes;

import com.loopj.android.http.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;

/**
 * Created by cychu on 11/04/2018.
 */

public class ParserUnitTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void parser_returnsString() throws Exception {
        final Episodes e = new Episodes();
        final Parser p = new Parser();

        String JSONFILE = "{\n" +
                "    \"search-results\": {\n" +
                "        \"offset\": \"0\",\n" +
                "        \"limit\": \"8\",\n" +
                "        \"total\": \"8\",\n" +
                "        \"searchTime\": \"0\",\n" +
                "        \"query\": \"(dc_is_part_of:1ace56be\\\\-eb47\\\\-4150\\\\-97c1\\\\-9e285f34e5de) AND oc_organization:mh_default_org AND (oc_acl_read:ROLE_ANONYMOUS) AND -oc_mediatype:Series AND -oc_deleted:[* TO *]\",\n" +
                "        \"result\": [\n" +
                "            {\n" +
                "                \"id\": \"876d2ecf-11df-4b69-9bc2-376bf719c759\",\n" +
                "                \"org\": \"mh_default_org\",\n" +
                "                \"mediapackage\": {\n" +
                "                    \"duration\": \"3128927\",\n" +
                "                    \"id\": \"876d2ecf-11df-4b69-9bc2-376bf719c759\",\n" +
                "                    \"start\": \"2013-11-14T15:15:00Z\",\n" +
                "                    \"title\": \"#6\",\n" +
                "                    \"series\": \"1ace56be-eb47-4150-97c1-9e285f34e5de\",\n" +
                "                    \"seriestitle\": \"[CO]706.001 13W Einführung in die strukturierte Programmierung\",\n" +
                "                    \"creators\": {\n" +
                "                        \"creator\": \"Safran C\"\n" +
                "                    },\n" +
                "                    \"license\": \"Creative Commons 3.0: Attribution-NonCommercial-NoDerivs\",\n" +
                "                    \"media\": {\n" +
                "                        \"track\": [\n" +
                "                            {\n" +
                "                                \"id\": \"96a94a0b-7508-4906-aac3-f22cdab7ade9\",\n" +
                "                                \"type\": \"presentation/delivery\",\n" +
                "                                \"ref\": \"track:be5419ea-cf90-4844-9e9a-5b5391c57ef6\",\n" +
                "                                \"mimetype\": \"video/mp4\",\n" +
                "                                \"tags\": {\n" +
                "                                    \"tag\": [\n" +
                "                                        \"engage-download\",\n" +
                "                                        \"engage-streaming\"\n" +
                "                                    ]\n" +
                "                                },\n" +
                "                                \"url\": \"https://tube.tugraz.at/static/mh_default_org/engage-player/876d2ecf-11df-4b69-9bc2-376bf719c759/d32e06cf-3fb1-4150-b790-e95162af31ae/track.mp4\",\n" +
                "                                \"checksum\": {\n" +
                "                                    \"type\": \"md5\",\n" +
                "                                    \"$\": \"0666aa8d010f5522f4ddeb5d0077181e\"\n" +
                "                                },\n" +
                "                                \"duration\": 3128927,\n" +
                "                                \"audio\": {\n" +
                "                                    \"id\": \"audio-1\",\n" +
                "                                    \"device\": \"\",\n" +
                "                                    \"encoder\": {\n" +
                "                                        \"type\": \"AAC (Advanced Audio Coding)\"\n" +
                "                                    },\n" +
                "                                    \"channels\": 2,\n" +
                "                                    \"samplingrate\": 44100,\n" +
                "                                    \"bitrate\": 65686\n" +
                "                                },\n" +
                "                                \"video\": {\n" +
                "                                    \"id\": \"video-1\",\n" +
                "                                    \"device\": \"\",\n" +
                "                                    \"encoder\": {\n" +
                "                                        \"type\": \"H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10\"\n" +
                "                                    },\n" +
                "                                    \"bitrate\": 178192,\n" +
                "                                    \"framerate\": 25.416666,\n" +
                "                                    \"resolution\": \"1280x960\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"id\": \"8b6c7ca6-8a66-45d7-a584-e003040041e6\",\n" +
                "                                \"type\": \"presenter/delivery\",\n" +
                "                                \"ref\": \"track:7849a07f-0fd6-4d4e-9a01-2e32020f7afa\",\n" +
                "                                \"mimetype\": \"video/mp4\",\n" +
                "                                \"tags\": {\n" +
                "                                    \"tag\": [\n" +
                "                                        \"engage-download\",\n" +
                "                                        \"engage-streaming\"\n" +
                "                                    ]\n" +
                "                                },\n" +
                "                                \"url\": \"https://tube.tugraz.at/static/mh_default_org/engage-player/876d2ecf-11df-4b69-9bc2-376bf719c759/8fbca277-bc51-4316-9c31-5bef03eadea0/track.mp4\",\n" +
                "                                \"checksum\": {\n" +
                "                                    \"type\": \"md5\",\n" +
                "                                    \"$\": \"9d03fff33258b9ab4cd3cbae9d6b0ebf\"\n" +
                "                                },\n" +
                "                                \"duration\": 3146184,\n" +
                "                                \"audio\": {\n" +
                "                                    \"id\": \"audio-1\",\n" +
                "                                    \"device\": \"\",\n" +
                "                                    \"encoder\": {\n" +
                "                                        \"type\": \"AAC (Advanced Audio Coding)\"\n" +
                "                                    },\n" +
                "                                    \"channels\": 2,\n" +
                "                                    \"samplingrate\": 44100,\n" +
                "                                    \"bitrate\": 65684\n" +
                "                                },\n" +
                "                                \"video\": {\n" +
                "                                    \"id\": \"video-1\",\n" +
                "                                    \"device\": \"\",\n" +
                "                                    \"encoder\": {\n" +
                "                                        \"type\": \"H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10\"\n" +
                "                                    },\n" +
                "                                    \"bitrate\": 48979,\n" +
                "                                    \"framerate\": 15,\n" +
                "                                    \"resolution\": \"320x240\"\n" +
                "                                }\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        p.parseJSON(JSONFILE, e);
        assertEquals(e.id, "876d2ecf-11df-4b69-9bc2-376bf719c759");
        assertEquals(e.episode_title, "#6");
        assertEquals(e.course_title, "[CO]706.001 13W Einführung in die strukturierte Programmierung");
        assertEquals(e.presentation_url, "https://tube.tugraz.at/static/mh_default_org/engage-player/876d2ecf-11df-4b69-9bc2-376bf719c759/d32e06cf-3fb1-4150-b790-e95162af31ae/track.mp4");
        assertEquals(e.presenter_url, "https://tube.tugraz.at/static/mh_default_org/engage-player/876d2ecf-11df-4b69-9bc2-376bf719c759/8fbca277-bc51-4316-9c31-5bef03eadea0/track.mp4");
    }

}
