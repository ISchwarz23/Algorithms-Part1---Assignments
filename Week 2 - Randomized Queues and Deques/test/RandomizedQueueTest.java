import org.junit.Before;
import org.junit.Test;

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
        cut.enqueue("foo");
        cut.enqueue("bar");
        assertFalse(cut.isEmpty());

        cut.dequeue();
        cut.dequeue();
        assertTrue(cut.isEmpty());
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
