import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    public void shouldEnqueueAndDequeueAllElements() {
        List<String> items = new ArrayList<>();
        for (char c = 'a'; c < 'z'; c++) {
            cut.enqueue("" + c);
            items.add("" + c);
        }
        assertEquals(items.size(), cut.size());

        while (!cut.isEmpty()) {
            items.remove(cut.dequeue());
        }
        assertTrue(items.isEmpty());
    }

    @Test
    public void shouldSampleAllElements() {
        List<String> items = new ArrayList<>();
        for (char c = 'a'; c < 'z'; c++) {
            cut.enqueue("" + c);
            items.add("" + c);
        }
        assertEquals(items.size(), cut.size());

        while (!items.isEmpty()) {
            items.remove(cut.sample());
        }
    }

    @Test
    public void shouldRandomlyDequeueItems() {
        int frequencyA = 0;
        int frequencyB = 0;
        int frequencyC = 0;

        for(int i=0; i<3000; i++) {
            cut = new RandomizedQueue<>();
            cut.enqueue("A");
            cut.enqueue("B");
            cut.enqueue("C");

            String item = cut.dequeue();
            switch (item) {
                case "A":
                    frequencyA++;
                    break;
                case "B":
                    frequencyB++;
                    break;
                case "C":
                    frequencyC++;
                    break;
            }
        }

        String errorMessage = "Dequeue is not random [A:" + frequencyA+", B:"+frequencyB + ", C:" + frequencyC +"]";
        assertTrue(errorMessage, 500 < frequencyA && frequencyA < 1500);
        assertTrue(errorMessage, 500 < frequencyB && frequencyB < 1500);
        assertTrue(errorMessage, 500 < frequencyC && frequencyC < 1500);
    }

    @Test
    public void shouldIterateOverAllElements() {
        List<String> items = new ArrayList<>();
        for (char c = 'a'; c < 'z'; c++) {
            cut.enqueue("" + c);
            items.add("" + c);
        }
        assertEquals(items.size(), cut.size());

        for (String aCut : cut) {
            items.remove(aCut);
        }
        assertTrue(items.isEmpty());
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
