package s4;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.introcs.In;
public final class SAP {
	private final Digraph d;
	private int root, V, W;
	private BreadthFirstDirectedPaths bfv, bfw;
	
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		Digraph temp = new Digraph(G);
		// Now we check if graph is rooted
		int c = 0;
		for (int i = 0; i < temp.V(); i++) {
			if (!temp.adj(i).iterator().hasNext()) {
				c++;
				root = i;	// Found the root. Lets keep it.
				// Note: if another root is found we throw an exception.
			}
			if (c > 1) {
				throw new IllegalArgumentException("Graph is not rooted");
			}
		}
		if (c == 0) {
			throw new IllegalArgumentException("Graph is not acyclic");
		}
		DirectedCycle dc = new DirectedCycle(G);
		if (dc.hasCycle()) {
			throw new IllegalArgumentException("Graph is not acyclic");
		}
		d = temp;
		V = -1;
		W = -1;
	}
	
	// Just a simple helper function to help return the right anchor
	private int compareAncs (int ancv, int ancw, int distv, int distw) {
		int comp = Integer.compare(distv, distw);
		if (comp < 0) {
			return ancv;
		}
		if (comp == 0) {
			return Math.max(ancv, ancw);
		}
		return ancw;
	}
	
	// Another helper function to clean up the code
	// It takes in an Iterable and two BFS's and
	// calculates both the ancestor and the distance and returns
	// them as an int[]. We won't be using it this time.
	/*
	private int[] getAncNDist(Iterable<Integer> v, Iterable<Integer> w, 
			BreadthFirstDirectedPaths bfv, BreadthFirstDirectedPaths bfw){
		int[] arrI = findBest(v, w, bfv, bfw);
		int[] arrR = findToRoot(bfv, bfw);		
		
		return new int[] {compareAncs(arrI[0], arrR[0], arrI[1], arrR[1]), 
				Math.min(arrI[1], arrR[1])};
	}*/
	
	// Finds the closest ancestor and distance to it from two points
	private int[] findToRoot() {
		int distv = Integer.MAX_VALUE, distw = Integer.MAX_VALUE;
		int ancv = -1, ancw = -1;
		for (int i : bfv.pathTo(root)) {
			if (bfv.hasPathTo(i) && bfw.hasPathTo(i)) {
				distv = Math.min(distv, (bfv.distTo(i) + bfw.distTo(i)));
				ancv = i;
				break;
			}
		}
		for (int i : bfw.pathTo(root)) {
			if (bfv.hasPathTo(i) && bfw.hasPathTo(i)) {
				distw = Math.min(distw, (bfv.distTo(i) + bfw.distTo(i)));
				ancw = i;
				break;
			}
		}
		
		return new int[] {compareAncs(ancv, ancw, distv, distw), Math.min(distv, distw)};
	}
	
	// Brute version of finding the best ancestor and distance to it
	// when receiving an Iterable of integers.
	private int[] findBestBrute() {
		int anc = -1;
		int distance = -1;
		// Loop through all vertices
		for(int i = 0; i < d.V(); i++) {
			// If both BFS's have paths to the same vertex...
			if (bfv.hasPathTo(i) && bfw.hasPathTo(i)) {
				// Save the distance
				int newDist = bfv.distTo(i) + bfw.distTo(i);
				if (distance == -1) {		// First occurrence
					distance = newDist;
					anc = i;
				}
				if (distance >= newDist) {
					distance = newDist;
					anc = i;
				}
			}
		}
		return new int[] {anc, distance};
	}
	
	// Finds the best ancestor and distance to it given an Iterable of vertices
	// We gave up on it, as we didn't figure out a way to do it properly without
	// making it into a monster to run (in a bad way, not the cool way)
	/*
	private int[] findBest(Iterable<Integer> v, Iterable<Integer> w, BreadthFirstDirectedPaths bfv, 
			BreadthFirstDirectedPaths bfw) {
		int[] ret = new int[2];
		int distV = Integer.MAX_VALUE;
		int ancV = -1;
		int distW = Integer.MAX_VALUE;
		int ancW = -1;
		for (int i : v) {
			int currDist = bfv.distTo(i) + bfw.distTo(i);
			if (bfv.hasPathTo(i) && bfw.hasPathTo(i)) {
				distV = Math.min(distV, currDist);
				if (distV <= currDist) {
					ancV = i;	
				}
			}
		}
		for (int i : w) {
			int currDist = bfv.distTo(i) + bfw.distTo(i);
			if (bfv.hasPathTo(i) && bfw.hasPathTo(i)) {
				distW = Math.min(distW, currDist);
				if (distW <= currDist) {
					ancW = i;	
				}
			}
		}
		ret[0] = compareAncs(ancV, ancW, distV, distW);
		ret[1] = Math.min(distV, distW);
		return ret;
	}*/
	
	// length of shortest ancestral path between v and w; -1 if no
	// such path
	public int length(int v, int w) {
		// Use BFS for v and w	
		// Just a little bit of optimization, if it so happens that
		// we land on the same vertices
		if (V != v) {
			bfv = new BreadthFirstDirectedPaths(d, v);
			V = v;
		}
		if (W != w) {
			bfw = new BreadthFirstDirectedPaths(d, w);
			W = w;
		}
		return findToRoot()[1];
	}
	
	// a common ancestor of v and w that participates in a shortest
	// ancestral path; -1 if no such path
	public int ancestor(int v, int w) {
		// Use BFS for v and w	
		// Just a little bit of optimization, if it so happens that
		// we land on the same vertices
		if (V != v) {
			bfv = new BreadthFirstDirectedPaths(d, v);
			V = v;
		}
		if (W != w) {
			bfw = new BreadthFirstDirectedPaths(d, w);
			W = w;
		}
		return findToRoot()[0];
	}
	
	// length of shortest ancestral path between any vertex in v
	// and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		// Use BFS for v and w
		bfv = new BreadthFirstDirectedPaths(d, v);
		bfw = new BreadthFirstDirectedPaths(d, w);
		return findBestBrute()[1];
	}
	
	// a common ancestor that participates in shortest ancestral
	// path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		// Use BFS for v and w
		bfv = new BreadthFirstDirectedPaths(d, v);
		bfw = new BreadthFirstDirectedPaths(d, w);
		return findBestBrute()[0];
	}
	
	// do unit testing of this class
	public static void main(String[] args) {
		In in = new In("wordnet/digraph6.txt");
		Digraph d = new Digraph(in);
		try {
			SAP s = new SAP(d);
			//System.out.println(s.length(1, 5));
//			for (int i = 0; i < 6; i++) {
//				for (int j = 0; j < 6; j++) {
//					int anc = s.ancestor(i, j);
//					System.out.println("s.ancestor(" + i + ", " + j + ")=" + anc + " len=" + s.length(i, j));
//				}
//			}
			Bag<Integer> b1 = new Bag<Integer>();
			Bag<Integer> b2 = new Bag<Integer>();
			b1.add(3);
			b1.add(4);
			b2.add(5);
			b2.add(6);
			System.out.println(s.ancestor(b1, b2) + " " + s.length(b1, b2));
			
		}
		catch (IllegalArgumentException ie) {
			System.out.println(ie);
		}
		catch (IndexOutOfBoundsException io) {
			System.out.println(io);
		}
		
	}
}
