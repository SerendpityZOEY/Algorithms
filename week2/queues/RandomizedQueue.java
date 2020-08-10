/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] rqArray;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.rqArray = (Item[]) new Object[1];
        this.size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Input item can't be null");
        rqArray[size++] = item;
        if (size == rqArray.length) resize(2 * rqArray.length);
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException("RandomizeQueue is empty!");
        int randInd = StdRandom.uniform(0, size);
        Item removed = rqArray[randInd];
        size--;
        rqArray[randInd] = rqArray[size];
        rqArray[size] = null;
        if (size > 0 && size == rqArray.length / 4) {
            resize(rqArray.length / 2);
        }
        return removed;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException("RandomizeQueue is empty!");
        return rqArray[StdRandom.uniform(0, size)];
    }

    private void resize(int newSize) {
        Item[] tmp = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            tmp[i] = rqArray[i];
        }
        rqArray = tmp;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator(size, rqArray);
    }

    private class RandomIterator implements Iterator<Item> {
        private Item[] randIterArray;
        private int randIterCounter;

        public RandomIterator(int size, Item[] rqArray) {
            this.randIterArray = (Item[]) new Object[size];
            this.randIterCounter = size;
            for (int i = 0; i < size; i++) {
                randIterArray[i] = rqArray[i];
            }
        }

        public boolean hasNext() {
            return randIterCounter > 0;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("All items visited");
            int randInd = StdRandom.uniform(0, randIterCounter);
            Item visited = randIterArray[randInd];
            randIterCounter--;
            randIterArray[randInd] = randIterArray[randIterCounter];
            randIterArray[randIterCounter] = visited;
            return visited;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove not supported.");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("A");
        rq.enqueue("B");
        rq.enqueue("C");
        rq.enqueue("D");
        rq.enqueue("E");
        rq.enqueue("F");
        rq.enqueue("G");
        rq.enqueue("H");
        rq.enqueue("I");
        rq.dequeue();
        Iterator<String> iter1 = rq.iterator();
        Iterator<String> iter2 = rq.iterator();
        while (iter1.hasNext()) {
            System.out.print(iter1.next() + ",");
        }
        System.out.println();
        while (iter2.hasNext()) {
            System.out.print(iter2.next() + ",");
        }
        System.out.println();
    }

}
