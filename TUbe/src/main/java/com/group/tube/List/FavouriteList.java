package com.group.tube.List;

import java.util.LinkedHashSet;
import java.util.Set;


public class FavouriteList extends LinkedHashSet<String> {
    private static FavouriteList singleton = new FavouriteList();

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private FavouriteList() { }

    /* Static 'instance' method */
    public static FavouriteList getInstance( ) {
        return singleton;
    }

    public void overwrite(Set<String> list)
    {
        clear();
        addAll(list);
    }
}


