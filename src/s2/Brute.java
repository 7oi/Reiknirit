package s2;

import java.util.Arrays;

import edu.princeton.cs.introcs.In;

public class Brute {

	public static boolean collCheck (Point p, Point q, Point r, Point s) {
		return p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s);
	}
	
	public static void collinear (Point p[]) {
		int n = p.length;
		Arrays.sort(p);
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				for (int k = j + 1; k < n; k++) {
					for (int l = k + 1; l < n; l++) {
						if (collCheck(p[i], p[j], p[k], p[l])) {
							System.out.println(p[i].toString() + " -> " + 
									p[j].toString() + " -> " + 
									p[k].toString() + " -> " + 
									p[l].toString());
						}
					}
				}
			}
		}
	}
	
	
	
	public static void main(String[] args) {
        /*
         * Do not modify
         */
        In in = new In("testinput/input10.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt(), y = in.readInt();
            points[i] = new Point(x, y);
        }
        collinear(points);
    }
}

