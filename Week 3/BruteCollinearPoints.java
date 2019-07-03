import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private final LineSegment [] segmentArray;
	
	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points) {
		// Error-checking
		if (points == null) {
			throw new IllegalArgumentException("points array cannot be null");
		}
		checkNullAndRepeats(points);
		
		Point[] pointsArrCopy = Arrays.copyOf(points, points.length);
		ArrayList<LineSegment> segmentList = new ArrayList<>();
		
		for (int i = 0; i < pointsArrCopy.length; i++) {
			Point p = pointsArrCopy[i];
			for (int j = i + 1; j < pointsArrCopy.length; j++) {
				Point q = pointsArrCopy[j];
				for (int k = j + 1; k < pointsArrCopy.length; k++) {
					Point r = pointsArrCopy[k];
					for (int m = k + 1; m < pointsArrCopy.length; m++) {
						Point s = pointsArrCopy[m];
						// check if all points are collinear--have same slope with p
						if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(s) == p.slopeTo(q)) {
							Point [] orderedPoints = {p, q, r, s}; // array of unordered points at first
							Arrays.sort(orderedPoints); // sort to figure out 2 end points of line segment
							LineSegment ls = new LineSegment(orderedPoints[0], orderedPoints[3]);
							segmentList.add(ls); // add to arrayList
						}
					}
				}
			}
		}
		segmentArray = segmentList.toArray(new LineSegment[segmentList.size()]); // copy over vals from segment list to arr
	}
	
	private void checkNullAndRepeats(Point [] points) {
		for (int i = 0; i < points.length; i++) {
			// check for null points in array
			if (points[i] == null) {
				throw new IllegalArgumentException("error: null point found in array");
			}
			for (int j = i + 1; j < points.length; j++) {
				if (points[j] == null) {
					throw new IllegalArgumentException("error: null point found in array");
				}
				// check for repeat points in the array
				if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
					throw new IllegalArgumentException("error: same point appears in array more than once");
				}
			}
		}
	}
	
	 // the number of line segments
	public int numberOfSegments() {
		return segmentArray.length;
	}
	
	// the line segments
	public LineSegment[] segments() {
		// return a copy of the member var --> Defensive copying
		return Arrays.copyOf(segmentArray, segmentArray.length);
	}
}
