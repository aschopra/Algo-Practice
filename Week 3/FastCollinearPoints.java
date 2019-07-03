import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
	private final LineSegment [] segmentArray;
	
	// finds all line segments containing 4 or more points
	public FastCollinearPoints(Point[] points) {
		// Error-checking
		if (points == null) {
			throw new IllegalArgumentException("points array cannot be null");
		}
		checkNullAndRepeats(points);
		ArrayList<LineSegment> segmentList = new ArrayList<>();
		// algorithm to find collinear points
		Point [] sortedPointsArr = points.clone();
		Arrays.sort(sortedPointsArr);
		for (int i = 0; i < sortedPointsArr.length; i++) {
			Point origin = sortedPointsArr[i];
			Point [] sortedSlopeArr = sortedPointsArr.clone();
			// sort with respect to origin point's slope
			Arrays.sort(sortedSlopeArr, origin.slopeOrder()); 
			int adjacentPointCounter = 1;
			Point segmentStart = null;
			for (int j = 0; j < sortedSlopeArr.length-1; j++) {
				if (origin.slopeTo(sortedSlopeArr[j]) == origin.slopeTo(sortedSlopeArr[j+1])) {
					adjacentPointCounter++;
					// check if need to set start of line segment
					if (adjacentPointCounter == 2) {
						segmentStart = sortedSlopeArr[j];
						adjacentPointCounter++;
					} else if (adjacentPointCounter >= 4 && j+1 == sortedSlopeArr.length-1) {
						if (segmentStart.compareTo(origin) > 0) {
							LineSegment ls = new LineSegment(origin, sortedSlopeArr[j+1]);
							segmentList.add(ls);
						}
						adjacentPointCounter = 1;
					}
				} else if (adjacentPointCounter >= 4) {
					if (segmentStart.compareTo(origin) > 0) {
						LineSegment ls = new LineSegment(origin, sortedSlopeArr[j]);
						segmentList.add(ls);
					}
					adjacentPointCounter = 1;
				} else {
					adjacentPointCounter = 1;
				}
			}			
		}
		segmentArray = segmentList.toArray(new LineSegment[segmentList.size()]);
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
		return Arrays.copyOf(segmentArray, segmentArray.length);
	}
}
