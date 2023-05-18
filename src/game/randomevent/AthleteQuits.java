package game.randomevent;

import game.GameEnvironment;
import game.Athlete;
import game.Team;

/**
 * The athlete quits random event.
 * Each new athlete creates this random event.
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class AthleteQuits extends RandomEvent {
	/**
	 * The athlete this random event effects.
	 */
	Athlete athlete;

	/**
	 * The constructor for the Athlete quits random event.
	 * 
	 * @param gameEnvironment				The game environment the athlete belongs to
	 */
	public AthleteQuits(GameEnvironment gameEnvironment, Athlete athlete) {
		super(gameEnvironment);
		this.athlete = athlete;
	}
	
	/**
	 * This method causes the athlete to quit their team.
	 */
	@Override
	protected void occur() {
		Team athletesTeam = athlete.getTeam();
		
		// Only remove the athlete if they are part of a team
		if (athletesTeam != null) {
			athlete.getTeam().removeAthlete(athlete);
		}
	}
	
	/**
	 * Calculates and returns the probability of an athlete quitting their team.
	 * The probability is based on whether or not the athlete is injured.
	 * 
	 * @return 								The probability of the athlete quitting in range: [0.0, 1.0)
	 */
	@Override
	protected float getProbability() {
		if (athlete.isInjured()) {
			return 0.1f;
		}
		return 0.025f;
	}

}
