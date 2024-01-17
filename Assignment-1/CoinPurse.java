// Name: Britt Li Kendle
// Pledge: I pledge my honor that abided by the Stevens Honor System.
import java.util.Random;

public class CoinPurse {

	// 17 Sickles = 1 Galleon
	// 29 Knuts = 1 Sickle
	private int numGalleons;
	private int numSickles;
	private int numKnuts;
	
	// Max number of coins in purse
	private static final int CAPACITY = 256;
	
	
	/**
	 * Creates an empty coin purse.
	 */
	public CoinPurse() {
		
	}
	
	/** 
	 * Creates a coin purse containing s Galleons, s Sickles, and k Knuts.
	 * @param g Galleons
	 * @param s Sickles
	 * @param k Knuts
	 */
	public CoinPurse(int g, int s, int k) {
		this.numGalleons = g;
		this.numSickles = s;
		this.numKnuts = k;
		
		if (g + s + k > CAPACITY) {
			throw new IllegalArgumentException(
				"Total number of coins can't exceed: " + CAPACITY);
		} else if (g + s + k < 0) {
			throw new IllegalArgumentException(
					"Total number of coins must be non-negative!");
			
		}
	}
	
	
	/** 
	 * Deposit n number of galleons
	 * @param n
	 */
	public void depositGalleons(int n) {
		// Is n negative?
		if (n < 0) {
			throw new IllegalArgumentException(
					"Deposit amount must be non-negative!");
		// Total coins would exceed capacity?
		} else if ((this.numGalleons + this.numKnuts + this.numSickles + n) > CAPACITY)
			throw new IllegalArgumentException(
					"Not enough space to store " + n + " more galleons." );
		
		// Add to current number of Galleons
		this.numGalleons += n;
	}
	
	/** 
	 * Deposit n number of sickles.
	 * @param n
	 */
	public void depositSickles(int n) {
		// Is n negative?
		if (n < 0) {
			throw new IllegalArgumentException(
					"Deposit amount must be non-negative!");
			// Total coins would exceed capacity?
		} else if ((this.numGalleons + this.numKnuts + this.numSickles + n) > CAPACITY)
			throw new IllegalArgumentException(
					"Not enough space to store " + n + " more sickles." );
		
		// Add to current number of Sickles
		this.numSickles += n;
	}
	
	
	/** 
	 * Deposit n number of knuts.
	 * @param n
	 */
	public void depositKnuts(int n) {
		// Is n negative?
		if (n < 0) {
			throw new IllegalArgumentException(
					"Deposit amount must be non-negative!");
			// Total coins would exceed capacity?
		} else if ((this.numGalleons + this.numKnuts + this.numSickles + n) > CAPACITY)
			throw new IllegalArgumentException(
					"Not enough space to store " + n + " more knuts.");
		
		// Add to current number of Knuts
		this.numKnuts += n;
	}
	
	
	/**
	 * Withdraw n number of galleons.
	 * @param n
	 */
	public void withdrawGalleons(int n) {
		// Is n negative?
		if (n < 0) {
			throw new IllegalArgumentException(
					"Withdraw amount must be non-negative!");
		} // Do we have enough to withdraw? 
		else if ((this.numGalleons - n) < 0) {
			throw new IllegalArgumentException(
					"Not enough galleons to withdraw " + n + " galleons.");
		}
		
		// Withdraw
		this.numGalleons -= n;
	}
	
	/** 
	 * Withdraw n number of sickles.
	 * @param n
	 */
	public void withdrawSickles(int n) {
		// Is n negative?
		if (n < 0) {
			throw new IllegalArgumentException(
					"Withdraw amount must be non-negative!");
		} else if ((this.numSickles - n) < 0) {
			throw new IllegalArgumentException(
					"Not enough sickles to withdraw " + n + " sickles.");
		}
		
		// Withdraw 
		this.numSickles -= n;
	}
	
	/** 
	 * Withdraw n number of knuts.
	 * @param n
	 */
	public void withdrawKnuts(int n) {
		// Is n negative?
		if (n < 0) {
			throw new IllegalArgumentException(
					"Withdraw amount must be non-negative!");
		} else if ((this.numKnuts - n) < 0) {
			throw new IllegalArgumentException(
					"Not enough knuts to withdraw " + n + " knuts.");
		}
		
		// Withdraw
		this.numKnuts -= n;
	}
	
	
	/** 
	 * Returns the total number of coins in the coin purse.
	 */
	public int numCoins() {
		return this.numGalleons + this.numSickles + this.numKnuts;
	}
	
	
	/** 
	 * Returns the value of the coin purse in Knuts.
	 * 29 knuts in a sickle.
	 * 493 knuts in a galleon.
	 */
	public int totalValue() {
		int sicklesToKnuts = this.numSickles * 29;
		int galleonsToKnuts = this.numGalleons * 493;
		return sicklesToKnuts + galleonsToKnuts + this.numKnuts;
	}
	
	
	/**
	 * Returns the number of each type of coin.
	 */
	public String toString() {
		return "This coin purse has:\n" + this.numGalleons + " galleons\n"
				+ this.numSickles + " sickles\n"
				+ this.numKnuts + " knuts";
	}
	
	
	/**
	 * Takes a coin value n, then returns whether or not there exists a combination
	 * of coins in the coin purse equivalent to n.
	 * @param n
	 */
	public boolean exactChange(int n) {	
		// Is n negative?
		if (n < 0) {
			throw new IllegalArgumentException(
					"Exact change must be non-negative!");
		}
		
		// Calculate galleons needed to give exact change
		int galleonsNeeded = Math.floorDiv(n, 493);
		int knutsLeft = n%493;
		
		// Calculate sickles needed based on remaining knuts
		int sicklesNeeded = Math.floorDiv(knutsLeft, 29);
		
		// Calculate knuts left based 
		knutsLeft = n%29;
		
		// Do we have the coins needed to give exact change?
		if (this.numGalleons >= galleonsNeeded 
				&& this.numSickles >= sicklesNeeded
				&& this.numKnuts >= knutsLeft) {
			return true;
		}
		return false;
	}
	
	/**
	 * If there's exact change in the purse, returns a combination 
	 * of coins necessary to create exact change of value n.
	 * If there isn't exact change in the purse, returns a combination
	 * of coins necessary to create exact change of a value slightly larger than n.
	 * @param n Desired exact change value.
	 * @return Three element array of coins needed to create exact change with value n. int[0] = galleons, int[1] = sickles, int[2] = knuts.
	 */
	public int[] withdraw(int n) {
		// Is n negative?
		if (n < 0) {
			throw new IllegalArgumentException(
					"Withdrawal amount must be non-negative!");
		// If n is greater than the total value of the purse?
		} else if (this.totalValue() < n) {
			throw new IllegalArgumentException(
					"This purse's value is less than: " + n);
		}
		
		// Subset of coins
		int[] coinWithdrawal = new int[3];
		
		// Calculate galleons needed to give exact change
		int galleonsNeeded = Math.floorDiv(n, 493);
		int knutsLeft = n%493;
		
		// Calculate sickles needed based on remaining knuts
		int sicklesNeeded = Math.floorDiv(knutsLeft, 29);
		
		// Calculate knuts left based 
		knutsLeft = n%29;
		
		if(this.exactChange(n)) {
			coinWithdrawal[0] = galleonsNeeded;
			coinWithdrawal[1] = sicklesNeeded;
			coinWithdrawal[2] = knutsLeft;
		} else {
			while (!this.exactChange(n)) {
				n++;				
			}
			// Calculate galleons needed to give exact change
			galleonsNeeded = Math.floorDiv(n, 493);
			knutsLeft = n%493;
			
			// Calculate sickles needed based on remaining knuts
			sicklesNeeded = Math.floorDiv(knutsLeft, 29);
			
			// Calculate knuts left based 
			knutsLeft = n%29;
			
			coinWithdrawal[0] = galleonsNeeded;
			coinWithdrawal[1] = sicklesNeeded;
			coinWithdrawal[2] = knutsLeft;
		}
		return coinWithdrawal;
	}
	
	/**
	 * Randomly draws a coin with the probability being proportional 
	 * to the number of coins of each kind in the purse.
	 * @return 0 = knut; 1 = sickle; 2 = galleon
	 */
	public int drawRandCoin() {
		// Are there coins in the purse?
		if (this.numCoins() <= 0) {
			throw new IllegalArgumentException(
					"There are no coins to draw in this purse.");
		}
		
		Random rand = new Random();
		
		// Create an array the size of the number of coins in the purse
		int[] coins = new int[this.numCoins()];
		
		// Add "2" for each galleon in the purse
		for (int i=0; i<this.numGalleons; i++) {
			coins[i] = 2;
		}
		
		// Add "1" for each sickle in the purse after the 2s from the galleons
		for (int i=this.numGalleons; i<this.numGalleons+this.numSickles; i++) {
			coins[i] = 1;
		}
		
		// Since the default value of an element in an int[] = 0, there doesn't need to 
		// be a for loop for knuts as the rest of the elements in the array are already 0.
		
		int coinIndex = rand.nextInt(coins.length); // Randomly generates a number between 0-coins.length
		
		return coins[coinIndex]; // Returns a random element in the coins array
	}
	
	/** 
	 * Randomly draws n number of coins from the coin purse.
	 * @param n Amount of coins to draw.
	 * @return Array of all coins drawn.
	 */
	public int[] drawRandSequence(int n) {
		// Are there any coins in this purse?
		if (this.numCoins() <= 0) {
			throw new IllegalArgumentException(
					"There are no coins to draw in this purse.");
		}
		
		// Create array of length n
		int[] coinsDrawn = new int[n];
		
		// Call the drawRandCoin() n times
		for (int i=0; i < n; i++) {
			// Assign result from drawRandCoin() into new array
			coinsDrawn[i] = this.drawRandCoin();
		}
		
		return coinsDrawn;
	}
	
	
	/**
	 * Takes two coin sequences and returns which sequence won the most comparisons.
	 * Coin sequences must be of equal length.
	 * @param coinSeq1 First coin sequence.
	 * @param coinSeq2 Second coin sequence.
	 * @return 0=Tie 1=coinSeq1 won the most -1=coinSeq2 won the most.
	 */
	public static int compareSequences(int[] coinSeq1, int[] coinSeq2) {
		// Are the two int[] the same length?
		if (coinSeq1.length != coinSeq2.length) {
			throw new IllegalArgumentException(
					"Coin sequences must be the same length!");
		}
		
		int seq1WinCount = 0;
		int seq2WinCount = 0;
		
		// Since both sequences are the same length, I can just use 
		// the length of either to iterate over both arrays.
		for (int i=0; i<coinSeq1.length; i++) {
			// If coinSeq1 wins
			if (coinSeq1[i] > coinSeq2[i]) {
				seq1WinCount++;
			// If coinSeq2 wins
			} else if (coinSeq1[i] < coinSeq2[i]) {
				seq2WinCount++;
			} 
			// Otherwise there's a tie
		}
		
		// If coinSeq1 won more than coinSeq2
		if (seq1WinCount > seq2WinCount) {
			return 1;
		// If coinSeq2 won more than coinSeq1
		} else if (seq2WinCount > seq1WinCount) {
			return -1;
		} 
		
		// Otherwise tieCount won
		return 0;
	}
	
	public static void main(String[] args) {
		
	}

}
