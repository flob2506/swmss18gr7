package com.group.tube;

import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.parser.Parser;

import org.junit.Test;

import java.text.ParseException;
import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;

public class TimeParserUnitTest {

    @Test
    public void parseTimeFromStamp() throws InterruptedException{
        final CountDownLatch signal = new CountDownLatch(1);

        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
        networkConnector.loadTimeOfEpisode(new AsyncResponse<String>() {
            @Override
            public void processFinish(String response) {
                assertEquals(timeResponse, response);
                signal.countDown();
            }

            @Override
            public void handleProcessException(Exception e) {
                fail("Exception mustn't be thrown");
            }
        }, "0ebba909-dfcb-4bb1-b809-c49a518a8c40");
        signal.await();
    }

    final String timeResponse = "[{\"flavor\":\"dublincore\\/episode\",\"title\":\"EVENTS.EVENTS.DETAILS.CATALOG.EPISODE\",\"fields\":[{\"readOnly\":false,\"id\":\"title\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.TITLE\",\"type\":\"text\",\"value\":\"test default wf with adaptive 1\",\"required\":true},{\"readOnly\":false,\"id\":\"subjects\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.SUBJECT\",\"type\":\"text\",\"value\":[],\"required\":false},{\"readOnly\":false,\"id\":\"description\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.DESCRIPTION\",\"type\":\"text_long\",\"value\":\"\",\"required\":false},{\"translatable\":true,\"readOnly\":false,\"id\":\"language\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.LANGUAGE\",\"type\":\"text\",\"value\":\"\",\"required\":false},{\"readOnly\":false,\"id\":\"rightsHolder\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.RIGHTS\",\"type\":\"text\",\"value\":\"\",\"required\":false},{\"translatable\":true,\"readOnly\":false,\"id\":\"license\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.LICENSE\",\"type\":\"text\",\"value\":\"\",\"required\":false},{\"translatable\":false,\"readOnly\":false,\"id\":\"isPartOf\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.SERIES\",\"type\":\"text\",\"value\":\"88c97553-3ea1-4c62-91ac-96d7efc2eaf2\",\"required\":false},{\"translatable\":false,\"readOnly\":false,\"id\":\"creator\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.PRESENTERS\",\"type\":\"mixed_text\",\"value\":[],\"required\":false},{\"translatable\":false,\"readOnly\":false,\"id\":\"contributor\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.CONTRIBUTORS\",\"type\":\"mixed_text\",\"value\":[],\"required\":false},{\"readOnly\":false,\"id\":\"startDate\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.START_DATE\",\"type\":\"date\",\"value\":\"2018-05-13\",\"required\":false},{\"readOnly\":false,\"id\":\"startTime\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.START_TIME\",\"type\":\"time\",\"value\":\"19:42\",\"required\":false},{\"readOnly\":false,\"id\":\"duration\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.DURATION\",\"type\":\"text\",\"value\":\"00:00:00\",\"required\":false},{\"readOnly\":false,\"id\":\"location\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.LOCATION\",\"type\":\"text\",\"value\":\"\",\"required\":false},{\"readOnly\":false,\"id\":\"source\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.SOURCE\",\"type\":\"text\",\"value\":\"\",\"required\":false},{\"readOnly\":true,\"id\":\"created\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.CREATED\",\"type\":\"date\",\"value\":\"2018-05-13T19:42:00.000Z\",\"required\":false},{\"readOnly\":true,\"id\":\"identifier\",\"label\":\"EVENTS.EVENTS.DETAILS.METADATA.ID\",\"type\":\"text\",\"value\":\"0ebba909-dfcb-4bb1-b809-c49a518a8c40\",\"required\":false}]}]";

}
