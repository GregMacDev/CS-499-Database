package enhancement;
/**
 * This class contains the structure to hold the data for ItemForBid
 * @author Greg MacPhelemy
 *
 */
public class ItemForBid {
	
	// Object variables
	private String itemTitle;
	private String fundType;
	private String vehicleType;
	private double bidAmount;
	
	/**
	 * This is the default class constructor
	 */
	public ItemForBid(){
		itemTitle = "";
		fundType = "";
		vehicleType = "";
		bidAmount = 0.0;
	}
	
	/**
	 * This is the class constructor
	 * @param title This is the title of the bid
	 * @param fund This is the fund the bid belongs to
	 * @param vehicle This is the vehicle type of the bid
	 * @param bid This is the amount of the bid
	 */
	public ItemForBid(String title, String fund, String vehicle, double bid){
		itemTitle = title;
		fundType = fund;
		vehicleType = vehicle;
		bidAmount = bid;
	}
	
	// Setters and Getters for each class variable
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	
	public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	public double getBidAmount() {
		return bidAmount;
	}
	public void setBidAmount(double bidAmount) {
		this.bidAmount = bidAmount;
	}
}
