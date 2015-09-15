import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by Ingo on 15.09.2015.
 */
public class RandomizedQueueTest {

    private RandomizedQueue<String> cut;

    @Before
    public void setUp() {
        cut = new RandomizedQueue<>();
    }

    @Test
    public void shouldBeAbleToEnqueueAndDequeueItems() {
        assertTrue(cut.isEmpty());
        cut.enqueue("foo");
        assertFalse(cut.isEmpty());
        cut.enqueue("bar");
        assertFalse(cut.isEmpty());
        cut.dequeue();
        assertFalse(cut.isEmpty());
        cut.dequeue();
        assertTrue(cut.isEmpty());
    }

    @Test
    public void shouldBeAbleToIterateOverEntries() {
        cut.enqueue("foo");
        cut.enqueue("bar");

        Iterator<String> iterator = cut.iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullpointerExpetionOnEnqueuingNullValue() {
        cut.enqueue(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionWhenDeuquingFromAnEmptyQueue() {
        cut.dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionWhenSamplingFromAnEmptyQueue() {
        cut.sample();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void iteratorShouldThrowUnsupportedOperationExceptionWhenCallingRemove() {
        cut.iterator().remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorShouldThrowNoSuchElementExceptionWhenCallingNextWhenReqchedLastItem() {
        cut.iterator().next();
    }

}
