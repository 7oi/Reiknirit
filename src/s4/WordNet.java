package s4;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.introcs.In;

public final class WordNet {
	private final SAP sap;
	private final ST<String,Bag<Integer>> st;
	private final ST<Integer,String> stI;
	
	
	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		In in = new In(synsets);
		st = new ST<String,Bag<Integer>>();
		stI = new ST<Integer,String>();
		int counter = 0;
		while (!in.isEmpty()) {
			String[] starr1 = in.readLine().split(",");

			for (String s : starr1[1].split(" ")) {			
				Bag<Integer> sids = new Bag<Integer>();
				
				if(st.contains(s))
				{
					sids = st.get(s);
				}

				sids.add(Integer.parseInt(starr1[0]));
				st.put(s, sids);

			}
			
			stI.put(Integer.parseInt(starr1[0]), starr1[1]);
			counter++;
		}
		in = new In(hypernyms);
		Digraph G = new Digraph(counter);
		while (!in.isEmpty()) {
			String[] starr1 = in.readLine().split(",");
			for (int i = 1; i < starr1.length; i++) {
				G.addEdge(Integer.parseInt(starr1[0]), Integer.parseInt(starr1[i]));
			}

		}
		sap = new SAP(G);
		//System.out.println("t: "+t +" - counter: " + counter);
	}
	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return st.keys();
	}
	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		return st.contains(word);
	}
	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		int dist = 0;
		if (isNoun(nounA) && isNoun(nounB)) {
			Bag<Integer> a = st.get(nounA);
			Bag<Integer> b = st.get(nounB);
			
			dist = sap.length(a, b);

		}
		else {
			throw new IllegalArgumentException("Not a WordNet word, sucka");
		}
		return dist;
	}
	// a synset (second field of synsets.txt) that is the common
	// ancestor of nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		int anc = 0;
		if (isNoun(nounA) && isNoun(nounB)) {
			Bag<Integer> a = st.get(nounA);
			Bag<Integer> b = st.get(nounB);
			anc = sap.ancestor(a, b);
		}
		else {
			throw new IllegalArgumentException("Not a WordNet word, sucka");
		}
		//System.out.println("arc: " + anc + " A:" + st.get(nounA).toString()+ " B:"   + st.get(nounB).toString());
		return stI.get(anc);
	}

	// do unit testing of this class
	public static void main(String[] args) {
		WordNet wn = new WordNet("wordnet/synsets3.txt", "wordnet/hypernymsInvalidCycle.txt");
//		System.out.println(wn.isNoun("subject_field"));
//		System.out.println(wn.distance("computer_scientist", "shoes"));
//		System.out.println(wn.sap("computer_scientist", "shoes"));
		for (String s : wn.nouns()) {
			for (String t : wn.nouns()) {
				System.out.println("sap(" + s + "," + t + ")=" + wn.sap(s, t) + " d=" + wn.distance(s, t));
			}
		}
		
	}

}
