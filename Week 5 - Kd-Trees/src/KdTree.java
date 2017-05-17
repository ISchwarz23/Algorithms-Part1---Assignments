import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 2D-Tree solution of 2D range and nearest calculations.
 *
 * @author ISchwarz
 */
public class KdTree {

    private Node root;


    public boolean isEmpty() {   // is the set empty?
        return size() == 0;
    }

    public int size() {   // number of points in the set
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.n;
    }

    public void insert(Point2D p) {   // add the value to the set (if it is not already in the set)
        checkNotNull(p, "Not supported to insert null as value");
        root = put(root, p, 0);
    }

    private Node put(final Node node, final Point2D pointToInsert, final int level) {
        if (node == null) {
            return new Node(level, pointToInsert, 1);
        }

        double cmp = node.compare(pointToInsert);
        if (cmp < 0) node.left = put(node.left, pointToInsert, level + 1);
        else if (cmp > 0) node.right = put(node.right, pointToInsert, level + 1);
        else if (!pointToInsert.equals(node.value)) node.right = put(node.right, pointToInsert, level + 1);

        node.n = 1 + size(node.left) + size(node.right);
        return node;
    }

    public boolean contains(Point2D p) {   // does the set contain value p?
        checkNotNull(p, "Null is never contained in a PointSET");
        return get(root, p, 0) != null;
    }

    private Point2D get(Node node, Point2D searchedPoint, int level) {
        if (node == null) return null;

        double cmp = node.compare(searchedPoint);
        if (cmp < 0) return get(node.left, searchedPoint, ++level);
        else if (cmp > 0) return get(node.right, searchedPoint, ++level);
        else if (!searchedPoint.equals(node.value)) return get(node.right, searchedPoint, ++level);
        else return node.value;
    }

    public void draw() {   // draw all points to standard draw
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) return;
        StdDraw.point(node.value.x(), node.value.y());
        draw(node.left);
        draw(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {   // all points that are inside the rectangle
        checkNotNull(rect, "Can't calculate range for a rect will value null");
        return range(rect, root);
    }

    private List<Point2D> range(RectHV rect, Node node) {
        if (node == null) return Collections.emptyList();

        Point2D currentPoint = node.value;
        if (rect.contains(currentPoint)) {
            List<Point2D> points = new ArrayList<>();
            points.add(currentPoint);
            points.addAll(range(rect, node.left));
            points.addAll(range(rect, node.right));
            return points;
        } else {
            double cmp = node.compare(currentPoint);
            if (cmp < 0) return range(rect, node.left);
            else if (cmp > 0) return range(rect, node.right);
            else {
                List<Point2D> points = new ArrayList<>();
                points.addAll(range(rect, node.left));
                points.addAll(range(rect, node.right));
                return points;
            }
        }
    }


    public Point2D nearest(Point2D p) {   // a nearest neighbor in the set to value p; null if the set is empty
        checkNotNull(p, "Can't calculate nearest value to a value with value null");
        return nearest(p, root);
    }

    private Point2D nearest(Point2D queryPoint, Node node) {
        if (node == null) return null;

        Point2D currentPoint = node.value;
        double currentDistance = currentPoint.distanceTo(queryPoint);

        Point2D nearestPoint;
        double cmp = node.compare(currentPoint);
        if (cmp < 0) {
            nearestPoint = nearest(queryPoint, node.left);
        } else {
            nearestPoint = nearest(queryPoint, node.right);
        }

        double nearestDistance = 2;
        if (nearestPoint != null) {
            nearestDistance = nearestPoint.distanceTo(queryPoint);
        }

        if (nearestDistance > currentDistance) {
            nearestDistance = currentDistance;
            nearestPoint = currentPoint;
        }

        if (nearestDistance > cmp) {
            Point2D foundPoint;
            if (cmp < 0) {
                foundPoint = nearest(queryPoint, node.right);
            } else {
                foundPoint = nearest(queryPoint, node.left);
            }

            double foundDistance = 2;
            if (foundPoint != null) {
                foundDistance = foundPoint.distanceTo(queryPoint);
            }

            if (nearestDistance > foundDistance) {
                nearestPoint = foundPoint;
            }
        }

        return nearestPoint;
    }

    private static void checkNotNull(Object o, String messageIfObjectIsNull) {
        if (o == null) throw new NullPointerException(messageIfObjectIsNull);
    }

    private static class Node {

        private Point2D value;
        private Node left, right;  // left and right subtrees
        private int n;             // number of nodes in subtree
        private int level;

        public Node(int level, Point2D value, int n) {
            this.level = level;
            this.value = value;
            this.n = n;
        }

        public double compare(Point2D point) {
            if (level % 2 == 0) {
                return point.x() - value.x();
            } else {
                return point.y() - value.y();
            }
        }

    }

}
