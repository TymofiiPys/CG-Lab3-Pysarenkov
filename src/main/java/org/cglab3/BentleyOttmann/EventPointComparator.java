package org.cglab3.BentleyOttmann;

import java.util.Comparator;

/**
 * Class for comparing points by the rule: if y coordinate of point 1 is smaller than of point 2, point 1 is larger,
 * if y coordinates are equal then if x coordinate point 1 is larger than of point 2, point 1 is larger.
 * If point is larger, then the event the point is associated with happens later during sweeping
 */
class EventPointComparator implements Comparator<Event> {

    @Override
    public int compare(Event o1, Event o2) {
        if(o1.getAssociatedPoint().getY() < o2.getAssociatedPoint().getY())
            return 1;
        if(o1.getAssociatedPoint().getY() > o2.getAssociatedPoint().getY())
            return -1;
        return Double.compare(o1.getAssociatedPoint().getX(), o2.getAssociatedPoint().getX());
    }
}
