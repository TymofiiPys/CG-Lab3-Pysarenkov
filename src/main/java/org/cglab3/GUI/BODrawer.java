package org.cglab3.GUI;

import lombok.Setter;
import org.cglab3.BentleyOttmann.BOInfo;
import org.cglab3.BentleyOttmann.Event;
import org.cglab3.BentleyOttmann.IntersectionSearch;
import org.cglab3.BentleyOttmann.Segment;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Objects;

public class BODrawer {
    private IntersectionSearch BO;
    private final JPanel panelDraw;
    private final int nodesRad = 4;
    private final Stroke segmentStroke = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
    private final Color regularSegmentColor = Color.BLACK;
    private final Color segmentInStatusColor = new Color(182, 130, 195);
    private final Color statusColor = Color.BLUE;
    private final Color intersectionColor = Color.YELLOW;
    private final Color processedEventColor = Color.GREEN;

    @Setter
    private int[] offsets = new int[]{0, 0};

    public BODrawer(JPanel panelDraw) {
        this.panelDraw = panelDraw;
    }

    public void setSegments(ArrayList<Segment> segments) {
        this.BO = new IntersectionSearch(segments);
    }

    public boolean BOSet() {
        return BO != null;
    }

    public Point2D.Double adaptToPanel(Point2D.Double p, int[] offsets) {
        return new Point2D.Double(p.x + offsets[0], panelDraw.getHeight() - (p.y + offsets[1]));
    }

    public Point2D.Double adaptFromPanel(Point2D.Double p, int[] offsets) {
        return new Point2D.Double(p.x - offsets[0], panelDraw.getHeight() - (p.y + offsets[1]));
    }

    private int[] calcOffsets() {
        return new int[]{offsets[0] + panelDraw.getWidth() / 2, offsets[1] + panelDraw.getHeight() / 2};
    }

    private void drawAxes(int[] offsets) {
        Graphics2D gr = (Graphics2D) panelDraw.getGraphics();
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
    }

    public void drawBO() {
        Graphics2D gr = (Graphics2D) panelDraw.getGraphics();
        gr.clearRect(0, 0, panelDraw.getWidth(), panelDraw.getHeight());
        int[] panelOffsets = calcOffsets();
        drawAxes(panelOffsets);
        gr.setColor(regularSegmentColor);
        gr.setStroke(segmentStroke);
        for (Segment s : BO.getSegments()) {
            Point2D.Double startAdapted = adaptToPanel(s.getStart(), panelOffsets);
            Point2D.Double endAdapted = adaptToPanel(s.getEnd(), panelOffsets);
            gr.drawLine((int) startAdapted.x, (int) startAdapted.y, (int) endAdapted.x, (int) endAdapted.y);
        }
        gr.setColor(segmentInStatusColor);
        for (Segment s : BO.getStatus()) {
            Point2D.Double startAdapted = adaptToPanel(s.getStart(), panelOffsets);
            Point2D.Double endAdapted = adaptToPanel(s.getEnd(), panelOffsets);
            gr.drawLine((int) startAdapted.x, (int) startAdapted.y, (int) endAdapted.x, (int) endAdapted.y);
        }
        gr.setColor(processedEventColor);
        for (Event e : BO.getProcessedEvents()) {
            if (Objects.requireNonNull(e.getEventType()) == Event.Type.INTERSECTION) {
                gr.setColor(intersectionColor);
            } else {
                gr.setColor(processedEventColor);
            }
            Point2D.Double p = adaptToPanel(e.getAssociatedPoint(), panelOffsets);
            gr.fillOval((int) (p.x - nodesRad), (int) (p.y - nodesRad), 2 * nodesRad, 2 * nodesRad);
        }
        gr.setColor(intersectionColor);
        BO.getEvents().stream().filter(event -> event.getEventType() == Event.Type.INTERSECTION).forEach(
                event -> {
                    Point2D.Double p = adaptToPanel(event.getAssociatedPoint(), panelOffsets);
                    gr.fillOval((int) (p.x - nodesRad), (int) (p.y - nodesRad), 2 * nodesRad, 2 * nodesRad);
                }
        );
        gr.setColor(statusColor);
        if (!BO.getProcessedEvents().isEmpty()) {
            int currentY = (int) BO.getProcessedEvents().getLast().getAssociatedPoint().y;
            Point2D.Double adaptedStart = adaptToPanel(new Point2D.Double(0, currentY), panelOffsets);
            Point2D.Double adaptedEnd = adaptToPanel(new Point2D.Double(0, currentY), panelOffsets);
            gr.drawLine(0, (int) adaptedStart.y, panelDraw.getWidth(), (int) adaptedEnd.y);
        }
    }

    public BOInfo nextEvent() {
        BO.nextEvent();
        this.drawBO();
        return new BOInfo(
                BO.getNEvents(),
                BO.getNIntersections(),
                BO.getLastEventInfo()
        );
    }

    public boolean checkNextEvent() {
        return !BO.getEvents().isEmpty();
    }
}
