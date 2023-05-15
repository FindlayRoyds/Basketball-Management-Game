package game.item;

import java.util.Random;

import game.Athlete;
import game.Purchasable;
import enumeration.Statistic;

/**
 * 
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class Steroid extends Item {
	/**
	 * How much each of the athlete's statistic is boosted
	 */
	private int boostAmount;
	
	/**
	 * The default description for a steroid item
	 */
	private final static String[] DESCRIPTIONS = {
			"Made from a high quality second hand syringe.",
			"Filled with a glowing green liquid.",
			"Not recommended by DFSNZ.",
			"Only for use by ages 3+. May contain choking hazards.",
			"WARNING: kinda spikey so watch out"
	};

	/**
	 * The constructor for Steroid
	 * 
	 * @param itemName							The name displayed with the item
	 * @param itemIsLegal						Whether the item is legal or illegal
	 * @param itemDescription					The text description displayed with the item
	 * @param price								The price it costs the player to purchase the steroid
	 */
	public Steroid(String name, boolean isLegal, String description, int price, int boostAmount) {
		super(name, isLegal, description, price);
		this.boostAmount = boostAmount;
	}
	
	/**
	 * 
	 * 
	 * @param qualityLevel						The quality level of the item. Influences randomness of generation
	 * @param rng								The random noise generator used by this instance of the game
	 * @return									The randomly generated steroid purchasable item
	 */
	public static Purchasable generateRandom(int qualityLevel, Random rng) {
		String randomDescription = DESCRIPTIONS[rng.nextInt(DESCRIPTIONS.length)];
		int randomBoostAmount = rng.nextInt(qualityLevel / 2, qualityLevel) / 5;
		int randomPrice = rng.nextInt(qualityLevel / 2, qualityLevel) / 2;
		return new Steroid("Steroid", false, randomDescription, randomPrice, randomBoostAmount);
	}

	/**
	 * Applys the steroid to the selected athlete
	 * 
	 * @param athlete							The athlete to whom the steroid is applied
	 */
	@Override
	public void applyItem(Athlete athlete) {
		for (Statistic statisticToIncrease : Statistic.values()) {
			int originalStatisticAmount = athlete.getStatistic(statisticToIncrease);
			athlete.setStatistic(statisticToIncrease, originalStatisticAmount + boostAmount);
		}
	}
	
}
