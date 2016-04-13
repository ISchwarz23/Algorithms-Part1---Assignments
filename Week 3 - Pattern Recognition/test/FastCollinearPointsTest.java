import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ingo on 23.09.2015.
 */
public class FastCollinearPointsTest {

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfNullIsPassedToConstructor() {
        new FastCollinearPoints(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfOnePointIsNull() {
        Point[] points = new Point[] { new Point(0, 0), new Point(1, 1), null, new Point(3, 3)};
        new FastCollinearPoints(points);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfPointIsGivenTwice() {
        Point[] points = new Point[] { new Point(0, 0), new Point(1, 1), new Point(0, 0), new Point(3, 3)};
        new FastCollinearPoints(points);
    }

    @Test
    public void shouldFindHorizontalLineSegment() {
        // given
        Point[] points = new Point[] { new Point(0, 0), new Point(1, 0), new Point(3, 0), new Point(1, 1), new Point(2, 0)};

        // when
        FastCollinearPoints cut = new FastCollinearPoints(points);

        // then
        assertEquals(1, cut.numberOfSegments());
        assertEquals("(0, 0) -> (3, 0)", cut.segments()[0].toString());
    }

    @Test
     public void shouldFindVerticalLineSegment() {
        // given
        Point[] points = new Point[] { new Point(0, 0), new Point(1, 1), new Point(0, 1), new Point(0, 2), new Point(0, 3)};

        // when
        BruteCollinearPoints cut = new BruteCollinearPoints(points);

        // then
        assertEquals(1, cut.numberOfSegments());
        assertEquals("(0, 0) -> (0, 3)", cut.segments()[0].toString());
    }

    @Test
    public void shouldFindLineSegment1() {
        // given
        Point[] points = new Point[] { new Point(0, 0), new Point(1, 1), new Point(0, 1), new Point(2, 2), new Point(3, 3)};

        // when
        BruteCollinearPoints cut = new BruteCollinearPoints(points);

        // then
        assertEquals(1, cut.numberOfSegments());
        assertEquals("(0, 0) -> (3, 3)", cut.segments()[0].toString());
    }

    @Test
    public void shouldFindLineSegment2() {
        // given
        Point[] points = new Point[] { new Point(0, 0), new Point(1, 2), new Point(0, 1), new Point(2, 4), new Point(3, 6)};

        // when
        BruteCollinearPoints cut = new BruteCollinearPoints(points);

        // then
        assertEquals(1, cut.numberOfSegments());
        assertEquals("(0, 0) -> (3, 6)", cut.segments()[0].toString());
    }

}
