// Name: Britt Li Kendle
// Pledge: I pledge my honor that I have abided by the Stevens Honor System

package homeworks;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class TreapTest {
	private Treap<Integer> testTreap = new Treap<Integer>();
	
	@Before
	public void setUp() {
		testTreap.add(4,19);
		testTreap.add(2,31);
		testTreap.add(6,70);
		testTreap.add(1,84);
		testTreap.add(3,12);
		testTreap.add(5,83);
		testTreap.add(7,26);
	}
	
	/**
	 * Testing add()
	 */
	@Test
	public void testAdd1() {
		testTreap.add(10); // add node 
		assertTrue(testTreap.find(10)); // node should be in treap
	}
	
	
	/**
	 * Testing if add() will insert a node that's already in treap
	 */
	@Test
	public void testAdd2() {
		assertFalse(testTreap.add(6,70));
	}
	
	/**
	 * Tests delete() for if the node 
	 * we want to delete has 2 children
	 */
	@Test
	public void testDelete1() {
		assertTrue(testTreap.delete(5)); // delete node
		assertFalse(testTreap.find(5)); // node should no longer exist
	}
	
	/**
	 * Tests delete() for if the node
	 * we want to delete has only a left child
	 */
	@Test
	public void testDelete2() {
		assertTrue(testTreap.delete(4)); // delete node
		assertFalse(testTreap.find(4)); // node should no longer exist
	}
	
	/**
	 * Tests delete() for if the node
	 * we want to delete has only a right child
	 */
	@Test
	public void testDelete3() {
		assertTrue(testTreap.delete(6)); // delete node
		assertFalse(testTreap.find(6)); // node should no longer exist
	}
	
	/**
	 * Testing find()
	 */
	@Test
	public void testFind() {
		testTreap.add(90); // add new node
		assertTrue(testTreap.find(90)); // new node should now be in treap
	}

}
