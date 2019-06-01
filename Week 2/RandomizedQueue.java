import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] q; // resizing array
	private int size;
	private int tail;
	
	private class UniformArrayIterator implements Iterator<Item> {
		private int remainingElements;
		private Item[] shuffledArr;
		
		public UniformArrayIterator() {
			remainingElements = size;
			shuffledArr = (Item[]) new Object[size];
			for (int i = 0; i < size; i++) {
				shuffledArr[i] = q[i]; // copy q[]
			}
			StdRandom.shuffle(shuffledArr);
		}
		public boolean hasNext() {
			return remainingElements > 0;
		}
		
		public void remove() {
			throw new UnsupportedOperationException("remove not supported");
		}
		
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException("no more items to return");
			}
			return shuffledArr[--remainingElements];
		}
	}
	//doubles or halves the array size
	private void resize(int capacity) {
		Item[] copy = (Item[])new Object[capacity];
		for (int i = 0; i < size; i++) {
			copy[i] = q[i];
		}
		q = copy;
	}
		
	// construct an empty randomized queue
   public RandomizedQueue() {
	   // cannot create array of generics so must cast
	   q = (Item[])new Object[1];
	   size = 0;
	   tail = 0;
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
	   if (item == null) {
		   throw new IllegalArgumentException("cannot enqueue value: null");
	   }
	   // reached capacity--need to double size of q[]
	   if (size == q.length) {
		   resize(2 * q.length);
	   }
	   q[size++] = item;
	   tail++;
   }
   // remove and return a random item
   public Item dequeue() {
	   if (isEmpty()) {
		   throw new NoSuchElementException("cannot dequeue from empty queue");
	   }
	   int index = StdRandom.uniform(0, q.length);
	   // find a random index with a value to remove
	   while (q[index] == null) {
		   index = StdRandom.uniform(0, q.length);
	   }
	   Item item = q[index];
	   /* to avoid gaps in q[] of null vals--replace 
	    element at random index with element at tail
	    */
	   q[index] = q[--tail];
	   q[tail] = null;
	   size--;
	   // can halve arr if at 25% capacity
	   if (size > 0 && size == q.length/4) {
		   resize(q.length/4);
	   }
	   return item;
   }
   // return a random item (but do not remove it)
   public Item sample() {
	   if (isEmpty()) {
		   throw new NoSuchElementException("cannot dequeue from empty queue");
	   }
	   int index = StdRandom.uniform(0, q.length);
	   while (q[index] == null) {
		   index = StdRandom.uniform(0, q.length);
	   }
	   Item item = q[index];
	   return item;
   }
   // return an independent iterator over items in random order
   public Iterator<Item> iterator() {
	   return new UniformArrayIterator();
   }
}
