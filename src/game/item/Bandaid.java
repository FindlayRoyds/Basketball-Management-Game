package game.item;

import java.util.Random;

import game.Athlete;
import game.GameEnvironment;
import game.Purchasable;
import util.MiscUtil;

/**
 * The class for bandaid. Restores an athlete's stamina if they're injured
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class Bandaid extends Item {
	/**
	 * An array of default descriptions for a bandaid item
	 */
	private final static String[] DESCRIPTIONS = {
			"Fixes cuts, scrapes, broken bones, spinal cord trauma, and all other injuries known to human kind",
			"Doctors hate this one simple trick", "I've run out of description ideas sorry" };

	/**
	 * The constructor for Bandaid
	 * 
	 * @param itemIsLegal     Whether the item is legal or illegal
	 * @param itemDescription Text describing the item
	 * @param price           The cost of purchasing the item
	 */
	public Bandaid(String description, int price, GameEnvironment gameEnvironment) {
		super("Bandaid", true, description, price, gameEnvironment);
	}

	/**
	 * 
	 * 
	 * @param qualityLevel    The quality level of the item. Influences randomness
	 *                        of generation
	 * @param gameEnvironment The game environment the game is being created in
	 * @return The randomly generated bandaid purchasable item
	 */
	public static Purchasable generateBandaid(int qualityLevel, GameEnvironment gameEnvironment) {
		Random rng = gameEnvironment.getRng();
		int difficulty = gameEnvironment.getDifficulty();

		// Clamp the quality level in range [0, 100]
		qualityLevel = MiscUtil.clampValue(qualityLevel);

		String randomDescription = DESCRIPTIONS[rng.nextInt(DESCRIPTIONS.length)];
		int randomPrice = (rng.nextInt(qualityLevel) + 3 * qualityLevel) / 5 * difficulty; // In range [0, 240]
		return new Bandaid(randomDescription, randomPrice, gameEnvironment);
	}

	/**
	 * If the athlete is injured their stamina will be restored and the bandaid
	 * consumed
	 * 
	 * @param athlete The athlete to whom the affect is being applied
	 */
	@Override
	public void applyItem(Athlete athlete) {
		if (athlete.isInjured()) {
			athlete.setStamina(100);
			this.consume();
		}
	}

}
