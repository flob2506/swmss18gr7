package com.group.tube.utils;

import android.content.Context;

import com.group.tube.List.EpisodeTimeList;
import com.group.tube.List.FavouriteList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class LocalStorageUtils {
    public static final String FILE_NAME_COURSE_FAVORITES = "CourseFavoritesList.txt";
    public static final String FILE_NAME_EPISODE_TIMES = "EpisodeTimesList.txt";

    public static void writeListToFile(Context context, HashMap<String, Float> episodeTimes)  {
        writeObjectToFile(context, episodeTimes, FILE_NAME_EPISODE_TIMES);
    }
    public static void writeListToFile(Context context, Set<String> favorites)  {
        writeObjectToFile(context, favorites, FILE_NAME_COURSE_FAVORITES);
    }
    public static void writeEpisodeListToFile(Context context)  {
        writeListToFile(context, EpisodeTimeList.getInstance());
    }
    public static void writeCourseFavoriteListToFile(Context context)  {
        writeListToFile(context, FavouriteList.getInstance());
    }
    public static void readEpisodeListFromFile(Context context)  {
        Map<String, Float> listFromFile = (Map<String, Float>)readObjectFromFile(context, FILE_NAME_EPISODE_TIMES);
        if(listFromFile == null) {
            writeListToFile(context, new HashMap<String, Float>());
            return;
        }
        EpisodeTimeList.getInstance().overwrite(listFromFile);
    }

    public static void readCourseFavoriteListFromFile(Context context)  {
        Set<String> listFromFile = (Set<String>)readObjectFromFile(context, FILE_NAME_COURSE_FAVORITES);
        if(listFromFile == null) {
            writeListToFile(context, new LinkedHashSet<String>());
            return;
        }
        FavouriteList.getInstance().overwrite(listFromFile);
    }


    private static void writeObjectToFile(Context context, Object objectToWrite, String fileName)  {
        try {
            FileOutputStream outStream = context.openFileOutput(fileName, context.MODE_PRIVATE);
            ObjectOutputStream objectStream = new ObjectOutputStream(outStream);
            objectStream.writeObject(objectToWrite);
            objectStream.close();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static Object readObjectFromFile(Context context, String fileName)  {
        boolean isListValid;
        Object inputReturn = null;
        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            ObjectInputStream inputObject = new ObjectInputStream(inputStream);
            inputReturn = inputObject.readObject(); // class not found exc thrown
            inputObject.close();
            inputStream.close();
            isListValid = true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            isListValid = false;
        }

        if(isListValid) {
            return inputReturn;
        }
        return null;
    }
    private LocalStorageUtils() {};
}
