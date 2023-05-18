package game.location;

import game.GameEnvironment;

/**
 * Defines the skeleton of a GameLocation class that is
 * implemented by each game location.
 * 
 * @author Jake van Keulen
 * @version 1.0
 *
 */
public abstract class GameLocation {
	/**
	 * The game environment object to which the location belongs
	 */
	private GameEnvironment gameEnvironment;
	
	/**
	 * Constructor for game location.
	 * Sets the gameEnvironment property
	 */
	public GameLocation(GameEnvironment gameEnvironment) {
		this.gameEnvironment = gameEnvironment;
	}
	
	/**
	 * Get the game environment object to which the location belongs
	 * @return		A GameEnvironment object.
	 */
	protected GameEnvironment getGameEnvironment() {
		return gameEnvironment;
	}
	
	/**
	 * Sets the current week of the season and updates the location's data accordingly.
	 * This does different things depending on the location.
	 * 
	 * @param week		The new week of the season to update to.
	 */
	abstract public void update(int week);
}
