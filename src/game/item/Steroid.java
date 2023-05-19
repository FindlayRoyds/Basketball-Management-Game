package game.item;

import java.util.Random;

import enumeration.Statistic;
import game.Athlete;
import game.GameEnvironment;
import game.Purchasable;
import util.Function3;

/**
 * 
 * 
 * @author Findlay Royds
 * @version 1.1, May 2023.
 */
public class Steroid extends Item {
	/**
	 * How much each of the athlete's statistic is boosted
	 */
	private int boostAmount;

	/**
	 * An array of default descriptions for a steroid item
	 */
	private final static String[] DESCRIPTIONS = { "Made from a high quality second hand syringe.",
			"Filled with a glowing green liquid.", "Not recommended by DFSNZ.",
			"Only for use by ages 3+. May contain choking hazards.", "WARNING: kinda spikey so watch out" };

	/**
	 * The constructor for Steroid
	 * 
	 * @param itemDescription The text description displayed with the item
	 * @param price           The price it costs the player to purchase the steroid
	 */
	public Steroid(String description, int price, int boostAmount, GameEnvironment gameEnvironment) {
		super("Steroid", false, description, price, gameEnvironment);
		this.boostAmount = boostAmount;
	}

	/**
	 * 
	 * 
	 * @param qualityLevel    The quality level of the item. Influences randomness
	 *                        of generation
	 * @param gameEnvironment The game environment the game is being created in
	 * @return The randomly generated steroid purchasable item
	 */
	public static Function3<Integer, GameEnvironment, Purchasable> generateSteroid = (qualityLevel,
			gameEnvironment) -> {
		Random rng = gameEnvironment.getRng();
		int difficulty = gameEnvironment.getDifficulty();
		String randomDescription = DESCRIPTIONS[rng.nextInt(DESCRIPTIONS.length)];
		int randomBoostAmount = (rng.nextInt(qualityLevel) + qualityLevel) / 2; // In range [0, 100]
		int randomPrice = (rng.nextInt(qualityLevel) + 3 * qualityLevel) * 5 * difficulty; // In range [0, 1500]
		return new Steroid(randomDescription, randomPrice, randomBoostAmount, gameEnvironment);
	};

	/**
	 * Applys the steroid to the selected athlete
	 * 
	 * @param athlete The athlete to whom the steroid is applied
	 */
	@Override
	public void applyItem(Athlete athlete) {
		for (Statistic statisticToIncrease : Statistic.values()) {
			int originalStatisticAmount = athlete.getStatistic(statisticToIncrease);
			athlete.setStatistic(statisticToIncrease, originalStatisticAmount + boostAmount);
		}

		// make the athlete able to be detected by a random drug test
		athlete.setHasUsedSteroids(true);

		this.consume();
	}

}
