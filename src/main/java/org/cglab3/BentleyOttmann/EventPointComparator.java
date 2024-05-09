package org.cglab3.BentleyOttmann;

import java.awt.geom.Point2D;
import java.util.Comparator;

/**
 * Class for comparing points by the rule: if y coordinate of point 1 is smaller than of point 2, point 1 is larger,
 * if y coordinates are equal then if x coordinate point 1 is larger than of point 2, point 1 is larger.
 * If point is larger, then the event the point is associated with happens later during sweeping
 */
public class EventPointComparator implements Comparator<Point2D> {

    @Override
    public int compare(Point2D o1, Point2D o2) {
        if(o1.getY() < o2.getY())
            return 1;
        if(o1.getY() > o2.getY())
            return -1;
        return Double.compare(o1.getX(), o2.getX());
    }
}
