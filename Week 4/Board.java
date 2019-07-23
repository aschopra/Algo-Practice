import java.util.ArrayList;

public class Board {
	private int[][] board;
	private final int dimension;
	
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
    	if (tiles == null) {
    		throw new IllegalArgumentException();
    	}
    	board = new int[tiles.length][tiles[0].length];
    	dimension = tiles.length; // gets row dimension but row dim == col dim
    	for (int i = 0; i < tiles.length; i++) {
    		for (int j = 0; j < tiles.length; j++) {
    			board[i][j] = tiles[i][j];
    		}
    	}
    }
    
	// helper function for creating the neighbors list
	private Board swapTiles(int zeroRow, int zeroCol, int newRow, int newCol) {
		Board b = new Board(this.board);
		int swapVal = b.board[zeroRow][zeroCol];
		b.board[zeroRow][zeroCol] = b.board[newRow][newCol];
		b.board[newRow][newCol] = swapVal;
		return b;
	}
                                           
    // string representation of this board
    public String toString() {
    	StringBuilder result = new StringBuilder(); // speeds up concatenation
    	result.append(dimension + "\n");
    	for (int i = 0; i < dimension; i++) {
    		for (int j = 0; j < dimension; j++) {
    			result.append(board[i][j] + " ");
    		}
    		result.append("\n");
    	}
    	return result.toString();
    }

    // board dimension n
    public int dimension() {
    	return dimension;
    }

    // number of tiles out of place
    public int hamming() {
    	int numTiles = 0;
    	for (int i = 0; i < dimension; i++) {
    		for (int j = 0; j < dimension; j++) {
    			// stop when get to final spot--reserved for blank space
    			if (i == dimension - 1 && j == dimension - 1) {
    				break;
    			}
    			// convert to 1-d array index and add one because treat index 0 as 1
    			if (board[i][j] != (((dimension * i) + j) + 1)) {
    				numTiles++;
    			}
    		}
    	}
    	return numTiles;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
    	int totalDistance = 0;
    	for (int i = 0; i < dimension; i++) {
    		for (int j = 0; j < dimension; j++) {
    			// skip over the blank square
    			if (board[i][j] == 0) {
    				continue;
    			}
    			// check whether tile is misplaced
    			if (board[i][j] != (((dimension * i) + j) + 1)) {
    				int currRow = i; // current row coord of tile
    				int trueRow; // row coord where tile should be
    				if (board[i][j] % dimension == 0) {
    					trueRow = board[i][j] / (dimension + 1); 
    				} else {
    					trueRow = board[i][j] / dimension; // row coord where tile should be
    				}
    				int rowDistance = Math.abs(currRow - trueRow);
    				
    				int currCol = j; // current col coord of tile
    				int trueCol = board[i][j] - (dimension * trueRow) - 1; // column coord where tile should be
    				int colDistance = Math.abs(currCol - trueCol);
    				
    				totalDistance += rowDistance + colDistance; // update total manhattan distance of board
    			}
    		}
    	}
    	return totalDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {    	
    	for (int i = 0; i < dimension; i++) {
    		for (int j = 0; j < dimension; j++) {
    			if (i == dimension - 1 && j == dimension - 1) {
    				if (board[i][j] != 0) {
    					return false;
    				}
    			} else {
    				if (board[i][j] != ((dimension * i) + j) + 1) {
        				return false;
        			}
    			}
    		}
    	}
    	return true;
    }

    // does this board equal y? 
    public boolean equals(Object y) {
    	if (this == y) {
    		return true;
    	}
    	// check for different sizes
    	if (y == null || y.getClass() != this.getClass() || ((Board) y).dimension != dimension) {
    		return false;
    	}
    	// now compare contents of each index
    	for (int i = 0; i < dimension; i++) {
    		for (int j = 0; j < dimension; j++) {
    			if (board[i][j] != ((Board) y).board[i][j]) {
    				return false;
    			}
    		}
    	}
    	return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
    	ArrayList<Board> neighbors = new ArrayList<>();
    	boolean adjacentLeft = false;
    	boolean adjacentRight = false;
    	boolean adjacentUp = false;
    	boolean adjacentDown = false;
    	boolean emptyFound = false;
    	int zeroRow = 0; // store index where 0 is in board
    	int zeroCol = 0;
    	for (int i = 0; i < dimension; i++) {
    		for (int j = 0; j < dimension; j++) {
    			if (board[i][j] == 0) {
    				zeroRow = i;
    				zeroCol = j;
    				// check if adjacent left tile
        			if (j-1 >= 0) {
        				adjacentLeft = true;
        			}
        			// check if adjacent right tile
        			if (j + 1 < dimension) {
        				adjacentRight = true;
        			}
        			// check if adjacent up tile
        			if (i - 1 >= 0) {
        				adjacentUp = true;
        			}
        			// check if adjacent down tile
        			if (i + 1 < dimension) {
        				adjacentDown = true;
        			}
        			emptyFound = true;
        			break;
    			}
    		}
    		if (emptyFound) {
    			break;
    		}
    	}
    	
    	// swap necessary tiles and push new boards to neighbors array list
    	if (adjacentLeft) {
    		neighbors.add(swapTiles(zeroRow, zeroCol, zeroRow, zeroCol-1));
    	}
    	if (adjacentRight) {
    		neighbors.add(swapTiles(zeroRow, zeroCol, zeroRow, zeroCol+1));
    	}
    	if (adjacentUp) {
    		neighbors.add(swapTiles(zeroRow, zeroCol, zeroRow-1, zeroCol));
    	}
    	if (adjacentDown) {
    		neighbors.add(swapTiles(zeroRow, zeroCol, zeroRow+1, zeroCol));
    	}
    	return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
    	Board b = null;
    	boolean madeSwap = false;
    	// exchange a tile with its neighbor on the right as long as both not 0
    	for (int i = 0; i < dimension; i++) {
    		for (int j = 0; j < dimension; j++) {
    			if (board[i][j] != 0 && j + 1 < dimension && board[i][j+1] != 0) {
    				b = swapTiles(i, j, i, j+1);
    				madeSwap = true;
    				break;
    			}
    		}
    		if (madeSwap) {
    			break;
    		}
    	}
    	return b;
    }
}