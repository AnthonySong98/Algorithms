import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node{
        private Item item;
        private Node previous;
        private Node next;
    }

    private int numOfNodes;
    private Node head;
    private Node tail;

    public Deque()                           // construct an empty deque
    {
        numOfNodes = 0;
        head = tail = null;
    }
    public boolean isEmpty()                 // is the deque empty?
    {
        return numOfNodes==0;
    }
    public int size()                        // return the number of items on the deque
    {
        return numOfNodes;
    }
    public void addFirst(Item item)          // add the item to the front
    {
        if(item==null) throw  new java.lang.IllegalArgumentException();

        Node oldHead = head;
        head = new Node();
        head.item = item;
        head.next = oldHead;

        if(oldHead==null){
            tail = head;
        }
        else{
            oldHead.previous=head;
        }
        numOfNodes++;
    }
    public void addLast(Item item)           // add the item to the end
    {
        if(item==null) throw  new java.lang.IllegalArgumentException();

        Node oldTail = tail;
        tail = new Node();
        tail.item = item;
        tail.previous = oldTail;

        if(oldTail==null){
            head = tail;
        }
        else {
            oldTail.next=tail;
        }
        numOfNodes++;
    }
    public Item removeFirst()                // remove and return the item from the front
    {
        if(isEmpty()) throw new java.util.NoSuchElementException();

        Item item = head.item;
        head = head.next;

        if(head==null){
            tail = null;
        }
        else{
            head.previous=null;
        }
        numOfNodes--;
        return item;
    }
    public Item removeLast()                 // remove and return the item from the end
    {
        if(isEmpty()) throw new java.util.NoSuchElementException();

        Item item = tail.item;
        tail = tail.previous;

        if(tail==null){
            head=null;
        }
        else{
            tail.next=null;
        }
        numOfNodes--;
        return item;
    }
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current!=null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    public static void main(String[] args)   // unit testing (optional)
    {
        Deque<String> deque = new Deque<>();
        while (!StdIn.isEmpty()){
            String item = StdIn.readString();

            if(item.equals("+")){
                item = StdIn.readString();
                deque.addFirst(item);
            }
            else if (item.equals("++")){
                item = StdIn.readString();
                deque.addLast(item);
            }
            else if (item.equals("exit")){
                break;
            }
            else if (item.equals("show")){
                for (String s:deque)
                    StdOut.println(s);
            }
            else if (item.equals("-")){
                if(!deque.isEmpty()){
                    StdOut.print(deque.removeFirst()+" ");
                }
            }
            else if (item.equals("--")){
                if (!deque.isEmpty()){
                    StdOut.print(deque.removeLast()+" ");
                }
            }
        }

        StdOut.print("("+deque.size()+" left on deque )");

    }
}