package enhancement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains the functionality to interact with the MySQL database.
 * @author Greg MacPhelemy
 *
 */

public class SQLDatabase {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;	
	private PreparedStatement preparedStatement = null;
	
	private String url = "";
	private String user = "";
	private String password = "";
	
	// Constructor
	public SQLDatabase() {
		url = "jdbc:mysql://localhost:3306/testdb?useSSL=false";
        user = "testuser";
        password = "test623";
        
		try {
			connect = DriverManager.getConnection(url, user, password);
		} catch(SQLException ex) {
			Logger lgr = Logger.getLogger(App.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
	
	/**
	 * This method prints all of the bids from the data base, bids table.
	 * @return none
	 */
	public void printStatement() {
		String query = "SELECT * FROM bids";
		int bidCount = 0;
		
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
			
			// Verifies there are results to display
			if(resultSet.next() == false) {
				System.out.println("No Results Found");
			}else {			
				do {
					System.out.printf("%6s%s\n"," ", "Bid " + ++bidCount + ": ");
	                System.out.printf("%-12s%s\n","Title: ", resultSet.getString("Name") );
	                System.out.printf("%-12s%s\n","Fund: ", resultSet.getString("fund"));
	                System.out.printf("%-12s%s\n","Vehicle: ", resultSet.getString("vehicle"));
	                System.out.printf("%-12s%.2f\n\n","Bid Amount: $", resultSet.getDouble("bid"));
	                System.out.println();	                
				}while(resultSet.next());
			}
		} catch(SQLException ex) {
			Logger lgr = Logger.getLogger(App.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
	
	/**
	 * This method takes in an ItemForBid item and adds it to the existing database.
	 * @param bid this is the bid to add to the database
	 * @return none
	 */
	public void addBid(ItemForBid bid) {
		String update = "INSERT INTO bids(NAME, FUND, VEHICLE, BID) VALUES (?, ?, ?, ?)";
		
		try {
			preparedStatement = connect.prepareStatement(update);
			preparedStatement.setString(1, bid.getItemTitle());
			preparedStatement.setString(2, bid.getFundType());
			preparedStatement.setString(3, bid.getVehicleType());
			preparedStatement.setDouble(4, bid.getBidAmount());
			preparedStatement.executeUpdate();
			System.out.println("New bid Entered...");
			
		} catch(SQLException ex) {
			Logger lgr = Logger.getLogger(App.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
	/**
	 * This method deletes an existing bid in the database.
	 * @param title this is the title of the bid to be deleted.
	 * @return none
	 */
	public void deleteBid(String title) {
		String delete = "DELETE FROM bids WHERE NAME = ?";
		int success = 0;
		
		try {
			preparedStatement = connect.prepareStatement(delete);
			preparedStatement.setString(1, title);
			success = preparedStatement.executeUpdate();
		}catch(SQLException ex) {
			Logger lgr = Logger.getLogger(App.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		
		if (success !=0) {
			System.out.println(title + " delete successfully.");
		}else {
			System.out.println(title + " not found.");
		}
	}
	
	/**
	 * This method updates an existing bid within the database.
	 * @param itemName This is the Title of the item to be updated
	 * @param column This is the column the user wishes to update
	 * @param data This is the data that the user wishes to use to update the bid
	 */
	public void updateBid(String itemName, String column, String data) {
		String update = "";
		double bidData = 0.0;
		
		try {
			if(column.equals("bid")) {
				bidData = Double.parseDouble(data);
				update = "UPDATE bids SET " + column + " = ?" + " WHERE NAME = ?"; 
				
				preparedStatement = connect.prepareStatement(update);
				preparedStatement.setDouble(1, bidData);
				preparedStatement.setString(2, itemName);
				
			}else {		
				update = "UPDATE bids SET " + column + " = ?" + " WHERE NAME = ?";
				preparedStatement = connect.prepareStatement(update);
				preparedStatement.setString(1, data);
				preparedStatement.setString(2, itemName);
			}
			
			preparedStatement.executeUpdate();
			
		} catch(SQLException ex) {
			Logger lgr = Logger.getLogger(App.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}		
	}
	
	/**
	 * This method sorts the data in the MySQL database and prints the sorted order
	 * to the screen.
	 * @param column this is the column to sort the data on
	 * @param order this is the order in which the data is sorted. ASC or DESC.
	 */
	public void sortBids(String column, String order) {
		String sort = "Select * FROM bids ORDER BY " + column + " " + order;
		int bidCount = 0;
		
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery(sort);
			
			// Verifies there are results to display
			if(resultSet.next() == false) {
				System.out.println("No Results Found");
			}else {			
				do {
					System.out.printf("%6s%s\n"," ", "Bid " + ++bidCount + ": ");
	                System.out.printf("%-12s%s\n","Title: ", resultSet.getString("Name") );
	                System.out.printf("%-12s%s\n","Fund: ", resultSet.getString("fund"));
	                System.out.printf("%-12s%s\n","Vehicle: ", resultSet.getString("vehicle"));
	                System.out.printf("%-12s%.2f\n\n","Bid Amount: $", resultSet.getDouble("bid"));
	                System.out.println();	                
				}while(resultSet.next());
			}
			
		} catch(SQLException ex) {
			Logger lgr = Logger.getLogger(App.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
	
	/**
	 * This method searches the database for data within a certain column
	 * @param column the column to search on
	 * @param data the data to search for
	 */
	public void searchBid(String column, String data) {
		String search = "";
		double bidData = 0.0;
		int bidCount = 0;
		/*
		// Check for searching on Bid Amount to change the search from an equals to
		// a less than the passed value. Also changes passed data from string to
		// double.
		if(column.equals("bid")) {
			bidData = Double.parseDouble(data);
			search = "SELECT * FROM bids WHERE " + column + "<= " + bidData 
					+ "ORDER BY " + column + " ASC"; 
		}else {		
			search = "SELECT * FROM bids WHERE " + column + "= '" + data + "' ORDER BY "
					+ column + " ASC";
		}
		*/
		try {
			// Check for searching on Bid Amount to change the search from an equals to
			// a less than the passed value. Also changes passed data from string to
			// double.
			if(column.equals("bid")) {
				bidData = Double.parseDouble(data);
				search = "SELECT * FROM bids WHERE " + column + "<= ? ORDER BY " + column + " ASC"; 
				preparedStatement = connect.prepareStatement(search);
				preparedStatement.setDouble(1, bidData);
			}else {		
				search = "SELECT * FROM bids WHERE " + column + "= ? ORDER BY "
						+ column + " ASC";
				preparedStatement = connect.prepareStatement(search);
				preparedStatement.setString(1, data);
			}
			resultSet = preparedStatement.executeQuery();
			
			// Verifies there are results to display
			if(resultSet.next() == false) {
				System.out.println("No Results Found");
			}else {			
				do {
					System.out.printf("%6s%s\n"," ", "Bid " + ++bidCount + ": ");
	                System.out.printf("%-12s%s\n","Title: ", resultSet.getString("Name") );
	                System.out.printf("%-12s%s\n","Fund: ", resultSet.getString("fund"));
	                System.out.printf("%-12s%s\n","Vehicle: ", resultSet.getString("vehicle"));
	                System.out.printf("%-12s%.2f\n\n","Bid Amount: $", resultSet.getDouble("bid"));
	                System.out.println();	                
				}while(resultSet.next());
			}
		} catch(SQLException ex) {
			Logger lgr = Logger.getLogger(App.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}				
	}
	
	/**
	 * This method closes the database to free up resources.
	 */
	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
			    statement.close();
			}

			if (connect != null) {
			    connect.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (Exception e) {

		}
	}	
}
