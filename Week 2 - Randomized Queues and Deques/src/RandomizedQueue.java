import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Ingo on 15.09.2015.
 */
public class RandomizedQueue<I> implements Iterable<I> {

    private Item<I> lastItem = null;
    private int size = 0;


    public boolean isEmpty() {  // is the queue empty?
        return lastItem == null;
    }

    public int size() {  // return the number of items on the queue
        return size;
    }

    public void enqueue(I item) {  // add the item
        if(item == null) {
            throw new NullPointerException("It is not allowed to enqueue null values.");
        }

        Item<I> newItem = new Item<>();
        newItem.value = item;
        newItem.previousItem = lastItem;
        lastItem = newItem;
        size++;
    }

    public I dequeue() {  // remove and return a random item
        I returnValue;

        if(size == 0) {
            throw new NoSuchElementException("Trying to dequeue an item from an empty RandomQueue.");
        } else if(size == 1) {
            returnValue = lastItem.value;
            lastItem = null;
        } else {
            int indexOfItemToReturn = StdRandom.uniform(size - 1) + 1;

            Item<I> itemAfterItemToReturn = getItem(indexOfItemToReturn - 1);
            Item<I> itemToReturn = itemAfterItemToReturn.previousItem;
            Item<I> itemBeforeItemToReturn = itemToReturn.previousItem;

            itemAfterItemToReturn.previousItem = itemBeforeItemToReturn;
            returnValue = itemToReturn.value;
        }
        size--;

        return returnValue;
    }

    public I sample() {  // return (but do not remove) a random item
        I returnValue;

        if(size == 0) {
            throw new NoSuchElementException("Trying to get a sample item from an empty RandomQueue.");
        } else if(size == 1) {
            returnValue = lastItem.value;
        } else {
            int indexOfItemToReturn = StdRandom.uniform(size - 1) + 1;
            returnValue = getItem(indexOfItemToReturn).value;
        }

        return returnValue;
    }

    private Item<I> getItem(int indexFromEnd) {
        Item<I> itemToReturn = lastItem;
        for (int i = 0; i < indexFromEnd; i++) {
            itemToReturn = itemToReturn.previousItem;
        }
        return itemToReturn;
    }

    @Override
    public Iterator<I> iterator() {
        return new RandomIterator<>(lastItem, size);
    }


    private static class Item<I> {
        I value;
        Item<I> previousItem;
    }

    private static class RandomIterator<I> implements Iterator<I> {

        private I[] items;
        private int index;

        public RandomIterator(Item<I> lastItem, int size) {
            items = getItems(lastItem, size);
            StdRandom.shuffle(items);
        }

        private I[] getItems(Item<I> lastItem, int size) {
            I[] items = (I[]) new Object[size];
            Item<I> item = lastItem;

            for (int i = 0; i < size; i++) {
                items[i] = item.value;
                item = item.previousItem;
            }

            return items;
        }

        @Override
        public boolean hasNext() {
            return index < items.length-1;
        }

        @Override
        public I next() {
            if(hasNext()) {
                return items[index++];
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
