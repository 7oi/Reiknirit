package StorSkil1;

import java.util.Arrays;


import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.introcs.StdOut;


public class DoublingRatio {

	public static void timeTrial(int N, int T, int runs) {
		// Create the connection array
		Connection[] conn;
		int numAlgs = 5;
		// Now to make these double[] to store previous numbers and totalTimes.
		double prev[] = new double[numAlgs];
		double totalTime[] = new double[numAlgs];
		Arrays.fill(prev, 0.0);
		Arrays.fill(totalTime, 0.0);
		
		StdOut.printf("%-11s", " T");
		
		StdOut.printf("|%-22s|%-22s|%-22s|%-22s|%-22s", 
		"     QuickFindUF", "     QuickUnionUF", "     WeightedQU", "     HeightedQU", "     WQUPC");
		StdOut.printf("%n %10s", " ");
		for (int i = 0; i < numAlgs; i++) {
			StdOut.printf("|%10s %8s %2s", "msec", "ratio", " ");
		}

		for (int M = N, x = 0; x < runs; M += M, x++) {	// Doubling loop
			
			/*---------------------------SETUP---------------------------*/
			
			// Set totalTime array items to 0.0
			Arrays.fill(totalTime, 0.0);
			// Fill the array with M connections, doubled in each loop
			int MbyM = M * M;
			conn = RandomConnections.genGrid(M);
			
			// ... and to make a timer
			Stopwatch timer;
			
			// Now for the tests
			
			/*---------------------------TESTS---------------------------*/
			
			// QuickFindUF
			/*QuickFindUF qf = new QuickFindUF(MbyM);
			for (int i = 0; i < T; i++) {				// Try it T times
				timer = new Stopwatch();	// Start the timer
				for (int j = 0; j < conn.length - 1; j++)	{		
					qf.union(conn[j].p(), conn[j].q());	// Connect
				}
				totalTime[0] += timer.elapsedTime()*1000;	// Add up the times
			}
			
			// QuickUnionUF
			QuickUnionUF quf = new QuickUnionUF(MbyM);
			for (int i = 0; i < T; i++) {				// Try it T times
				timer = new Stopwatch();	// Start the timer
				for (int j = 0; j < conn.length - 1; j++)	{		
					quf.union(conn[j].p(), conn[j].q());	// Connect
				}
				totalTime[1] += timer.elapsedTime()*1000;	// Add up the times
			}*/
			
			// WeightedQuickUnionUF
			WeightedQuickUnionUF wquf = new WeightedQuickUnionUF(MbyM);
			for (int i = 0; i < T; i++) {				// Try it T times
				timer = new Stopwatch();	// Start the timer
				for (int j = 0; j < conn.length - 1; j++)	{		
					wquf.union(conn[j].p(), conn[j].q());	// Connect
				}
				totalTime[2] += timer.elapsedTime()*1000;	// Add up the times
			}
			
			// HeightedQU			
			HeightedQU hqu = new HeightedQU(MbyM);
			for (int i = 0; i < T; i++) {				// Try it T times
				timer = new Stopwatch();	// Start the timer
				for (int j = 0; j < conn.length - 1; j++)	{		
					hqu.union(conn[j].p(), conn[j].q());	// Connect
				}
				totalTime[3] += timer.elapsedTime()*1000;	// Add up the times
			}
			
			// WQUPC
			WQUPC wqupc = new WQUPC(MbyM);
			for (int i = 0; i < T; i++) {				// Try it T times
				timer = new Stopwatch();	// Start the timer
				for (int j = 0; j < conn.length - 1; j++)	{		
					wqupc.union(conn[j].p(), conn[j].q());	// Connect
				}
				totalTime[4] += timer.elapsedTime()*1000;	// Add up the times
			}
			
			/*---------------------------RESULT---------------------------*/
			
			
			StdOut.printf("%n %-10d", M);
            // Now for the after-processing and printing of the results
			for (int i = 0; i < numAlgs; i++) {
				totalTime[i] = (totalTime[i])/T;	// Take the average
				if (x == 0)
					StdOut.printf("|%10.3f %8.3f %2s", totalTime[i], 0.0, " ");
				else
					StdOut.printf("|%10.3f %8.3f %2s", totalTime[i], totalTime[i]/prev[i], " ");
				prev[i] = totalTime[i];	
			}
		}
		
    }
	
	public static void main(String[] args) {
		timeTrial(10, 10, 10);
	}

}
