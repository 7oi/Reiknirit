package d4;

public class Testy {
	
	public static void loops(int N) {
		String toPrint = "N = " + N + ": ";
		int sum = 0;
		// a.)
		for (int n = N; n > 0; n /= 2)
		   for (int i = 0; i < n; i++) {
		      sum++;
		   }
		toPrint += "a" + sum + " ";
		// b.)
		sum = 0;
		for (int i = 1; i < N; i *= 2)
			   for (int j = 0; j < i; j++){
			       sum++;
			   }
		toPrint += "b" + sum + " ";
		// c.)
		sum = 0;
		for (int i = 1; i <= N; i++)
		    for (int j = 1; j <= N; j += i) {
		        sum++;
		    }
		toPrint += "c" + sum + " ";
		/* d.)
		sum = 0;
		for (int j=1; j < N; j = j * j) {
			   sum++;
		}
		toPrint += "d" + sum + " ";*/
		sum = 0;
		// e.)
		for (int i=0; i < N; i++) {
			   for (int j=0; j < i; j+=2)
			      sum++;
		}
		toPrint += "e" + sum + " ";
		sum = 0;
		// f.)
		for (int i=0; i < N*N; i++) 
			  for (int j=0; j < 2*N; j++) {
			      sum++;	
			  }
		toPrint += "f" + sum + " ";
		System.out.println(toPrint);
	}
	public static void main(String[] args) {
		for (int i = 1; i < 65; i += i)
			loops(i);

	}

}
