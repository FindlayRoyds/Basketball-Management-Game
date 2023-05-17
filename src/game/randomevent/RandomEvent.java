package game.randomevent;

import java.util.Random;

import game.GameEnvironment;

/**
 * The abstract random event class.
 * Contains the abstract trigger() method
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public abstract class RandomEvent {
	/**
	 * Stores the game environment the random event was created in
	 */
	GameEnvironment gameEnvironment;

	/**
	 * The constructor for the random event abstract class
	 * 
	 * @param gameEnvironment				The game environment the random event is being created in
	 */
	public RandomEvent(GameEnvironment gameEnvironment) {
		this.gameEnvironment = gameEnvironment;
	}
	
	/**
	 * The abstract method that causes the random event to actually happen.
	 */
	protected abstract void occur();
	
	/**
	 * Calculates and returns the probability that the event will actually occur.
	 * 
	 * @return								The probability of the event occuring in range: [0.0, 1.0)
	 */
	protected abstract float getProbability();
	
	/**
	 * Randomly determines whether the event should occur based on the event's probability.
	 * If it should, the occur() method is called by this method
	 */
	public void trigger() {
		Random rng = gameEnvironment.getRng();
		float probability = getProbability();
		
		if (rng.nextFloat() < probability) {
			occur();
		}
	}
}
