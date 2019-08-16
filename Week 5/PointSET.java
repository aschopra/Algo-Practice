import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
	private final SET<Point2D> s;
	
	// construct an empty set of points 
   public PointSET() {
	   s = new SET<>();
   }
   
   // is the set empty?
   public boolean isEmpty() {
	   return s.isEmpty();
   }
   
   // number of points in the set 
   public int size() {
	   return s.size();
   }
   
   // add the point to the set (if it is not already in the set)
   public void insert(Point2D p) {
	   if (p == null) {
		   throw new IllegalArgumentException();
	   }
	   s.add(p);
   }
   
   // does the set contain point p?
   public boolean contains(Point2D p) {
	   if (p == null) {
		   throw new IllegalArgumentException();
	   }
	   return s.contains(p);
   }
   
   // draw all points to standard draw 
   public void draw() {
	   for (Point2D p: s) {
		   p.draw();
	   }
   }
   
   // all points that are inside the rectangle (or on the boundary) 
   public Iterable<Point2D> range(RectHV rect) {
	   if (rect == null) {
		   throw new IllegalArgumentException();
	   }
	   ArrayList<Point2D> rangePoints = new ArrayList<>();
	   for (Point2D p: s) {
		   if (rect.contains(p)) {
			   rangePoints.add(p);
		   }
	   }
	   return rangePoints;
   }
   
   // a nearest neighbor in the set to point p; null if the set is empty 
   public Point2D nearest(Point2D p) {
	   if (p == null) {
		   throw new IllegalArgumentException();
	   }
	   if (!s.isEmpty()) {
		   Point2D closestNeighbor = s.min(); // need to initialize with something
		   for (Point2D setPoint: s) {
			   double newDistance = setPoint.distanceSquaredTo(p);
			   double oldDistance = closestNeighbor.distanceSquaredTo(p);
			   if (newDistance < oldDistance) {
				   closestNeighbor = setPoint;
			   }
		   }
		   return closestNeighbor;
	   }
	   return null;
   }
}