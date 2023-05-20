package game.randomevent;

import game.Athlete;
import game.GameEnvironment;
import game.Team;

/**
 * A weekly random event that adds a new randomly generated athlete to the
 * player's team. The athlete is always added to reserves, and the probability
 * is based on the number of free slots.
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class AthleteJoins extends RandomEvent {
	/**
	 * The team the random event effects.
	 */
	private Team team;

	/**
	 * @param gameEnvironment
	 */
	public AthleteJoins(GameEnvironment gameEnvironment, Team team) {
		super(gameEnvironment);
		this.team = team;
	}

	/**
	 * Adds a new randomly generated athlete to the player's reserve. The quality of
	 * the athlete is determined by the progression through the season and the
	 * difficulty of the game.
	 */
	@Override
	protected void occur() {
		float seasonProgression = gameEnvironment.getWeek() / gameEnvironment.getSeasonLength();
		int athleteQuality = (int) seasonProgression * 80 + 10 * (3 - gameEnvironment.getDifficulty());
		Athlete newAthlete = (Athlete) Athlete.generateAthlete.apply(athleteQuality, gameEnvironment);
		team.addAthleteToReserve(newAthlete);

		// Alert the player that the event occured
		String message = newAthlete.getName() + " has joined your team as a reserve!";
		gameEnvironment.getUIEnvironment().displayPopup(message);
	}

	/**
	 * Calculates and returns the probability of a new athlete being added to a
	 * team's reserve. The probability is based on the number of free slots in the
	 * team's reserve and the difficulty of the game.
	 */
	@Override
	protected float getProbability() {
		return 0.01f * team.getNumberOfFreeReserveSlots() * (4 - gameEnvironment.getDifficulty());
	}

}
