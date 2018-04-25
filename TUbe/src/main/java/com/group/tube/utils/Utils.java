package com.group.tube.utils;

import com.group.tube.ArrayAdapter.EpisodeArrayAdapter;
import com.group.tube.Comparators.DateSortComparator;
import com.group.tube.Models.Episodes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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

    // nope
    private Utils() {};
}
