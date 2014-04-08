package s4;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.introcs.In;
public class SAPBrute {
	private Digraph d;
	private int root;
	
	// constructor takes a digraph (not necessarily a DAG)
	public SAPBrute(Digraph G) {
		Digraph temp = new Digraph(G);
		DirectedCycle dc = new DirectedCycle(temp);
		
		if (dc.hasCycle()) {
			throw new IllegalArgumentException("Graph is not acyclic");
		}
		int c = 0;
		for (int i = 0; i < temp.V(); i++) {
			if (!temp.adj(i).iterator().hasNext()) {
				c++;
				root = i;
			}
			if (c > 1) {
				throw new IllegalArgumentException("Graph is not rooted");
			}
		}
		d = temp;
	}
	
	public int getRoot() {
		return root;
	}
	// length of shortest ancestral path between v and w; -1 if no
	// such path
	public int length(int v, int w) {
		// Use BFS for v and w
		BreadthFirstDirectedPaths bfv = new BreadthFirstDirectedPaths(d, v);
		BreadthFirstDirectedPaths bfw = new BreadthFirstDirectedPaths(d, w);
		int result = -1;
		// Loop through all vertices
		for(int i = 0; i < d.V(); i++) {
			int newDist = bfv.distTo(i) + bfw.distTo(i);
			// If both BFS's have paths to the same vertex...
			if (bfv.hasPathTo(i) && bfw.hasPathTo(i)) {
				// Save the distance
				if (result == -1) {		// First occurrence
					result = newDist;
				}
				else {
					result = Math.min(result, newDist);
				}
			}
		}
		return result;
	}
	// a common ancestor of v and w that participates in a shortest
	// ancestral path; -1 if no such path
	public int ancestor(int v, int w) {
		// Use BFS for v and w
		BreadthFirstDirectedPaths bfv = new BreadthFirstDirectedPaths(d, v);
		BreadthFirstDirectedPaths bfw = new BreadthFirstDirectedPaths(d, w);
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
		return anc;
	}
	// length of shortest ancestral path between any vertex in v
	// and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		// Use BFS for v and w
		BreadthFirstDirectedPaths bfv = new BreadthFirstDirectedPaths(d, v);
		BreadthFirstDirectedPaths bfw = new BreadthFirstDirectedPaths(d, w);
		int result = -1;
		// Loop through all vertices
		for(int i = 0; i < d.V(); i++) {
			int newDist = bfv.distTo(i) + bfw.distTo(i);
			// If both BFS's have paths to the same vertex...
			if (bfv.hasPathTo(i) && bfw.hasPathTo(i)) {
				// Save the distance
				if (result == -1) {		// First occurrence
					result = newDist;
				}
				else {
					result = Math.min(result, newDist);
				}
			}
		}
		return result;
	}
	// a common ancestor that participates in shortest ancestral
	// path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		// Use BFS for v and w
		BreadthFirstDirectedPaths bfv = new BreadthFirstDirectedPaths(d, v);
		BreadthFirstDirectedPaths bfw = new BreadthFirstDirectedPaths(d, w);
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
		return anc;
	}
	// do unit testing of this class
	public static void main(String[] args) {
		In in = new In("wordnet/digraph2.txt");
		Digraph d = new Digraph(in);
		try {
			SAPBrute s = new SAPBrute(d);
			System.out.println(s.length(1, 4));
			System.out.println(s.ancestor(1, 4));
		}
		catch (IllegalArgumentException ie) {
			System.out.println(ie);
		}
		catch (IndexOutOfBoundsException io) {
			System.out.println(io);
		}
		
	}
}
