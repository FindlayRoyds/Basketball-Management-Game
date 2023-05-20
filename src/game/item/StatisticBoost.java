package game.item;

import java.util.Map;
import java.util.Random;

import enumeration.Statistic;
import game.Athlete;
import game.GameEnvironment;
import game.Purchasable;
import util.MiscUtil;

/**
 * The class for statistic boost. Will increase an athlete's particular
 * statistic when applied
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class StatisticBoost extends Item {
	/**
	 * The particular statistic which gets boosted
	 */
	private Statistic statisticToBoost;

	/**
	 * How much the statistic is boosted by
	 */
	private int boostAmount;

	private static final Map<Statistic, String[]> StatisticBoostTypes = Map.of(Statistic.HEIGHT,
			new String[] { "Insole", "A height increasing shoe insole" }, Statistic.SHOOTING_POWER,
			new String[] { "Dumbbell", "A one pound weight to improve shooting power" }, Statistic.SHOOTING_ACCURACY,
			new String[] { "Magical Biscuit", "Somehow makes anyone more accurate at shooting. No idea how" },
			Statistic.DEFENCE,
			new String[] { "SWAT Shield",
					"A bulletproof shield for better defense. Found outside the local police station" },
			Statistic.DRIBBLING,
			new String[] { "Spring Gloves", "Gloves with little springs that improve dribbling abilities" },
			Statistic.FITNESS, new String[] { "Treadmill", "A one time use disposable treadmill to improve fitness" },
			Statistic.JUMPING, new String[] { "Pogostick", "The more pogosticks you use the higher you can jump" },
			Statistic.OFFENCE, new String[] { "Sword", "A medieval weapon to improve offensive capabilities" });

	/**
	 * @param name            The name of the statistic boost item
	 * @param description     The text description given to the statistic boost item
	 * @param price           The price the player gets charged to purchase the item
	 * @param gameEnvironment The game environment the item exists in
	 */
	public StatisticBoost(String name, String description, int price, int boostAmount, Statistic statisticToBoost,
			GameEnvironment gameEnvironment) {
		super(name, true, description, price, gameEnvironment);
		this.boostAmount = boostAmount;
		this.statisticToBoost = statisticToBoost;
	}

	/**
	 * 
	 * 
	 * @param qualityLevel    The quality level of the item. Influences randomness
	 *                        of generation
	 * @param gameEnvironment The game environment the game is being created in
	 * @return The randomly generated steroid purchasable item
	 */
	public static Purchasable generateStatisticBoost(int qualityLevel, GameEnvironment gameEnvironment) {
		Random rng = gameEnvironment.getRng();
		int randomIndex = rng.nextInt(StatisticBoostTypes.size());
		int difficulty = gameEnvironment.getDifficulty();

		// Clamp the quality level in range [0, 100]
		qualityLevel = MiscUtil.clampValue(qualityLevel, 1, 100);

		Statistic randomStatistic = Statistic.values()[randomIndex];
		String randomName = StatisticBoostTypes.get(randomStatistic)[0];
		String randomDescription = StatisticBoostTypes.get(randomStatistic)[1];

		int randomBoostAmount = (rng.nextInt(qualityLevel) + qualityLevel) / 4; // In range [0, 50]
		int randomPrice = (rng.nextInt(qualityLevel) + 3 * qualityLevel) / 4 * difficulty; // In range [0, 300]

		return new StatisticBoost(randomName, randomDescription, randomPrice, randomBoostAmount, randomStatistic,
				gameEnvironment);
	}

	/**
	 * increases one of the athlete's statistics and consumes the item
	 * 
	 * @param athlete The athlete to whom the affect is being applied
	 */
	@Override
	public void applyItem(Athlete athlete) {
		int originalStatisticAmount = athlete.getStatistic(statisticToBoost);
		athlete.setStatistic(statisticToBoost, originalStatisticAmount + boostAmount);
		this.consume();
	}

	@Override
	public String getDetails() {
		return "Boosted statistic: " + statisticToBoost.name() + "\nBoost amount: " + boostAmount
				+ "\nBoosts an athlete's statistic by boost amount";
	}

}
