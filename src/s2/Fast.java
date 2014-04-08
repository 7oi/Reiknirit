package s2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import edu.princeton.cs.introcs.In;

public class Fast {
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
	
	public static void fastOrder (Point p[]) {
		// Make an ArrayList to hold the ArrayList that hold the Points
		ArrayList<ArrayList<Point>> bloop = new ArrayList<ArrayList<Point>>();
		// Sort p[]
		Arrays.sort(p);
		// Make new temp array from p[]
		Point temp[] = p.clone();
		// Loop
		for (int i = 0; i < temp.length; i++) {
			Arrays.sort(temp, p[i].SLOPE_ORDER);
			for (int j = 0; j < temp.length; j++) {
				
				int count = 1;
				while ((j+count < temp.length) && (p[i].SLOPE_ORDER.compare(temp[j], temp[j+count]) == 0)) {
					count++;
				}
				if (count >= 3) {
					ArrayList<Point> pointArr = new ArrayList<Point>();
					int x = j;
					pointArr.add(p[i]);
					for (; x < j + count; x++) {
						pointArr.add(temp[x]);
					}
					
					Collections.sort(pointArr);
					if (!checkContain(bloop, pointArr)/*!bloop.containsAll(pointArr)*/) {
						bloop.add(pointArr);
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
				}
			}			
		}		
	}
	
	public static void fastOrder_v1 (Point p[]) {
		// Make an ArrayList to hold the ArrayList that hold the Points
		ArrayList<ArrayList<Point>> bloop = new ArrayList<ArrayList<Point>>();
		// Sort p[]
		Arrays.sort(p);
		// Make new temp array from p[]
		Point temp[] = p.clone();
		
		// Loop
		for (int i = 0; i < p.length; i++) {
			// Sort the temp array by slope order
			Arrays.sort(temp, p[i].SLOPE_ORDER);
			// Inner loop
			for (int j = 0; j < temp.length; j++) {
				// Make new ArrayList of Points
				ArrayList<Point> pointArr = new ArrayList<Point>();
				// Add the first point
				pointArr.add(temp[i]);
				int k = j;
				while ((k < temp.length) && (temp[i].slopeTo(temp[j]) == temp[i].slopeTo(temp[k]))) {
					pointArr.add(temp[k]);
					k++;
				}
				Collections.sort(pointArr);
				if ((pointArr.size() > 3) && !bloop.contains(pointArr)) {
					bloop.add(pointArr);
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
			}
			
		}
		
		Collections.sort(bloop, new Comparator<ArrayList<Point>>() {
			public int compare (ArrayList<Point> thisPoint, ArrayList<Point> thatPoint) {
				return thisPoint.get(0).compareTo(thatPoint.get(0));
			}
		});
		
		for (ArrayList<Point> point: bloop) {
			for (Point poi: point) {
				System.out.print(poi.toString());
				if (point.indexOf(poi) < point.size() - 1) {
					System.out.print(" -> ");
				}
				else{
					System.out.println("");
				}
			}
		}
	}
	
	public static void main(String[] args) {
        /*
         * Do not modify
         */
        In in = new In("testinput/inputMoo29.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt(), y = in.readInt();
            points[i] = new Point(x, y);
        }
        fastOrder(points);
    }
}