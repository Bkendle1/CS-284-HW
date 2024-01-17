// Name: Britt Li Kendle
// Pledge: I pledge my honor that abided by the Stevens Honor System.
package homeworks;

public class MultiplayerGame {
	private GameEntity turnTracker; // Keeps track of whose turn it is
	private GameEntity[] index; // Array of GamePlayer with n entries where n=# of GamePlayer and n should correspond to the GamePlayer's playerId
	
	/** Constructor that creates a new MultiplayerGame with n players
	 * @param n The number of players and size of index[]
	 */
	public MultiplayerGame(int n) {
		// Create index with size of n
		index = new GameEntity[n]; 
		
		// Create n GamePlayers
		for (int i=0; i<n; i++) {
			GamePlayer player = new GamePlayer(null, null, i);
			// Add GamePlayer to index
			index[i] = player;
		}
		
		// Assign players their next and prev fields
		for (int i=0; i<n; i++) {
			// If there's only 1 player in the game, have it point to itself 
			if (n == 1) {
				index[i].next = index[i];
				index[i].prev = index[i];
				return;
			}
			
			// The first player's prev should point to the last player.
			if (i == 0) {
				index[0].next = index[1];
				index[0].prev = index[n-1];
			// The last player's next should point to the first player.
			} else if (i == n-1){
				index[i].next = index[0];
				index[i].prev = index[i-1];
			} else {				
				index[i].prev = index[i-1];
				index[i].next = index[i+1];
			}
		}
	}

	/**
	 * @return The number of GamePieces in play
	 */
	public int size() {
		int size = 0;
		GameEntity currentEntity = index[0];
		int i=0;
		// Traverse through all the GameEntities in play
		while (currentEntity.next != index[0]) {
			if (!currentEntity.isGamePlayer()) {
				size++;
			}
			currentEntity = currentEntity.next;
		}
		// Since the while loop exits while we're on the last
		// GameEntity, it doesn't check if its a GamePiece or not
		// so this is where we'll check it
		if (!currentEntity.isGamePlayer()) {
			size++;
		}
		return size;
	}
	
	/** Adds GamePiece from a given playerId, with a given strength, to the game
	 * @param playerId Id of the GamePlayer
	 * @param name Name for new GamePiece
	 * @param strength Strength for new GamePiece
	 */
	public void addGamePiece(int playerId, String name, int strength) {
		// Check if there's a GamePlayer at the given index (playerId)
		// Also check if playerId is out of index's bounds
		if (playerId > index.length-1 || playerId < 0) {
			throw new IllegalArgumentException("addGamePiece: no such player");
		}
		
		// Start from the given GamePlayer
		GameEntity current = index[playerId];

		// Traverse GamePlayer's GamePieces
		while (!current.next.isGamePlayer()) {
			// If we find a GamePiece with the same name as the given name, we have a duplicate.
			if (current.getName() == name) {
				throw new IllegalArgumentException("addGamePiece: duplicate entity");
			}
			current = current.next;
		}
		
		// While loop exits before checking the last GamePiece, so we check it here.
		if (current.getName() == name) {
			throw new IllegalArgumentException("addGamePiece: duplicate entity");
		}
		
		// Add new GamePiece
		GameEntity currentEntity = index[playerId];
		// Traverse from GamePlayer to their latest GamePiece
		while (!currentEntity.next.isGamePlayer()) {
			currentEntity = currentEntity.next;
		}

		GamePiece newPiece = new GamePiece(currentEntity, currentEntity.next, name, strength);
		currentEntity.next.prev = newPiece;
		currentEntity.next = newPiece;
		
	}
	
	/** Removes the GamePiece owned by specified player of the given name.
	 * @param playerId Id of the GamePiece's owner.
	 * @param name Name of the GamePiece you wish to remove.
	 */
	public void removeGamePiece(int playerId, String name) {
		// Check if GamePlayer exists
		if (playerId > index.length-1 || playerId < 0) {
			throw new IllegalArgumentException("removeGamePiece: no such player");
		}
		
		// Start at the GameEntity next to our current GamePlayer
		GameEntity currentEntity = index[playerId].next;
		// Traverse GameEntities starting from player until we get to GamePiece we want to remove
		while(currentEntity.getName() != name) {
			// If currentEntity is a GamePlayer, then we traversed through all the GamePieces of 
			// the specified GamePlayer this means that the GamePiece doesn't exist 
			if(currentEntity.isGamePlayer()) {
				throw new IllegalArgumentException("removeGamePiece: entity does not exist.");
			}
			currentEntity = currentEntity.next;
		}
		// Update next pointer for GameEntity before the one we want to remove
		currentEntity.prev.next = currentEntity.next;
		// Update the prev pointer for GameEntity after the one we want to remove
		currentEntity.next.prev = currentEntity.prev;		
		
	}
	
	/** Checks if any GamePlayer has GamePiece of the given name.
	 * @param name Name of GamePiece we wish to find.
	 * @return True if a player has the piece, false if otherwise.
	 */
	public boolean hasGamePiece(String name) {
		// Start from first player
		GameEntity current = index[0];
		// Traverse through all GameEntities
		while (!current.next.equals(index[0])) {
			// If the name of the current GameEntity is the 
			// name of the GamePiece we're looking for, we're done
			if (current.getName() == name) {
				return true;
			}
			// Otherwise, update current GameEntity to its next pointer
			current = current.next;
		}
		// The while loop exits without checking last entity so we do that here.
		if (current.getName() == name) {
			return true;
		}
		// If we go through the while loop and if statement, then the GamePiece doesn't exist.
		return false;
	}
	
	/** Remove all GamePieces owned by a specified player.
	 * @param playerId The id of the player we want to clear of GamePieces
	 */
	public void removeAllGamePieces(int playerId) {
		if (playerId > index.length-1 || playerId < 0) {
			throw new IllegalArgumentException("removeAllGamePieces: no such player");
		}
		GameEntity player = index[playerId];
		GameEntity nextPlayer = player;
		
		// Traverse from given player to next player
		while (!nextPlayer.next.isGamePlayer()) {
			nextPlayer = nextPlayer.next;
		}
		// The while loop stops 1 before the next player so we account for that here
		nextPlayer = nextPlayer.next;
		
		// Update player's next pointer to point to next player 
		player.next = nextPlayer;
		// Update nextPlayer's prev pointer to point to player
		nextPlayer.prev = player;
	}
	
	/** Increases the strength of all GamePieces owned by specified player by n
	 * @param playerId Id of the player whose GamePieces' strengths we want to modify
	 * @param n Can be negative, the amount of which the GamePiece(s) strength is modified.
	 */
	public void increaseStrength(int playerId, int n) {
		// Check if player exists
		if (playerId > index.length-1 || playerId < 0) {
			throw new IllegalArgumentException("increaseStrength: no such player");
		}
		GameEntity current = index[playerId].next;
		while (current.getClass() == GamePiece.class) {	
			// Casting current to GamePiece to use updateStrength
			((GamePiece) current).updateStrength(n);
			// Update current to the next GamePiece
			current = current.next;
			
		}
	}
	/**
	 * String representation of the MultiplayerGame. Outputs the String representation
	 * of the first player, then each of their pieces and so on.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<index.length; i++) {
			GameEntity current = index[i];
			while (!current.next.isGamePlayer()) {
				sb.append("\n");
				sb.append(current.toString());
				current = current.next;
			}
			if(!current.isGamePlayer()) {
				sb.append("\n");
				sb.append(current.toString());
			}
			sb.append("\n");
		}

		return sb.toString();
	}
	
	/**
	 * Sets turnTracker to the first GamePlayer.
	 */
	public void initializeTurnTracker() {
		turnTracker = index[0];
	}
	
	/**
	 * Moves the turnTracker to the next GamePlayer.
	 */
	public void nextPlayer() {
		// Traverse to next player
		while (!turnTracker.next.isGamePlayer()) {
			turnTracker = turnTracker.next;
		}
		// The while loop gets us 1 before the next player so we account for that here
		turnTracker = turnTracker.next;
	}
	
	/**
	 * Moves the turnTracker to the next GameEntity, 
	 * which could be either a GamePlayer or a GamePiece.
	 */
	public void nextEntity() {
		turnTracker = turnTracker.next;
	}
	
	/**
	 * Backtracks the turnTracker to the previous GamePlayer.
	 */
	public void prevPlayer() {
		// Traverse to next player
		while (!turnTracker.prev.isGamePlayer()) {
			turnTracker = turnTracker.prev;
		}
		// The while loop gets us 1 before the next player so we account for that here
		turnTracker = turnTracker.prev;
	}
	
	/**
	 * @return The string representation of the current entity the turnTracker is pointing to.
	 */
	public String currentEntityToString() {
		return turnTracker.toString();
	}
	
	public static void main(String[] args) {
		MultiplayerGame mp = new MultiplayerGame(3);

		mp.addGamePiece(0, "Adora", 1);
		mp.addGamePiece(0, "Goku", 9001);
		mp.addGamePiece(1, "Catra", 2);
		mp.addGamePiece(1, "Vegeta", 9000);
		mp.addGamePiece(2, "Greyskull", 3);
		mp.addGamePiece(2, "Deez", 4);
		mp.increaseStrength(0, -2);
		
		mp.initializeTurnTracker();
		mp.nextPlayer();
		mp.nextPlayer();
		mp.prevPlayer();
		mp.prevPlayer();
		//System.out.println(mp.toString());

	}

}
