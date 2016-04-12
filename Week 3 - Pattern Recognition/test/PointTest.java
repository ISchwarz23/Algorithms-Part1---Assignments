import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;


/**
 * Test for the {@link Point} class.
 * @author ISchwarz
 */
public class PointTest {

    private static final double VALID_DELTA = 0;

    @Test
    public void shouldReturnNegativeInfinityWhenComparingIdenticalPoints() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);
        Assert.assertEquals(Double.NEGATIVE_INFINITY, p1.slopeTo(p2), VALID_DELTA);
    }

    @Test
    public void shouldReturnPositiveInfinityWhenComparingVerticalPoints() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 2);
        Assert.assertEquals(Double.POSITIVE_INFINITY, p1.slopeTo(p2), VALID_DELTA);
    }

    @Test
    public void shouldReturnPositiveZeroWhenComparingHorizontalPoints() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 1);
        Assert.assertEquals(+0, p1.slopeTo(p2), VALID_DELTA);
    }

    @Test
    public void shouldCalculateCorrectSlope() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        Assert.assertEquals(1.0, p1.slopeTo(p2), VALID_DELTA);
    }

    @Test
    public void shouldReturnZeroWhenComparingIdenticalPoints() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);
        Assert.assertEquals(0, p1.compareTo(p2));
    }

    @Test
    public void shouldReturnPositiveOneWhenComparingWithSmallerPoint() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(0, 0);
        Assert.assertEquals(+1, p1.compareTo(p2));
    }

    @Test
    public void shouldReturnNegativeOneWhenComparingWithSmallerPoint() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        Assert.assertEquals(-1, p1.compareTo(p2));
    }

    @Test
    public void comparatorShouldReturnZeroWhenComparingTwoPointsWithEqualSlopeRelativeToReferencePoint() {
        Point referencePoint = new Point(0, 0);
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);

        Comparator<Point> comparator = referencePoint.slopeOrder();
        Assert.assertEquals(0, comparator.compare(p1, p2));
    }

    @Test
    public void comparatorShouldReturnPositiveOneWhenComparingPointWithBiggerSlopeAndPointWithSmallerSlopeRelativeToReferencePoint() {
        Point referencePoint = new Point(0, 0);
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 0);

        Comparator<Point> comparator = referencePoint.slopeOrder();
        Assert.assertEquals(1, comparator.compare(p1, p2));
    }

    @Test
    public void comparatorShouldReturnNegativeOneWhenComparingPointWithSmallerSlopeAndPointWithBiggerSlopeRelativeToReferencePoint() {
        Point referencePoint = new Point(0, 0);
        Point p1 = new Point(1, 0);
        Point p2 = new Point(2, 2);

        Comparator<Point> comparator = referencePoint.slopeOrder();
        Assert.assertEquals(-1, comparator.compare(p1, p2));
    }

}
