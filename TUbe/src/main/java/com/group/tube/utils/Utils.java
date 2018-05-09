package com.group.tube.utils;

import android.support.v4.util.Pair;

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

    // nope
    private Utils() {};
}
