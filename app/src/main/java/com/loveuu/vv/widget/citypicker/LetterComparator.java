package com.loveuu.vv.widget.citypicker;

import java.util.Comparator;

/**
 * Created by VV on 2016/9/22.
 */

public class LetterComparator implements Comparator<SortModel> {

    @Override
    public int compare(SortModel o1, SortModel o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
