package org.cglab3.BentleyOttmann;

import lombok.*;

import java.awt.geom.Point2D;
import java.util.Optional;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Segment {
    private final Point2D.Double start;
    private final Point2D.Double end;
    @ToString.Exclude
    public static double currentY;
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    private final double slope;
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    private final double intercept;

    public Segment(Point2D.Double start, Point2D.Double end) {
        this.start = start;
        this.end = end;
        if (start.equals(end)) {
            throw new IllegalArgumentException("Man, this is not a line, its end are the same point!");
        }
        if (start.y == end.y) {
            throw new IllegalArgumentException("Horizontal segments not yet supported");
        }
        this.slope = (end.x - start.x) != 0 ? (end.y - start.y) / (end.x - start.x) : Double.POSITIVE_INFINITY;
        this.intercept = this.slope != Double.POSITIVE_INFINITY ? end.y - this.slope * end.x : this.slope;
    }

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
            if (this.liesOnSegment(x, y) && other.liesOnSegment(x, y))
                return Optional.of(new Point2D.Double(x, y));
            else
                return Optional.empty();
        }
    }

    private boolean liesOnSegment(double x, double y) {
        return Math.min(this.start.x, this.end.x) <= x && x <= Math.max(this.start.x, this.end.x)
                && Math.min(this.start.y, this.end.y) <= y && y <= Math.max(this.start.y, this.end.y);
    }

    public double getCurrentX() {
        return this.slope != Double.POSITIVE_INFINITY ? (currentY - this.intercept) / this.slope : end.x;
    }
}
