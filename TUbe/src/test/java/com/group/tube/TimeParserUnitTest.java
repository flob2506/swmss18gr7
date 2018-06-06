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

    final String timeResponse = "[{\"flavor\": \"dublincore/episode\",\"title\": \"EVENTS.EVENTS.DETAILS.CATALOG.EPISODE\", \"fields\": [{\"readOnly\": false, \"id\": \"title\", \"label\": \"EVENTS.EVENTS.DETAILS.METADATA.TITLE\", \"type\": \"text\", \"value\": \"test default wf with adaptive 1\", \"required\": true},{\"readOnly\": false, \"id\": \"startDate\", \"label\": \"EVENTS.EVENTS.DETAILS.METADATA.START_DATE\", \"type\": \"date\", \"value\": \"2018-05-13\", \"required\": false},{\"readOnly\": false, \"id\": \"duration\", \"label\": \"EVENTS.EVENTS.DETAILS.METADATA.DURATION\", \"type\": \"text\", \"value\": \"00:00:00\",\"required\": false}, {\"readOnly\": true, \"id\": \"identifier\", \"label\": \"EVENTS.EVENTS.DETAILS.METADATA.ID\", \"type\": \"text\", \"value\": \"0ebba909-dfcb-4bb1-b809-c49a518a8c40\", \"required\": false}]}]"

}
