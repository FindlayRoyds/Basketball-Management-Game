package game.location;

import enumeration.Location;

/**
 * A class defining the Map location.
 * From the map location, a player can choose to go to any one
 * of a range of other locations.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
public class Map extends GameLocation {
	/**
	 * Update the week to a given week.
	 * Does nothing in this location.
	 * Included for consistency with other location classes.
	 */
	@Override
	public void update(int week) {
		// nothing needs to happen here
	}
	
	/**
	 * Changes the current game location to a new location.
	 * Used to travel to different locations from the map.
	 * 
	 * @param location		The location to change to.
	 */
	public void changeLocation(Location location) {
		getGameEnvironment().changeLocation(location);
	}
}
