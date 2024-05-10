package org.cglab3.util;

import lombok.SneakyThrows;
import org.cglab3.BentleyOttmann.Segment;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class SegmentParser {
    @SneakyThrows(IOException.class)
    public static ArrayList<Segment> readFromFile(String filename) {
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

        return segments;
    }
}
