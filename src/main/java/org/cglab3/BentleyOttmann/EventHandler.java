package org.cglab3.BentleyOttmann;

import lombok.extern.java.Log;

import java.awt.geom.Point2D;
import java.util.*;

@Log
class EventHandler {
    static void handleStartEvent(Segment segment, PriorityQueue<Event> events, TreeSet<Segment> status) {
        log.info("Handling start endpoint event for segment" + segment.toString());
        // Add segment to the status
        status.add(segment);
        // Find the segment's neighbours
        Segment leftNeighbour = status.lower(segment);
        Segment rightNeighbour = status.higher(segment);
        // Check if the segment intersects its neighbours (if they exist at all)
        // and if so add intersection events to the PQ
        if (leftNeighbour != null)
            checkIntersection(segment, leftNeighbour, events);
        if (rightNeighbour != null)
            checkIntersection(segment, rightNeighbour, events);
    }

    static void handleEndEvent(Segment segment, PriorityQueue<Event> events, TreeSet<Segment> status) {
        log.info("Handling end endpoint event for segment" + segment.toString());
        // Find the segment's neighbours
        Segment leftNeighbour = status.lower(segment);
        Segment rightNeighbour = status.higher(segment);
        // Remove segment from the status
        status.add(segment);
        // Check if the segment's ex-neighbours intersect (again, if they exist)
        // and if so add intersection event to the PQ
        if (leftNeighbour != null && rightNeighbour != null)
            checkIntersection(leftNeighbour, rightNeighbour, events);
    }

    static void handleIntersectionEvent(Segment segment, Segment segment2, PriorityQueue<Event> events, TreeSet<Segment> status) {
        double currentY = Segment.currentY;
        final double deltaY = 0.000001;
        log.info("Handling intersection event between segments " + segment.toString()
                + " and " + segment2.toString()
                + " at y=" + currentY);
        // Add a little to currentY so comparing intersecting segments doesn't show they're equal,
        // thus making it possible (or just easier) to find neighbors of the segments
        Segment.currentY = currentY + deltaY;
        // Determine which of the segments is to the left and which is to the right
        if (new SegmentXComparator().compare(segment, segment2) < 0) {
            // If segment is to the left of segment2
            // Search for left neighbour of segment and right neighbour of segment2
            // and find intersections of segment and its left neighbour
            // and of segment2 and its right neighbour (if the neighbours exist)
            Segment leftNeighbour = status.lower(segment);
            Segment rightNeighbour = status.higher(segment2);
            if (leftNeighbour != null)
                checkIntersection(leftNeighbour, segment, events);
            if (rightNeighbour != null)
                checkIntersection(rightNeighbour, segment2, events);
        } else {
            // If segment2 is to the left of segment
            Segment leftNeighbour = status.lower(segment2);
            Segment rightNeighbour = status.higher(segment);
            if (leftNeighbour != null)
                checkIntersection(leftNeighbour, segment2, events);
            if (rightNeighbour != null)
                checkIntersection(rightNeighbour, segment, events);
        }
    }

    private static void checkIntersection(Segment segment, Segment segment1, PriorityQueue<Event> events) {
        Optional<Point2D.Double> intersectionPoint = segment.intersection(segment1);
        if (intersectionPoint.isPresent() && intersectionPoint.get().y < Segment.currentY) {
            Event intersectionEvent = new Event(Event.Type.INTERSECTION, segment, segment1, intersectionPoint.get());
            if (!events.contains(intersectionEvent)) {
                events.add(intersectionEvent);
            }
        }
    }
}
