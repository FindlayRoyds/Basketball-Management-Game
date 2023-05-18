package game.randomevent;

import game.GameEnvironment;
import game.Athlete;
import game.Team;

/**
 * A weekly random event that adds a new randomly generated athlete to the player's team.
 * The athlete is always added to reserves, and the probability is based on the number of free slots.
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class AthleteJoins extends RandomEvent {
	/**
	 * The team the random event effects
	 */
	Team team;

	/**
	 * @param gameEnvironment
	 */
	public AthleteJoins(GameEnvironment gameEnvironment, Team team) {
		super(gameEnvironment);
		this.team = team;
	}

	@Override
	protected void occur() {
		team.addAthleteToReserve(Athlete.generateAthlete(40, gameEnvironment));
	}
	
	/**
	 * Calculates and returns the probability of a new athlete being added to a team's reserve.
	 * The probability is based on the number of free slots in the team's reserve.
	 */
	@Override
	protected float getProbability() {
		return 0.05f * team.getNumberOfFreeReserveSlots();
	}

}
