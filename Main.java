import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/* Scott Dickson  
 * Mark Dickson
 * 1/1/2018
 * Main file for the game
*/

public class Main {
	
	public static void main(String args[]){
		boolean done = false;
		Scanner in = new Scanner(System.in);
		String input;
		
		//Print start screen to either start a new game or load existing save file
		
		
		while(!done) {
			//Print game status and prompt for options
			
			System.out.println("Enter an option:");
			input = in.nextLine();
			
			switch(input) {
				case "quit":
					done = true;
					break;
				default:
					System.out.println("Invalid command!");
					break;
			}
			
			
		}
		
		System.out.println("Thanks for playing!");
		
	}
	
	//Target data/.saved and try to create a state object from it
	public State read_state() {
		try {
			BufferedReader fr = new BufferedReader(new FileReader(""));
			
			float capital = Float.parseFloat(fr.readLine());
			int employees = Integer.parseInt(fr.readLine());
			int managers = Integer.parseInt(fr.readLine());

			//Pass all the parameters to this constructor
			State st = new State();
			return st;
		} catch (IOException e) {
			System.out.println("File not found");
		}
		
		State st = new State();
		return st;
	}
	
	
	

}