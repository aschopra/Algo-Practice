-Solver.java is a program that solves the 8-puzzle problem through
implementing the A* search algorithm (Manhattan priority). Further 
optimizations include pruning the search space to reduce unnecessary 
exploration of repeat nodes and pre-computing a particular nodes' 
Manhattan value to avoid recomputing this value, which is a bottleneck
operation.

-Bugs/Logic Errors encountered but FIXED
	-Bug: In Board.java's equals() method, I forgot to check that
	the passed in object's class/type was the same as the current object.
	This can potentially cause issues if someone were to pass in a string 
	with the same tiles as your board.
	
	-Logic Error: encountered an issue with my comparator in Solver.java.
	I was merely returning the difference in 2 boards' priority
	values. However, I forgot to account for the case when 2 boards
	at different distances away from the starting board may have
	the same priority value. For instance, let's say that after two moves,
	there are two boards in the priority queue with the same priorities even
	if they may be at different levels of the game tree. In this case, you must
	compare their manhattan distances and choose the node with the smaller value
	by this criteria, given that their total priorities may be equal. 
	
	-Logic Error: My program was running slowly because each time I was deciding which
	neighbors to push onto the priority queue, I was looping through all of
	the board states in the current solution to ensure none of the neighbors
	had already been seen. However, the only thing I needed to check was whether
	each neighbor was equal to the popped off min node's parent. If so, then this
	neighbor node would not be pushed onto the priority queue, but if not, then push
	this neighbor.