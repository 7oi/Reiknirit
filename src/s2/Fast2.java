package s2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import edu.princeton.cs.introcs.In;

public class Fast2 {
	
	// This helper function checks if an ArrayList<Point> contains a subset of 2 or more points
	// in a list of points.
	public static boolean checkContain (ArrayList<ArrayList<Point>> arrList, ArrayList<Point> pList) {
		for (ArrayList<Point> a: arrList) {
			int count = 0;
			for (Point p: a) {
				if(pList.contains(p)) {
					count++;
				}
			}
			if (count >= 2) {
				return true;
			}
		}
		return false;
	}
	
	// Helper function to clean up the code that takes care of the printing
	public static void printArr (ArrayList<Point> pointArr) {
		for (Point poi: pointArr) {
			System.out.print(poi.toString());
			if (pointArr.indexOf(poi) < pointArr.size() - 1) {
				System.out.print(" -> ");
			}
			else{
				System.out.println("");
			}
		}
	}
	
	public static void fastOrder (Point p[]) {
		// Make an ArrayList to hold the ArrayList that hold the Points
		ArrayList<ArrayList<Point>> bloop = new ArrayList<ArrayList<Point>>();
		// Sort p[]
		Arrays.sort(p);
		// Make new temp array from p[]
		Point temp[] = p.clone();
		// Loop
		for (int i = 0; i < temp.length; i++) {
			// Start by sorting the array by SLOPE_ORDER
			Arrays.sort(temp, p[i].SLOPE_ORDER);
			// Inner loop
			for (int j = 0; j < temp.length; j++) {
				// Make a counter
				int count = 1;
				// Count number of points with same slope towards p[i]
				while ((j+count < temp.length) && (p[i].SLOPE_ORDER.compare(temp[j], temp[j+count]) == 0)) {
					count++;
				}
				// Only if they're more than 3 do we need to do the following
				if (count >= 3) {
					// Make a new list of points
					ArrayList<Point> pointArr = new ArrayList<Point>();
					int x = j;
					// Add the points
					pointArr.add(p[i]);
					pointArr.addAll(Arrays.asList(Arrays.copyOfRange(temp, x, x+count)));
					// Sort them
					Collections.sort(pointArr);
					// Check if any subset of it is in the array containing all of the lists of points
					if (!checkContain(bloop, pointArr)) {
						// If not, add it
						bloop.add(pointArr);
						// ...and print it
						printArr(pointArr);
					}
				}
			}			
		}		
	}
	
	public static void main(String[] args) {
        /*
         * Do not modify
         */
        In in = new In();
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt(), y = in.readInt();
            points[i] = new Point(x, y);
        }
        fastOrder(points);
    }
}