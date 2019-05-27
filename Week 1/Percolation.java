import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/*
 More efficient implementation of the Percolation class. Improvements include
 storing whether a site is open/closed as a bool array rather than an int array
 to save space. Secondly, rather than actually physically appending the virtual top
 and bottom sites to the N*N grid, I treated these sites as virtual to make the logic
 clearer.
 */

public class Percolation {
	private int N; //grid size
	private boolean [][] siteStatus; //tracks open/closed locations in grid
	private WeightedQuickUnionUF wq;
	private int numOpenSites;
	private int topVirtualIndex;
	private int bottomVirtualIndex;
	
	private boolean validIndices(int r, int c) {
		if(r < 1 || r > N || c < 1 || c > N) {
			return false;
		}
		return true;
	}
	
	private int xyToID(int r, int c) {
		int index = (N * (r-1)) + c;
		return index;
	}
	
	private void performUnion(char dir, int row, int col) {
		int neighborIndex = 0;
		int p = xyToID(row, col);
		int q = 0;
		if(dir == 'u') {
			neighborIndex = row -1;
			if(validIndices(neighborIndex, col)) {
				q = xyToID(neighborIndex, col);
				if(isOpen(neighborIndex, col) && !wq.connected(p, q)) {
					wq.union(p, q);
				}
			}
		} else if(dir == 'd') {
			neighborIndex = row + 1;
			if(validIndices(neighborIndex, col)) {
				q = xyToID(neighborIndex, col);
				if(isOpen(neighborIndex, col) && !wq.connected(p, q)) {
					wq.union(p, q);
				}
			}
		} else if(dir == 'l') {
			neighborIndex = col - 1;
			if(validIndices(row, neighborIndex)) {
				q = xyToID(row, neighborIndex);
				if(isOpen(row, neighborIndex) && !wq.connected(p, q)) {
					wq.union(p, q);
				}
			}
		} else {
			neighborIndex = col + 1;
			if(validIndices(row, neighborIndex)) {
				q = xyToID(row, neighborIndex);
				if(isOpen(row, neighborIndex) && !wq.connected(p, q)) {
					wq.union(p, q);
				}
			}
		}
	}
	
	 // create n-by-n grid, with all sites blocked
	public Percolation(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("n must be positive");
		}
		N = n;
		wq = new WeightedQuickUnionUF((N*N)+2); //need extra 2 objects for virtual top and bottom sites
		siteStatus = new boolean [N][N];
		numOpenSites = 0;
		topVirtualIndex = 0;
		bottomVirtualIndex = (N*N) + 1;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				siteStatus[i][j] = false; //false = site is blocked
				
			}
		}		
	}
	
	 // open site (row, col) if it is not open already
	public void open(int row, int col) {
		//make sure indices valid
		if(validIndices(row, col)) {
			if(!siteStatus[row-1][col-1]) {
				//site not already opened
				siteStatus[row-1][col-1] = true;
				numOpenSites++;
				//connect every node in 1st row to virtual top
				if(row == 1) {
					wq.union(topVirtualIndex, xyToID(row, col));
				}
				//connect every node in last row to virtual bottom
				if(row == N) {
					wq.union(bottomVirtualIndex, xyToID(row, col));
				}
				//check if any of neighbor nodes are open; if so, make a connection
				performUnion('u', row, col);
				performUnion('d', row, col);
				performUnion('l', row, col);
				performUnion('r', row, col);
			}
		} else {
			throw new IllegalArgumentException("row/col outside prescribed range");
		}
	}
	
	 // is site (row, col) open
	public boolean isOpen(int row, int col) {
		if(validIndices(row, col)) {
			return siteStatus[row-1][col-1] ? true : false;
		} else {
			throw new IllegalArgumentException("row/col outside prescribed range");
		}
		
	}
	
	// is site (row, col) full?
	public boolean isFull(int row, int col) {
		if(validIndices(row, col)) {
			if(isOpen(row, col)) {
				//now see if there is a path from this object to top virtual object
				int pInd = xyToID(row, col);
				return wq.connected(pInd, topVirtualIndex);
			} else {
				return false;
			}
		} else {
			throw new IllegalArgumentException("row/col outside prescribed range");
		}
	}
    // number of open sites
	public int numberOfOpenSites() {
		return numOpenSites;
	}
	
    // does the system percolate?
	public boolean percolates() {
		return wq.connected(topVirtualIndex, bottomVirtualIndex);
	}

}
