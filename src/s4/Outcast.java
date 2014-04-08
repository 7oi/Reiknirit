package s4;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public final class Outcast {
	private final WordNet wn;
	
	// constructor takes a WordNet object
	public Outcast(WordNet wordnet) {
		wn = wordnet;
	}
	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns) {
		String str = "";
		int maxL = 0;
		for (int i = nouns.length - 1; i >= 0; i--) {
			int sum = 0;
			for (int j = nouns.length - 1; j >= 0; j--) {
				sum += wn.distance(nouns[i], nouns[j]);
			}
			maxL = Math.max(maxL, sum);
			if (maxL == sum){
				str = nouns[i];	
			}
 		}
		return str;
	}
//	public static void main(String[] args) {
//		WordNet wordnet = new WordNet("wordnet/synsets.txt", "wordnet/hypernyms.txt");
//		Outcast outcast = new Outcast(wordnet);
//		In in = new In("wordnet/outcast8a.txt");
//		String[] nouns = in.readAllStrings();
//		StdOut.println("Outcast: " +
//		outcast.outcast(nouns));
//	}
	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
		Outcast outcast = new Outcast(wordnet);
		for (int t = 2; t < args.length; t++) {
		String[] nouns = In.readStrings(args[t]);
		StdOut.println(args[t] + ": " +
		outcast.outcast(nouns));
		}
	}
}
