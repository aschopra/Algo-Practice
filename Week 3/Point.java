import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x; // x-coordinate of this point
    private final int y; // y-coordinate of this point
    
    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
 	   double numerator = that.y - y;
 	   double denominator = that.x - x;
 	   double slope = 0;
 	   // Case 1: Degenerate line segment -- 2 points are the same
 	   if (numerator == 0 && denominator == 0) {
 		   slope = Double.NEGATIVE_INFINITY;
 	   } else if (numerator == 0) {
 		   // Case 2: horizontal line segment- slope-> 0
 		   slope = 0;
 	   } else if (denominator == 0) {
 		   // Case 3: vertical line segment- slope-> +inf
 		   slope = Double.POSITIVE_INFINITY;
 	   } else {
 		   slope = numerator/denominator;
 	   }
 	   return slope;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
 	   if (this.y < that.y) {
		   return -1;
	   } else if (this.y > that.y) {
		   return 1;
	   } else if (this.y == that.y) {
		   if (this.x < that.x) {
			   return -1;
		   } else if (this.x > that.x) {
			   return 1;
		   }
	   }
 	  return 0; // case where x-coord and y-coord of 2 points equal
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    
    private class SlopeOrder implements Comparator<Point> {
    	public int compare(Point p1, Point p2) {
    		double p1Slope = slopeTo(p1);
    		double p2Slope = slopeTo(p2);
    		if (p1Slope < p2Slope) {
    			return -1;
    		} else if (p1Slope > p2Slope) {
    			return 1;
    		} else {
    			return 0;
    		}
    	}
    }
    
    public Comparator<Point> slopeOrder() {
    	return new SlopeOrder();
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
//    public static void main(String[] args) {
//        /* YOUR CODE HERE */
//    }
}

