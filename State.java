
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import static java.lang.Math.log;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

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
    HashMap<String, Integer> inventory;
    int[] inventory_quality;

    
    
    double stock_price; //Possibly later
    float store_quality; //Calculation TBD
    double sq_Change;
    int store_type; //Hardware, convenience, etc. Later replace with an object 

    
    public State(String name) {
        /* Possibly determine these randomly */
        this.storeName = name;
    	this.capital = 20000;
        this.employees = 5;
        this.managers = 1;
        this.item_prices = load_prices();
        this.num_item_types = 25;
        this.inventory = new HashMap<String, Integer>();//SCOTT TODO initialize this with some zeros
        this.store_quality = get_quality();//Call quality function after 
    }

    /* Getters and setters */
    
    public String getName() {
    	return this.storeName;
    }
    
    public float getCapital() {
    	return this.capital;
    }
    
    public int getNumEmployees() {
    	return this.employees;
    }
    
    public int getNumManagers() {
    	return this.managers;
    }
    
    public int getNumItemTypes() {
    	return this.num_item_types;
    }
    
    public HashMap<String, Float> getItemPrices(){
    	return this.item_prices;
    }
    
    public HashMap<String, Integer> getInventory(){
    	return this.inventory;
    }
    
    public int getTotalNumItems() {
    	int num = 0;
    	for(Map.Entry<String, Integer> en : this.inventory.entrySet()) {
    		num += en.getValue();
    	}
    	return num;
    }
    
    public float getTotalInventoryValue() {
    	float val = 0;
    	for(Map.Entry<String, Integer> en : this.inventory.entrySet()) {
    		val += en.getValue() * this.item_prices.get(en.getKey());
    	}
    	return val;
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
    
    //SCOTT TODO 1
    //Given a hashmap, encode its contents in a string and return it
    private String encrypt_map(HashMap<String, Integer> hm) {
    	return "";
    }
    
    
    
    //MARK TODO 2
    //Given x which was encrypted by the above encrypt function return the 
    //original value before encryption by reversing the function used to encrypt it (1/1+...)
    private double decrypt(double x) {
    	return log((1/x)-1);
    	
    }
    //Tried my hand at making the stock part
    //TODO - randomness? 
    private double stock_change() {
      this.stock_price = 0.02 * sq_Change;  
      return 0;
      
    }
    
    //MARK TODO 3
    //Given an array of encrypted values, decrypt all of them and return the decrypted array 
    private double[] decrypt_arr(double[] x) {
    	double[] res = x;
    	double [] decArr = new double[res.length];   
    	for (int i = 0; i < res.length; i++) {
    	double decVal = decrypt(res[i]);
    	decArr[i] = decVal;
    	//Why are we returning a decrypted int array but the encrypted array is a double array? 
    	}
    	return decArr;
    }
    
    
    //Take a string as input in the format "s1 i1 s2 i2..." etc.
    private HashMap<String, Integer> decrypt_map(String hm){
    	String[] kv_pairs = hm.split(" ");
    	HashMap<String, Integer> hmap = new HashMap<String, Integer>();
    	
    	for(int i = 0; i < kv_pairs.length; i += 2) {
    		hmap.put(kv_pairs[i], Integer.parseInt(kv_pairs[i+1]) );
    	}
    	return hmap;
    }
    
    
    //MARK TODO 1: test out this function and see how it stores the arrays. Preferably it'll be 
    //space separated so storing [1,2,3] given something like "1 2 3" which can then be parsed
    private void save(String filename) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filename, false));
            out.println(storeName);
            out.println(encrypt((double) capital));
            out.println(encrypt((double) employees));
            out.println(encrypt_map(inventory)); 
            out.println(encrypt_arr(inventory_quality)); 
            out.println(encrypt((double) managers));
            out.println(encrypt((double) num_item_types));
            out.println(encrypt((double) stock_price));
            out.println(encrypt((double) store_quality));
            out.println(encrypt((double) store_type));
            out.close();
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
        this.capital = (float) decrypt(Double.parseDouble(sc.nextLine()));
        this.employees = (int) decrypt(Double.parseDouble(sc.nextLine()));
        //Not certain how to do the array and hashmap. Will work on later.
        this.inventory_quality = cast_to_int_array(decrypt_arr((array_from_string(sc.nextLine()))));
        this.managers = (int) decrypt(Double.parseDouble(sc.nextLine()));
        this.num_item_types = (int) decrypt(Double.parseDouble(sc.nextLine()));
        this.stock_price =(int) decrypt(Double.parseDouble(sc.nextLine()));
        this.store_quality = (float) decrypt(Double.parseDouble(sc.nextLine()));
        this.store_type = (int) decrypt(Double.parseDouble(sc.nextLine()));
        //Add in lines to read inventory and the rest of the variables as above 
        sc.close();        
    }
    
    //Attempt to buy n of item [item] and return a string of the result
    public String buy(int n, String item) {
    	if(!this.item_prices.containsKey(item))
    		return String.format("%s is not a valid item\n", item);
    	if(this.capital < n*this.item_prices.get(item)) {
    		return String.format("Insufficent funds\n");
    	} else {
    		this.capital -= n*this.item_prices.get(item);
    		this.inventory.put(item, this.inventory.get(item)+n); 
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
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found in load_prices");
		} catch (NumberFormatException e) {
			System.out.println("Bad input passed to parseFloat");
		} catch (IOException e) {
			System.out.println("IOException in load_prices");
		}
 
    	return prices;
    }
    
    /* Given a string representation of an array with elements space separated,
     * return its array representation. Helper method for reading a saved file */
    private double[] array_from_string(String arr) {
    	return Arrays.stream(arr.split(" ")).mapToDouble(Double::parseDouble).toArray();
    }
    
    /* Helper to do the same thing as arr = (int[]) arr */
    private int[] cast_to_int_array(double[] arr) {
    	int[] new_arr = new int[arr.length];
    	for(int i = 0; i < arr.length; i++) {
    		new_arr[i] = (int) arr[i];
    	}
    	return new_arr;
    }
    
    /* Print the information about this State object in a well formatted way */
    public void print_state() {
		System.out.printf("%s Business Report:\n", this.storeName);
		System.out.printf("Captial: %f\n", this.capital);
		System.out.printf("Managers: %d\n", this.managers);
		System.out.printf("Employees: %d\n", this.employees);
		System.out.printf("Items in stock: %d\n", this.getTotalNumItems());
		System.out.printf("Total inventory value: %.2f\n", this.getTotalInventoryValue());
	}
    
    
}
