// Name: Britt Li Kendle
// Pledge: I pledge my honor that I have abided by the Stevens Honor System.
package homeworks;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MultiplayerGameTest {
	private MultiplayerGame mp;
	
	@Before
	public void setUp() {
		mp = new MultiplayerGame(3);
		mp.addGamePiece(0, "Adora", 10);
		mp.addGamePiece(1, "Catra", 8);
		mp.addGamePiece(2, "Entrapta", 6);
		mp.addGamePiece(2, "Glimmer", 5);
	}
	
	@Test
	public void testSize() {
		int expectedSize = 4;
		assertEquals(expectedSize, mp.size());
	}
	
	@Test
	public void testAddGamePiece() {
		mp.addGamePiece(0, "Hordak", 1);
		assertTrue(mp.hasGamePiece("Hordak"));
	}
	
	@Test
	public void testRemoveGamePiece() {
		assertTrue(mp.hasGamePiece("Adora"));
		mp.removeGamePiece(0, "Adora");
		assertFalse(mp.hasGamePiece("Adora"));		
	}
	
	@Test
	public void testHasGamePiece() {
		assertTrue(mp.hasGamePiece("Catra"));
	}
	
	@Test
	public void testRemoveAllGamePieces() {
		mp.removeAllGamePieces(2);
		
		assertFalse(mp.hasGamePiece("Entrapta"));
		assertFalse(mp.hasGamePiece("Glimmer"));
	}
	
	@Test
	public void testIncreaseStrength() {
		mp.increaseStrength(0, 10);
		mp.increaseStrength(1, 2);
		mp.increaseStrength(2, 4);
		
		assertEquals("\nPlayer0\nGamePiece: Adora strength: " + 20
				+ "\n\nPlayer1\nGamePiece: Catra strength: " + 10 
				+ "\n\nPlayer2\nGamePiece: Entrapta strength: " + 10
				+ "\nGamePiece: Glimmer strength: " + 9 + "\n", mp.toString());
	}
	
	@Test
	public void testToString() {
		assertEquals("\nPlayer0\nGamePiece: Adora strength: " + 10
				+ "\n\nPlayer1\nGamePiece: Catra strength: " + 8
				+ "\n\nPlayer2\nGamePiece: Entrapta strength: " + 6
				+ "\nGamePiece: Glimmer strength: " + 5 + "\n", mp.toString());
	}
		
}
