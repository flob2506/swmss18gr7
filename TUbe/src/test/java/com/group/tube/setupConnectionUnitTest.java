package com.group.tube;
import android.os.Bundle;

import org.junit.Test;
public class setupConnectionUnitTest {

    @Test
    public void setup_connection_test() throws Exception {
        MainActivity ma = new MainActivity();
        Bundle bundle = new Bundle();

        try
        {
            ma.onCreate(bundle);
        }
        catch (Exception e)
        {
            System.out.println("Test didn't pass.");

        }

        assert(false);
        System.out.println("Passed test.");
    }
}
