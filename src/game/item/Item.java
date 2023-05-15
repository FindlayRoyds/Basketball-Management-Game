package game.item;

import java.util.Map;
import java.util.Random;

import game.Player;
import game.Purchasable;
import game.GameEnvironment;
import game.Athlete;
import game.item.Bandaid;
import game.item.StatisticBoost;

/**
 * This class is the abstract class for items in game
 * It implements purchasing an item, generating items,
 * and determining whether an item is legal
 * 
 * @author Findlay Royds
 * @version 1.1, May 2023.
 */
public abstract class Item extends Purchasable {
	/**
	 * The name describing the item
	 */
	private String name;
	
	/**
	 * A text description of the item
	 */
	private String description;
	
	/**
	 * Whether the item is legal or illegal
	 */
	private boolean isLegal;
	
	/**
	 * The constructor for Item
	 * 
	 * @param itemName						The name of the item
	 * @param itemIsLegal					Whether the item is legal or illegal
	 * @param itemDescription				Text describing the item
	 * @param price							The cost of purchasing the item
	 * @param gameEnvironment				The current gameEnvironment
	 */
	public Item(String name, boolean isLegal, String description, int price) {
		super(price);
		this.name = name;
		this.isLegal = isLegal;
		this.description = description;
	}
	
	/**
	 * The abstract apply item method
	 * Calling this method with any item will apply the item's effects to the given athlete
	 * 
	 * @param athlete						The athlete to whom the affect is being applied
	 */
	public abstract void applyItem(Athlete athlete);
	
	/**
	 * Purchase the item and put it into the player's inventory
	 * 
	 * @param player						The player who is purchasing the item
	 * @return 								Whether or not the purchase was succesfull
	 */
	public boolean purchase(Player player) {
		boolean chargeSuccess = player.chargeMoney(price);
		
		if (chargeSuccess) {
			player.addToInventory(this);
		}
		return chargeSuccess;
	}
	
	/**
	 * Generate a stat boost item or a bandaid item
	 * 
	 * @param qualityLevel					The quality of the item in range: [0, 100]
	 * @return								A randomly generated legal item
	 */
	public static Item generateLegalItem(int qualityLevel, Random rng) {
		int randomInteger = rng.nextInt(10);
		if (randomInteger == 0) {
			return Bandaid.generateRandom(qualityLevel, rng);
		}
		return StatisticBoost.generateRandom(qualityLevel, rng);
	}
	
	/**
	 * Get whether the item is legal or illegal
	 * 
	 * @return 								Whether the item is legal or illegal
	 */
	public boolean getIsLegal() {
		return isLegal;
	}
	
	/**
	 * Get the name of the item
	 * 
	 * @return 								The name of the item
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the text description of the item
	 * 
	 * @return 								A text description of the item
	 */
	public String getDescription() {
		return description;
	}
 }
