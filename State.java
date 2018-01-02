/* Mark Dickson 
 * Scott Dickson 
 * 1/1/2018
 * Class for the game state 
*/

//Keep track of money, competitiors, various store quality metrics like items stocked, employees, etc 
//Competitiors have quality, money, can go out of business
//Have an update state method that runs through each thing in event and runs it with a certain probabilty 
//Customers go to each store, including competitors based off of advertising share. store quality determines purchases among customers




public class State {
	//Instance vars

	float capital;
	int employees;
	int managers;
	int num_item_types;
	int[] inventory;
	int[] inventory_quality;

	float stock_price; //Possibly later
	float store_quality; //Calculation TBD

	int store_type; //Hardware, convenience, etc. Later replace with an object 

	public State(){
		/* Possibly determine these randomly */
		this.capital = 20000;
		this.employees = 5;
		this.managers = 1;
		this.num_item_types = 25;
		this.store_quality = get_quality();//Call quality function after 
	}


	/* Use the instance variables to calculate the store quality 
	 TODO: Mark */
	private float get_quality(){

		return 0;
	}


}