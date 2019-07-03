-The Collinear Points program focuses on solving a pattern recognition
problem: given a set of n distinct points in a plane, identify
every maximal line segment that connects a subset of 4 or more points.

-Point.java is a class used to represent a point in the plane. 

-BruteCollinearPoints.java is a brute-force solution to this
problem. It solves it in worst-case O(N^4) time and uses space
proportional to N in order to build up the line segment array
to return.

-FastCollinearPoints.java is a faster, sorting-based solution 
to this problem. It solves it in worse-case O(N^2 log N) time
and uses space proportional to N.

-Bugs / Logic Errors
	-Point.java
		-Logic error inside of the SlopeOrder Comparator's
		compare function. In the else if block, I was checking
		whether p2Slope > p1Slope to return +1. However, this
		is the exact same check that appears in the else block
		when I check if p1Slope < p2Slope to return -1. As a result,
		my function wasn't operating properly since it never returned
		+1, making it impossible to use this comparator to sort by
		slopes. The fix is quite simple: the else if block should check
		whether p1Slope > p2Slope to return +1. 
		
	-BruteCollinearPoints.java
		-Logic error inside of the nested for loops used to identify
		collinear points. I was originally constructing a line segment
		with endpoints point p (start) and point s (end) once I determined
		that p->q->r->s is collinear. However, because the points array is
		not sorted, that means that you cannot blindly assume that point p
		(the first point encountered in the for loop) and point s (the last
		point encountered in the for loop) are truly the end points of this
		collinear line segment instead of just a sub segment. Solution: add
		points p, q, r, and s to a temporary array, sort this array, then construct
		the maximal line segment from this array's first and last points.
		
		-Bug inside of the segments function as I was just returning the private
		variable segmentArray. However, this proved to be problematic because this
		allows the client to mutate the private member variable, segmentArray, by 
		calling segments(), then altering the array that was returned. Solution: make
		use of a concept called Defensive Copying where getter functions return a new 
		copy of the private member variable each time rather than the member variable 
		itself. If the client mutates the copy, the member variable remains unaltered.
		