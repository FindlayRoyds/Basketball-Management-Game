package game.location;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import enumeration.Location;
import enumeration.Statistic;
import game.Athlete;
import game.GameEnvironment;
import game.Team;

/**
 * A class defining the Map location. From the map location, a player can choose
 * to go to any one of a range of other locations.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
public class GameMap extends GameLocation {
	/**
	 * Constructor for Map.
	 * 
	 * @param gameEnvironment The game environment to which the map location
	 *                        belongs.
	 */
	public GameMap(GameEnvironment gameEnvironment) {
		super(gameEnvironment);
	}

	/**
	 * Take a bye. Skips a week and sets every athlete's stamina back to full.
	 */
	public void takeABye() {
		Team playerTeam = getGameEnvironment().getPlayer().getTeam();

		// Reset every active athlete's stamina to full.
		for (Athlete athlete : playerTeam.getAllAthletes()) {
			athlete.setStamina(100);
		}

		List<Athlete> playerAthletes = new ArrayList<>(playerTeam.getAllAthletes());

		// Allow user to train a selected athlete
		if (playerAthletes.size() > 0) {
			String message = "Select an athlete to train:";
			List<String> options = playerAthletes.stream().map(athlete -> athlete.getName())
					.collect(Collectors.toList());

			// Add the option to not train any athletes
			options.add("Don't train any athletes");
			int selectedAthleteIndex = getGameEnvironment().getUIEnvironment().displayPopup(message,
					options.toArray(new String[0]));
			// Only train athletes if an athlete was selected
			if (selectedAthleteIndex != options.size() - 1) {
				Athlete selectedAthlete = playerAthletes.get(selectedAthleteIndex);
				int statisticIncrease = (4 - getGameEnvironment().getDifficulty()) * 15;

				// Increase athlete's statistics
				for (Statistic statistic : Statistic.values()) {
					int originalStatistic = selectedAthlete.getStatistic(statistic);
					selectedAthlete.setStatistic(statistic, originalStatistic + 20);
				}

				getGameEnvironment().getUIEnvironment().displayPopup(
						selectedAthlete.getName() + " had their statistics increased by " + statisticIncrease);
			}
		}

		getGameEnvironment().progressWeek();
	}

	/**
	 * @return The amount of money the player currently has
	 */
	public int getMoney() {
		return getGameEnvironment().getPlayer().getMoney();
	}

	/**
	 * @return The number of the current week. Weeks start from 1.
	 */
	public int getWeek() {
		return getGameEnvironment().getWeek();
	}

	/**
	 * Calculates and returns the number of weeks remaining in the season
	 * 
	 * @return the number of remaining weeks in the season
	 */
	public int getWeeksRemaining() {
		return getGameEnvironment().getSeasonLength() - getGameEnvironment().getWeek();
	}

	/**
	 * Takes the user to the game end screen if the game is over.
	 */
	public void checkForGameEnd() {
		if (getGameEnvironment().hasEnded()) {
			changeLocation(Location.END);
		}
	}
}
