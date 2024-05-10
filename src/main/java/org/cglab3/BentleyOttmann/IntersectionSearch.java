package org.cglab3.BentleyOttmann;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Log
public class IntersectionSearch {
    @SneakyThrows(IOException.class)
    public static IntersectionSearch readFromFile(String filename) {
        Stream<String> fileLines;
        List<String> lines;
        fileLines = Files.lines(Paths.get(filename));
        lines = fileLines.toList();
        fileLines.close();

        ArrayList<Segment> segments = new ArrayList<>();
        Scanner lineScanner;
        double x1, y1, x2, y2;
        for (String line : lines) {
            lineScanner = new Scanner(line);
            try {
                x1 = lineScanner.nextDouble();
                y1 = lineScanner.nextDouble();
                x2 = lineScanner.nextDouble();
                y2 = lineScanner.nextDouble();
            } catch (Exception e) {
                throw new IllegalArgumentException("File opened has wrong format");
            }
            segments.add(new Segment(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2)));
        }

//        return new RTree2D(segments);
        return new IntersectionSearch();
    }

    public static Point2D.Double sweep(ArrayList<Segment> segments) {
        ArrayList<Event> endpointEvents = new ArrayList<>();
        for (Segment s : segments) {
            endpointEvents.add(new Event(Event.Type.START, s));
            endpointEvents.add(new Event(Event.Type.END, s));
        }
        log.info("Parsed " + segments.size() + " segments," +
                " created in total " + endpointEvents.size() + " endpoint events");

        PriorityQueue<Event> events = new PriorityQueue<>(new EventPointComparator());
        TreeSet<Segment> status = new TreeSet<>(new SegmentXComparator());

        events.addAll(endpointEvents);
        int nEvents = 0;
        int nIntersections = 0;

        while (!events.isEmpty()) {
            Event e = events.poll();
            nEvents++;
            log.info("Got event №" + nEvents + ": " + e.toString());
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
        return null;
    }
}
