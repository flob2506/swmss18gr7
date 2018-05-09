package com.group.tube.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.util.Pair;

import com.group.tube.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils
{
    public static String formatDate(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    public static Date getDate(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Pair<Integer, Boolean> getCurrentSemester()
    {
        Date currentDate = new Date();
        int currentMonth = currentDate.getMonth();
        int semesterYear;
        boolean isWs;
        if(currentMonth >= 10) {
            semesterYear = currentDate.getYear();
            isWs = true;
        } else if (currentMonth > 2 && currentMonth < 10) {
            semesterYear = currentDate.getYear();
            isWs = false;
        } else { // currentMonth <= 2
            semesterYear = currentDate.getYear() - 1;
            isWs = true;
        }
        semesterYear += 1900;
        return new Pair<>(semesterYear, isWs);
    }
    public static Pair<Integer, Boolean> getLastSemester()
    {
        Pair<Integer, Boolean> currentSemester = getCurrentSemester();
        if(currentSemester.second) {
            return new Pair<>(currentSemester.first, false);
        }
        return new Pair<>(currentSemester.first - 1, true);
    }

    public static String getChosenSemesterText(int semesterYear, boolean isWs, Context context)
    {
        String text;
        boolean isCurrent;
        if((isCurrent = Utils.isSemesterPair(semesterYear, isWs, getCurrentSemester())) ||
            Utils.isSemesterPair(semesterYear, isWs, getLastSemester())) {

            int stringResource = isWs ? R.string.ws : R.string.ss;
            String semesterType = context.getResources().getString(stringResource);
            String semesterText = isCurrent ? "This semester" : "Last semester";
            text = String.format("%s (%02d%s)", semesterText, semesterYear % 100, semesterType);
        } else {
            int stringResource = isWs ? R.string.winter_semester : R.string.summer_semester;
            String semesterType = context.getResources().getString(stringResource);
            text = String.format("%s %d", semesterType, semesterYear);
        }
        return text;
    }


    private static boolean isSemesterPair(int semesterYear, boolean isWs, Pair<Integer, Boolean> semesterPair)
    {
        return semesterPair.first == semesterYear && semesterPair.second == isWs;
    }

    // nope
    private Utils() {};
}
