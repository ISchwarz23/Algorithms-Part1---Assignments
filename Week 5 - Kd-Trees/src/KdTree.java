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

    public void insert(Point2D p) {   // add the point to the set (if it is not already in the set)
        checkNotNull(p, "Not supported to insert null as point");
        root = put(root, p, 0, new RectHV(0, 0, 1, 1));
    }

    private Node put(final Node node, final Point2D pointToInsert, final int level, RectHV rect) {
        if (node == null) {
            return new Node(level, pointToInsert, rect);
        }

        RectHV rectLeft;
        if (node.left == null) {
            if (level % 2 == 0) {
                rectLeft = new RectHV(node.rect.xmin(), node.rect.ymin(), node.point.x(), node.rect.ymax());
            } else {
                rectLeft = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.point.y());
            }
        } else {
            rectLeft = node.left.rect;
        }
        RectHV rectRight;
        if (node.right == null) {
            if (level % 2 == 0) {
                rectRight = new RectHV(node.point.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
            } else {
                rectRight = new RectHV(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.rect.ymax());
            }
        } else {
            rectRight = node.right.rect;
        }

        double cmp = node.compare(pointToInsert);
        if (cmp < 0) node.left = put(node.left, pointToInsert, level + 1, rectLeft);
        else if (cmp > 0) node.right = put(node.right, pointToInsert, level + 1, rectRight);
        else if (!pointToInsert.equals(node.point)) node.right = put(node.right, pointToInsert, level + 1, rectRight);

        node.n = 1 + size(node.left) + size(node.right);
        return node;
    }

    public boolean contains(Point2D p) {   // does the set contain point p?
        checkNotNull(p, "Null is never contained in a PointSET");
        return get(root, p, 0) != null;
    }

    private Point2D get(Node node, Point2D searchedPoint, int level) {
        if (node == null) return null;

        double cmp = node.compare(searchedPoint);
        if (cmp < 0) return get(node.left, searchedPoint, ++level);
        else if (cmp > 0) return get(node.right, searchedPoint, ++level);
        else if (!searchedPoint.equals(node.point)) return get(node.right, searchedPoint, ++level);
        else return node.point;
    }

    public void draw() {   // draw all points to standard draw
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) return;
        StdDraw.point(node.point.x(), node.point.y());
        draw(node.left);
        draw(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {   // all points that are inside the rectangle
        checkNotNull(rect, "Can't calculate range for a rect will point null");
        return range(rect, root);
    }

    private List<Point2D> range(RectHV queryRect, Node node) {
        if (node == null) return Collections.emptyList();

        if (node.doesSpittingLineIntersect(queryRect)) {
            List<Point2D> points = new ArrayList<>();
            if (queryRect.contains(node.point)) {
                points.add(node.point);
            }
            points.addAll(range(queryRect, node.left));
            points.addAll(range(queryRect, node.right));
            return points;
        } else {
            if (node.isRightOf(queryRect)) return range(queryRect, node.left);
            else return range(queryRect, node.right);
        }
    }

    public Point2D nearest(Point2D p) {   // a nearest neighbor in the set to point p; null if the set is empty
        checkNotNull(p, "Can't calculate nearest point to a point with point null");
        if (root == null) {
            return null;
        }
        return nearest(p, root, root.point, p.distanceTo(root.point));
    }

    private Point2D nearest(Point2D queryPoint, Node node, Point2D currentlyClosestPoint, double currentlyClosestDistance) {
        if (node == null) return null;
        Point2D closestPoint = currentlyClosestPoint;
        double closestDistance = currentlyClosestDistance;

        Point2D currentPoint = node.point;
        double currentDistance = queryPoint.distanceTo(currentPoint);
        if (currentDistance < closestDistance) {
            closestDistance = currentDistance;
            closestPoint = currentPoint;
        }

        double cmp = node.compare(queryPoint);
        if (cmp < 0) {
            currentPoint = nearest(queryPoint, node.left, closestPoint, closestDistance);
        } else {
            currentPoint = nearest(queryPoint, node.right, closestPoint, closestDistance);
        }

        if (currentPoint != null) {
            if (currentPoint != closestPoint) {
                closestDistance = currentPoint.distanceTo(queryPoint);
                closestPoint = currentPoint;
            }
        }

        double nodeRectDistance = -1;
        if (cmp < 0 && node.right != null) {
            nodeRectDistance = node.right.rect.distanceTo(queryPoint);
        } else if (cmp >= 0 && node.left != null) {
            nodeRectDistance = node.left.rect.distanceTo(queryPoint);
        }

        if (nodeRectDistance != -1 && nodeRectDistance < closestDistance) {
            if (cmp < 0) {
                currentPoint = nearest(queryPoint, node.right, closestPoint, closestDistance);
            } else {
                currentPoint = nearest(queryPoint, node.left, closestPoint, closestDistance);
            }
        }

        if (currentPoint != null) {
            closestPoint = currentPoint;
        }

        return closestPoint;
    }

    private static void checkNotNull(Object o, String messageIfObjectIsNull) {
        if (o == null) throw new NullPointerException(messageIfObjectIsNull);
    }

    private static class Node {

        private Point2D point;
        private Node left, right;  // left and right subtrees
        private int level;
        private RectHV rect;

        private int n = 1;             // number of nodes in subtree

        public Node(int level, Point2D point, RectHV rect) {
            this.level = level;
            this.point = point;
            this.rect = rect;
        }

        public double compare(Point2D pointToCompare) {
            if (level % 2 == 0) {
                return pointToCompare.x() - point.x();
            } else {
                return pointToCompare.y() - point.y();
            }
        }

        public boolean doesSpittingLineIntersect(RectHV rectToCheck) {
            if (level % 2 == 0) {
                return rectToCheck.xmin() <= point.x() && point.x() <= rectToCheck.xmax();
            } else {
                return rectToCheck.ymin() <= point.y() && point.y() <= rectToCheck.ymax();
            }
        }

        public boolean isRightOf(RectHV rectToCheck) {
            if (level % 2 == 0) {
                return rectToCheck.xmin() < point.x() && rectToCheck.xmax() < point.x();
            } else {
                return rectToCheck.ymin() < point.y() && rectToCheck.ymax() < point.y();
            }
        }

    }

}
