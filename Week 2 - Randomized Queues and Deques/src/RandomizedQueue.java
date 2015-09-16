import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Ingo on 15.09.2015.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[2];
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

    private boolean isStorageFull() {
        return items.length == size;
    }

    private void doubleStorage() {
        Item[] newItemStorage = (Item[]) new Object[items.length * 2];
        for (int i = 0; i < size; i++) {
            newItemStorage[i] = items[i];
        }
        items = newItemStorage;
    }

    public Item dequeue() {  // remove and return a random item
        if (size == 0) {
            throw new NoSuchElementException("Trying to dequeue an item from an empty RandomQueue.");
        }

        int indexOfItemToReturn = StdRandom.uniform(size);
        Item returnValue = items[indexOfItemToReturn];
        items[indexOfItemToReturn] = items[--size];

        return returnValue;
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
