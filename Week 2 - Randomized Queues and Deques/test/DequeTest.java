import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Ingo on 15.09.2015.
 */
public class DequeTest {

    private Deque<String> cut;

    @Before
    public void setUp() {
        cut = new Deque<>();
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullpointerByAddingNullAsFistElement() {
        cut.addFirst(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullpointerByAddingNullAsLastElement() {
        cut.addLast(null);
    }

    @Test
    public void shouldReturnFirstItem() {
        // when
        cut.addFirst("foo");

        assertFalse(cut.isEmpty());
        assertEquals(1, cut.size());
        assertEquals("foo", cut.removeFirst());
    }

    @Test
    public void shouldReturnLastItem() {
        // when
        cut.addLast("foo");

        assertFalse(cut.isEmpty());
        assertEquals(1, cut.size());
        assertEquals("foo", cut.removeLast());
    }

    @Test
    public void shouldReturnFirstItemAsLastItemOfQueueIsSizeOne() {
        // when
        cut.addFirst("foo");

        // then
        assertFalse(cut.isEmpty());
        assertEquals(1, cut.size());
        assertEquals("foo", cut.removeLast());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionOnRemovingFirstItemFromEmptyDequq() {
        cut.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionOnRemovingLastItemFromEmptyDequq() {
        cut.removeLast();
    }

    @Test
    public void testIterationAfterAddingFirst() {
        // given
        cut.addFirst("foo");
        cut.addFirst("bar");

        // when
        Iterator<String> iterator = cut.iterator();

        // then
        assertEquals("bar", iterator.next());
        assertEquals("foo", iterator.next());
    }

    @Test
    public void testIterationAfterAddingLast() {
        // given
        cut.addLast("foo");
        cut.addLast("bar");

        // when
        Iterator<String> iterator = cut.iterator();

        // then
        assertEquals("foo", iterator.next());
        assertEquals("bar", iterator.next());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void iteratorShouldThrowUnsupportedOperationExceptionWhenCallingRemove() {
        cut.iterator().remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorShouldThrowNoSuchElementExceptionIfLastElementIsReached() {
        assertFalse(cut.iterator().hasNext());
        cut.iterator().next();
    }

}
