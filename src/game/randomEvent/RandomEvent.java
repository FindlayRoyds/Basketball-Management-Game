package game.randomEvent;

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
	 * The abstract trigger method.
	 * When called the random event should randomly determine whether the event should occur.
	 * If the event should occur, this method should contain the functionality for the event
	 */
	public abstract void trigger();
}
