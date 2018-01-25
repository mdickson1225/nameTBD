/* Scott Dickson
 * Mark Dickson
 * Turn.java
 *  1/15/2018
 *  Run a single turn of the game based off of the game state and other 
 *  internally tracked parameters
 * */

import java.util.Random;
import java.util.Scanner;
import java.util.Date;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	// Add support for more commands for possible 
	public void stepTurn(String input) {
		String[] cmd = input.split(" ");
		Scanner in = new Scanner(System.in);
		
		switch(cmd[0]) {
		case "status":
			this.st.print_state();
			break;
		case "buy":
			int num = Integer.parseInt(cmd[1]);
			String type = cmd[2];
			System.out.println(this.st.buy(num,type));
			break;
		case "market":
			print_prices();
			break;
		case "save":
			System.out.printf("Save game progress?\n1)Yes\n2)No\n>> ");
			switch(in.nextLine().split(" ")[0]) {
				case "1":
					this.st.save("progress.boss");
					System.out.println("Saved game progress!");
					break;
				default:
					break;
			}
			break;
		case "help":
			print_help();
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
	
	/* MARK TODO 1: Generate a unique seed for the rng above
	  by converting the current date to a long.
	  [date] will be a string of the current date and time in the specified format
 	  parse that string to get the numbers of the date/time and combine them into a final seed.
	  Resources:
	  - String.split(" ") 
	  - Integer.parseInt() 
	  - */
	private long getSeed() {
		DateFormat df = new SimpleDateFormat("dd MM yyyy HH mm ss");
		Date now = new Date();
		String date = df.format(now);
		
		return 0;
	}
	
	/* Prints the help message */
	private void print_help() {
		System.out.printf("Thanks for playing Boss!\nCommands:\n");
		System.out.printf("[status]-prints information about current game state\n");
		System.out.printf("[buy n i]-tries to buy n of item i\n[market]-list available items and prices\n");
		System.out.printf("[save]-Save progress\n[help]-print commands\n[quit]-end current game\n");
	}
	
	/* Print the prices as declared in prices.txt in a well formatted way */
	/* MARK TODO 2:
	 * Each line in "prices.txt" will be in the forma <item_name> <price> <quality>
	 * Loop through each line in the file and print out the name and price for each one.
	 * Resources:
	 * - String.split(" ")
	 * - load_prices() from state.java  */
	private void print_prices() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("prices.txt"));
			
		} catch (FileNotFoundException e) {
			System.out.println("Items.txt not found!");
		}
	}
	
	
	
}