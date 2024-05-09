package org.cglab3.BentleyOttmann;

import lombok.SneakyThrows;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

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

        return new RTree2D(segments);
    }

    public static Point2D.Double sweep(ArrayList<Segment> segments) {
        ArrayList<Event> endpointEvents = new ArrayList<>();
        for (Segment s : segments) {
            endpointEvents.add(new Event(Event.Type.START, s));
            endpointEvents.add(new Event(Event.Type.END, s));
        }

        ArrayList<Event> events = new ArrayList<>(endpointEvents);
        ArrayList<Segment> status = new ArrayList<>();

        int nEvents = 0;
        int nIntersections = 0;

        while (!events.isEmpty()) {
            Event e = events.get(0);
            nEvents++;
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
}
