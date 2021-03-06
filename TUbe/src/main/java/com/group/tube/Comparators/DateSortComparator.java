package com.group.tube.Comparators;
import com.group.tube.Models.Episode;

import java.util.Comparator;

public class DateSortComparator implements Comparator<Episode> {
    @Override
    public int compare(Episode episode1, Episode episode2) {
        return episode1.getDate().compareTo(episode2.getDate());
    }
}