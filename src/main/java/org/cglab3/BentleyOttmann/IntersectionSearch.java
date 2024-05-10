package org.cglab3.BentleyOttmann;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.cglab3.exception.EmptyQueueException;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletionException;
import java.util.stream.Stream;

@Log
@Getter
public class IntersectionSearch {
    private final ArrayList<Segment> segments;
    private final PriorityQueue<Event> events = new PriorityQueue<>(new EventPointComparator());
    private final TreeSet<Segment> status = new TreeSet<>(new SegmentXComparator());
    private int nEvents = 0;
    private int nIntersections = 0;
    private final ArrayList<Event> processedEvents = new ArrayList<>();

    public IntersectionSearch(ArrayList<Segment> segments) {
        this.segments = segments;
        // Create endpoint event for each segment
        ArrayList<Event> endpointEvents = new ArrayList<>();
        for (Segment s : segments) {
            endpointEvents.add(new Event(Event.Type.START, s));
            endpointEvents.add(new Event(Event.Type.END, s));
        }
        events.addAll(endpointEvents);
        log.info("Parsed " + segments.size() + " segments," +
                " created in total " + endpointEvents.size() + " endpoint events");
    }

    public void sweep() {
        while (!events.isEmpty()) {
            nextEvent();
        }
    }

    public void nextEvent() {
        if (events.isEmpty()) {
            throw new EmptyQueueException();
        }
        Event e = events.poll();
        nEvents++;
        log.info("Got event №" + nEvents + ": " + e.toString());
        processedEvents.add(e);
        switch (e.getEventType()) {
            case INTERSECTION -> {
                nIntersections++;
                EventHandler.handleIntersectionEvent(e.getSegment(), e.getSegment2(), events, status);
            }
            case START -> {
                EventHandler.handleStartEvent(e.getSegment(), events, status);
            }
            case END -> {
                EventHandler.handleEndEvent(e.getSegment(), events, status);
            }
        }
    }
}
