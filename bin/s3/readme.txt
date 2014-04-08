/**********************************************************************
 *  readme.txt template                                                   
 *  Kd-tree
**********************************************************************/

Name: J�hann Fri�geir J�hannsson   
Login: johannj12  
Section instructor:
RU/Mooshak login names: johannj12

Partner name: G�sli R�nar Gu�mundsson
Partner login: gislig10   
Partner section instructor:
Partner RU/Mooshak login names: gislig10

/**********************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 **********************************************************************/

The Node (named KdNode) is as follows:

 private class KdNode {
        private Point2D point;           // sorted by key
        private RectHV rect;
        private KdNode left, right;  // left and right subtrees

        public KdNode(Point2D p, RectHV r) {
            this.point = p;
            this.rect = r;
        }
    }

We store in it: 
	1. Point2d point: The point, which acts as the key of the node
	2. RectHV rect: A rectangle, using RectHV, as to streamline the process
	3. KdNode left, right: Pointers to the children
We didn't find it necessary to store the orientation of the node, as the
functions can completely take care of that.

/**********************************************************************
 *  Describe your method for range search in a kd-tree.
 **********************************************************************/

The range search function was fairly easy to make, because we store each
node's rectangle within it. Therefor we can use the RectHV function
intersects(RectHV that) to decide which way to traverse down the tree.
If the node's rectangle intesects the rectangle which we are searching
for, then we recurse further down the tree until we stop finding
intersecting triangles. On the way back up the recursion we check if the
search rectangle contains the points of the nodes. If so, we push them
on the stack we created to store the points, which we finally return.
The base case within the recursive function is of course if the current
node is null, in which case we just return.

/**********************************************************************
 *  Describe your method for nearest neighbor search in a kd-tree.
 **********************************************************************/
Again, due to storing each node's rectangle, this turned out to be
fairly straight forward. We start by checking if the tree is empty.
If not, we start the recursive function by sending in the root node.
In the recursive function we start by checking if the current node is
null, and if so we return the nearest point so far.
Next we measure the distance from the point to the current node's
point against the distance from the point to the nearest point so far.
If the distance to the current node is smaller, it will become the
nearest point.
Then we measure the distance from the point to the current node's 
rectangle against the distance from the point to the nearest point.
If that turns out to be smaller we continue the recursion, as there 
might be points further down the tree tha are smaller. If the distance
is greater, though, there's no chance there's a point closer than the
current nearest point so there's no need to recurse further.
Finally we just return the nearest point.

/**********************************************************************
 *  Give the total memory usage in bytes (using tilde notation and 
 *  the standard 64-bit memory cost model) of your 2d-tree data
 *  structure as a function of the number of points N. Justify your
 *  answer below.
 *
 *  Include the memory for all referenced objects (deep memory),
 *  including memory for the nodes, points, and rectangles.
 **********************************************************************/

bytes per Point2D: 32 bytes
2 * 64-bit double = 16 bytes
overhead = 16 bytes

bytes per RectHV: 48 bytes
4 * 64-bit double = 256 bit = 32 bytes
overhead = 16 bytes
total: 32 + 16 = 48 bytes

bytes per KdNode: 100 bytes
1 * Point2d = 32 bytes
1 * RectHV = 48 bytes
static overhead = 8 bytes
total: 32 + 48 + 8 = 88 bytes

bytes per KdTree of N points (using tilde notation):   ~88N
[include the memory for any referenced Node, Point2D and RectHV objects]


/**********************************************************************
 *  Give the expected running time in seconds (using tilde notation)
 *  to build a 2d-tree on N random points in the unit square.
 *  Use empirical evidence by creating a table of different values of N
 *  and the timing results. (Do not count the time to generate the N 
 *  points or to read them in from standard input.)
 **********************************************************************/
Measured running time on Toshiba L850 laptop, running Ubuntu 13.04 (i3wm), 
Processor 2.53 GHz Intel Core 2 Duo
Memory 4 GB 1067 MHz DDR3
      brute force	2-d
 N  | time (ms)		time (ms)
100k| 845			915
1M  | 5781			5919


/**********************************************************************
 *  How many nearest neighbour calculations can your brute-force
 *  implementation perform per second for input100K.txt (100,000 points)
 *  and input1M.txt (1 million points), where the query points are
 *  random points in the unit square? Explain how you determined the
 *  operations per second. (Do not count the time to read in the points
 *  or to build the 2d-tree.)
 *
 *  Repeat the question but with the 2d-tree implementation.
 **********************************************************************/
Measured running time on Macbook Pro, late 2009 aluminum, OSX 10.9, 
Processor 2.4 GHz Intel Core i7 Quad Core
Memory 16 GB 1366 MHz DDR3

The times are for 100 randomly generated points

      brute force	2d-tree
 N  | time (ms)		time (ms)		
100k| 240			12
1M  | 7474			18		

100/t = calls per second

                     calls to nearest() per second
                    	brute force		2d-tree	
input100K.txt			416.666...		8333.333...
input1M.txt				13.38			5555.555...			



/**********************************************************************
 *  Known bugs / limitations.
 **********************************************************************/
None that we know of. Well, except for working with huge trees. That
will be slow, but should work, given that there is enough memory on the
machine.

/**********************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and d�mat�mar, but do
 *  include any help from people (including course staff, 
 *  classmates, and friends) and attribute them by name.
 **********************************************************************/
We had some discussions with our classmates Steind�r Helgi and 
Eyj�lfur, but mostly the work was our own and without assistance. 

/**********************************************************************
 *  Describe any serious problems you encountered.                    
 **********************************************************************/
Of course there were frustrating times. Firstly, while trying to add
rectangles to our nodes, there were problems with constructing them
properly (a lot of invalid rectangles were hurt during the making of 
this project). The biggest problems were finding the right orientation
and figuring out all the puzzling conditionals. Once we constructed a
special function for that, though, it all got a bit easier.
Then came the normal, frustrating brain-freezes while trying to figure 
out how to recurse properly and figuring out the best way to do the
nearest point search. 

/**********************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **********************************************************************/
In one sentence: We both worked diligently at our own versions of KdTree 
and then combined our efforts to put it together. 


/**********************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 **********************************************************************/
The assignment provided us with a good and fast way of detecting nearest 
neighbours and solid insight into its implementation.
