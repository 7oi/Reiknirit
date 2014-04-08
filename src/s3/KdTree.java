package s3;

/*************************************************************************
 *************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdDraw;

public class KdTree {
	private KdNode root;             // root of BST
	private int size;

    private static class KdNode {
        private Point2D point;           // sorted by key
        private RectHV rect;
        private KdNode left, right;  // left and right subtrees

        public KdNode(Point2D p, RectHV r) {
            this.point = p;
            this.rect = r;
        }
    }
    
    public KdTree() {
    	// We create a new KdNode that has no point, but has a RectHV for
    	// the whole area
    	root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }
    
    // Helper function to streamline the comparison process
    private int comparison (Point2D a, Point2D b, boolean u) {
    	int cmp;
    	if (u) {
    		// If it's on x-axis it will return:
        	// -1 for smaller
        	// 1 for bigger
    		cmp = Point2D.X_ORDER.compare(a, b);
    		if (cmp == 0) {
    			cmp = Point2D.Y_ORDER.compare(a, b);
    		}
    	}
    	else {
    		// If it's on y-axis it will return:
        	// -2 for smaller
        	// 2 for bigger
    		cmp = Point2D.Y_ORDER.compare(a, b);
    		if (cmp == 0) {
    			cmp = Point2D.X_ORDER.compare(a, b);
    		}
    		cmp *= 2;
    	}
    	// Always returns 0 for ties...
    	return cmp;
    }
    
    private RectHV makeRect(RectHV r, Point2D p, int cmp) {
    	RectHV tmpR = null;
    	switch (cmp) {
    	case -1:	// Smaller on x-axis
    		tmpR = new RectHV(r.xmin(), r.ymin(), p.x(), r.ymax());
			break;
    	case -2:	// Smaller on y-axis
    		tmpR = new RectHV(r.xmin(), r.ymin(), r.xmax(), p.y());
			break;
    	case 1:		// Bigger on x-axis
    		tmpR = new RectHV(p.x(), r.ymin(), r.xmax(), r.ymax());
			break;
    	case 2:		// Bigger on y-axis
    		tmpR = new RectHV(r.xmin(), p.y(), r.xmax(), r.ymax());
			break;
			// Nothing for ties. 
    	}
    	return tmpR;
    }
    
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
    	// implement recursively
    	// If we have an empty KdTree, we create the first node 
    	// with a rectangle for the whole area
    	if (isEmpty()) {
    		size++;
    		root = new KdNode(p, new RectHV(0.0, 0.0, 1.0, 1.0));
    	}
    	// If KdTree isn't empty, we send in root's rect.
    	else {
    		root = insert(root, p, root.point, true, root.rect, 0);
    	}
    }
    
    private KdNode insert(KdNode n, Point2D p, Point2D q, boolean u, RectHV r, int c) {
    	// If we land on a null node, then add the point to that node
    	if (n == null) {  		
    		size++;
    		return new KdNode(p, makeRect(r, q, c));
    	}
    	// Make a cmp variable
    	int cmp = comparison(p, n.point, u);
    	if (cmp < 0) {
    		n.left = insert(n.left, p, n.point, !u, n.rect, cmp);
    	}
    	else if (cmp > 0) {
    		n.right = insert(n.right, p, n.point, !u, n.rect, cmp);
    	}
		// Nothing for ties. 
    	// Return it
    	return n;
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
    	// Call recursive version
        return contains(root, p, true);
    }
    
    private boolean contains (KdNode n, Point2D p, boolean u) {
    	// If we land on a null node, then it's not there
    	if (n == null) {
    		return false;
    	}
    	// Make a cmp variable
    	int cmp = comparison(p, n.point, u);
    	// So, now we can compare to find the right route down the tree
    	if (cmp < 0) {
    		return contains(n.left, p, !u);
    	}
    	else if (cmp > 0) {
    		return contains(n.right, p, !u);
    	}
    	else {
    		return true;
    	}
    }

    // draw all of the points to standard draw
    public void draw() {
    	// Call recursive draw function
    	draw(root, true);
    }
    
    private void draw(KdNode n, boolean u) {
    	// If we hit a null node, then there's nothing to draw
    	if (n == null) {
    		return;
    	}
//    	StdDraw.setPenColor(StdDraw.BLACK);
//        StdDraw.setPenRadius(.01);
//    	n.point.draw();
    	// If u == true -> vertical line 
    	StdDraw.setPenRadius();
    	if (u) {
    		StdDraw.setPenColor(StdDraw.RED);
    		StdDraw.line(n.point.x(), n.rect.ymin(), n.point.x(), n.rect.ymax());
    	}
    	// u == false -> horizontal line
    	else {
    		StdDraw.setPenColor(StdDraw.BLUE);
    		StdDraw.line(n.rect.xmin(), n.point.y(), n.rect.xmax(), n.point.y());
    	}
    	// Then descend down the tree to draw more.
    	draw(n.left, !u);
    	draw(n.right, !u);
    	return;
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
    	// Let's make an iterable object to store the points in.
    	Stack<Point2D> s = new Stack<Point2D>();  // A Stack will do
    	// Call recursive version
        range(root, rect, s);
        return s;
    }
    
    private void range(KdNode n, RectHV rect, Stack<Point2D> s) {
    	// If the node here is null, then just return the Stack without changes
    	if (n == null) {
    		return;
    	}
    	// If we can traverse to the left and it intersects the rectangle
    	if (n.rect.intersects(rect)) {
			// ...then keep on going!
    		range(n.left, rect, s);
    		range(n.right, rect, s);
		}
    	if (rect.contains(n.point)) {
    		s.push(n.point);
    	}
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
    	if (isEmpty()) {
    		return null;
    	}
    	// Call recursive version
    	return nearest(root, p, root.point);
    }

    private Point2D nearest(KdNode n, Point2D p, Point2D np) {
    	// TODO: implement recursively
    	if (n == null) {
    		return np;
    	}
    	if (p.distanceSquaredTo(n.point) < p.distanceSquaredTo(np)) {
    		np = n.point;
    	}
    	// Check if point nearest on line for n.point is closer than np    	
    	if (n.rect.distanceSquaredTo(p) < p.distanceSquaredTo(np)) {
			np = nearest(n.right, p, np);
			np = nearest(n.left, p, np);
		}
    	return np;
    }
    
    /*******************************************************************************
     * Test client
     ******************************************************************************/
    public static void main(String[] args) {
    	String filename = "SomeInputs/SomeInputs/input100K.txt";
        In in = new In(filename);
        Out out = new Out();
        System.out.println("working on it ..");
        Stopwatch timer = new Stopwatch();
        KdTree set = new KdTree();
        //PointSET set = new PointSET();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble(), y = in.readDouble();
            set.insert(new Point2D(x, y));
            if (i%20000 == 0) {
            	System.out.print("*");
            }
        }
        System.out.print("|| \n");
        System.out.println("time to build " + timer.elapsedTime()*1000);
        StdDraw.setCanvasSize(1024, 768);
        set.draw();
        StdDraw.show(50);
/*
        Point2D[] testpoints = new Point2D[100];
        
        for (int i = 0; i < 100; i++) {
        	double x = Math.random();
            double y = Math.random();
        	testpoints[i] = new Point2D(x, y);
            //out.println((i + 1) + ": " + ));
        }
        timer = new Stopwatch();
        out.println("Nearest test:");
        for (int i = 0; i < 100; i++) {
        	set.nearest(testpoints[i]);
        	if (i%2 == 0) {
            	System.out.print("*");
            }
            //out.println((i + 1) + ": " + ));
        }
        System.out.print("|| \n");
        double timeNear = timer.elapsedTime();
        System.out.println("Calls for nearest " + 100/timeNear + " calls p/sec");
        System.out.println("Total time: " + timeNear * 1000 + " msec");
  */      
        
        
        out.println();
    }	
}
