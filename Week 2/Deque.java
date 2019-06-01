import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private Node first;
	private Node last;
	private int numItems;
	
	private class Node {
		Item item;
		Node next;
		Node prev;
	}
	
	private class ListIterator implements Iterator<Item> {
		private Node current = first;
		public boolean hasNext() {
			return current != null;
		}
		public void remove() {
			// not supported--throw exception
			throw new UnsupportedOperationException("remove operation is not supported");
		}
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException("no more items to return");
			}
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	// construct an empty deque
   public Deque() {
	   first = null;
	   last = null;
	   numItems = 0;
   }
   // is the deque empty?
   public boolean isEmpty() {
	   return numItems == 0;
   }
   // return the number of items on the deque
   public int size() {
	   return numItems;
   }
   // add the item to the front
   public void addFirst(Item item) {
	   if (item == null) {
		   throw new IllegalArgumentException("cannot add a null item to deque");
	   }
	   Node oldfirst = first; // reference to first node
	   first = new Node();
	   first.item = item;
	   first.next = null;
	   first.prev = null;
	   if (isEmpty()) {
		   // means node created will be first in linked list
		   last = first; // both ptrs point to this single node now
	   } else {
		   first.next = oldfirst; // connect new node to rest linked list
		   oldfirst.prev = first;
	   }
	   numItems++;
   }
   // add the item to the end
   public void addLast(Item item) {
	   if (item == null) {
		   throw new IllegalArgumentException("cannot add a null item to deque");
	   }
	   Node oldlast = last;
	   last = new Node();
	   last.item = item;
	   last.next = null;
	   last.prev = null;
	   if (isEmpty()) {
		   // means node created is only node in linked list
		   first = last;
	   } else {
		   oldlast.next = last;
		   last.prev = oldlast;
	   }
	   numItems++;
   }
   // remove and return the item from the front
   public Item removeFirst() {
	   if (isEmpty()) {
		   throw new NoSuchElementException("cannot remove from empty deque");
	   }
	   Item firstItem = first.item;
	   //Case 1: only 1 item in the list
	   if (size() == 1) {
		   first = null;
		   last = null;
	   } else {
		   //Case 2: remove first element from a bunch of items in list
		   first = first.next; //lose reference to old head--available for gc
		   first.prev.next = null;
		   first.prev = null;
	   }
	   numItems--;
	   return firstItem;
   }
   // remove and return the item from the end
   public Item removeLast() {
	   if (isEmpty()) {
		   throw new NoSuchElementException("cannot remove from empty deque");
	   }
	   Item lastItem = last.item;
	   if (size() == 1) {
		   first = null;
		   last = null;
	   } else {
		   last = last.prev;
		   last.next.prev = null;
		   last.next = null;
	   }
	   numItems--;
	   return lastItem;
	   
   }
   // return an iterator over items in order from front to end
   public Iterator<Item> iterator() {
	   return new ListIterator();
   }

}
