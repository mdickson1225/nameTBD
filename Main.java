import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Scanner;

/* Scott Dickson  
 * Mark Dickson
 * 1/1/2018
 * Main file for the game
*/

public class Main {
	
	public static void main(String args[]){
		boolean done = false;
		State st = new State("");
		Scanner in = new Scanner(System.in);
		String input = "";
		
		//Print start screen to either start a new game or load existing save file
		while(!done) {
			System.out.printf("Welcome to Boss!\n1) New Game\n2)Load Saved\n");
			input = in.nextLine();
			
			switch(input) {
			case "1":
				System.out.println("What's the name of this new business?");
				st = new State(in.nextLine());
				
				done = true;
				break;
			case "2":
				st = read_state();
				done = true;
				break;
			default:
				break;
			}
			
		}
		
		done = false;
		while(!done) {
			System.out.println("What will you do? Enter an action or \"help\"");
						
			input = in.nextLine();
			
			switch(input) {
				case "status":
					print_status(st);
					break;
				case "help":
					break;
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
	//WIP: need better constrcutors
	private static State read_state() {
		try {
			BufferedReader fr = new BufferedReader(new FileReader(""));
			String name = fr.readLine();
			float capital = Float.parseFloat(fr.readLine());
			int employees = Integer.parseInt(fr.readLine());
			int managers = Integer.parseInt(fr.readLine());

			//Pass all the parameters to this constructor
			State st = new State(name);
			return st;
		} catch (IOException e) {
			System.out.println("File not found");
		}
		
		State st = new State("");
		return st;
	}
	
	private static void print_status(State st) {
		System.out.printf("%s Business Report:\n", st.storeName);
		System.out.printf("Captial: %f\n", st.capital);
		System.out.printf("Managers: %d\n", st.managers);
		System.out.printf("Employees: %d\n", st.employees);
		System.out.printf("Items in stock: %d\n", array_sum(st.inventory));
	}
	
	
	
	private static int array_sum(int[] arr) {
		int sum = 0;
		
		for(int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		return sum;
	}
	
	

}