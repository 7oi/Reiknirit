package d5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * <h1>An Interval representation in java</h1>
 * 
 * <p>
 * In mathematics, an interval is a set of real numbers with the property that
 * any number that lies between two numbers in the set is also included in the
 * set. For example, the set of all numbers x satisfying 0 ≤ x ≤ 5 is an
 * interval which contains 0 and 5 as well as all numbers between them.
 * 
 * 
 * <p>
 * Open <a href="https://mooshak.ru.is/~reir/">Mooshak contest D5</a>.
 * 
 * <p>
 * <em>Tip 1. Try hovering over classes, methods and variables with your mouse
 * in Eclipse to see this text displayed in html</em>
 * 
 * <p>
 * <em>Tip 2. Try clicking on the Mooshak link here above, Eclipse has a built in browser</em>
 * 
 * <p>
 * Contains an Interval scheduling method, see {@link #schedule(Interval[])}
 */
public class Interval {
    /**
     * Starting position and length of this interval
     */
    private int start, length;

    /**
     * Construct an interval
     * 
     * @param start
     *            Start point
     * @param length
     *            Length of interval > 1. Gets assigned to 1 if given length is
     *            negative or zero
     */
    public Interval(int start, int length) {
        length = (length <= 0) ? 1 : length;
        this.start = start;
        this.length = length;
    }

    /**
     * Compare two intervals by their start position
     * 
     * <p>
     * Ties are broken by length
     */
    public final Comparator<Interval> BY_START = new ByStart();

    /**
     * Compare two intervals by their distance from this interval.
     * 
     * <p>
     * Order two intervals <code>a</code> and <code>b</code> according to the
     * distance from their midpoints to the midpoint of the interval
     * <code>t</code>.
     * 
     * <p>
     * Example: <code>
     * Interval t = new Interval(0,2),
                a = new Interval(0, 1),
                b = new Interval(1,2);
     * </code>
     * <ul>
     * <li>t.DISTANCE_ORDER.compare(a, b) = -1</li>
     * <li>t.DISTANCE_ORDER.compare(a, a) = 0</li>
     * <li>t.DISTANCE_ORDER.compare(b, a) = 1</li>
     * </ul>
     * 
     * <p>
     * Ties between two distinct intervals are broken firstly by start position
     * and secondly by length.
     */
    public final Comparator<Interval> DISTANCE_ORDER = new DistanceOrder();
    
    public final Comparator<Interval> BY_END = new ByEnd();

    private class DistanceOrder implements Comparator<Interval> {
        @Override
        public int compare(Interval a, Interval b) {
            double compA = Math.abs(a.getMid() - getMid());
            double compB = Math.abs(b.getMid() - getMid());
            int compared = new Double(compA).compareTo(compB);
            if (compared == 0) {
            	return BY_START.compare(a, b);
            }
            else {
            	return compared;
            }
        }
    }

    private class ByStart implements Comparator<Interval> {
        @Override
        public int compare(Interval a, Interval b) {
        	int comp = new Integer(a.getStart()).compareTo(b.getStart());
        	
        	if (comp == 0) {
        		return new Integer(a.getLength()).compareTo(b.getLength());
        	}
        	else {
        		return comp;
        	}
        }
    }
    
    private class ByEnd implements Comparator<Interval> {
        @Override
        public int compare(Interval a, Interval b) {
        	int comp = new Integer(a.getEnd()).compareTo(b.getEnd());
        	
        	if (comp == 0) {
        		return new Integer(a.getLength()).compareTo(b.getLength());
        	}
        	else {
        		return comp;
        	}
        }
    }

    /**
     * @return Start position of this interval
     */
    public int getStart() {
        // Returns start position of interval
        return this.start;
    }

    /**
     * @return Length of this interval
     */
    public int getLength() {
        // Returns length of interval
        return this.length;
    }

    /**
     * @return End position of this interval
     */
    public int getEnd() {
        // Returns end value of interval
        return this.start + this.length;
    }

    /**
     * @return Middle position of this interval
     */
    public double getMid() {
        // Returns middle value of interval
        return (this.getStart() + this.getEnd() - 1) / 2.0;
    }

    /**
     * <h3>Interval scheduling</h3>
     * 
     * <p>
     * You have a resource — it may be a lecture room, a supercomputer, or an
     * electron microscope — and many people request to use the resource for
     * periods of time. A request takes the form: Can I reserve the resource
     * starting at time <code>start</code>, until time <code>end</code>? We will
     * assume that the resource can be used by at most one person at a time. A
     * scheduler wants to accept a subset of these requests, rejecting all
     * others, so that the accepted requests do not overlap in time. The goal is
     * to maximize the number of requests accepted.
     * 
     * <p>
     * We have a set of requests {1, 2, . . . , n}; the i-th request corresponds
     * to an interval of time starting at <code>i.getStart()</code> and
     * finishing at <code>i.getEnd()</code> We'll say that a subset of the
     * requests is <code>compatible</code> if no two of them overlap in time,
     * and our goal is to accept as large a compatible subset as possible.
     * 
     * <p>
     * <em> Your goal is to return a compatible set of intervals of maximum size </em>
     * 
     * <p>
     * Hint. You might have to create a new comparator...
     * 
     * @param intervals
     *            Intervals to schedule
     * @return Compatible set of intervals of maximum size
     */
    public static Interval[] schedule(Interval[] intervals) {
        // TODO: Implement this
    	Arrays.sort(intervals, new Interval(0, 1).BY_END);
        ArrayList<Interval> intervalList = new ArrayList<Interval>(Arrays.asList(intervals));
        for (int i = 0; i < intervals.length; i++) {
        	int j = i + 1;
           	while ((j < intervals.length) && intervalList.contains(intervals[i]) 
           			&& (intervals[i].overlapping(intervals[j]))) {
           		if(intervalList.contains(intervals[j])){
           			intervalList.remove(intervals[j]);          			
           		}
           		j++;
           	}  	
        }
    	
        return intervalList.toArray(new Interval[intervalList.size()]);
    }

    /**
     * Test if this interval overlaps another interval <code>that</code>
     * 
     * <p>
     * Two intervals are overlapping if they share a common interval. An
     * interval that ends at position <code>a</code> does <em>not</em> overlap
     * another interval that starts at position <code>a</code>.
     * 
     * <p>
     * Some examples
     * 
     * <ul>
     * <li>Interval(0,1) and Interval(0,2) overlap</li>
     * <li>Interval(0,1) and Interval(1,1) do not overlap</li>
     * <li>Interval(1,2) and Interval(1,2) overlap</li>
     * <li>Interval(1,2) and Interval(2,2) overlap</li>
     * <li>Interval(1,2) and Interval(3,2) do not overlap</li>
     * </ul>
     * 
     * @param that
     *            Interval to check if overlapping
     * @return True if intervals overlap, false otherwise
     */
    public boolean overlapping(Interval that) {
    	/** 
    	 * We have to take into account that where one ends, the other one starts
    	 * so if "this's" interval's end is the same as "that's" interval's start, 
    	 * they're not overlapping.
    	*/
    	
    	// If this's start overlaps that's end
    	if ((this.getStart() < that.getEnd()) && (this.getStart() >= that.getStart())) {
    		return true;
    	}
    	// If this's end overlaps that's start
    	if ((this.getEnd() > that.getStart()) && (this.getEnd() <= that.getEnd())){
    		return true;
    	}
    	return false;
    }

    /**
     * String representation of this Interval.
     * 
     * <p>
     * Prints out <code>start</code> nr. of spaces " " followed by
     * <code>length</code> number of dashes "-".
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getEnd(); i++) {
            if (i < start)
                sb.append(" ");
            else
                sb.append("-");
        }
        return sb.toString();
    }

    /**
     * Print out an array of intervals.
     * 
     * <p>
     * Useful for debugging
     * 
     * @param intervals
     *            Intervals to print out
     */
    public static void printIntervals(Interval[] intervals) {
        int k = (int) Math.ceil(Math.log10(intervals.length));
        int i = 0;
        for (Interval interval : intervals) {
            System.out.printf("%-" + k + "d", i++);
            System.out.println(": " + interval);
        }
    }

    /**
     * Example usage of interval class
     * 
     * <p>
     * Useful for testing
     */
    public static void main(String[] args) {
        Interval[] intervals = new Interval[] {
                new Interval(10, 5),
                new Interval(8, 1), // -2 gets changed to 1
                new Interval(3, 5), 
                new Interval(7, 6), 
                new Interval(3, 5) /*,
                new Interval(7, 6), 
                new Interval(16, 13), 
                new Interval(6, 7),
                new Interval(9, 6), 
                new Interval(6, 15), 
                new Interval(5, 11)*/ };
        System.out.println("Array of intervals");
        printIntervals(intervals);
        printIntervals(schedule(intervals));
        //System.out.println(intervals[5].overlapping(intervals[2]));
    }
}
