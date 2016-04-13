import java.util.ArrayList;
import java.util.Arrays;


/**
 * A fst solution of finding colinear points in a set of points.
 * @author ISchwarz
 */
public class FastCollinearPoints {

    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        checkDuplicatedEntries(points);
        ArrayList<LineSegment> foundSegments = new ArrayList<>();

        Arrays.sort(points);
        Point[] sortedPoints = Arrays.copyOf(points, points.length);

        for(Point p : sortedPoints) {
            Arrays.sort(points);
            Arrays.sort(points, p.slopeOrder());

            int slopeCounter = 0;
            Point startPoint = points[0];
            Point endPoint = points[0];
            for(int i=1; i< points.length; i++) {
                if(p.slopeTo(points[i]) == p.slopeTo(points[i-1])) {
                    slopeCounter++;
                    if(points[i].compareTo(startPoint) < 0) {
                        startPoint = points[i];
                    } else if(points[i].compareTo(endPoint) > 0){
                        endPoint = points[i];
                    }
                } else {
                    if(points[0].compareTo(startPoint) < 0) {
                        startPoint = points[0];
                    } else if(points[0].compareTo(endPoint) > 0){
                        endPoint = points[0];
                    }

                    if(slopeCounter >= 2) {
                        LineSegment newSegment = new LineSegment(startPoint, endPoint);
                        if(!foundSegments.contains(newSegment)) {
                            foundSegments.add(newSegment);
                        }
                    }
                    slopeCounter = 0;
                    startPoint = points[i];
                    endPoint = points[i];
                }
            }


            if(points[0].compareTo(startPoint) < 0) {
                startPoint = points[0];
            } else if(points[0].compareTo(endPoint) > 0){
                endPoint = points[0];
            }

            if(slopeCounter >= 2) {
                LineSegment newSegment = new LineSegment(startPoint, endPoint);
                if(!foundSegments.contains(newSegment)) {
                    foundSegments.add(newSegment);
                }
            }
        }

        segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);
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

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments;
    }


}
