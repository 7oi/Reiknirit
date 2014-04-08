/**********************************************************************
*  readme.txt template                                                   
*  WordNet
**********************************************************************/

Name: Jóhann Friðgeir Jóhannsson   
Login: johannj12  
Mooshak login: johannj12
Hópur: 4

Partner name:  Eyjolfur Krijstjánsson        
Partner login: eyjolfur12   
Mooshak login: eyolfur12
Hópur: 4


/**********************************************************************
*  Describe concisely the data structure(s) you used to store the 
*  information in synsets.txt. Why did you make this choice?
**********************************************************************/
We used two symbol tables, using the ST class. 

One with string key, and a Bag object for value. We insterted words as keys 
in that table and the synset id of the synset the key word belonged to as items int the bags.
We used that one to look up the synset a word belonged to, to make queries to the SAP datatype.

The other symbol table is inverse, with synset id as key, and a string object with all words in the synset as key.

We chose this structure because it is efficent and suited the data well.
Also it made implementing the functions nouns() and isNoun() quite a breeze! 



/**********************************************************************
*  Describe concisely the data structure(s) you used to store the 
*  information in hypernyms.txt. Why did you make this choice?
**********************************************************************/
We used the data structure SAP, since we had already made it. It so 
happens that it's structure was conveniently suited to storing and
working with the hypernyms. Coincidence?


/**********************************************************************
*  Describe concisely the algorithm you used to check if the digraph 
*  is rooted and the algorithm you used to check if the digrah is a DAG.  
*  What is the order of growth of the best case 
*  running time as a function of the number of vertices V and the 
*  number of edges E in the digraph? And what is the order of growth 
*  of the worst case running time?
*
*  Be careful! It is very easy to get these wrong. Keep in mind
*  what the 'best case' and 'worst case' entail. Don't forget about
*  the fact that starting a breadth first search in Java means 
*  initializing edgeTo[] arrays, etc.
**********************************************************************/

Description:
SAP contains the following:
Digraph d: The digraph.
int root: we keep the root for optimization 
int V and W: also a bit of optimization, to check if we need to make new
BFS's each time.
BreadthFirstDirectedPaths bfw, bfv: since we create them all the time,
why not keep them so we can check if we need to recreate them. Slight 
chance, we know, but a slight chance is still better than nothing.

The SAP constructor accepts a Digraph as an input. We used that Digraph
to create the Digraph for the SAP class.
Then we iterated through all the vertices in the graph, counting only
vertices that have empty adjacency lists. If the count reaches 1, we 
have a root and we save it. If the count goes any higher than 1, the 
graph is not rooted, and therefor we throw an exception.
On the other hand, if this iteration returns no vertex with an empty
adjacency list, the graph is not acyclic, therefor we throw another
exception.
Then we create a DirectedCycle object, using the Digraph as an input,
and call the hasCycle() method to determine if the graph is acyclic
or not, and throw an exception if not acyclic. So if the cycle was
not detected in the loop, we detect it now.

method                               best case            worst case
------------------------------------------------------------------------
rooted check						2 (constant)			V

DAG check							V						V + E



/**********************************************************************
*  Describe concisely your algorithm to compute the shortest ancestral
*  path in SAP.java? What is the order of growth of the worst-case
*  running time of your methods as a function of the number of
*  vertices V and the number of edges E in the digraph? What is the
*  order of growth of the best-case running time?
**********************************************************************/

Description:
We decided to create a helper function that handles these computations.
Since we always needed to use the distances to find the closest 
ancestor we created two functions that return both as an int[]. We
then call those functions in the ancestor() and lenght() functions.
	The first of those functions is findToRoot(), which works with the 
SAP's BFS's. It is meant for the cases where ancestor() and lenght() 
only accept a pair of integers. Mainly it calculates the shortest paths 
from v and w to the root and iterates through both paths, finding common
vertices between both paths until it hits the first common ancestor in 
each path. Then we compare those two and pick the one with the
smaller distance, and if the distances are equal we pick the one with
the higher value. If at least one of the BFS's is the same as in the
previous operation, we don't need to recreate it as we do a comparison
in the beginning of the operation.
	The second of those functions is findBestBrute. It is meant for the
cases where we're dealing with two Iterables of vertices. It also accepts
two BFS's as arguments. It basically just iterates through all vertices
in the graph, finding the best ancestor and the lenght of the path to it. 
We thought that this was the best option, as brutetly forced as it might 
seem. Any other option we could think of would use a BFS for each of
the vertices in the Iterables, which would be really expensive. We tried
to make a more inexpensive method than brute forcing it, but as close
as the function got (the commented out fintBest()) it couldn't go all
the way.


method                               best case            worst case
------------------------------------------------------------------------
length(int v, int w)				 2(constant)		3V + 2E

ancestor(int v, int w)				 2(constant)		3V + 2E

length(Iterable<Integer> v,			 N(3V + 2E)*		N(3V + 2E)*
       Iterable<Integer> w)

ancestor(Iterable<Integer> v,		 N(3V + 2E)*		N(3V + 2E)*
         Iterable<Integer> w)

* Where N is the combined number of Integers in Iterables v and w


/**********************************************************************
*  If you implemented an extra credit optimization describe it here.
**********************************************************************/
Limited 


/**********************************************************************
*  Known bugs / limitations.
**********************************************************************/
Our WordNet worked remarkably well, but we can imagine that on huge
Graphs it could start working more poorly.

/**********************************************************************
*  Describe whatever help (if any) that you received.
*  Don't include readings, lectures, and recitation classes, but do
*  include any help from people (including course staff, lab TAs,
*  classmates, and friends) and attribute them by name.
**********************************************************************/
Some good hints from Geir Matti Jarvela and Arnor Barkarson, lab, TAs.

/**********************************************************************
*  Describe any serious problems you encountered.                    
**********************************************************************/
Figuring out an efficient algorithm for SAP was quite a pickle. We
started off with brute forcing it (and kept one of the functions in the 
final version), but found out that a more efficient algorithm for at 
the case where we were trying to find ancestors of an Iterable of 
vertices was quite difficult and felt always just within reach. But, 
alas, no such luck. We also encountered a few silly errors and had some
arguments with Mooshak about the right output (ex. the ordering in D, 
solved by reversing loops).

/**********************************************************************
*  If you worked with a partner, assert below that you followed
*  the protocol as described on the assignment page. Give one
*  sentence explaining what each of you contributed.
**********************************************************************/
Did most in peer programming, with Johann on the keyboard. Johann 
contributed more to optimising the SAP class. Eyjolfur to the WordNet.



/**********************************************************************
*  List any other comments here. Feel free to provide any feedback   
*  on how much you learned from doing the assignment, and whether    
*  you enjoyed doing it.                                             
**********************************************************************/
