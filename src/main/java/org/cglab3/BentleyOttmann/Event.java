package org.cglab3.BentleyOttmann;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.awt.geom.Point2D;

@Getter
class Event {
    enum Type {INTERSECTION, START, END}

    private final Type eventType;
    private final Segment segment;
    //Two fields for intersection event only
    private final Segment segment2;
    private final Point2D.Double associatedPoint;

    Event(Type eventType, Segment segment, Segment segment2, Point2D.Double intersectionPoint) {
        this.eventType = eventType;
        this.segment = segment;
        this.segment2 = segment2;
//        this.associatedPoint = intersectionPoint;
        switch (this.eventType) {
            case INTERSECTION -> {
                this.associatedPoint = intersectionPoint;
            }
            case START -> {
                if (segment.getStart().y < segment.getEnd().y
                        || (segment.getStart().y == segment.getEnd().y && segment.getEnd().x < segment.getStart().x)) {
                    this.associatedPoint = segment.getEnd();
                } else {
                    this.associatedPoint = segment.getStart();
                }
            }
            case END -> {
                if (segment.getStart().y > segment.getEnd().y
                        || (segment.getStart().y == segment.getEnd().y && segment.getEnd().x > segment.getStart().x)) {
                    this.associatedPoint = segment.getEnd();
                } else {
                    this.associatedPoint = segment.getStart();
                }
            }
            default -> {
                throw new IllegalArgumentException("No event type supplied");
            }
        }
    }

    Event(Type eventType, Segment segment) {
        this(eventType, segment, null, null);
    }
}
