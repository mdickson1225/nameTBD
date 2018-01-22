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
	boolean done;
	
	public Turn(State st) {
		this.st = st;
		this.turn_number = 1;
		this.rng = new Random(getSeed());
		done = false;
	}
	
	//SCOTT TODO: Given user input for a command step the game state
	public void stepTurn(String input) {
		String[] cmd = input.split(" ");
				
		switch(cmd[0]) {
		case "status":
			this.st.print_state();
			break;
		case "buy":
			int num = Integer.parseInt(cmd[1]);
			String type = cmd[2];
			System.out.println(this.st.buy(num,type));
			break;
		case "help":
			break;
		case "quit":
			this.done = true;
			break;
		default:
			System.out.println("Invalid command!");
			break;
		}
		this.turn_number++;
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