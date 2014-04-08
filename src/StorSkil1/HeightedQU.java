package StorSkil1;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;

public class HeightedQU {
	private int[] id;    // id[i] = parent of i
    private int[] ht;    // sz[i] = number of objects in subtree rooted at i
    private int count;   // number of components

    // Create an empty union find data structure with N isolated sets.
    public HeightedQU(int N) {
        count = N;
        id = new int[N];
        ht = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            ht[i] = 0;
        }
    }

    // Return the number of disjoint sets.
    public int count() {
        return count;
    }

    // Return component identifier for component containing p
    public int find(int p) {
        while (p != id[p])
            p = id[p];
        return p;
    }

   // Are objects p and q in the same set?
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

  
   // Replace sets containing p and q with their union.
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;

        // Now to maintain the height of the trees
        if (ht[i] == ht[j]) {
        	id[i] = j; ht[j] += 1; 
        }
        else if (ht[i] < ht[j]) { 
        	id[i] = j;
        }
        else { 
        	id[j] = i; 
        }
        count--;
    }


    public static void main(String[] args) {
        int N = StdIn.readInt();
        HeightedQU uf = new HeightedQU(N);

        // read in a sequence of pairs of integers (each in the range 0 to N-1),
         // calling find() for each pair: If the members of the pair are not already
        // call union() and print the pair.
        while (uf.count > 1) {
            int p = StdRandom.uniform(N);
            int q = StdRandom.uniform(N);
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }
}
