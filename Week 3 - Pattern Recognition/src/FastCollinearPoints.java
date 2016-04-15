import java.util.ArrayList;
import java.util.Arrays;


/**
 * A fst solution of finding colinear points in a set of points.
 *
 * @author ISchwarz
 */
public class FastCollinearPoints {

    private ArrayList<PointTuple> tuples = new ArrayList<>();
    private ArrayList<LineSegment> segments = new ArrayList<>();


    public FastCollinearPoints(Point[] points) {
        checkDuplicatedEntries(points);

        Point[] pointsCopy = Arrays.copyOf(points, points.length);

        for (Point currentPoint : points) {
            Arrays.sort(pointsCopy, currentPoint.slopeOrder());

            Point startPoint = pointsCopy[0];
            Point endPoint = pointsCopy[0];

            double previousSlope = currentPoint.slopeTo(startPoint);
            double currentSlope;
            int slopeLength = 0;

            for (int i = 1; i < pointsCopy.length; i++) {
                currentSlope = currentPoint.slopeTo(pointsCopy[i]);
                if (currentSlope == previousSlope) {
                    if (pointsCopy[i].compareTo(startPoint) < 0) {
                        startPoint = pointsCopy[i];
                    } else if (pointsCopy[i].compareTo(endPoint) > 0) {
                        endPoint = pointsCopy[i];
                    }
                    slopeLength++;
                } else {
                    if (slopeLength >= 2) {
                        if (currentPoint.compareTo(startPoint) < 0) {
                            startPoint = currentPoint;
                        } else if (currentPoint.compareTo(endPoint) > 0) {
                            endPoint = currentPoint;
                        }
                        addSlopeIfNotAddedYet(startPoint, endPoint);
                    }
                    startPoint = pointsCopy[i];
                    endPoint = pointsCopy[i];
                    slopeLength = 0;
                }
                previousSlope = currentSlope;
            }

            if (slopeLength >= 2) {
                if (currentPoint.compareTo(startPoint) < 0) {
                    startPoint = currentPoint;
                } else if (currentPoint.compareTo(endPoint) > 0) {
                    endPoint = currentPoint;
                }
                addSlopeIfNotAddedYet(startPoint, endPoint);
            }
        }
    }

    private void addSlopeIfNotAddedYet(Point startPoint, Point endPoint) {
        PointTuple tuple = new PointTuple(startPoint, endPoint);
        if (!tuples.contains(tuple)) {
            tuples.add(tuple);
            segments.add(tuple.toLineSegment());
        }
    }


    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicated entries in given points.");
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }


    private class PointTuple {

        private final Point p1;
        private final Point p2;

        private PointTuple(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        public LineSegment toLineSegment() {
            return new LineSegment(p1, p2);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PointTuple that = (PointTuple) o;

            if (p1.compareTo(that.p1) != 0) return false;
            if (p2.compareTo(that.p2) != 0) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = p1.hashCode();
            result = 31 * result + p2.hashCode();
            return result;
        }
    }


}
