/* Scott Dickson
 * Mark Dickson
 * Turn.java
 *  1/15/2018
 *  Run a single turn of the game based off of the game state and other 
 *  internally tracked parameters
 * */

import java.util.Random;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Turn {
	State st;
	int turn_number;
	Random rng;
	
	public Turn(State st) {
		this.st = st;
		this.turn_number = 0;
		this.rng = new Random(getSeed());
	}
	
	//SCOTT TODO: Given user input for a command step the game state
	public void stepTurn(String input) {
		
	}
	
	/* MARK TODO 4: Generate a unique seed for the rng above
	by converting the current date to a long.
	[date] will be a string of the current date and time in the specified format
	parse that string to get the numbers of the date/time and combine them into a final seed. */
	private long getSeed() {
		DateFormat df = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
		Date now = new Date();
		String date = df.format(now);
		
		return 0;
	}
	
	
	
}