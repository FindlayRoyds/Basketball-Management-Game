package game.location;

import java.util.Map;
import java.util.Set;

import game.Athlete;
import game.GameEnvironment;
import enumeration.Position;

/**
 * A class for defining the Locker game location.
 * From the Locker location a Player can view the Athletes in their Team
 * and manage which Athletes are reserves or active and which positions
 * they occupy.
 * 
 * @author Jake van Keulen
 * @version 1.0
 *
 */
public class GameLocker extends GameLocation {
	/**
	 * Constructor for Locker
	 */
	public GameLocker(GameEnvironment gameEnvironment) {
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
	
	/**
	 * Gets the reserve Athletes from the Player's Team.
	 * Accesses the Player through the GameEnvironment.
	 * 
	 * @return		The Set of reserve Athletes in the Player's Team
	 */
	public Set<Athlete> getReserves() {
		return getGameEnvironment().getPlayer().getTeam().getReserveAthletes();
	}
	
	/**
	 * Gets the active Athletes from the Player's Team.
	 * Accesses the Player through the GameEnvironment.
	 * 
	 * @return		The Map of Positions to active Athletes in the Player's Team
	 */
	public Map<Position, Athlete> getActive() {
		return getGameEnvironment().getPlayer().getTeam().getActiveAthletes();
	}
	
	/**
	 * Moves an Athlete from the Player's active athletes to their reserves.
	 * Accesses the Player through the GameEnvironment.
	 * 
	 * @param athlete		The Athlete to move
	 */
	public void moveToReserve(Athlete athlete) {
		getGameEnvironment().getPlayer().getTeam().moveToReserve(athlete);
	}
	
	/**
	 * Moves an Athlete from the Player's Team to a given Position in their active Athletes.
	 * Accesses the Player through the GameEnvironment.
	 * 
	 * @param athlete		The Athlete to move
	 * @param position		The Position to move the Athlete to
	 */
	public void moveToActive(Athlete athlete, Position position) {
		getGameEnvironment().getPlayer().getTeam().moveToActive(athlete, position);
	}
}
