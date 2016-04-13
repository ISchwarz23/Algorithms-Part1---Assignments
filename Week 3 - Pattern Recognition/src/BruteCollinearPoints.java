import java.util.ArrayList;
import java.util.Arrays;


/**
 * A BruteForce solution for finding collinear points in a set of points.
 * @author ISchwarz
 */
public class BruteCollinearPoints {

    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        checkDuplicatedEntries(points);
        ArrayList<LineSegment> foundSegments = new ArrayList<>();

        for (int p = 0; p < points.length - 3; p++) {
            for (int q = p + 1; q < points.length - 2; q++) {
                for (int r = q + 1; r < points.length - 1; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        Point[] subPoints = new Point[4];
                        subPoints[0] = points[p];
                        subPoints[1] = points[q];
                        subPoints[2] = points[r];
                        subPoints[3] = points[s];
                        Arrays.sort(subPoints);

                        if (subPoints[0].slopeTo(subPoints[1]) == subPoints[0].slopeTo(subPoints[2]) &&
                                subPoints[0].slopeTo(subPoints[1]) == subPoints[0].slopeTo(subPoints[3])) {
                            foundSegments.add(new LineSegment(subPoints[0], subPoints[3]));
                        }
                    }
                }
            }
        }

        segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments;
    }

    private void checkDuplicatedEntries(Point[] points) {
        for(int i=0; i<points.length-1; i++) {
            for(int j=i+1; j<points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicated entries in given points.");
                }
            }
        }
    }

}
