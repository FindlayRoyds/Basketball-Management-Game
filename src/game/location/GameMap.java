package game.location;

import java.util.Set;

import enumeration.Statistic;
import game.GameEnvironment;
import game.Purchasable;
import game.Athlete;
import game.Team;

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
	
	/**
	 * Take a bye. Skips a week and sets every athlete's stamina back to full.
	 */
	public void takeABye() {
		Team playerTeam = getGameEnvironment().getPlayer().getTeam();
		
		// Reset every active athlete's stamina to full.
		for (Athlete athlete : playerTeam.getAllAthletes()) {
			athlete.setStamina(100);
		}
		
		getGameEnvironment().progressWeek();
	}
	
	/**
	 * Take a bye and train a selected Athlete.
	 */
	public void takeABye(Athlete athleteToTrain) {
		takeABye();
		
		for (Statistic statistic : Statistic.values()) {
			int originalStatistic = athleteToTrain.getStatistic(statistic);
			athleteToTrain.setStatistic(statistic, originalStatistic + 20);
		}
	}
	
	/**
	 * @return The amount of money the player currently has
	 */
	public int getMoney() {
		return getGameEnvironment().getPlayer().getMoney();
	}
	
	/**
	 * @return current week number
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
}
