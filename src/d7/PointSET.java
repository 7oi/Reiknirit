package d7;


/****************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:    
 *  Dependencies:
 *  Author:
 *  Date:
 *
 *  Data structure for maintaining a set of 2-D points, 
 *    including rectangle and nearest-neighbor queries
 *
 *************************************************************************/

import java.util.Arrays;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;

public class PointSET {
	
	private SET<Point2D> set;
	
    // construct an empty set of points
    public PointSET() {
    	// ...thusly:
    	set = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
    	// SET has an isEmpty() function that calls TreeSet's isEmpty()
        return set.isEmpty();	// isEmption!!!!
    }

    // number of points in the set
    public int size() {
        return set.size();		// We return the size...
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
    	set.add(p);		// What better way to add p to the set?
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
    	// Lets check:
        return set.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
    	// Simple. Loop and draw.
    	for (Point2D p : set) {
    		p.draw();
    	}
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
    	// Let's make an iterable object to store the points in.
    	Stack<Point2D> s = new Stack<Point2D>();  // A Stack will do
    	// Loop through the set
    	for (Point2D p : set) {
    		// Check if any points are in the rect
    		if (rect.contains(p)) {
    			s.push(p);		// If so, push the point to the stack
    		}
    	}
        return s;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
    	// Make a point
    	Point2D n = null;
    	// First check if the set is empty
    	if (!set.isEmpty()) {
    		// If not, loop through the set
    		for (Point2D poi : set) {
    			// If it's the first point in the set
    			if (n == null) {
    				n = poi;
    			}
    			// If the distance is less from p in the new point
    			else if (poi.distanceTo(p) < n.distanceTo(p)) {
    				n = poi;
    			}
    		}
    	}
        return n;
    }

    public static void main(String[] args) {
        In in = new In();
        Out out = new Out();
        int nrOfRecangles = in.readInt();
        int nrOfPointsCont = in.readInt();
        int nrOfPointsNear = in.readInt();
        RectHV[] rectangles = new RectHV[nrOfRecangles];
        Point2D[] pointsCont = new Point2D[nrOfPointsCont];
        Point2D[] pointsNear = new Point2D[nrOfPointsNear];
        for (int i = 0; i < nrOfRecangles; i++) {
            rectangles[i] = new RectHV(in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsCont; i++) {
            pointsCont[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsNear; i++) {
            pointsNear[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        PointSET set = new PointSET();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble(), y = in.readDouble();
            set.insert(new Point2D(x, y));
        }
        for (int i = 0; i < nrOfRecangles; i++) {
            // Query on rectangle i, sort the result, and print
            Iterable<Point2D> ptset = set.range(rectangles[i]);
            int ptcount = 0;
            for (Point2D p : ptset)
                ptcount++;
            Point2D[] ptarr = new Point2D[ptcount];
            int j = 0;
            for (Point2D p : ptset) {
                ptarr[j] = p;
                j++;
            }
            Arrays.sort(ptarr);
            out.println("Inside rectangle " + (i + 1) + ":");
            for (j = 0; j < ptcount; j++)
                out.println(ptarr[j]);
        }
        out.println("Contain test:");
        for (int i = 0; i < nrOfPointsCont; i++) {
            out.println((i + 1) + ": " + set.contains(pointsCont[i]));
        }

        out.println("Nearest test:");
        for (int i = 0; i < nrOfPointsNear; i++) {
            out.println((i + 1) + ": " + set.nearest(pointsNear[i]));
        }

        out.println();
    }

}
