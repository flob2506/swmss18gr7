package com.group.tube.List;

import com.group.tube.Models.Episode;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;


public class WatchLaterList extends LinkedHashSet<String> {
    private static WatchLaterList singleton = new WatchLaterList();

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private WatchLaterList() { }

    /* Static 'instance' method */
    public static WatchLaterList getInstance( ) {
        return singleton;
    }

    public void overwrite(Set<String> list)
    {
        clear();
        addAll(list);
    }
}


