import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double [] vals; //tracks the mean calculated on each trial
	private int N;
	private int T;
	private double mean;
	private double stddev;
	private double lowConfidence;
	private double hiConfidence;
	
	public PercolationStats(int n, int trials) {
		if(n <= 0 || trials <= 0) {
			throw new IllegalArgumentException("invalid n and trials entered");
		}
		N = n;
		T = trials;
		mean = 0;
		stddev = 0;
		lowConfidence = 0;
		hiConfidence = 0;
		vals = new double [T];
		for(int i = 0; i < T; i++) {
			Percolation p = new Percolation(N);
			while(!p.percolates()) {
				int row = StdRandom.uniform(1, N+1); //endpt is exclusive
				int col = StdRandom.uniform(1, N+1);
				if(!p.isOpen(row, col)) {
					p.open(row, col);
				}
			}
			double pThreshold = (double) p.numberOfOpenSites()/(N*N);
			vals[i] = pThreshold;
		}
	}
	
	 public double mean()  {
		 mean = StdStats.mean(vals);
		 return mean;
	 }
	 
	 public double stddev() {
		 if(T == 1) {
			 return Double.NaN;
		 }
		 stddev = StdStats.stddev(vals);
		 return stddev;
	 }
	 
	 public double confidenceLo() {
		 lowConfidence = mean - ((1.96 * stddev)/(Math.sqrt(T)));
		 return lowConfidence;
	 }
	 
	 public double confidenceHi() {
		 hiConfidence = mean + ((1.96 * stddev)/(Math.sqrt(T)));
		 return hiConfidence;
	 }
	
	public static void main(String[] args) {
		int N = StdIn.readInt();
		int T = StdIn.readInt();
		PercolationStats ps = new PercolationStats(N, T);
		StdOut.println("mean = " + ps.mean());
		StdOut.println("stddev = " + ps.stddev());
		StdOut.println("95% confidence interval = " + "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
		
	}
}
