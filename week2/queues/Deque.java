/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        private Node prev;
        private Node next;
        private Item item;

        private Node() {
            this.prev = null;
            this.next = null;
            this.item = null;
        }
    }

    // construct an empty deque
    public Deque() {
        this.head = new Node();
        this.tail = new Node();
        head.next = tail;
        tail.prev = head;
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Input item can't be null");
        Node next = head.next;
        Node newNode = new Node();
        newNode.item = item;
        head.next = newNode;
        newNode.prev = head;
        newNode.next = next;
        next.prev = newNode;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Input item can't be null");
        Node last = tail.prev;
        Node newTail = new Node();
        newTail.item = item;
        last.next = newTail;
        newTail.prev = last;
        newTail.next = tail;
        tail.prev = newTail;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size <= 0) throw new NoSuchElementException("List is empty");
        Node removedNode = head.next;
        Node nextNode = head.next.next;
        head.next = nextNode;
        nextNode.prev = head;
        size--;
        return removedNode.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size <= 0) throw new NoSuchElementException("List is empty");
        Node removedNode = tail.prev;
        Node preNode = tail.prev.prev;
        preNode.next = tail;
        tail.prev = preNode;
        size--;
        return removedNode.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node ptr = head;

        public boolean hasNext() {
            return ptr.next != null && ptr.next.item != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("At the end of list.");
            }
            else {
                Item res = ptr.next.item;
                ptr = ptr.next;
                return res;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove not supported.");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> queue = new Deque<String>();
        System.out.println(queue.size);
        queue.addFirst("a");
        queue.addFirst("b");
        queue.addLast("c");
        queue.addFirst("d");
        queue.addLast("e");
        System.out.println(queue.size);
        String first = queue.removeFirst();
        System.out.println("removed first: " + first);
        String last = queue.removeLast();
        System.out.println("removed last: " + last);
        System.out.println(queue.size);
        Iterator<String> iter = queue.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

}
