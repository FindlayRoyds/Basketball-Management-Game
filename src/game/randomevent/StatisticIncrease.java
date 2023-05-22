package game.randomevent;

import java.util.Random;

import enumeration.Statistic;
import game.Athlete;
import game.GameEnvironment;

/**
 * The random event for one of an athlete's statistics increasing.
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class StatisticIncrease extends RandomEvent {
	/**
	 * The athlete this random event affects
	 */
	private Athlete athlete;

	/**
	 * Constructor for the statistic increase random event. Sets the athlete that
	 * the random event effects.
	 * 
	 * @param gameEnvironment The game environment that the athlete belongs to.
	 * @param athlete         The athlete the random event affects.
	 */
	public StatisticIncrease(GameEnvironment gameEnvironment, Athlete athlete) {
		super(gameEnvironment);
		this.athlete = athlete;
	}

	/**
	 * This method causes a random one of the athlete's statistics to increase.
	 */
	@Override
	protected void occur() {
		Random rng = gameEnvironment.getRng();
		Statistic[] statistics = Statistic.values();
		Statistic randomStatistic = statistics[rng.nextInt(statistics.length)];
		int originalStatisticValue = athlete.getStatistic(randomStatistic);
		// Increase statistic depending on the difficulty of the game
		int increaseAmount = 10 * (4 - gameEnvironment.getDifficulty());
		athlete.setStatistic(randomStatistic, originalStatisticValue + increaseAmount);

		// Alert user that the event occured
		String message = "Your athlete " + athlete.getName() + " had their " + randomStatistic.name().toLowerCase()
				+ " statistic increased by " + increaseAmount;
		gameEnvironment.getUIEnvironment().displayPopup(message);
	}

	/**
	 * Calculates and returns the probability of one of the athlete's statistics
	 * increasing. The probability is determined by the game's difficulty.
	 * 
	 * @return The probability of the random event occuring in range: [0.0, 1.0).
	 */
	@Override
	protected float getProbability() {
		return (4 - gameEnvironment.getDifficulty()) / 100f;
	}

}
