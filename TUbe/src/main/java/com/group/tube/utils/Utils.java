package com.group.tube.utils;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.Window;

import com.group.tube.ArrayAdapter.EpisodeArrayAdapter;
import com.group.tube.Comparators.DateSortComparator;
import com.group.tube.MainActivity;

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


    public static int getColor(final Context context)
    {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary,value, true );
        return value.data;
    }

    // nope
    private Utils() {};
}
