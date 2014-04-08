package s4;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.introcs.In;
public final class SAPDeluxe {
	private final Digraph d;
	private int root;
	
	// constructor takes a digraph (not necessarily a DAG)
	public SAPDeluxe(Digraph G) {
		Digraph temp = new Digraph(G);
		// First check for a cycle
		DirectedCycle dc = new DirectedCycle(temp);
		if (dc.hasCycle()) {
			throw new IllegalArgumentException("Graph is not acyclic");
		}
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
		d = temp;
	}
	
	// Just a simple helper function to help return the right anchor
	private int compareAncs (int ancv, int ancw, int comp) {
		if (comp < 0) {
			return ancv;
		}
		if (comp == 0) {
			return Math.max(ancv, ancw);
		}
		return ancw;
	}
	
	// Another helper function to clean up the code
	// It takes in an Iterable (can take null) and two BFS's and
	// calculates both the ancestor and the distance and returns
	// them as an int[]
	private int[] getAncDist(Iterable<Integer> it, BreadthFirstDirectedPaths bfv, 
			BreadthFirstDirectedPaths bfw){
		int[] ret = new int[2];
		int counter = 0;
		int dist = Integer.MAX_VALUE;
		int anc = -1;
		// Check paths to vertices in iterable
		if (it != null) {
			for (int i : it) {
				int currDist = bfv.distTo(i) + bfw.distTo(i);
				if (bfv.hasPathTo(i) && bfw.hasPathTo(i)) {
					dist = Math.min(dist, currDist);
					if (dist <= currDist) {
						anc = i;	
					}
					counter++;
				}
			}
		}
		// If none found, check path to root from both
		if (counter == 0) {
			int distv = Integer.MAX_VALUE, distw = Integer.MAX_VALUE;
			int ancv = -1, ancw = -1;
			for (int i : bfv.pathTo(root)) {
				if (bfv.hasPathTo(i) && bfw.hasPathTo(i)) {
					distv = Math.min(dist, (bfv.distTo(i) + bfw.distTo(i)));
					ancv = i;
					break;
				}
			}
			for (int i : bfw.pathTo(root)) {
				if (bfv.hasPathTo(i) && bfw.hasPathTo(i)) {
					distw = Math.min(dist, (bfv.distTo(i) + bfw.distTo(i)));
					ancw = i;
					break;
				}
			}
			dist = Math.min(distv, distw);
			int comp = Integer.compare(distv, distw);
			anc = compareAncs(ancv, ancw, comp);
		}
		ret[0] = anc;
		
		ret[1] = dist;
		return ret;
	}
	
	// length of shortest ancestral path between v and w; -1 if no
	// such path
	public int length(int v, int w) {
		// Use BFS for v and w		
		BreadthFirstDirectedPaths bfv = new BreadthFirstDirectedPaths(d, v);
		BreadthFirstDirectedPaths bfw = new BreadthFirstDirectedPaths(d, w);
		return getAncDist(null, bfv, bfw)[1];
	}
	
	// a common ancestor of v and w that participates in a shortest
	// ancestral path; -1 if no such path
	public int ancestor(int v, int w) {
		// Use BFS for v and w
		BreadthFirstDirectedPaths bfv = new BreadthFirstDirectedPaths(d, v);
		BreadthFirstDirectedPaths bfw = new BreadthFirstDirectedPaths(d, w);
		return getAncDist(null, bfv, bfw)[0];
	}
	
	// length of shortest ancestral path between any vertex in v
	// and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		// Use BFS for v and w
		BreadthFirstDirectedPaths bfv = new BreadthFirstDirectedPaths(d, v);
		BreadthFirstDirectedPaths bfw = new BreadthFirstDirectedPaths(d, w);
		return Math.min(getAncDist(v, bfv, bfw)[1], getAncDist(w, bfv, bfw)[1]);
	}
	
	// a common ancestor that participates in shortest ancestral
	// path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		// Use BFS for v and w
		BreadthFirstDirectedPaths bfv = new BreadthFirstDirectedPaths(d, v);
		BreadthFirstDirectedPaths bfw = new BreadthFirstDirectedPaths(d, w);
		int[] ancv = getAncDist(v, bfv, bfw);
		int[] ancw = getAncDist(w, bfv, bfw);
		int comp = Integer.compare(ancv[1], ancw[1]);
		return compareAncs(ancv[0], ancw[0], comp);
	}
	
	// do unit testing of this class
	public static void main(String[] args) {
		In in = new In("wordnet/digraph2.txt");
		Digraph d = new Digraph(in);
		try {
			SAPDeluxe s = new SAPDeluxe(d);
			//System.out.println(s.length(1, 5));
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 6; j++) {
					int anc = s.ancestor(i, j);
					System.out.println("s.ancestor(" + i + ", " + j + ")=" + anc + " len=" + s.length(i, j));
				}
			}
			Bag<Integer> b1 = new Bag<Integer>();
			Bag<Integer> b2 = new Bag<Integer>();
			b1.add(0);
			b1.add(1);
			b2.add(2);
			b2.add(3);
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
