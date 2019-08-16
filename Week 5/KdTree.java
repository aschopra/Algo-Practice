import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	private static class Node {
		private final Point2D data;
		private Node left;
		private Node right;
		private final boolean orientation;
		private RectHV rect;
		
		public Node(Point2D data, boolean dir) {
			this.data = data;
			this.left = null;
			this.right = null;
			orientation = dir;
		}
	}
	private Node root;
	private int size;
	
	// construct an empty set of points 
   public KdTree() {
	   root = null;
	   size = 0;
   }
   
   // is the set empty?
   public boolean isEmpty() {
	   return size == 0;
   }
   
   // number of points in the set 
   public int size() {
	   int localCopy = size;
	   return localCopy;
   }
   
   private Node insertHelper(Node r, Point2D point, boolean orientation, Node parent, char dir) {
	   if (r == null) {
		   Node n = new Node(point, orientation);
		   if (!orientation) {
			   // horizontal
			   if (dir == 'r') {
				   n.rect = new RectHV(parent.data.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax());
			   } else {
				   n.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.data.x(), parent.rect.ymax());
			   }
		   } else {
			   // vertical
			   if (dir == 'r') {
				   n.rect = new RectHV(parent.rect.xmin(), parent.data.y(), parent.rect.xmax(), parent.rect.ymax());
			   } else {
				   n.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(), parent.data.y());
			   }
		   }
		   return n;
	   }
	   double cmp;
	   if (orientation) {
		// vertical orientation means compare x-coords
		   cmp = point.x() - r.data.x();
	   } else {
		   // horizontal orientation means compare y-coords
		   cmp = point.y() - r.data.y();
	   }
	   if (cmp < 0) {
		   // go left
		   r.left = insertHelper(r.left, point, !orientation, r, 'l');
	   } else {
		   r.right = insertHelper(r.right, point, !orientation, r, 'r');
	   }
	   return r;
   }
   
   // add the point to the set (if it is not already in the set)
   public void insert(Point2D p) {
	   if (p == null) {
		   throw new IllegalArgumentException();
	   }
	   // true orientation means vertical and false means horizontal
	   if (size == 0) {
		   Node n = new Node(p, true);
		   n.rect = new RectHV(0, 0, 1, 1);
		   root = n;
		   size++;
	   } else if (!this.contains(p)) {
		   root = insertHelper(root, p, root.orientation, null, 'l');
		   size++;
	   }
   }
      
   // does the set contain point p?
   public boolean contains(Point2D p) {
	   if (p == null) {
		   throw new IllegalArgumentException();
	   }
	   Node x = root;
	   while (x != null) {
		   double cmp;
		   if (x.orientation) {
			  cmp = p.x() - x.data.x();
		   } else {
			   cmp = p.y() - x.data.y();
		   }
		   
		   if (cmp < 0) {
			   // go left
			   x = x.left;
		   } else {
			   // check if search hit
			   if (x.data.equals(p)) {
				   return true;
			   }
			   // otherwise, go right
			   x = x.right;
		   }
	   }
	   return false;
   }
   
   private void drawHelper(Node r) {
	   if (r == null) {
		   return;
	   }
	   // first draw the root
	   if (r.orientation) {
		   StdDraw.setPenColor(StdDraw.BLACK);
		   StdDraw.setPenRadius(0.01);
           StdDraw.point(r.data.x(), r.data.y());
           StdDraw.setPenRadius(0.005);
           StdDraw.setPenColor(StdDraw.RED);
           StdDraw.line(r.data.x(), r.rect.ymin(), r.data.x(), r.rect.ymax());
	   } else {
		   StdDraw.setPenColor(StdDraw.BLACK);
		   StdDraw.setPenRadius(0.01);
           StdDraw.point(r.data.x(), r.data.y());
           StdDraw.setPenRadius(0.005);
           StdDraw.setPenColor(StdDraw.BLUE);
           StdDraw.line(r.rect.xmin(), r.data.y(), r.rect.xmax(), r.data.y());
	   }
       // now recurse to left subtree
       drawHelper(r.left);
       // now recurse to right subtree
       drawHelper(r.right);
   }
   // draw all points to standard draw 
   public void draw() {
	   drawHelper(root);
   }
   
   private void rangeHelper(Node r, RectHV rect, ArrayList<Point2D> rangePoints) {
	   if (r == null) {
		   return;
	   }
	   if (rect.contains(r.data)) {
		   rangePoints.add(r.data);
	   }
	   // vertical --> compare x-coords to determine where to search
	   if (r.orientation) {
		   if (rect.xmin() < r.data.x() && rect.xmax() < r.data.x()) {
			   // recurse on left-subtree
			   rangeHelper(r.left, rect, rangePoints);
		   } else if (rect.xmin() > r.data.x() && rect.xmax() > r.data.x()) {
			   // recurse on right-subtree
			   rangeHelper(r.right, rect, rangePoints);
		   } else {
			   // recurse on both subtrees if rectangle intersects splitting line through point
			   rangeHelper(r.left, rect, rangePoints);
			   rangeHelper(r.right, rect, rangePoints);
		   }
	   } else {
		   // horizontal --> compare y-coords to determine where to search
		   if (rect.ymin() < r.data.y() && rect.ymax() < r.data.y()) {
			   // recurse on left-subtree
			   rangeHelper(r.left, rect, rangePoints);
		   } else if (rect.ymin() > r.data.y() && rect.ymax() > r.data.y()) {
			   // recurse on right-subtree
			   rangeHelper(r.right, rect, rangePoints);
		   } else {
			   // recurse on both subtrees if rectangle intersects splitting line through point
			   rangeHelper(r.left, rect, rangePoints);
			   rangeHelper(r.right, rect, rangePoints);
		   }
	   }
   }
   
   // all points that are inside the rectangle (or on the boundary) 
   public Iterable<Point2D> range(RectHV rect) {
	   if (rect == null) {
		   throw new IllegalArgumentException();
	   }
	   ArrayList<Point2D> rangePoints = new ArrayList<>();
	   rangeHelper(root, rect, rangePoints);
	   return rangePoints;
   }
   
   private Point2D nearestHelper(Node r, Point2D p, Point2D currNearest, double currDistance) {
	   if (r == null) {
		   return currNearest;
	   }
	   double distanceToQuery = r.data.distanceSquaredTo(p);
	   if (distanceToQuery < currDistance) {
		   currNearest = r.data;
		   currDistance = distanceToQuery;
	   }
	   double cmp;
	   Point2D temp;
	   if (r.orientation) {
		   // vertical line so compare x-coords
		   cmp = p.x() - r.data.x();
	   } else {
		   // horizontal line so compare y-coords
		   cmp = p.y() - r.data.y();
	   }
	   if (cmp < 0) {
		   // query point to left/below splitting line so go to left subtree first
		   temp = nearestHelper(r.left, p, currNearest, currDistance);
		   // closest point could be somewhere else in right subtree; if not, prune
		   if (r.right != null && temp.distanceSquaredTo(p) > r.right.rect.distanceSquaredTo(p)) {
			   temp = nearestHelper(r.right, p, temp, temp.distanceSquaredTo(p));
		   }
	   } else {
		   // query point to right/above splitting line so go to right subtree first
		   temp = nearestHelper(r.right, p, currNearest, currDistance);
		   if (r.left != null && temp.distanceSquaredTo(p) > r.left.rect.distanceSquaredTo(p)) {
			   temp = nearestHelper(r.left, p, temp, temp.distanceSquaredTo(p));
		   }
	   }
	   return temp;
   }
   
   // a nearest neighbor in the set to point p; null if the set is empty 
   public Point2D nearest(Point2D p) {
	   if (p == null) {
		   throw new IllegalArgumentException();
	   }
	   Point2D nearestNeighbor = null;
	   double currDistance = 0;
	   if (!isEmpty()) {
		   nearestNeighbor = root.data;
		   currDistance = root.data.distanceSquaredTo(p);
		   nearestNeighbor = nearestHelper(root, p, nearestNeighbor, currDistance);
	   }
	   return nearestNeighbor;
   }	
}
