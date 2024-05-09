package org.cglab3.BentleyOttmann;

import lombok.Data;

import java.awt.geom.Point2D;
import java.util.Optional;

@Data
public class Segment implements Comparable<Segment>{
    private final Point2D.Double start;
    private final Point2D.Double end;
    private double currentY;

    Optional<Point2D.Double> intersection(Segment other) {
        // this segment represented as a1x + b1y = c1
        double a1 = this.end.y - this.start.y;
        double b1 = this.start.x - this.end.x;
        double c1 = a1 * (this.start.x) + b1 * (this.start.y);

        // other segment represented as a2x + b2y = c2
        double a2 = other.end.y - other.start.y;
        double b2 = other.start.x - other.end.x;
        double c2 = a2 * (other.start.x) + b2 * (other.start.y);

        double determinant = a1 * b2 - a2 * b1;

        if (determinant == 0) {
            //Lines are collinear
            return Optional.empty();
        } else {
            double x = (b2 * c1 - b1 * c2) / determinant;
            double y = (a1 * c2 - a2 * c1) / determinant;
            //Now check that the point actually lies on segments:
            if (Math.min(this.start.x, this.end.x) <= x && x <= Math.max(this.start.x, this.end.x)
                    && Math.min(this.start.y, this.end.y) <= x && x <= Math.max(this.start.y, this.end.y))
                return Optional.of(new Point2D.Double(x, y));
            else
                return Optional.empty();
        }
    }

    @Override
    public int compareTo(Segment o) {
//        if(this.start.y > o.start.y)
//
            return 0;
    }
}