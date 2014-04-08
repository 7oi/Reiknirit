package StorSkil1;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class RandomConnections {
	
	static Connection[] genConnections(int N) {
		
		WeightedQuickUnionUF quickie = new WeightedQuickUnionUF(N);
		Connection[] connArr = new Connection[N];
		while (quickie.count() > 1) {	
			// Make two random numbers for p and q
			int p = StdRandom.uniform(N);
			int q = StdRandom.uniform(N);
			// Check if they're connected in quickie
			if (quickie.connected(p, q)) { continue; }	// If so, start over
			// If not connected, connect!
			quickie.union(p, q);
			// ...and add to connArr
			connArr[quickie.count() - 1] = new Connection(p, q);
		}
		return connArr;		
	}
	
	static Connection[] genGrid(int M) {
		int MbyM = M*M;
		Connection[] connArr = new Connection[MbyM];
		WeightedQuickUnionUF quickie = new WeightedQuickUnionUF(MbyM);
		
		// Generate random connections and check whether they are legal
		while (quickie.count() > 1) { // Check if the grid is fully connected
			int p = StdRandom.uniform(MbyM);
			int q = StdRandom.uniform(MbyM);
			
			int[] coords = findCoords(p, M);
			int rowP = coords[0];
			int colP = coords[1];
			coords = findCoords(q, M);
			int rowQ = coords[0];
			int colQ = coords[1];

			// checks if the random connection is legal
			if ( (rowP == rowQ && Math.abs(colP - colQ) == 1)
					|| (colP == colQ && Math.abs(rowP - rowQ) == 1) ) {
				if (quickie.connected(p, q)) { continue; }	// If so, start over
				quickie.union(p, q);
				connArr[MbyM - quickie.count()-1] = new Connection(p, q);
			}
		}

		return connArr;
	}

	// Find the coordinates for the given item n, in an array of size M by M
	private static int[] findCoords(int n, int M){
		int[] coors = new int[2];
		coors[0] = (int)Math.ceil(n/M) - 1;
		coors[1] = (n % M) - 1;
		return coors;
	}

}