
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import static java.lang.Math.log;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    String file;
    String storeName;
    float capital;
    int employees;
    int managers;
    
    HashMap<String, Float> item_prices;
    int num_item_types;
    int[] inventory;
    int[] inventory_quality;

    
    
    float stock_price; //Possibly later
    float store_quality; //Calculation TBD

    int store_type; //Hardware, convenience, etc. Later replace with an object 

    public State(String name) {
        /* Possibly determine these randomly */
        this.storeName = name;
    	this.capital = 20000;
        this.employees = 5;
        this.managers = 1;
        this.item_prices = load_prices();
        this.num_item_types = 25;
        this.inventory = new int[25];
        this.store_quality = get_quality();//Call quality function after 
    }


    /* Use the instance variables to calculate the store quality 
	 TODO: Mark */
    private float get_quality() {
     store_quality = this.capital + 20*this.employees+ 40*this.managers + 30*this.num_item_types;
     //First attempt at the quality function should probibly be changed later.
        return store_quality;
    }
    
    //Quick function to mask the values of the  game state 
    private double encrypt(double x) {
        return 1/(1+ Math.pow(Math.E , x));
    }
    
    //For array variables, map each element to the encryption function and
    //return the resulting array 
    private double[] encrypt_arr(int[] x) {
    	double[] res = new double[x.length];
    	
    	for(int i = 0; i < x.length; i++) {
    		res[i] = encrypt((double) x[i]);
    	}
    	return res;
    }
    
    //MARK TODO 2
    //Given x which was encrypted by the above encrypt function return the 
    //original value before encryption by reversing the function used to encrypt it (1/1+...)
    private double decrypt(double x) {
        return log((2*x) - 1);
        //I'm not sure I did the math on this right.
    }
    
    //MARK TODO 3
    //Given an array of encrypted values, decrypt all of them and return the decrypted array 
    private int[] decrypt_arr(double[] x) {
    	double[] res = x;
    	double [] decArr = new double[res.length];   
        for (int i = 0; i < res.length; i++) {
           double decVal = decrypt(res[i]);
           decArr[i] = decVal;
          //Why are we returning a decrypted int array but the encrypted array is a double array? 
        }
    	return decArr;
    }
    
    //MARK TODO 1: test out this function and see how it stores the arrays. Preferably it'll be 
    //space separated so storing [1,2,3] given something like "1 2 3" which can then be parsed
    private void save(String filename) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filename, false));
            out.println(encrypt((double) capital));
            out.println(encrypt((double) employees));
            out.println(encrypt_arr(inventory)); 
            out.println(encrypt_arr(inventory_quality)); 
            out.println(encrypt((double) managers));
            out.println(encrypt((double) num_item_types));
            out.println(encrypt((double) stock_price));
            out.println(encrypt((double) store_quality));
            out.println(encrypt((double) store_type));
        } catch (IOException ex) {
            System.out.println("IO exception");
        }
        
    }
    /* Seems to try and load the game state data from its stored format?
     * Will need to account for undoing encryption
     * MARK TODO 1: Given a file stored just how "save" above does it
     * go through and get values for each of state's instance variables
     * by reading the line and undoing the encryption */
    public void load(String filename) throws FileNotFoundException {
        File f = new File (filename); //should be a constant but might as well allow different names 
        Scanner sc = new Scanner(f);
        this.capital = (float) Double.parseDouble(sc.nextLine());
        this.employees = 0; //Placeholder. repalce 0 with actual code
        //Add in lines to read inventory and the rest of the variables as above 
        
        
    }
    
    //Attempt to buy n of item [item] and return a string of the result
    public String buy(int n, String item) {
    	if(this.capital < n*this.item_prices.get(item)) {
    		return String.format("Insufficent funds\n");
    	} else {
    		this.capital -= n*this.item_prices.get(item);
    		this.inventory[0] += n; //SCOTT TODO: Update vars to be able to fix this
    		return String.format("Successfully bought %d %s\n", n, item + "s");
    	}
    }
    
    //Read prices from prices.txt or a similar file and return a hashmap
    //containing those mappings 
    private HashMap<String, Float> load_prices(){
    	HashMap<String, Float> prices = new HashMap<String, Float>();
    	//Read prices.txt or a similar file to load in the data
    	try {
			BufferedReader br = new BufferedReader(new FileReader("prices.txt"));
			
			String line;
			String[] pieces;
			
			while((line = br.readLine()) != null){
				pieces = line.split(" ");
				prices.put(pieces[0],Float.parseFloat(pieces[1]) );
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found in load_prices");
		} catch (NumberFormatException e) {
			System.out.println("Bad input passed to parseFloat");
		} catch (IOException e) {
			System.out.println("IOException in load_prices");
		}
    	
    	return prices;
    }
    
    
}
