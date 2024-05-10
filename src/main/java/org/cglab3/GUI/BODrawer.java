package org.cglab3.GUI;

import lombok.Getter;
import lombok.Setter;
import org.cglab3.BentleyOttmann.Event;
import org.cglab3.BentleyOttmann.IntersectionSearch;
import org.cglab3.BentleyOttmann.Segment;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class BODrawer {
    private IntersectionSearch BO;
    private ArrayList<Segment> segments;
    private final JPanel panelDraw;
    private final int nodesRad = 4;
    private final Stroke segmentStroke = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
    private final Color regularSegmentColor = Color.BLACK;
    private final Color segmentInStatusColor = new Color(182, 130, 195);
    private final Color statusColor = Color.BLUE;
    private final Color intersectionColor = Color.YELLOW;
    private final Color processedEventColor = Color.GREEN;

    @Setter
    private int[] offsets;
    private int layer = 0;

    public BODrawer(JPanel panelDraw) {
        this.panelDraw = panelDraw;
    }

    public void setSegments(ArrayList<Segment> segments) {
        this.BO = new IntersectionSearch(segments);
    }

    public boolean BOSet() {
        return BO != null;
    }

    public Point2D.Double adaptToPanel(Point2D.Double p) {
        return new Point2D.Double(p.x + offsets[0], panelDraw.getHeight() - (p.y + offsets[1]));
    }

    public Point2D.Double adaptFromPanel(Point2D.Double p) {
        return new Point2D.Double(p.x - offsets[0], panelDraw.getHeight() - (p.y + offsets[1]));
    }

    public void drawPoints() {
        Graphics2D gr = (Graphics2D) panelDraw.getGraphics();
        if (layer == 0)
            gr.clearRect(0, 0, panelDraw.getWidth(), panelDraw.getHeight());

        gr.drawLine(0, panelDraw.getHeight() - offsets[1], panelDraw.getWidth(), panelDraw.getHeight() - offsets[1]);
        gr.drawLine(offsets[0], 0, offsets[0], panelDraw.getHeight());
        gr.drawLine(50 + offsets[0],
                panelDraw.getHeight() - (10 + offsets[1]),
                50 + offsets[0],
                panelDraw.getHeight() - (-10 + offsets[1]));
        gr.drawLine(-10 + offsets[0],
                panelDraw.getHeight() - (50 + offsets[1]),
                10 + offsets[0],
                panelDraw.getHeight() - (50 + offsets[1]));
        gr.drawString("50", 45 + offsets[0], panelDraw.getHeight() - (-25 + offsets[1]));
        gr.drawString("50", -30 + offsets[0], panelDraw.getHeight() - (45 + offsets[1]));

//        ArrayList<Point2D.Double> points = rtree.getPoints();
        int k = 1;
        int textXOffset = -15;
        int textYOffset = 15;
        gr.setColor(Color.BLUE);
//        for (var point : points) {
//            var adapted = adaptToPanel(point);
//            gr.fillOval((int) (adapted.x - nodesRad),
//                    (int) (adapted.y - nodesRad),
//                    2 * nodesRad,
//                    2 * nodesRad);
//            gr.drawString(k++ + "", (int) adapted.x + textXOffset, (int) adapted.y + textYOffset);
//        }
        layer = 0;
    }

    public void drawRectangle(Point2D.Double p1, Point2D.Double p2) {
        Graphics2D gr = (Graphics2D) panelDraw.getGraphics();
        if (layer == 0)
            gr.clearRect(0, 0, panelDraw.getWidth(), panelDraw.getHeight());
        layer++;
        drawPoints();
        gr.setColor(Color.RED);
        if (p1.x < p2.x && p1.y < p2.y) {
            gr.drawRect((int) p1.x, (int) p1.y, (int) (p2.x - p1.x), (int) (p2.y - p1.y));
        } else if (p1.x < p2.x && p1.y > p2.y) {
            gr.drawRect((int) p1.x, (int) p2.y, (int) (p2.x - p1.x), (int) (p1.y - p2.y));
        } else if (p1.x > p2.x && p1.y < p2.y) {
            gr.drawRect((int) p2.x, (int) p1.y, (int) (p1.x - p2.x), (int) (p2.y - p1.y));
        } else {
            gr.drawRect((int) p2.x, (int) p2.y, (int) (p1.x - p2.x), (int) (p1.y - p2.y));
        }
    }

    private void drawAxes() {
        Graphics2D gr = (Graphics2D) panelDraw.getGraphics();
        gr.drawLine(0, panelDraw.getHeight() - offsets[1], panelDraw.getWidth(), panelDraw.getHeight() - offsets[1]);
        gr.drawLine(50 + offsets[0],
                panelDraw.getHeight() - (10 + offsets[1]),
                50 + offsets[0],
                panelDraw.getHeight() - (-10 + offsets[1]));
        gr.drawLine(-10 + offsets[0],
                panelDraw.getHeight() - (50 + offsets[1]),
                10 + offsets[0],
                panelDraw.getHeight() - (50 + offsets[1]));
        gr.drawString("50", 45 + offsets[0], panelDraw.getHeight() - (-25 + offsets[1]));
        gr.drawString("50", -30 + offsets[0], panelDraw.getHeight() - (45 + offsets[1]));
    }

    public void drawBO() {
        Graphics2D gr = (Graphics2D) panelDraw.getGraphics();
        gr.clearRect(0, 0, panelDraw.getWidth(), panelDraw.getHeight());
        drawAxes();
        gr.setColor(regularSegmentColor);
        gr.setStroke(segmentStroke);
        for (Segment s : BO.getSegments()) {
            gr.drawLine((int) s.getStart().x, (int) s.getStart().y, (int) s.getEnd().x, (int) s.getEnd().y);
        }
        gr.setColor(segmentInStatusColor);
        for (Segment s : BO.getStatus()) {
            gr.drawLine((int) s.getStart().x, (int) s.getStart().y, (int) s.getEnd().x, (int) s.getEnd().y);
        }
        gr.setColor(processedEventColor);
        for (Event e : BO.getProcessedEvents()) {
            if (Objects.requireNonNull(e.getEventType()) == Event.Type.INTERSECTION) {
                gr.setColor(intersectionColor);
            } else {
                gr.setColor(processedEventColor);
            }
            Point2D.Double p = e.getAssociatedPoint();
            gr.fillOval((int) (p.x - nodesRad), (int) (p.y - nodesRad), 2 * nodesRad, 2 * nodesRad);
        }
        gr.setColor(intersectionColor);
        BO.getEvents().stream().filter(event -> event.getEventType() == Event.Type.INTERSECTION).forEach(
                event -> {
                    Point2D.Double p = event.getAssociatedPoint();
                    gr.fillOval((int) (p.x - nodesRad), (int) (p.y - nodesRad), 2 * nodesRad, 2 * nodesRad);
                }
        );
        gr.setColor(statusColor);
        int currentY = (int) BO.getProcessedEvents().getLast().getAssociatedPoint().y;
        gr.drawLine(50, currentY, panelDraw.getWidth() - 50, currentY);
    }

    public void nextEvent() {
        BO.nextEvent();
        this.drawBO();
    }

    public boolean checkNextEvent() {
        return !BO.getEvents().isEmpty();
    }

    public int[] getEvents() {
        return new int[]{BO.getNEvents(), BO.getNIntersections()};
    }
}
