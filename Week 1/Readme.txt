-Percolation.java and Percolation2.java are slightly different
implementations of a popular dynamic connectivity problem.
Using a weighted Quick-Union + Path Compression algorithm,
we can determine whether an N*N grid percolates. That is,
whether there is a path of open sites from an entry in the top row 
of the grid to an entry in the bottom row of the grid. This problem 
has practical uses, including whether a composite system is an 
electrical conductor or whether water can drain through from
the top to bottom in a porous landscape.


-PercolationStats.java allows us to compute the percolation
threshold, p*, given an N*N grid by running a Monte-Carlo simulation
T times. The grid starts out with all blocked sites, and 
a site is chosen uniformly at random to open. The moment at which
a site that is opened causes the grid to percolate, p* is calculated
as the ratio of open sites to total sites in the grid. No mathematical
solution to determine the percolation threshold has been derived, so
we must attempt to solve this problem with a computation model
+ simulations. 

-Bugs/Fixes in this project:
	-Percolation.java
		-Changed 2D-array of ints to bools when tracking open
		vs closed sites in the grid to save SPACE.
		-Off-by-1 error in accessing 2-D grid's index
		mapped to 1-D array in performUnion() helper. 
		Took a long time to fix/spot!
		Project assumed indexing from 1 to N, but I indexed
		siteStatus[][] from 0 to N-1.
	-Percolation2.java
		-same Off-by-1 error as above.
 