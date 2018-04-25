package com.group.tube.Comparators;
import com.group.tube.Models.Episodes;

import java.util.Comparator;

public class DateSortComparator implements Comparator<Episodes> {
    @Override
    public int compare(Episodes episode1, Episodes episode2) {
        return episode2.getDate().compareTo(episode1.getDate());
    }
}