package com.group.tube.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.util.Pair;

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
import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
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
import java.util.Set;
import java.util.List;

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

    public static int getColor(final Context context)
    {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary,value, true );
        return value.data;
    }

    public static void writeListToFile(Context context, Set<String> favorites)  {
        try {
            FileOutputStream outStream = context.openFileOutput(FILE_NAME, context.MODE_PRIVATE);
            ObjectOutputStream objectStream = new ObjectOutputStream(outStream);
            objectStream.writeObject(favorites);
            objectStream.close();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeListToFile(Context context)  {
        writeListToFile(context, FavouriteList.getInstance());
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
            isListValid = inputReturn instanceof Set;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            isListValid = false;
        }

        if(isListValid) {
            Set<String> favoritesList = (Set<String>)inputReturn;
            FavouriteList.getInstance().overwrite(favoritesList);
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
    public static boolean matchesAll(Course course, List<String> query) {
        for (String s : query){
            if (!course.getCourseTitle().toLowerCase().contains(s.toLowerCase()))
                return false;
        }
        return true;
    }

    // nope
    private Utils() {};
}
