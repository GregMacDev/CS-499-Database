package enhancement;

import java.util.Scanner;


/**
* <h1>Item bids SQL Database Enhancement</h1>
* This program creates and stores ItemForBid class objects in a MySQL database. 
* This program has been converted from C++. The database
* can be sorted, and searched, by each of the ItemForBid object's
* variables. Title, Fund, Vehicle, or Bid Amount.
* <p>
*
* @author  Greg MacPhelemy
* @version 3.0
* @since   06-04-2021
*/

public class App {

	/**
	 * This method gathers information from the user, stores it as an ItemForBid
	 * object and returns the object.
	 * @param scanner This is the scanner object that reads a user's input
	 * @return newItem This returns the object containing user input data.
	 */
	private static ItemForBid getBid(Scanner scanner) {
		
		// Creates a new ItemForBid object
		ItemForBid newItem = new ItemForBid();
		
		// Collects user input data for each of the object's variables
		System.out.println("Enter title: ");
		newItem.setItemTitle(scanner.nextLine());
		
		System.out.println("Enter fund: ");
		newItem.setFundType(scanner.nextLine());
		
		System.out.println("Enter vehicle: ");
		newItem.setVehicleType(scanner.nextLine());
		
		System.out.println("Enter amount: ");
		// User validation to prevent invalid data type to be entered where a double is required.
		while(!scanner.hasNextDouble()) {
			System.out.println("Invalid Bid Amount. Must be a number. Please Re-enter the new price.");
			scanner.nextLine();
		}
		newItem.setBidAmount(scanner.nextDouble());
				
		return newItem;
	}
	
	/**
	 * This method updates a bid within the database. The user is prompted to enter
	 * the item to update, which element to change and the new value to update the
	 * item with.
	 * @param db This is SQLDatabase
	 * @param scanner This is the scanner object that reads a user's input
	 */
	private static void updateBid(SQLDatabase db, Scanner scanner) {
		String itemTitle = "";
		String updateColumn = "";
		String updateData = "";
		
		System.out.println("Enter title of item to update.");
		itemTitle = scanner.nextLine();
		System.out.println("Enter element to change. (Title, Fund, Vehicle, Bid)");
		updateColumn = scanner.nextLine();
		System.out.println("Enter new data.");
		updateData = scanner.nextLine();
		
		db.updateBid(itemTitle, updateColumn, updateData);
	}
	
	/**
	 * This method controls the deletion of an element from the database. The user is
	 * prompted to enter the title of the item to be deleted.
	 * @param db This is the SQLDatabase
	 * @param scanner This is the scanner object that reads a user's input
	 */
	private static void deleteBid(SQLDatabase db, Scanner scanner) {
		String itemTitle = "";
		
		System.out.println("Enter title of item to delete.");
		itemTitle = scanner.nextLine();
		
		db.deleteBid(itemTitle);
	}
	
	/**
	 * This method sorts the bids based upon the user input of which element to sort
	 * on.  The user is then prompted to specify an ascending or descending sort.
	 * @param db This is SQLDatabase
	 * @param scanner This is the scanner object that reads a user's input
	 */
	private static void sortBids(SQLDatabase db, Scanner scanner) {
		int userChoice = 0;
		int sortOrder = 0;
		
		while(userChoice != 5) {
			System.out.println("Sort Bids by...");
			System.out.println("1. Title");
			System.out.println("2. Fund");
			System.out.println("3. Vehicle");
			System.out.println("4. Bid Amount");
			System.out.println("5. Back...");
		
		// Validating user input
			do {
				// Prevents invalid input by checking for an int input from the user
				while(!scanner.hasNextInt()) {
					System.out.println("Number not entered. Please enter a valid number.");
					scanner.next();
				}
				userChoice = scanner.nextInt();
				
				// White listing user input
				if(userChoice !=1 && userChoice !=2 && userChoice !=3 && userChoice !=4 && userChoice !=5) {
					System.out.println("Invalid menu option. Please enter a valid menu option.");
				}
				//White listing conditional for do/while loop
			} while (userChoice != 1 && userChoice != 2 && userChoice !=3 && userChoice !=4 && userChoice !=5);
			
			// Clear scanner buffer
			scanner.nextLine();
			
			// User input switch control. calls SQL Sort method based on variable to sort on. Requests
			// an ascending list or a descending list and prints the list.
			switch(userChoice) {			
			case 1:	// Sort by Title
				sortOrder = sortOrder(scanner);
				System.out.println("Sorted by Title\n");
				if (sortOrder == 1) {
					db.sortBids("name", "ASC");
					userChoice = 5;
				}else if(sortOrder == 2) {
					db.sortBids("name", "DESC");
					userChoice = 5;
				}				
				break;
			case 2:	// Sort by Fund
				sortOrder = sortOrder(scanner);
				System.out.println("Sorted by Fund\n");
				if (sortOrder == 1) {
					db.sortBids("fund", "ASC");
					userChoice = 5;
				}else if(sortOrder == 2) {
					db.sortBids("fund", "DESC");
					userChoice = 5;
				}	
				break;
			case 3:	// Sort by Vehicle
				sortOrder = sortOrder(scanner);
				System.out.println("Sorted by Vehicle\n");
				if (sortOrder == 1) {
					db.sortBids("vehicle", "ASC");
					userChoice = 5;
				}else if(sortOrder == 2) {
					db.sortBids("vehicle", "DESC");
					userChoice = 5;
				}	
				break;
			case 4:	// Sort by Bid Amount
				sortOrder = sortOrder(scanner);
				System.out.println("Sorted by Bid Amount\n");
				if (sortOrder == 1) {
					db.sortBids("bid", "ASC");
					userChoice = 5;
				}else if(sortOrder == 2) {
					db.sortBids("bid", "DESC");
					userChoice = 5;
				}	
				break;
			}
		}
	}
	
	/**
	 * This method creates the secondary menu for a user to choose whether to sort
	 * the bids by ascending or descending order.
	 * @param scanner This is the scanner object that reads a user's input
	 * @return returns 1 for ascending, 2 for descending or 0 to go back.
	 */
	private static int sortOrder(Scanner scanner) {
		int userChoice = 0;
		
		while(userChoice != 3) {
			System.out.println("Sort Bids by...");
			System.out.println("1. Ascending");
			System.out.println("2. Descending");
			System.out.println("3. Back...");
		
		// Validating user input
			do {
				// Prevents invalid input by checking for an int input from the user
				while(!scanner.hasNextInt()) {
					System.out.println("Number not entered. Please enter a valid number.");
					scanner.next();
				}
				userChoice = scanner.nextInt();
				
				// White listing user input
				if(userChoice !=1 && userChoice !=2 && userChoice !=3) {
					System.out.println("Invalid menu option. Please enter a valid menu option.");
				}
				//White listing conditional for do/while loop
			} while (userChoice != 1 && userChoice != 2 && userChoice !=3);
			
			// Clear scanner buffer
			scanner.nextLine();
		
			if (userChoice == 1) {
				return 1;
			}else if(userChoice == 2) {
				return 2;
			}else {
				return 0;
			}
		}
		return 0;
	}
	
	/**
	 * This method calls to search the bids in the database. The user is prompted to 
	 * specify which element to search on, and then prompted to enter the value to
	 * search for. In the case of searching on Bid Amount, the user is prompted to
	 * enter a max value to search.
	 * @param db This is SQLDatabase
	 * @param scanner This is the scanner object that reads a user's input
	 */
	private static void searchBids(SQLDatabase db, Scanner scanner) {
		int userChoice = 0;
		String userData = "";
		
		while(userChoice != 5) {
			System.out.println("Search for....");
			System.out.println("1. Title");
			System.out.println("2. Fund");
			System.out.println("3. Vehicle");
			System.out.println("4. Bid Amount");
			System.out.println("5. Back...");
		
		// Validating user input
			do {
				// Prevents invalid input by checking for an int input from the user
				while(!scanner.hasNextInt()) {
					System.out.println("Number not entered. Please enter a valid number.");
					scanner.next();
				}
				userChoice = scanner.nextInt();
				
				// White listing user input
				if(userChoice !=1 && userChoice !=2 && userChoice !=3 && userChoice !=4 && userChoice !=5) {
					System.out.println("Invalid menu option. Please enter a valid menu option.");
				}
				//White listing conditional for do/while loop
			} while (userChoice !=1 && userChoice !=2 && userChoice !=3 && userChoice !=4 && userChoice !=5);
			
			// Clear scanner buffer
			scanner.nextLine();			
			
			switch(userChoice) {
			case 1:	// Search for Title
				System.out.println("Enter value to search for.");
				userData = scanner.nextLine();
				db.searchBid("name", userData);
				userChoice = 5;
				break;
			case 2:	// Search for Fund
				System.out.println("Enter value to search for.");
				userData = scanner.nextLine();
				db.searchBid("fund", userData);
				userChoice = 5;
				break;
			case 3:	// Search for Vehicle
				System.out.println("Enter value to search for.");
				userData = scanner.nextLine();
				db.searchBid("vehicle", userData);
				userChoice = 5;
				break;
			case 4:	// Search for Bid Amount
				System.out.println("Enter MAX value to search for.");
				while(!scanner.hasNextDouble()) {
					System.out.println("Invalid Bid Amount. Must be a number. Please Re-enter the new price.");
					scanner.nextLine();
				}
				userData = scanner.nextLine();
				db.searchBid("bid", userData);
				userChoice = 5;
				break;
			}
		}
		
	}

	/**
	 * This is the main method that prints the menu to the screen and controls the
	 * user's navigation of the menu.
	 * @param args unused
	 */
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		ItemForBid newItem = new ItemForBid();
		int userChoice = 0;
		SQLDatabase db = new SQLDatabase();
		 
		// Main menu for user selection
		while(userChoice != 9) {
			System.out.println("Menu:");
			System.out.println("1. Enter Bid");
			System.out.println("2. Display Bids");
			System.out.println("3. Update bid");
			System.out.println("4. Delete bid");
			System.out.println("5. Sort bids...");
			System.out.println("6. Search bids...");
			System.out.println("9. Exit");
			
			// Validating user input
			do {
				// Prevents invalid input by checking for an int input from the user
				while(!scanner.hasNextInt()) {
					System.out.println("Number not entered. Please enter a valid number.");
					scanner.next();
				}
				userChoice = scanner.nextInt();
				
				// White listing user input
				if(userChoice !=1 && userChoice !=2 && userChoice !=3 && userChoice !=4 && userChoice !=5 && userChoice !=6 && userChoice !=9) {
					System.out.println("Invalid menu option. Please enter a valid menu option.");
				}
				//White listing conditional for do/while loop
			} while (userChoice != 1 && userChoice != 2 && userChoice !=3 && userChoice !=4 && userChoice !=5 && userChoice !=6 && userChoice !=9);
			
			// Clear scanner buffer
			scanner.nextLine();
			
			// User input switch control.
			switch(userChoice) {
			case 1: // Enter bids
				newItem = getBid(scanner);
				db.addBid(newItem);
				break;
			case 2:	// Display all bids
				db.printStatement();
				break;
			case 3:	// update bid
				updateBid(db, scanner);
				break; 
			case 4: // Delete bid
				deleteBid(db, scanner);
				break;
			case 5:	// Sort bids by...
				sortBids(db, scanner);
				break;
			case 6:	// Search bids
				searchBids(db, scanner);
				break;
			}
		}
		// Always close the scanner!
		scanner.close();
		db.close();
	}

}
