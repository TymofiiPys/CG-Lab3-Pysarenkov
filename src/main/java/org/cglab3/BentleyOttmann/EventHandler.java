package org.cglab3.BentleyOttmann;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Optional;

class EventHandler {
    static void handleStartEvent(Segment segment, ArrayList<Event> events, ArrayList<Segment> status) {

    }
    static void handleEndEvent(Segment segment, ArrayList<Event> events, ArrayList<Segment> status) {

    }
    static void handleIntersectionEvent(Segment segment, Segment segment2, ArrayList<Event> events, ArrayList<Segment> status) {

    }

    private void checkIntersection(int pos, int pos2, ArrayList<Event> events, ArrayList<Segment> status) {
        Segment segment = status.get(pos);
        Segment segment1 = status.get(pos2);
        Optional<Point2D.Double> intersectionPoint = segment.intersection(segment1);
        if(intersectionPoint.isPresent() && intersectionPoint.get().y < segment.getCurrentY()) {
            Event intersectionEvent = new Event(Event.Type.INTERSECTION, segment, segment1, intersectionPoint.get());
            // index = Events.bisect_left(ievent)
            int index = events.indexOf(intersectionEvent);
            if (index == events.size() || !(events.get(index).getEventType() == Event.Type.INTERSECTION) {
                events.add(intersectionEvent);
            }
        }
    }
}
