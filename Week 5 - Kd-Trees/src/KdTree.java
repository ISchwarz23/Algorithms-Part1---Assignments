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

    private Node put(Node node, Point2D value, int level) {
        if (node == null) return new Node(level, value, 1);

        double key;
        if (level % 2 == 0) {
            key = value.x();
        } else {
            key = value.y();
        }

        double cmp = key - node.key;
        if (cmp < 0) node.left = put(node.left, value, ++level);
        else if (cmp > 0) node.right = put(node.right, value, ++level);
        else node.value = value;

        node.n = 1 + size(node.left) + size(node.right);
        return node;
    }

    public boolean contains(Point2D p) {   // does the set contain value p?
        checkNotNull(p, "Null is never contained in a PointSET");
        return get(root, p, 0) != null;
    }

    private Point2D get(Node node, Point2D value, int level) {
        if (node == null) return null;

        double key;
        if (level % 2 == 0) {
            key = value.x();
        } else {
            key = value.y();
        }

        double cmp = key - node.key;
        if (cmp < 0) return get(node.left, value, ++level);
        else if (cmp > 0) return get(node.right, value, ++level);
        else return node.value;
    }

    public void draw() {   // draw all points to standard draw
        draw(root);
    }

    private void draw(Node node) {
        if(node == null) return;

        Point2D currentPoint = node.value;
        StdDraw.point(currentPoint.x(), currentPoint.y());
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
            double key;
            if (node.level % 2 == 0) {
                key = currentPoint.x();
            } else {
                key = currentPoint.y();
            }

            double cmp = key - node.key;
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
        if (currentPoint.equals(queryPoint)) return currentPoint;

        Point2D nearestPoint;
        double key;
        if (node.level % 2 == 0) {
            key = currentPoint.x();
        } else {
            key = currentPoint.y();
        }

        double currentDistance = currentPoint.distanceTo(queryPoint);
        double cmp = key - node.key;
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
            if (cmp < 0) {
                nearestPoint = nearest(queryPoint, node.right);
            } else {
                nearestPoint = nearest(queryPoint, node.left);
            }

            nearestDistance = 2;
            if (nearestPoint != null) {
                nearestDistance = nearestPoint.distanceTo(queryPoint);
            }

            if (nearestDistance > currentDistance) {
                nearestPoint = currentPoint;
            }
        }

        return nearestPoint;
    }

    private static void checkNotNull(Object o, String messageIfObjectIsNull) {
        if (o == null) throw new NullPointerException(messageIfObjectIsNull);
    }

    private static class Node {

        private double key;
        private Point2D value;
        private Node left, right;  // left and right subtrees
        private int n;             // number of nodes in subtree
        private int level;

        public Node(int level, Point2D value, int n) {
            if (level % 2 == 0) {
                key = value.x();
            } else {
                key = value.y();
            }
            this.level = level;
            this.value = value;
            this.n = n;
        }
    }

}
