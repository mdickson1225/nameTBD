import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Scanner;

/* Scott Dickson  
 * Mark Dickson
 * 1/1/2018
 * Main file for the game. Handles loading saved state
 * or starting a new game.
*/

public class Main {
	
	public static void main(String args[]){
		
		State st = init_state();
		repl(st);
		System.out.println("Thanks for playing!");
	}
	
	/* Prompt to either start a new game or read a saved file */
	private static State init_state() {
		Scanner in = new Scanner(System.in);
		State st = new State("");
		boolean done = false;
		
		while(!done) {
			System.out.printf("Welcome to Boss!\n1) New Game\n2)Load Saved");
			System.out.printf("\n>> ");		
			
			switch(in.nextLine().split(" ")[0]) {
				case "1":
					System.out.println("What's the name of this new business?");
					System.out.printf("\n>> ");
					st = new State(in.nextLine());
					done = true;
					break;
				case "2":
					try {
						st.load("progress.boss");
						done = true;
					} catch (FileNotFoundException e) {
						System.out.println("No saved file on record!");
					}
					break;
				default:
					break;
			}
		}
		return st;
	}
		
	/* Effectively the main method. Read input and loop */
	private static void repl(State st) {
		Turn turn = new Turn(st);
		Scanner in = new Scanner(System.in);
		
		while(!turn.done) {
			System.out.println("What will you do? Enter an action or \"help\"");
			System.out.printf("\n>> ");
			turn.stepTurn(in.nextLine());// command format: "<cmd> arg1 arg2..."
		}
	}
	
	
	

}