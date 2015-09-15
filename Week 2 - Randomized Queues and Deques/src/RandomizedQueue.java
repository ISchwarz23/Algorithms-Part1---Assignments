import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Ingo on 15.09.2015.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private InternalItem<Item> lastItem = null;
    private int size = 0;


    public boolean isEmpty() {  // is the queue empty?
        return lastItem == null;
    }

    public int size() {  // return the number of items on the queue
        return size;
    }

    public void enqueue(Item item) {  // add the item
        if (item == null) {
            throw new NullPointerException("It is not allowed to enqueue null values.");
        }

        InternalItem<Item> newItem = new InternalItem<>();
        newItem.value = item;
        newItem.previousItem = lastItem;
        lastItem = newItem;
        size++;
    }

    public Item dequeue() {  // remove and return a random item
        Item returnValue;

        if (size == 0) {
            throw new NoSuchElementException("Trying to dequeue an item from an empty RandomQueue.");
        } else if (size == 1) {
            returnValue = lastItem.value;
            lastItem = null;
        } else {
            int indexOfItemToReturn = StdRandom.uniform(size - 1) + 1;

            InternalItem<Item> itemAfterItemToReturn = getItem(indexOfItemToReturn - 1);
            InternalItem<Item> itemToReturn = itemAfterItemToReturn.previousItem;
            InternalItem<Item> itemBeforeItemToReturn = itemToReturn.previousItem;

            itemAfterItemToReturn.previousItem = itemBeforeItemToReturn;
            returnValue = itemToReturn.value;
        }
        size--;

        return returnValue;
    }

    public Item sample() {  // return (but do not remove) a random item
        Item returnValue;

        if (size == 0) {
            throw new NoSuchElementException("Trying to get a sample item from an empty RandomQueue.");
        } else if (size == 1) {
            returnValue = lastItem.value;
        } else {
            int indexOfItemToReturn = StdRandom.uniform(size - 1) + 1;
            returnValue = getItem(indexOfItemToReturn).value;
        }

        return returnValue;
    }

    private InternalItem<Item> getItem(int indexFromEnd) {
        InternalItem<Item> itemToReturn = lastItem;
        for (int i = 0; i < indexFromEnd; i++) {
            itemToReturn = itemToReturn.previousItem;
        }
        return itemToReturn;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomIterator<>(lastItem, size);
    }


    private static class InternalItem<I> {
        private I value;
        private InternalItem<I> previousItem;
    }

    private static class RandomIterator<Item> implements Iterator<Item> {

        private Item[] items;
        private int index;

        public RandomIterator(InternalItem<Item> lastItem, int size) {
            items = getItems(lastItem, size);
            StdRandom.shuffle(items);
        }

        private Item[] getItems(InternalItem<Item> lastItem, int size) {
            Item[] itemArray = (Item[]) new Object[size];
            InternalItem<Item> item = lastItem;

            for (int i = 0; i < size; i++) {
                itemArray[i] = item.value;
                item = item.previousItem;
            }

            return itemArray;
        }

        @Override
        public boolean hasNext() {
            return index < items.length;
        }

        @Override
        public Item next() {
            if (hasNext()) {
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
