package com.group.tube.utils;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.group.tube.ArrayAdapter.EpisodeArrayAdapter;
import com.group.tube.Comparators.DateSortComparator;
import com.group.tube.List.FavouriteList;
import com.group.tube.MainActivity;

import com.group.tube.R;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Utils
{
    public static String FILE_NAME = "CourseFavoritesList.txt";
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

    public static void writeListToFile(Context context)  {
        try {
            FileOutputStream outStream = context.openFileOutput(FILE_NAME, context.MODE_PRIVATE);
            ObjectOutputStream objectStream = new ObjectOutputStream(outStream);
            ArrayList<String> favorites = FavouriteList.getInstance();
            objectStream.writeObject(favorites);
            objectStream.close();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readListFromFile(Context context)  {
        boolean isListValid;
        Object inputReturn = null;
        try {
            FileInputStream inputStream = context.openFileInput(Utils.FILE_NAME);
            ObjectInputStream inputObject = new ObjectInputStream(inputStream);
            inputReturn = inputObject.readObject(); // class not found exc thrown
            inputObject.close();
            inputStream.close();
            isListValid = inputReturn instanceof ArrayList;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            isListValid = false;
        }

        if(isListValid) {
            ArrayList<String> favoritesList = (ArrayList<String>)inputReturn;
            favoritesList.addAll(FavouriteList.getInstance());
        } else {
            // list is invalid -> empty list
            writeListToFile(context);
        }
    }

    public static View getChildViewOfListView(ListView list, int wantedPosition) {
        int firstPosition = list.getFirstVisiblePosition() - list.getHeaderViewsCount();
        int wantedChild = wantedPosition - firstPosition;
        return list.getChildAt(wantedChild);
    }

    // nope
    private Utils() {};
}
