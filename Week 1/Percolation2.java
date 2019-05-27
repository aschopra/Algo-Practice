import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation2 {
	private int N; //grid size
	private int [][] siteStatus; //tracks open/closed locations in grid
	private WeightedQuickUnionUF wq;
	private int numOpenSites;
	
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
		int p = xyToID(row, col)-1;
		int q = 0;
		if(dir == 'u') {
			neighborIndex = row -1;
			if(validIndices(neighborIndex, col)) {
				q = xyToID(neighborIndex, col)-1;
				if(isOpen(neighborIndex, col) && !wq.connected(p, q)) {
					wq.union(p, q);
				}
			}
		} else if(dir == 'd') {
			neighborIndex = row + 1;
			if(validIndices(neighborIndex, col)) {
				q = xyToID(neighborIndex, col)-1;
				if(isOpen(neighborIndex, col) && !wq.connected(p, q)) {
					wq.union(p, q);
				}
			}
		} else if(dir == 'l') {
			neighborIndex = col - 1;
			if(validIndices(row, neighborIndex)) {
				q = xyToID(row, neighborIndex)-1;
				if(isOpen(row, neighborIndex) && !wq.connected(p, q)) {
					wq.union(p, q);
				}
			}
		} else {
			neighborIndex = col + 1;
			if(validIndices(row, neighborIndex)) {
				q = xyToID(row, neighborIndex)-1;
				if(isOpen(row, neighborIndex) && !wq.connected(p, q)) {
					wq.union(p, q);
				}
			}
		}
	}
	
	 // create n-by-n grid, with all sites blocked
	public Percolation2(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("n must be positive");
		}
		N = n;
		wq = new WeightedQuickUnionUF((N*N)+2); //objects from 0 to N^2-1
		siteStatus = new int [N+2][N+1];
		numOpenSites = 0;
		for(int i = 0; i < N+2; i++) {
			for(int j = 1; j <= N; j++) {
				if(i == 0 || i == (N+2)-1) {
					siteStatus[i][j] = 1;
					break;
				} else {
					siteStatus[i][j] = 0; //0 = site is blocked
				}
				
			}
		}
		//now need to connect virtual top and bottom to every node in top and bottom
		int pIndex = 0;
		int qIndex = 0;
		for(int j = 1; j <= N; j++) {
			qIndex = xyToID(1, j);
			wq.union(pIndex, qIndex);
		}
		
		int p = ((N*N) + 2) -1;
		int q = 0;
		for(int j = 1; j <=N; j++) {
			q = xyToID(N, j);
			wq.union(p,  q);
		}
		
	}
	
	 // open site (row, col) if it is not open already
	public void open(int row, int col) {
		//make sure indices valid
		if(validIndices(row, col)) {
			if(siteStatus[row][col] == 1) {
				return; //site already open
			}
			siteStatus[row][col] = 1;
			numOpenSites++;
			performUnion('u', row, col);
			performUnion('d', row, col);
			performUnion('l', row, col);
			performUnion('r', row, col);
		} else {
			throw new IllegalArgumentException("row/col outside prescribed range");
		}
	}
	
	 // is site (row, col) open
	public boolean isOpen(int row, int col) {
		if(validIndices(row, col)) {
			return siteStatus[row][col] == 1 ? true : false;
		} else {
			throw new IllegalArgumentException("row/col outside prescribed range");
		}
		
	}
	
	// is site (row, col) full?
	public boolean isFull(int row, int col) {
		if(validIndices(row, col)) {
			if(isOpen(row, col)) {
				//now see if there is a path from this object to top abstract object
				int pInd = xyToID(row, col);
				return wq.connected(pInd, 0);
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
		return wq.connected(0, (N*N)+2-1);
	}

}
