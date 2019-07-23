import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
	private final MinPQ<SearchNode> pq;
	private final MinPQ<SearchNode> pqTwin;
	private final Board initial;
	
	private class SearchNode {
		private final Board board;
		private final int moves;
		private final SearchNode prev;
		private final int manhattanVal; // save manhattan val to avoid recomputing
		
		public SearchNode(Board b, int moves, SearchNode prev) {
			board = b;
			this.moves = moves;
			this.prev = prev;
			manhattanVal = b.manhattan();
		}
	}
	
	private class SearchNodeOrder implements Comparator<SearchNode> {
		public int compare(SearchNode a, SearchNode b) {
			int aPriority = a.manhattanVal + a.moves;
			int bPriority = b.manhattanVal + b.moves;
			if (aPriority < bPriority) {
				return -1;
			}
			if (aPriority > bPriority) {
				return 1;
			}
			// if have same overall priority, compare manhattan values
			if (a.manhattanVal < b.manhattanVal) {
				return -1;
			}
			if (a.manhattanVal > b.manhattanVal) {
				return 1;
			}
			return 0;
		}
	}
	
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	if (initial == null) {
    		throw new IllegalArgumentException();
    	}
    	this.initial = initial;
    	pq = new MinPQ<SearchNode>(new SearchNodeOrder());
    	pqTwin = new MinPQ<SearchNode>(new SearchNodeOrder());
    	
    	// create root node of initial board
    	SearchNode node = new SearchNode(initial, 0, null);
    	pq.insert(node);
    	// twin pq should be initialized as initial board with 2 tiles swapped
    	SearchNode twinNode = new SearchNode(initial.twin(), 0, null);
    	pqTwin.insert(twinNode);
  
    	while (!pq.min().board.isGoal() && !pqTwin.min().board.isGoal()) {
    		SearchNode min = pq.delMin();
    		SearchNode twinMin = pqTwin.delMin();
    		// prune the neighbors list, then add neighbors to pq
    		for (Board neighbor: min.board.neighbors()) {
    			// in first move, no repeat neighbors
    			if (min.moves == 0) {
    				pq.insert(new SearchNode(neighbor, min.moves + 1, min));
    			} else if (!neighbor.equals(min.prev.board)) {
    				// make sure not pushing repeat board states to priority queue
    				pq.insert(new SearchNode(neighbor, min.moves + 1, min));
    			}
    		}
    		// do same for twin's neighbor list
    		for (Board twinNeighbor: twinMin.board.neighbors()) {
    			if (twinMin.moves == 0) {
    				pqTwin.insert(new SearchNode(twinNeighbor, twinMin.moves + 1, twinMin));
    			} else if (!twinNeighbor.equals(twinMin.prev.board)) {
    				pqTwin.insert(new SearchNode(twinNeighbor, twinMin.moves + 1, twinMin));
    			}
    		}
    	}
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
    	if (pq.min().board.isGoal()) {
    		return true;
    	}
    	if (pqTwin.min().board.isGoal()) {
    		return false;
    	}
    	return false;
    }

    // min number of moves to solve initial board
    public int moves() {
    	if (!isSolvable()) {
    		return -1;
    	}
    	return pq.min().moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
    	if (!isSolvable()) {
    		return null;
    	}
    	Stack<Board> s = new Stack<>();
    	// push boards in reverse order
    	SearchNode end = pq.min();
    	while (end.prev != null) {
    		s.push(end.board);
    		end = end.prev;
    	}
    	// push the initial state of the board
    	s.push(initial);
    	return s;
    }
}
