import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Ingo on 15.09.2015.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int MINIMUM_STORAGE_SIZE = 2;

    private Item[] items;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[MINIMUM_STORAGE_SIZE];
        size = 0;
    }

    public boolean isEmpty() {  // is the queue empty?
        return size == 0;
    }

    public int size() {  // return the number of items on the queue
        return size;
    }

    public void enqueue(Item item) {  // add the item
        if (item == null) {
            throw new NullPointerException("It is not allowed to enqueue null values.");
        }

        if (isStorageFull()) {
            doubleStorage();
        }

        items[size++] = item;
    }

    public Item dequeue() {  // remove and return a random item
        if (size == 0) {
            throw new NoSuchElementException("Trying to dequeue an item from an empty RandomQueue.");
        }

        int indexOfItemToReturn = StdRandom.uniform(size);
        Item returnValue = items[indexOfItemToReturn];
        size--;
        items[indexOfItemToReturn] = items[size];
        items[size] = null;

        if (isStorageOversized()) {
            halveStorage();
        }

        return returnValue;
    }

    private boolean isStorageFull() {
        return items.length == size;
    }

    private boolean isStorageOversized() {
        return items.length > MINIMUM_STORAGE_SIZE && size <= items.length / 4;
    }

    private void halveStorage() {
        resizeStorage(items.length / 2);
    }

    private void doubleStorage() {
        resizeStorage(items.length * 2);
    }

    private void resizeStorage(int newSize) {
        Item[] newItemStorage = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newItemStorage[i] = items[i];
        }
        items = newItemStorage;
    }

    public Item sample() {  // return (but do not remove) a random item
        if (size == 0) {
            throw new NoSuchElementException("Trying to sample an item from an empty RandomQueue.");
        }

        int indexOfItemToReturn = StdRandom.uniform(size);
        return items[indexOfItemToReturn];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }


    private class RandomIterator implements Iterator<Item> {

        private Item[] iteratorItems;
        private int index;

        public RandomIterator() {
            iteratorItems = copyRandomQueueItems();
            StdRandom.shuffle(iteratorItems);
        }

        private Item[] copyRandomQueueItems() {
            Item[] copiedItems = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                copiedItems[i] = items[i];
            }
            return copiedItems;
        }

        @Override
        public boolean hasNext() {
            return index < iteratorItems.length;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                return iteratorItems[index++];
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }
    }

}
