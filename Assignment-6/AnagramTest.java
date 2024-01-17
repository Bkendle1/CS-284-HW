// Name: Britt Li Kendle
// Pledge: I pledge my honor that I have abided by the Stevens Honor System

package assignment6;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class AnagramTest {
	private Anagrams a;
	
	@Before
	public void setUp() {
		a = new Anagrams();
	}
	
	/**
	 * Testing myHashCode() to make sure it outputs expected hash codes.
	 */
	@Test
	public void testMyHashCode() {
		assertEquals(a.myHashCode("alerts"), a.myHashCode("alters"));
		assertEquals(a.myHashCode("alerts"), 236204078);
	}
	
	/**
	 * Testing addWord() to make sure it adds words to anagramTable.
	 */
	@Test
	public void testAddWord() {
		// Ensure key isn't mapped in anagramTable yet.
		String word = "kiriko";
		long key = a.myHashCode(word);
		assertEquals(a.anagramTable.get(key), null);
		
		// Add word to anagramTable.
		a.addWord(word);
		
		// Ensure word has been added to correct key in anagramTable.
		assertTrue(a.anagramTable.get(key).contains(word));
	}
	
	@Test
	public void testGetMaxEntries() {
		a.addWord("alerts");
		a.addWord("alters");
		a.addWord("rastle");
		a.addWord("estral");
		a.addWord("samira");
		a.addWord("aamirs");
		
		// Ensure the max anagram is the correct size
		assertEquals(a.getMaxEntries().get(0).getValue().size(), 4);
		
		String entries = "[236204078=[alerts, alters, rastle, estral]]";
		assertEquals(a.getMaxEntries().toString(), entries);
	}
	
	
	@Test
	public void testAnagrams() {
		
	}
}
