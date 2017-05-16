import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;


/**
 * 2D-Tree solution of 2D range and nearest calculations.
 *
 * @author ISchwarz
 */
public class KdTree {

    public boolean isEmpty() {   // is the set empty?
        return true;
    }

    public int size() {   // number of points in the set
        return 0;
    }

    public void insert(Point2D p) {   // add the point to the set (if it is not already in the set)
        checkNotNull(p, "Not supported to insert null as point");
    }

    public boolean contains(Point2D p) {   // does the set contain point p?
        checkNotNull(p, "Null is never contained in a PointSET");
        return false;
    }

    public void draw() {   // draw all points to standard draw

    }

    public Iterable<Point2D> range(RectHV rect) {   // all points that are inside the rectangle
        checkNotNull(rect, "Can't calculate range for a rect will value null");
        return null;
    }

    public Point2D nearest(Point2D p) {   // a nearest neighbor in the set to point p; null if the set is empty
        checkNotNull(p, "Can't calculate nearest point to a point with value null");
        return null;
    }

    private static void checkNotNull(Object o, String messageIfObjectIsNull) {
        if (o == null) throw new NullPointerException(messageIfObjectIsNull);
    }

}
