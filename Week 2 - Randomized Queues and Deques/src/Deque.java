import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * A queue implementation that allows adding and removing elements at the start and at the end.
 * @author ISchwarz
 */
public class Deque<I> implements Iterable<I> {

    private Item<I> firstItem = null;
    private Item<I> lastItem = null;
    private int size = 0;

    
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(I item) {
        if (item == null) {
            throw new NullPointerException("Can't add null to the deque.");
        }

        if (firstItem == null) {
            Item<I> onlyItem = new Item<>();
            onlyItem.value = item;
            firstItem = onlyItem;
            lastItem = onlyItem;
        } else {
            Item<I> oldFirstItem = firstItem;
            firstItem = new Item<>();
            firstItem.value = item;
            firstItem.nextItem = oldFirstItem;
            oldFirstItem.previousItem = firstItem;
        }

        size++;
    }

    public void addLast(I item) {
        if (item == null) {
            throw new NullPointerException("Can't add null to the deque.");
        }

        if (lastItem == null) {
            Item<I> onlyItem = new Item<>();
            onlyItem.value = item;
            firstItem = onlyItem;
            lastItem = onlyItem;
        } else {
            Item<I> oldLastItem = lastItem;
            lastItem = new Item<>();
            lastItem.value = item;
            lastItem.previousItem = oldLastItem;
            oldLastItem.nextItem = lastItem;
        }

        size++;
    }

    public I removeFirst() {
        if (firstItem == null) {
            throw new NoSuchElementException("Client tries to remove an Item from empty deque.");
        }

        Item<I> oldFirstItem = firstItem;
        firstItem = firstItem.nextItem;
        firstItem.previousItem = null;
        size--;

        return oldFirstItem.value;
    }

    public I removeLast() {
        if (lastItem == null) {
            throw new NoSuchElementException("Client tries to remove an Item from empty deque.");
        }

        Item<I> oldLastItem = lastItem;
        lastItem = oldLastItem.previousItem;
        lastItem.nextItem = null;
        size--;

        return oldLastItem.value;
    }

    @Override
    public Iterator<I> iterator() {
        return new ForwardIterator();
    }

    private class ForwardIterator implements Iterator<I> {

        private Item<I> currentItem = firstItem;

        @Override
        public boolean hasNext() {
            return currentItem != null;
        }

        @Override
        public I next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next element available. Reached end of deque.");
            }

            I returnValue = currentItem.value;
            currentItem = currentItem.nextItem;
            return returnValue;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }
    }

    private static class Item<I> {
        I value;
        Item<I> nextItem;
        Item<I> previousItem;
    }

}
