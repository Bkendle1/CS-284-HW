// Name: Britt Li Kendle
// Pledge: I pledge my honor that abided by the Stevens Honor System.
public class Complexity {

	/**
	 * Method that has time complexity of O(n^2)
	 * @param n
	 */
	public static void method1(int n) {
		int counter = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.println("Operation " + counter);
				counter++;
			}
		}
	}

	/**
	 * Method that has time complexity of O(n^3)
	 * 
	 * @param n
	 */
	public static void method2(int n) {
		int counter = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					System.out.println("Operation " + counter);
					counter++;
				}
			}
		}
	}

	/**
	 * Method that has time complexity of O(logn)
	 * 
	 * @param n
	 */
	public static void method3(int n) {
		int counter = 0;
		for (int i = 1; i < n; i *= 2) {
			System.out.println("Operation " + counter);
			counter++;
		}
	}

	/*
	 * Q4 a If the length of a is 32
	 * 
		| Iteration | start | end |
		|-----------|-------|-----|
		| 1         | 0     | 31  |
		| 2         | 16    | 31  |
		| 3         | 24    | 31  |
		| 4         | 28    | 31  |
		| 5         | 30    | 31  |
		| 6         | 31    | 31  |
	 * 
	 *
	 */

	/*
	 * Q4 b If the length of a is 64
	 * 
		| Iteration | start | end |
		|-----------|-------|-----|
		| 1         | 0     | 63  |
		| 2         | 32    | 63  |
		| 3         | 48    | 63  |
		| 4         | 56    | 63  |
		| 5         | 60    | 63  |
		| 6         | 62    | 63  |
		| 7         | 63    | 63  |
		 *
	 */
	// Q5 The number of iterations increase by 1 for every power of 2 size n of a is.
	// Q6 The time complexity of bSearch is O(log(n))
	public static boolean bSearch(int[] a, int x) {
		int end = a.length - 1;
		int start = 0;
		int counter = 0;
		while (start <= end) {

			System.out.println("Operation " + counter + " Start " + start + " End " + end);
			int mid = (end - start) / 2 + start;
			if (a[mid] == x)
				return true;
			else if (a[mid] > x)
				end = mid - 1;
			else
				start = mid + 1;
			counter++;
		}
		return false;
	}

	/**
	 * Method with time complexity of O(nlog(n))
	 * @param n
	 */
	public static void method4(int n) {
		int counter = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < n; j *= 2) {
				System.out.println("Operation " + counter);
				counter++;
			}
		}
	}

	/**
	 * Method with time complexity of O(log(log(n))
	 * @param n
	 */
	public static void method5(int n) {
		int counter = 0;
		for (int i=1; i<n; i *= 4) {
			System.out.println("Operation " + counter);
			counter++;
		}
	}

	public static void main(String[] args) {
		
	}

}
