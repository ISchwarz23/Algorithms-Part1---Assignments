import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * A queue implementation that allows adding and removing elements at the start and at the end.
 *
 * @author ISchwarz
 */
public class Deque<Item> implements Iterable<Item> {

    private InternalItem<Item> firstItem = null;
    private InternalItem<Item> lastItem = null;
    private int size = 0;


    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't add null to the deque.");
        }

        if (firstItem == null) {
            InternalItem<Item> onlyItem = new InternalItem<>();
            onlyItem.value = item;
            firstItem = onlyItem;
            lastItem = onlyItem;
        } else {
            InternalItem<Item> oldFirstItem = firstItem;
            firstItem = new InternalItem<>();
            firstItem.value = item;
            firstItem.nextItem = oldFirstItem;
            oldFirstItem.previousItem = firstItem;
        }

        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't add null to the deque.");
        }

        if (lastItem == null) {
            InternalItem<Item> onlyItem = new InternalItem<>();
            onlyItem.value = item;
            firstItem = onlyItem;
            lastItem = onlyItem;
        } else {
            InternalItem<Item> oldLastItem = lastItem;
            lastItem = new InternalItem<>();
            lastItem.value = item;
            lastItem.previousItem = oldLastItem;
            oldLastItem.nextItem = lastItem;
        }

        size++;
    }

    public Item removeFirst() {
        if (firstItem == null) {
            throw new NoSuchElementException("Client tries to remove an Item from empty deque.");
        }

        InternalItem<Item> oldFirstItem = firstItem;
        firstItem = firstItem.nextItem;
        if (firstItem == null) {
            lastItem = null;
        } else {
            firstItem.previousItem = null;
        }
        size--;

        return oldFirstItem.value;
    }

    public Item removeLast() {
        if (lastItem == null) {
            throw new NoSuchElementException("Client tries to remove an Item from empty deque.");
        }

        InternalItem<Item> oldLastItem = lastItem;
        lastItem = oldLastItem.previousItem;
        if (lastItem == null) {
            firstItem = null;
        } else {
            lastItem.nextItem = null;
        }
        size--;

        return oldLastItem.value;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ForwardIterator();
    }

    private class ForwardIterator implements Iterator<Item> {

        private InternalItem<Item> currentItem = firstItem;

        @Override
        public boolean hasNext() {
            return currentItem != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next element available. Reached end of deque.");
            }

            Item returnValue = currentItem.value;
            currentItem = currentItem.nextItem;
            return returnValue;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }
    }

    private static class InternalItem<I> {
        I value;
        InternalItem<I> nextItem;
        InternalItem<I> previousItem;
    }

}
