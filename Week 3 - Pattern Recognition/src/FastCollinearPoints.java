import java.util.*;


/**
 * A fst solution of finding colinear points in a set of points.
 *
 * @author ISchwarz
 */
public class FastCollinearPoints {

    private HashMap<Double, List<Point>> foundSegments = new HashMap<>();
    private List<LineSegment> segments = new ArrayList<>();


    public FastCollinearPoints(Point[] points) {
//        checkDuplicatedEntries(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
//        Arrays.sort(pointsCopy);

        for (Point startPoint : points) {
            Arrays.sort(pointsCopy, startPoint.slopeOrder());

            List<Point> slopePoints = new ArrayList<>();
            double slope = 0;
            double previousSlope = Double.NEGATIVE_INFINITY;

            for (int i = 1; i < pointsCopy.length; i++) {
                slope = startPoint.slopeTo(pointsCopy[i]);
                if (slope == previousSlope) {
                    slopePoints.add(pointsCopy[i]);
                } else {
                    if (slopePoints.size() >= 3) {
                        slopePoints.add(startPoint);
                        addSegmentIfNew(slopePoints, previousSlope);
                    }
                    slopePoints.clear();
                    slopePoints.add(pointsCopy[i]);
                }
                previousSlope = slope;
            }

            if (slopePoints.size() >= 3) {
                slopePoints.add(startPoint);
                addSegmentIfNew(slopePoints, slope);
            }
        }
    }

    private void addSegmentIfNew(List<Point> slopePoints, double slope) {
        List<Point> endPoints = foundSegments.get(slope);
        Collections.sort(slopePoints);

        Point startPoint = slopePoints.get(0);
        Point endPoint = slopePoints.get(slopePoints.size() - 1);

        if (endPoints == null) {
            endPoints = new ArrayList<>();
            endPoints.add(endPoint);
            foundSegments.put(slope, endPoints);
            segments.add(new LineSegment(startPoint, endPoint));
        } else {
            for (Point currentEndPoint : endPoints) {
                if (currentEndPoint.compareTo(endPoint) == 0) {
                    return;
                }
            }
            endPoints.add(endPoint);
            segments.add(new LineSegment(startPoint, endPoint));
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

}
