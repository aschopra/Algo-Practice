-Deque.java implements a generic double-ended queue using a doubly 
linked list that supports constant worst-case time for all of its operations.

-Bugs/Major Changes to Deque.java:
	-1) In addFirst(Item item), I had a logic error inside the if
	statement checking whether the deque was empty. I originally set
	first = last when trying to make both pointers point to this single
	node that was just created. However, last was null, so I was unable
	to successfully add any items to the deque because I was losing the reference
	to the head of the deque. After tracing the code out on paper, I realized 
	this was a small fix--it should have been last = first. 
	
	-2) The removeLast() function was not deleting the last node in the
	deque because I was not setting last.prev.next to null, so the last node
	to delete was not available for the garbage collector because there were
	still outstanding references to it ("loitering").
	
	-3) I originally was implementing the deque with a singly-linked list by
	maintaining references to the first and last nodes. However, I ran into a 
	problem trying to implement the removeLast() function in that I could not 
	figure out how to keep track of the previous to last node every time a call
	to removeLast() was made. I was trying to maintain constant worst-case time
	for all operations, meaning that I could not iterate to find the new last node
	after making a removal from the end. I fixed this issue by pivoting to a doubly
	linked-list where each node has a next and prev ptr.
	
	
-RandomizedQueue.java implements a generic randomized queue that supports operations
(besides those in the iterator) in constant amortized time. It was implemented with
a resizing array that doubles every time the capacity is reached and halves when the
array is only 1/4th the size of the total capacity. 

-Bugs/Major Changes to RandomizedQueue.java
	-1) I was having trouble implementing dequeue() at a uniformly random index
	in q[] because if an element from the middle array was removed, I wrote a 
	method shifting all elements to the right of this newly removed element left
	one space to ensure no gaps of null vals existed in q[]. However, this would be
	linear-time for dequeue(). The solution is quite clever: when removing an element
	in q[], simply replace its value with the value that was at the tail and set the 
	tail value to null. This ensures no gaps will occur in the array and maintains 
	amortized constant time for dequeue().