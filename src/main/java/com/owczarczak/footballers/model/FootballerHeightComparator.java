package com.owczarczak.footballers.model;

import java.util.Comparator;

public class FootballerHeightComparator implements Comparator<Footballer> {
    @Override
    public int compare(final Footballer o1, final Footballer o2) {
        return Integer.compare(o2.getHeight(), o1.getHeight());
    }
}