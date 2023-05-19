package game.location;

import game.GameEnvironment;

/**
 * A class defining the End location. This location is used at the end of the
 * game to get stats about the game and player so that they can be summarized
 * and shown to the user.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
public class GameEnd extends GameLocation {
	/**
	 * Constructor for End
	 */
	public GameEnd(GameEnvironment gameEnvironment) {
		super(gameEnvironment);
	}

	/**
	 * Update the week to a given week. Does nothing in this location. Included for
	 * consistency with other location classes.
	 */
	@Override
	public void update(int week) {
		// nothing needs to happen here
	}

	/**
	 * @return The Player's score.
	 */
	public int getScore() {
		return getGameEnvironment().getPlayer().getScore();
	}

	/**
	 * @return The amount of money the Player has in dollars.
	 */
	public int getMoney() {
		return getGameEnvironment().getPlayer().getMoney();
	}

	/**
	 * @return The name of the Player's Team.
	 */
	public String getTeamName() {
		return getGameEnvironment().getPlayer().getTeam().getName();
	}

	/**
	 * @return The length of the game's season in weeks.
	 */
	public int getSeasonLength() {
		return getGameEnvironment().getSeasonLength();
	}

	/**
	 * @return The current week of the game's season. Starts from 1.
	 */
	public int getWeek() {
		return getGameEnvironment().getWeek();
	}

	/**
	 * Stops execution of the program when the game is over.
	 */
	public void endGame() {
		System.exit(0);
	}
}
