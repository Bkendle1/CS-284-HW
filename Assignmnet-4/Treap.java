// Name: Britt Li Kendle
// Pledge: I pledge my honor that I have abided by the Stevens Honor system.
package homeworks;
import java.util.Random;
import java.util.Stack;

public class Treap<E extends Comparable<E>> {
	private static class Node<E> {
		public E data; // key for the search
		public int priority; // random heap priority
		public Node<E> left; // left pointer
		public Node<E> right; // right pointer
		
		/** Constructor for a Treap node, left and right pointers are null.
		 * @param data The data to be stored in the node.
		 * @param priority Randomly assigned upon insertion.
		 */
		public Node(E data, int priority) {
			if (data == null) 
				throw new IllegalArgumentException("Node: data can't be null.");
			
			this.data = data;
			this.priority = priority;
			
		}
		
		/** Performs a right rotation on the Node in the Treap.
		 * @return Reference to the root of the result.
		 */
		public Node<E> rotateRight() {
			Node<E> newRoot = left; // update root
			left = newRoot.right; // update old root's left pointer to newRoot's right node
			newRoot.right = this; // update right child of newRoot
			return newRoot;
		}
		
		
		/** Performs a left rotation on the Node in the Treap.
		 * @return Reference to the root of the result.
		 */
		public Node<E> rotateLeft() {
			Node<E> newRoot = right; // update root 
			right = newRoot.left; // update old root's right pointer to newRoot's left node
			newRoot.left = this; // update left child of newRoot
			return newRoot;
		}
		
		public String toString() {
			return "(key=" + data + ", priority=" + priority + ")";
		}
	}
	
	private Random priorityGenerator;
	private Node<E> root;
	
	/**
	 * Constructs empty treap.
	 */
	public Treap() {
		priorityGenerator = new Random();
	}
	
	public Treap(long seed) {
		priorityGenerator = new Random(seed);
	}
	
	/** Creates a new node containing key as its data and a random priority.
	 * @param key Data for new node.
	 * @return True if node insertion was successfully added to treap, false if otherwise.
	 */
	boolean add(E key) {
		// if node with key already exists in treap...
		if (find(key)) {
			return false;
		} else {			
			return add(key, priorityGenerator.nextInt());
		}
		
	} // end of add()
	
	/** Helper function for add()
	 * @param key Data for new node.
	 * @param priority Randomly generated integer.
	 * @return True if insertion is successful, false if otherwise.
	 */
	boolean add(E key, int priority) {
		// Is the treap empty?
		if (root == null) {
			root = new Node<E>(key, priority);
			return true;
		}
		
		Node<E> current = root; // start at root
		Node<E> newNode = new Node<E>(key, priority); // Create new node
		
		
		Stack<Node<E>> nodeStack = new Stack<Node<E>>(); // stores traversed nodes in stack
		nodeStack.push(current);
		
		boolean foundSpot = false; // flag for when we find spot to insert new node
		
		while (!foundSpot) {
			int cmpResult = key.compareTo(current.data);
			
			// key is less than key in current and the left pointer is empty
			if (cmpResult < 0 && current.left == null) { 
				foundSpot = true;
				// add new node
				current.left = newNode;
			} 
			// else update current to left pointer
			else if (cmpResult < 0) {
				current = current.left;	
				
				// push updated current into stack
				nodeStack.push(current);
			}
			
			// key is greater than key in current
			if (cmpResult > 0 && current.right == null) { 
				foundSpot = true;
				// add new node
				current.right = newNode;
			} 
			// else update current to right pointer
			else if (cmpResult > 0) {
				current = current.right; 
				
				// push updated current into stack
				nodeStack.push(current);
			}
			
			// node must already exist
			if (cmpResult == 0){  
				return false;
			}
			
		}

		// Reheap if needed
		if (newNode.priority > nodeStack.peek().priority) {
			reheap(nodeStack, newNode);
		}
		
		return true;
	} // end of add() helper
	
	/** Restore heap invariant
	 * @param poppedNode
	 * @param newNode
	 * @return
	 */
	private void reheap(Stack<Node<E>> nodeStack,Node<E> newNode) {
		while (!nodeStack.isEmpty()) {
			Node<E> current = nodeStack.pop();
			if (newNode.priority > current.priority) {
				// if the child node is the left pointer of its parent, rotateRight
				if (current.left == newNode) {
					if (current == root) {
						root = current.rotateRight();
						// is current its parent's left node?
					} else if (nodeStack.peek().left == current) {
						nodeStack.peek().left = current.rotateRight();						
					}	 // is current its parent's right node?
						else if (nodeStack.peek().right == current) {
						nodeStack.peek().right = current.rotateRight();
					}
					// if the child node is the right pointer of its parent, rotateLeft
				} else if (current.right == newNode) {
					if (current == root) {
						root = current.rotateLeft();
					} else if (nodeStack.peek().left == current) {						
						// is current its parent's left node?
						nodeStack.peek().left = current.rotateLeft();
					} 	// is current its parent's right node?
						else if (nodeStack.peek().right == current) {
						nodeStack.peek().right = current.rotateLeft();
					}
				}
			} 
		}
	} // end of reheap
	
	/** Deletes the node with the given key from the treap
	 * @param key Key of node we want to delete
	 * @return True if deletion was successful, false if otherwise.
	 */
	boolean delete(E key) {
		// Node doesn't exist
		if (!find(key)) {
			return false;
		} 
		Node<E> current = root; // start search at root
		boolean foundNode = false; // flag for when we find the correct node
		
		Stack<Node<E>> nodeStack = new Stack<Node<E>>(); // store nodes traversed
		nodeStack.push(current);
		
		// Locate node's position in treap
		while (!foundNode) {
			int cmpResult = key.compareTo(current.data);
			// we found the node
			if (cmpResult == 0) {
				nodeStack.pop(); // remove node we want to delete from stack as we don't need it in there
				foundNode = true; 
				// if the node is to the right of current
			} else if (cmpResult > 0){
				current = current.right; // update current
				nodeStack.push(current);
			} else { // cmpResult < 0, the node is to the left of current
				current = current.left; // update current
				nodeStack.push(current);
			}
		}
		
		// Send current to leaf
		while (current.left != null || current.right != null) {
			// Case 1: current has only left child
			if (current.left != null && current.right == null) {
				if (current == root) {
					root = current.rotateRight();
					nodeStack.push(root); // add new root to stack
					continue; // move to next iteration of while loop
				} 
				// is current to the left of its parent?
				else if (nodeStack.peek().left == current) {
					nodeStack.peek().left = current.rotateRight(); // update current parent's left pointer
					nodeStack.push(nodeStack.peek().left); // add current parent's new left pointer to stack
					continue; // move to next iteration of while loop
				} 
				// is current to the right of its parent?
				else if (nodeStack.peek().right == current) {
					nodeStack.peek().right = current.rotateRight();	// updating current parent's right pointer
					nodeStack.push(nodeStack.peek().right); // adding current parent's new right pointer to stack
					continue; // move to next iteration of while loop
				}
			} 
			// Case 2: current has only right child
			else if (current.left == null && current.right != null) {
				if (current == root) {
					root = current.rotateLeft();
					nodeStack.push(root); // add new root to stack
					continue; // move to next iteration of while loop
				} // is current to the left of its parent?
				else if (nodeStack.peek().left == current) {
					nodeStack.peek().left = current.rotateLeft(); // updating current parent's left pointer
					nodeStack.push(nodeStack.peek().left); // adding current parent's new left pointer to stack
					continue; // move to next iteration of while loop
				} // is current to the right of its parent?
				else if (nodeStack.peek().right == current) {
					nodeStack.peek().right = current.rotateLeft(); // updating current parent's right pointer
					nodeStack.push(nodeStack.peek().right); // adding current parent's new right pointer to stack
					continue; // move to next iteration of while loop
				}
			} 
			// Case 3: current has two children
			else {
				// if the left node is less than the right AND is the root
				if (current.left.priority < current.right.priority && current == root) {
					root = current.rotateLeft();
					nodeStack.push(root); // adding new root to stack
					continue; // move to next iteration of while loop
					// if the left node is less than the right and isn't the root
				} else if (current.left.priority < current.right.priority) {
					// is current to the left of its parent?
					if (nodeStack.peek().left == current) {
						nodeStack.peek().left = current.rotateLeft(); // updating current parent's left pointer
						nodeStack.push(nodeStack.peek().left); // adding current parent's new left pointer to stack
						continue; // move to next iteration of while loop
					} 
					// is current to the right of its parent?
					else if (nodeStack.peek().right == current) { // is the current node to the right of its parent?
						nodeStack.peek().right = current.rotateLeft(); // updating current parent's right pointer
						nodeStack.push(nodeStack.peek().right); // adding current parent's new right pointer to stack
						continue; // move to next iteration of while loop
					}
					
				}
				// if the left node is greater than the right AND is the root
				if (current.left.priority > current.right.priority && current == root) {
					root = current.rotateRight();
					nodeStack.push(root); // add new root to stack
					continue; // move to next iteration of while loop
				}
				// if the left node is greater than the right and isn't the root
				else if (current.left.priority > current.right.priority) {
					// is current to the left of its parent?
					if(nodeStack.peek().left == current) {
						nodeStack.peek().left = current.rotateRight(); // updating current parent's left pointer
						nodeStack.push(nodeStack.peek().left); // adding current parent's new left pointer to stack
						continue; // move to next iteration of while loop
					} 
					// is current to the right of its parent?
					else if (nodeStack.peek().right == current) {
						nodeStack.peek().right = current.rotateRight(); // updating current parent's right pointer
						nodeStack.push(nodeStack.peek().right); // adding current parent's new right pointer to stack
						continue; // move to next iteration of while loop
					}
					
				}
				
			} // end of else Case 3
			
		} // end of while loop; current should be a leaf

		// Delete current
		if (nodeStack.peek().left == current) {
			nodeStack.peek().left = null;	
			return true;
		} else if (nodeStack.peek().right == current) {
			nodeStack.peek().right = null;
			return true;
		}
		
		return false;
	}
	
	/** Finds a node with the given key in the treap.
	 * @param key Key used to find node in treap.
	 * @return True if node with given key is found and false if otherwise.
	 */
	public boolean find(E key) {
		return find(root, key);
	}
	
	
	/** Helper function for find(). Finds a node with the given key in the treap rooted at root 
	 * @param root
	 * @param key
	 * @return True if we find a node with given key in treap, false if otherwise.
	 */
	private boolean find(Node<E> root, E key) {
		if (root == null) return false; // base case
		
		if (root.data == key) return true; // if root has key...
		
		// check if node with key exists in either the left or right nodes
		return find(root.left, key) || find(root.right, key);		
	}
	
	/** Helper function for toString() 
	 * @param current the given root node of heap
	 * @param depth current depth of treap
	 * @return String representation of treap
	 */
	private String toString(Node<E> current, int depth) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<depth;i++) {
			sb.append("-");
		}
		if (current==null) {
			sb.append("null\n");
		} else {
			sb.append(current.toString()+"\n");
			sb.append(toString(current.left, depth+1)); //
			sb.append(toString(current.right,depth+1));
		}
		return sb.toString();
	}
	
	/**
	 * Returns string representation of treap.
	 */
	public String toString() {
		return toString(root,0);
	}
	
	public static void main(String[] args) {
		Treap<Integer> testTree = new Treap<Integer>();
		testTree.add(4,19); // typo in assignment, the diagram has 4,14 but the code is 4,19
		testTree.add(2,31);
		testTree.add(6,70);
		testTree.add(1,84);
		testTree.add(3,12);
		testTree.add(5,83);
		testTree.add(7,26);
		System.out.println(testTree.toString());
		System.out.println("ROOT:" + testTree.root);
		System.out.println("----------------------------");
		System.out.println("ROOT:" + testTree.root);
		System.out.println("Delete: " + testTree.delete(5));
		
		System.out.println(testTree.toString());
	
//		Treap<Integer> testTree2 = new Treap<Integer>();
//		testTree2.add(4,19);
//		testTree2.add(2,31);
//		testTree2.add(1,18);
//		System.out.println(testTree2.toString());
//		System.out.println("---------------------------------");
//		System.out.println("Delete: " + testTree2.delete(4));
//		System.out.println(testTree2.toString());
//		System.out.println("ROOT: " + testTree2.root);
	}
}