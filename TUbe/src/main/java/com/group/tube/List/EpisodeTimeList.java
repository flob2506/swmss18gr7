package com.group.tube.List;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;


public class EpisodeTimeList extends HashMap<String, Float> {
    private static EpisodeTimeList singleton = new EpisodeTimeList();

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private EpisodeTimeList() { }

    /* Static 'instance' method */
    public static EpisodeTimeList getInstance( ) {
        return singleton;
    }
}


