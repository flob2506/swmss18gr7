package com.group.tube.List;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class FavouriteList extends ArrayList<String> {
    private static FavouriteList singleton = new FavouriteList();

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private FavouriteList() { }

    /* Static 'instance' method */
    public static FavouriteList getInstance( ) {
        return singleton;
    }
}


