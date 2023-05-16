package game;

/**
 * abstract class for everything sold at markets in-game
 * 
 * @author Findlay Royds
 * @version 1.3, May 2023.
 */
public abstract class Purchasable {
	/**
	 * The amount of money the player is charged when purchasing from a market
	 */
	protected int price;
	
	/**
	 * A constructor for for Purchasable
	 * 
	 * @param price					The price of the purchasable
	 */
	public Purchasable(int price) {
		this.price = price;
	}
	
	/**
	 * @param player				The player attempting to make the purchase
	 * @return						If the purchase was successful
	 */
	public abstract boolean purchase(Player player);
	
	/**
	 * get the price of the purchasable
	 * 
	 * @return 						The price of the purchasable
	 */
	public int getPrice() {
		return price;
	}
}
