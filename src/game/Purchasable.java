/**
 * abstract class for everything sold at markets in-game
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
package game;

public abstract class Purchasable {
	/**
	 * The amount of money the player is charged when purchasing from a market
	 */
	protected float price;
	
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
	public float getPrice() {
		return price;
	}
}
