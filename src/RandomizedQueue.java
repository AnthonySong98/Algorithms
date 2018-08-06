import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

        private Item[] arr;
        private int capacity;

        public RandomizedQueue()                 // construct an empty randomized queue
    {
        capacity = 0;
        arr = (Item[]) new Object[2];

    }
    public boolean isEmpty()                 // is the randomized queue empty?
    {
        return capacity==0;

    }
    public int size()                        // return the number of items on the randomized queue
    {
        return capacity;
    }

    private void resize(int newcapacity)
    {
        Item[] temp = (Item[]) new Object[newcapacity];
        int i;
        for(i=0;i<capacity;i++){
            temp[i] = arr[i];
        }
        arr = temp;
    }
    public void enqueue(Item item)           // add the item
    {
        if(item==null) throw new java.lang.IllegalArgumentException();

        if(capacity==arr.length) resize(2*capacity);

        arr[capacity++]=item;

    }
    public Item dequeue()                    // remove and return a random item
    {
        if(isEmpty()) throw new java.util.NoSuchElementException();

        int targetIndex = (int)StdRandom.uniform(0,capacity);
        Item item = arr[targetIndex];
        arr[targetIndex] = arr[capacity-1];
        arr[capacity-1]=null;
        capacity--;

        if(capacity>0&&capacity<=arr.length/4) resize(arr.length/2);
        return item;


    }
    public Item sample()                     // return a random item (but do not remove it)
    {
        if(isEmpty()) throw new java.util.NoSuchElementException();

        int targetIndex = (int)StdRandom.uniform(0,capacity);
        Item item = arr[targetIndex];
        return item;

    }
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private int i;
        private Item[] temp;

        public RandomizedQueueIterator(){
            temp = (Item[]) new Object[capacity];
            for(int j=0;j<capacity;j++){
                temp[j] = arr[j];
            }
            i = capacity-1;
        }

        @Override
        public boolean hasNext() {
            return i>=0;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();

            int targetIndex = (int)StdRandom.uniform(0,i+1);
            Item item = temp[targetIndex];
            temp[targetIndex]=temp[i];
            temp[i] = item;
            i--;
            return item;
        }
    }
    public static void main(String[] args)   // unit testing (optional)
    {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("-") && !queue.isEmpty()){
                StdOut.print(queue.dequeue() + " ");
            }
            else if (item.equals("--") && !queue.isEmpty()){
                StdOut.print(queue.sample() + " ");
            }
            else if (item.equals("show")) {
                for (String s : queue) {
                    StdOut.println(s);
                }
            }
            else if (item.equals("size")){
                StdOut.println("(" + queue.size() + " left on stack)");
            }
            else if (item.equals("exit")){
                break;
            }
            else {
                queue.enqueue(item);
            }
        }
        StdOut.println("(" + queue.size() + " left on stack)");

    }
}
