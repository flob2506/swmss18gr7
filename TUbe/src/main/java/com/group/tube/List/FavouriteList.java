package com.group.tube.List;

import android.support.annotation.NonNull;

import java.util.List;


public class FavouriteList {

    public
            //MISSES
    List<String> list = new List<String>() {
    };

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


