package game.location;

import enumeration.Location;
import game.GameEnvironment;

/**
 * Defines the skeleton of a GameLocation class that is implemented by each game
 * location.
 * 
 * @author Jake van Keulen
 * @version 1.0
 *
 */
public abstract class GameLocation {
	/**
	 * The game environment object to which the location belongs.
	 */
	private GameEnvironment gameEnvironment;

	/**
	 * Constructor for game location.
	 * 
	 * @param gameEnvironment The game environment to which the location belongs.
	 */
	public GameLocation(GameEnvironment gameEnvironment) {
		this.gameEnvironment = gameEnvironment;
	}

	/**
	 * Get the game environment object to which the location belongs
	 * 
	 * @return The location's game environment.
	 */
	protected GameEnvironment getGameEnvironment() {
		return gameEnvironment;
	}

	/**
	 * Sets the current week of the season and updates the location's data
	 * accordingly. This does different things depending on the location.
	 * 
	 * @param week The new week of the season to update to.
	 */
	public abstract void update(int week);

	/**
	 * Changes the current game location to a new location. Used to travel to
	 * different locations.
	 * 
	 * @param location The location to change to.
	 */
	public void changeLocation(Location location) {
		getGameEnvironment().changeLocation(location);
	}
}
