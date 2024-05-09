package org.cglab3.BentleyOttmann;

import junit.framework.TestCase;

import java.awt.geom.Point2D;

public class SegmentTest extends TestCase {

    public void testIntersection() {
        //case 1 - intersect:
        Segment s1 = new Segment(new Point2D.Double(1, 2), new Point2D.Double(4,6));
        Segment s2 = new Segment(new Point2D.Double(4, 2), new Point2D.Double(1,6));
        assertTrue(s1.intersection(s2).isPresent());
        assertEquals(new Point2D.Double(2.5, 4), s1.intersection(s2).get());
        //case 2 - no intersect:
        s2 = new Segment(new Point2D.Double(4, 7), new Point2D.Double(1,11));
        assertFalse(s1.intersection(s2).isPresent());
        //case 3 - collinear
        s2 = new Segment(new Point2D.Double(1, 7), new Point2D.Double(4,11));
        assertFalse(s1.intersection(s2).isPresent());
        //case 4 - intersect
        s1 = new Segment(new Point2D.Double(0, 1), new Point2D.Double(4,6));
        s2 = new Segment(new Point2D.Double(2, 1), new Point2D.Double(2,4));
        assertTrue(s1.intersection(s2).isPresent());
        assertEquals(new Point2D.Double(2, 3.5), s1.intersection(s2).get());
    }
}