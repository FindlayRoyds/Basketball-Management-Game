package game.location;

import java.util.Map;
import java.util.Set;

import enumeration.Position;
import game.Athlete;
import game.GameEnvironment;
import game.Team;

/**
 * A class for defining the Locker game location. From the Locker location a
 * Player can view the Athletes in their Team and manage which Athletes are
 * reserves or active and which positions they occupy.
 * 
 * @author Jake van Keulen
 * @version 1.0
 *
 */
public class GameLocker extends GameLocation {
	/**
	 * Constructor for Locker.
	 * 
	 * @param gameEnvironment The game environment to which the locker location
	 *                        belongs.
	 */
	public GameLocker(GameEnvironment gameEnvironment) {
		super(gameEnvironment);
	}

	/**
	 * @return The maximum number of reserve Athletes that can be in a Team.
	 */
	public int getMaxNumberOfReserves() {
		return Team.getMaxNumberOfReserves();
	}

	/**
	 * @return The name of the Player's Team.
	 */
	public String getTeamName() {
		return getGameEnvironment().getPlayer().getTeam().getName();
	}

	/**
	 * @return The Set of reserve Athletes in the Player's Team
	 */
	public Set<Athlete> getReserves() {
		return getGameEnvironment().getPlayer().getTeam().getReserveAthletes();
	}

	/**
	 * Gets the active Athletes from the Player's Team. Accesses the Player through
	 * the GameEnvironment.
	 * 
	 * @return The Map of Positions to active Athletes in the Player's Team
	 */
	public Map<Position, Athlete> getActive() {
		return getGameEnvironment().getPlayer().getTeam().getActiveAthletes();
	}

	/**
	 * Gets all the Athletes in the Player's Team.
	 * 
	 * @return A Set of all Athletes in the Player's Team.
	 */
	public Set<Athlete> getAllAthletes() {
		return getGameEnvironment().getPlayer().getTeam().getAllAthletes();
	}

	/**
	 * Moves an Athlete from the Player's active athletes to their reserves.
	 * Accesses the Player through the GameEnvironment.
	 * 
	 * @param athlete The Athlete to move
	 */
	public void moveToReserve(Athlete athlete) {
		getGameEnvironment().getPlayer().getTeam().moveToReserve(athlete);
	}

	/**
	 * Moves an Athlete from the Player's Team to a given Position in their active
	 * Athletes. Accesses the Player through the GameEnvironment.
	 * 
	 * @param athlete  The Athlete to move
	 * @param position The Position to move the Athlete to
	 */
	public void moveToActive(Athlete athlete, Position position) {
		getGameEnvironment().getPlayer().getTeam().moveToActive(athlete, position);
	}
}
