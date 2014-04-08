package D9;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class LongestPath {
	
	 	private static boolean[] marked;  // marked[v] = true if v is reachable from s
	    private static int[] edgeTo;      // edgeTo[v] = last edge on path from s to v
	    
	public static int longest(Digraph G) {
		Topological t = new Topological(G);
		int max = 0;
		for (int v : t.order()) {
			int temp = max;
			marked = new boolean[G.V()];
	        edgeTo = new int[G.V()];
	        max = Math.max(dfs(G, v), temp);
		}
		return max;
	}
	
	private static int dfs(Digraph G, int v) { 
		marked[v] = true;
		if (G.adj(v) == null) {
			return 1;
		}
        int max = 0;
        for (int w : G.adj(v)) {
        	int temp = max;
            if (!marked[w]) {
                edgeTo[w] = v;
                max = dfs(G, w);                
            }
            max = Math.max(max, temp);
        }
        return max;
    }
	
	
	public static void main(String[] args) {
		In in = new In("SomeInputs/tinyDAG.txt");
        Digraph G = new Digraph(in);
        StdOut.print(G.toString());
        StdOut.print(longest(G));
	}
}
