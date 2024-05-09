package org.cglab3.BentleyOttmann;

import lombok.Data;

import java.awt.geom.Point2D;

@Data
public class Segment {
    private final Point2D.Double start;
    private final Point2D.Double end;
}
