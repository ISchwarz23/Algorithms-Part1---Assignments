import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


/**
 * Tests for the {@link KdTree}.
 *
 * @author ISchwarz
 */
public class KdTreeTest {

    private KdTree cut;


    @Before
    public void setUp() {
        cut = new KdTree();
    }

    @Test
    public void shouldReturnTrueIfSetIsEmpty() {
        assertTrue(cut.isEmpty());
    }


    @Test
    public void shouldReturnFalseIfSetIsNotEmpty() {
        cut.insert(new Point2D(.0, .0));
        assertFalse(cut.isEmpty());
    }

    @Test
    public void shouldReturnTheCorrectSize() {
        assertEquals(0, cut.size());
        cut.insert(new Point2D(.0, .0));
        assertEquals(1, cut.size());
        cut.insert(new Point2D(.1, .1));
        assertEquals(2, cut.size());
    }

    @Test
    public void shouldCorrectlyDiscoverIfElementIsInSET() {
        cut.insert(new Point2D(.1, .1));

        assertFalse(cut.contains(new Point2D(.0, .0)));
        assertTrue(cut.contains(new Point2D(.1, .1)));
    }

    @Test
    public void shouldGiveTheCorrectRange() {
        cut.insert(new Point2D(.0, .0));
        cut.insert(new Point2D(.2, .2));
        cut.insert(new Point2D(.3, .3));
        cut.insert(new Point2D(.5, .5));

        List<Point2D> range = (List<Point2D>) cut.range(new RectHV(.1, .1, .4, .4));
        assertEquals(2, range.size());
        assertTrue(range.contains(new Point2D(.2, .2)));
        assertTrue(range.contains(new Point2D(.3, .3)));
    }

    @Test
    public void shouldGiveTheCorrectNearestPoint() {
        cut.insert(new Point2D(.0, .0));
        cut.insert(new Point2D(.1, .1));
        cut.insert(new Point2D(.2, .2));
        cut.insert(new Point2D(.3, .3));
        cut.insert(new Point2D(.4, .4));

        Point2D nearest = cut.nearest(new Point2D(.2, .22));

        assertEquals(new Point2D(.2, .2), nearest);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenInsertingNull() {
        cut.insert(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenCheckingIfSETContainsNull() {
        cut.contains(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenCheckingRangeWithinNull() {
        cut.range(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenCheckingNearestToNull() {
        cut.nearest(null);
    }

}
