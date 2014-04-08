/**********************************************************************
 *  readme.txt template                                                   
 *  WordNet
**********************************************************************/

Name:    
Login:   
Precept: 

Partner name:     
Partner login:    
Partner precept:  


/**********************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in synsets.txt. Why did you make this choice?
 **********************************************************************/



/**********************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in hypernyms.txt. Why did you make this choice?
 **********************************************************************/



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


method                               best case            worst case
------------------------------------------------------------------------
rooted check

DAG check



/**********************************************************************
 *  Describe concisely your algorithm to compute the shortest ancestral
 *  path in SAP.java? What is the order of growth of the worst-case
 *  running time of your methods as a function of the number of
 *  vertices V and the number of edges E in the digraph? What is the
 *  order of growth of the best-case running time?
 **********************************************************************/

Description:


method                               best case            worst case
------------------------------------------------------------------------
length(int v, int w)

ancestor(int v, int w)

length(Iterable<Integer> v,
       Iterable<Integer> w)

ancestor(Iterable<Integer> v,
         Iterable<Integer> w)




/**********************************************************************
 *  If you implemented an extra credit optimization describe it here.
 **********************************************************************/


/**********************************************************************
 *  Lecture topics theory questions.
 * 
 *  There will be a few of these per readme for the last four
 *  assignments. It is ok to discuss these in any level of detail 
 *  with anyone, including students outside your group, 
 *  but do not submit an answer without fully understanding it.
 *  
 *  You are encouraged to attempt to solve the problems with as
 *  little help as possible, solely for the sake of the learning
 *  experience.
 *
 *  1. If you ran Kosaraju's algorithm on hyperynms.txt, where would
 *     the root appear in the reverse postorder of G^R -- the 
 *     beginning, end, or somewhere in the middle? How many 
 *     strongly connected components would be found?
 *
 *  2. If we used BFS instead of DFS for Kosaraju's algorithm,
 *     would it work? Why or why not?
 *
 *  3. Suppose we have an edge weighted undirected graph G.
 *     Suppose we have a set of Q edges which form a cycle such
 *     that no vertex is visited more than once along that cycle.
 *     That is all we know about the graph.
 *    
 *     At most, how many of these Q edges are part of the MST?
 *     If your answer is less than Q, explain which edge(s) can
 *     be definitively ruled out.
 *
 *  4. Same as 3, but at LEAST how many of the Q edges are part
 *     of the MST? Why?
 **********************************************************************/



/**********************************************************************
 *  Known bugs / limitations.
 **********************************************************************/


/**********************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **********************************************************************/


/**********************************************************************
 *  Describe any serious problems you encountered.                    
 **********************************************************************/


/**********************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **********************************************************************/




/**********************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 **********************************************************************/
