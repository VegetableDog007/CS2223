package algs.days.day13;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Dominant {

	static int numComparisons = 0;

	// Equation for #comparisons is:
	// C(n) = 2*C(n/2) + n
	public static int dominant(int[] collection, int lo, int hi) {
		// one element only
		if (lo == hi) { return collection[lo]; }

		// find dominant value in left and right halves
		int mid = lo + (hi-lo)/2;
		int left = dominant(collection, lo, mid);
		int right = dominant(collection, mid+1, hi);

		// same on both sides? You've found it!
		// NOTE: IF YOU ADD THIS INNOCUOUS LITTLE STATEMENT, THE NUMBER OF COMPARISONS
		// GROWS DRAMATICALLY because the equation becomes: C(n) = 2*C(n/2) + (n+1)
		// numComparisons++;
		// if (left == right) { return left; }

		// Does the left one win? It does if the right side has same value as dominant.
		int i, leftCount = 0, max=(hi-lo+1)/2;
		for (i = lo; i <= hi; i++) {
			numComparisons++;
			if (collection[i] == left) { 
				if (++leftCount > max) { return left; }
			}
		}

		// perhaps right is the majority
		return right;
	}

	public static void main(String[] args) {
		
		StdOut.println("N\tDom.\tMaxCompares\tn log n\tRatio");
		for (int n = 4, idx =2 ; n <= 1048576; n*= 2, idx++) {
			int nlogn = n*idx;
			int[] set = new int[n];

			// since a new integer array, all values are ZERO. Make ZERO the dominant value, and 
			// put random values into first n - (n/2 + 1), or n/2-1 spots
			for (int k = 0; k < n/2-1; k++) {
				set[k] = StdRandom.uniform(1, n);  // none of these can be dominant.
			}

			int maxCompares = 0;
			for (int t = 0; t < 100; t++) {
				// move things around and try random permutations in 100 trials
				StdRandom.shuffle(set);
				numComparisons = 0;
				if (dominant(set, 0, n-1) != 0) {
					StdOut.println ("ERROR");
				}
				if (numComparisons > maxCompares) {
					maxCompares = numComparisons;
				}

			}
			float ratio = (nlogn*1.0f)/maxCompares;
			StdOut.println(n + "\t" + dominant(set, 0, n-1) + "\t" + maxCompares + "\t" + nlogn + "\t" + ratio);
		}
	}
}
