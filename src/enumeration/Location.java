package enumeration;

/**
 * This enum defines the locations in the game
 * 
 * @author Jake van Keulen
 * @version 1.0, May 2023.
 */
public enum Location {
	/**
	 * The game setup
	 */
	START,
	/**
	 * The game map
	 */
	MAP,
	/**
	 * The player's inventory
	 */
	INVENTORY,
	/**
	 * The player's athletes
	 */
	LOCKER_ROOM,
	/**
	 * Allows the user to select a match to play against
	 */
	MATCH_SELECTION,
	/**
	 * A match between two teams
	 */
	MATCH,
	/**
	 * Allows the user to purchase new athletes
	 */
	ATHLETE_MARKET,
	/**
	 * Allows the user to purchase new items
	 */
	ITEM_MARKET,
	/**
	 * Allows the user to purchase steroid items
	 */
	BLACK_MARKET,
	/**
	 * Displays the game's stats at the end of the game
	 */
	END
}
