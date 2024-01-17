// Name: Britt Li Kendle
// Pledge: I pledge my honor that I have abided by the Stevens Honor System
package assignment6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Anagrams {

	final Integer[] primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83,
			89, 97, 101 };

	Map<Character, Integer> letterTable;
	Map<Long, ArrayList<String>> anagramTable;

	/**
	 * Gets invoked by constructor Anagrams(), this builds the hash table
	 * letterTable.
	 */
	public void buildLetterTable() {
		letterTable = new HashMap<Character, Integer>();
		Character[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
				'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		for (int i = 0; i < alphabet.length; i++) {
			letterTable.put(alphabet[i], primes[i]);
		}
	}

	/**
	 * Constructor for anagrams. Creates a letterTable and anagramTable.
	 */
	Anagrams() {
		buildLetterTable();
		anagramTable = new HashMap<Long, ArrayList<String>>();
	}

	/**
	 * Computes the hash code of the given string and adds this to the hash table
	 * anagramTable. If string is already in the table, throw
	 * IllegalArgumentException();
	 * 
	 * @param s
	 */
	public void addWord(String s) {
		// Calculate hash code
		long hashCode = myHashCode(s);

		// Throw exception if there's a list at given key and it already contains s.
		if (anagramTable.get(hashCode) != null 
				&& anagramTable.get(hashCode).contains(s)) {
			throw new IllegalArgumentException("addWord: duplicate value.");
		}

		// Check if list exists at given key
		if (anagramTable.get(hashCode) == null) {
			// Create new list
			ArrayList<String> list = new ArrayList<String>();
			// Map key with new list
			anagramTable.put(hashCode, list);
		}

		// Add string to existing list at the calculated hash code
		anagramTable.get(hashCode).add(s);
	}

	/**
	 * Computes the hash code for a given String.
	 * 
	 * @param s The String that we're going to calculate the hash code for.
	 * @return The hash code for the given String.
	 */
	public long myHashCode(String s) {
		// if s is empty
		if (s == null) {
			throw new IllegalArgumentException("myHashCode: string s is empty.");
		}
		Long hashCode = 1L;
		for (int i = 0; i < s.length(); i++) {
			hashCode *= letterTable.get(s.charAt(i));
		}
		return hashCode;
	}

	/**
	 * Takes a text file containing words, one per line, and builds the hash table
	 * anagramTable.
	 * 
	 * @param s The name of a text file containing words.
	 * @throws IOException
	 */
	public void processFile(String s) throws IOException {
		FileInputStream fstream = new FileInputStream(s);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		while ((strLine = br.readLine()) != null) {
			this.addWord(strLine);
		}
		br.close();
	}

	/**
	 * Returns a list of entries in anagramTable that have the largest number of
	 * anagrams.
	 * 
	 * @return
	 */
	public ArrayList<Map.Entry<Long, ArrayList<String>>> getMaxEntries() {
		// Create list to store entries with the largest number of anagrams
		ArrayList<Map.Entry<Long, ArrayList<String>>> maxEntries = new ArrayList<Map.Entry<Long, ArrayList<String>>>();

		// Store current max entry size
		int tempSize = 0;

		// Iterate through all values to find largest list size
		for (ArrayList<String> value : anagramTable.values()) {
			if (value.size() > tempSize) {
				tempSize = value.size();
			}
		}

		// Iterate through all entries and store those who have the largest list size
		for (Map.Entry<Long, ArrayList<String>> entry : anagramTable.entrySet()) {
			if (entry.getValue().size() == tempSize) {
				maxEntries.add(entry);
			}
		}

		return maxEntries;

	}

	public static void main(String[] args) {
		Anagrams a = new Anagrams();

		final long startTime = System.nanoTime();
		try {
			a.processFile("words_alpha.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<Map.Entry<Long, ArrayList<String>>> maxEntries = a.getMaxEntries();
		final long estimatedTime = System.nanoTime() - startTime;
		final double seconds = ((double) estimatedTime / 1000000000);
		System.out.println("Time: " + seconds);
		System.out.println("List of max anagrams: " + maxEntries);

	}

}
