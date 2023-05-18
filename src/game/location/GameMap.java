package game.location;

import game.GameEnvironment;

/**
 * A class defining the Map location.
 * From the map location, a player can choose to go to any one
 * of a range of other locations.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
public class GameMap extends GameLocation {
	/**
	 * Constructor for Map
	 */
	public GameMap(GameEnvironment gameEnvironment) {
		super(gameEnvironment);
	}
	
	/**
	 * Update the week to a given week.
	 * Does nothing in this location.
	 * Included for consistency with other location classes.
	 */
	@Override
	public void update(int week) {
		// nothing needs to happen here
	}
}
