package org.cglab3.BentleyOttmann;

import java.util.Comparator;

public class SegmentXComparator implements Comparator<Segment> {
    @Override
    public int compare(Segment o1, Segment o2) {
        return Double.compare(o1.getCurrentX(), o2.getCurrentX());
    }
}
