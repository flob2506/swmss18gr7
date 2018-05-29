package com.group.tube;

import android.content.Context;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.group.tube.utils.Utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class InternalStorageUITest {

    @Rule
    public ActivityTestRule<CoursesOverviewActivity> mActivityRule = new ActivityTestRule<>(
            CoursesOverviewActivity.class);
    private Context context = mActivityRule.getActivity();
    private String filename = "myfile.txt";
    private String fileContents = "TEST";
    private FileOutputStream Outstream;
    private FileInputStream Inpstream;

    @Before
    public void setUpFile(){
        try{
            Outstream = context.openFileOutput(filename, context.MODE_PRIVATE);
            Outstream.write(fileContents.getBytes());
            Outstream.close();
        }
        catch(Exception e)  {
            e.printStackTrace();
        }
    }

    @Test
    public void checkFileExists(){
        try{
            Inpstream = context.openFileInput(filename);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        assertNull(Inpstream);
    }

    @Test
    public void checkIfContentCorrect() {
        try {
            Inpstream = context.openFileInput(filename);
            int c;

            String temp = "";
            while ((c = Inpstream.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }

            assertTrue(temp.equals(fileContents));
            Inpstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}