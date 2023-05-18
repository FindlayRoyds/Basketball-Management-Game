package game;

/**
 * abstract class for everything sold at markets in-game
 * 
 * @author Findlay Royds
 * @version 1.4, May 2023.
 */
public abstract class Purchasable {
	/**
	 * The amount of money the player is charged when purchasing from a market
	 */
	protected int price;
	
	/**
	 * Whether the item is legal or illegal
	 */
	private boolean isLegal;
	
	/**
	 * A constructor for for Purchasable
	 * 
	 * @param price					The price of the purchasable
	 */
	public Purchasable(int price, boolean isLegal) {
		this.price = price;
		this.isLegal = isLegal;
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
	
	/**
	 * Get whether the item is legal or illegal
	 * 
	 * @return 								Whether the item is legal or illegal
	 */
	public boolean getIsLegal() {
		return isLegal;
	}
}
