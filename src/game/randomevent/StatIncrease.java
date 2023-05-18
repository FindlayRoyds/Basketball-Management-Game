package game.randomevent;

import java.util.Random;

import game.GameEnvironment;
import game.Athlete;
import enumeration.Statistic;

/**
 * The random event for one of an athlete's statistics increasing.
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class StatIncrease extends RandomEvent {
	/**
	 * The athlete this random event effects
	 */
	private Athlete athlete;

	/**
	 * @param gameEnvironment				The game environment that the athlete belongs to.
	 * @param athlete						The athlete the random event effects.
	 */
	public StatIncrease(GameEnvironment gameEnvironment, Athlete athlete) {
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
		athlete.setStatistic(randomStatistic, originalStatisticValue + 20);
	}
	
	/**
	 * Calculates and returns the probability of one of the athlete's statistics increasing.
	 * 
	 * @return								The probability of the random event occuring in range: [0.0, 1.0)
	 */
	@Override
	protected float getProbability() {
		return 0.1f;
	}

}
