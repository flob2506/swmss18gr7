package unitTests;

import android.support.v4.util.Pair;

import com.group.tube.utils.Utils;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class GetCurrentSemesterUtilUnitTest {
    @Test
    public void checkGetCurrentSemester() {
        Pair<Integer, Boolean> currentSemester = Utils.getCurrentSemester();

        Date date = new Date();
        int year = date.getYear() + 1900;
        int month = date.getMonth();

        if (month >= 10) {
            assertEquals(true, currentSemester.second);
            assertEquals(year, (int) currentSemester.first);
        } else if (month > 2 && month < 10) {
            assertEquals(false, currentSemester.second);
            assertEquals(year, (int) currentSemester.first);
        } else {
            assertEquals(true, currentSemester.second);
            assertEquals(year - 1, (int) currentSemester.first);
        }
    }
}