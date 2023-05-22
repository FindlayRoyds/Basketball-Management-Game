package game.randomevent;

import java.util.Set;

import enumeration.Location;
import game.Athlete;
import game.GameEnvironment;
import game.Team;

/**
 * A random drug test event that is triggered weekly. Every athlete in the
 * player's team is tested for drug use. If any athletes are found to have used
 * steroids, the game ends.
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class DrugTest extends RandomEvent {

	/**
	 * The constructor for the drug test random event.
	 * 
	 * @param gameEnvironment The game environment to which the random event
	 *                        belongs.
	 */
	public DrugTest(GameEnvironment gameEnvironment) {
		super(gameEnvironment);
	}

	/**
	 * Tests every athlete in the player's team for steroid use. If any athletes
	 * have used steroids, the game location is changed to the end location
	 */
	@Override
	protected void occur() {
		boolean passedDrugTest = true;
		Athlete athleteDetected = null;
		final Team playerTeam = gameEnvironment.getPlayer().getTeam();
		final Set<Athlete> playerAthletes = playerTeam.getAllAthletes();

		// Test if any athletes have used steroids
		for (Athlete athlete : playerAthletes) {
			if (athlete.getHasUsedSteroids()) {
				passedDrugTest = false;
				athleteDetected = athlete;
				break;
			}
		}

		// If any athletes failed the drug test, end the game
		if (!passedDrugTest) {
			gameEnvironment.changeLocation(Location.END);

			final String popupMessage = athleteDetected.getName()
					+ " failed a randomised drug test! You have been disqualified from the tournament.";
			gameEnvironment.getUIEnvironment().displayPopup(popupMessage);
		} else {
			final String popupMessage = "Your team passed a randomised drug test.";
			gameEnvironment.getUIEnvironment().displayPopup(popupMessage);
		}
	}

	/**
	 * Calculate and return the probability of a randomised drug test occuring.
	 * Probability is based on how far through the tournament you are in the game
	 * and the difficulty of the game.
	 * 
	 * @return The probability of the event occuring in range: [0.0, 1.0)
	 */
	@Override
	protected float getProbability() {
		final float tournamentProgression = gameEnvironment.getWeek() / gameEnvironment.getSeasonLength();
		return 0.2f * tournamentProgression * gameEnvironment.getDifficulty();
	}
}
