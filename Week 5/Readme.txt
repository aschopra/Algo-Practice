-PointSET.java and KdTree.java are two different approaches
to solving range search (find all of the points contained in a 
query rectangle) and nearest-neighbor search (find the closest 
point to a query point). PointSET.java takes a brute-force approach
to solving these two problems using a balanced BST as its underlying
data structure. KdTree.java uses a 2d-tree to more efficiently solve
range search (O(R + log N) where N is the number of points and R is the
number of points found in the query rectangle) and nearest-neighbor 
search (O(log N)) through efficiently pruning the search space.

-Bugs/Logic Errors in PointSET.java:
	-When implementing the nearest() function, I made a logic error in 
	setting the oldDistance to setPoint.distanceSquaredTo(closestNeighbor),
	causing nearest() to not function properly. The oldDistance should instead
	be set to closestNeighbor.distanceSquaredTo(p) where p is the query point 
	because we are interested in the previous distance from the closest neighbor 
	to the query point not the distance from the closest neighbor to the next
	point in the set.
	
-Bugs/Logic Errors in KdTree.java:
	-Bug in insert() function when I would attempt to add a point that already existed
	in the 2d-tree. The problem was that I was not checking whether the tree already contained
	the point before incrementing size. Every time insert() was called, I would increment size, 
	meaning during test cases where I tried to add duplicate entries, I was still incrementing
	size even though I was not allowing duplicates to exist in the tree. I fixed this issue
	by only calling insert() and incrementing the size instance variable if the 2d-tree does
	not already contain the point that is trying to be added by using the contains() method.
	
	-Logic error in nearestHelper() recursive function because when I was checking whether I
	could prune the search space and avoid going down one of the branches of the 2d-tree, I was
	only checking that the distance from the query point to the current closest neighbor was less
	than the distance from the query point to the root of either the left or right subtree, 
	depending on which side I visited first. However, the root of a subtree does not necessarily 
	contain the closest point to the query point, meaning this check I was performing was inaccurate. 
	Instead, I had to check that the distance from the query point to the current closest neighbor was
	less than the distance from the query point to the closest point in the rectangle represented by
	the root of the left or right subtree. 
	
	-Bug in nearestHelper() when I had to go down one of the other branches because there is a node in that 
	particular subtree that is possibly closer to the query point than the current nearest point. I was setting
	temp to the result of nearestHelper(r.right or r.left, p, currNearest, currDistance), but this was problematic 
	because if temp changed during a particular recursive call, then previous calls would still be passing their 
	copy of the local variables, currNearest and currDistance, up the call stack. I fixed this issue by setting
	temp to the result of nearestHelper(r.right or r.left, p, temp, temp.distanceSquaredTo(p)).  
	
	