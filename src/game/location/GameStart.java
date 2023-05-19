package game.location;

import game.GameEnvironment;

/**
 * A class defining the Start location. This location is used at the start of
 * the game to set up the game using user input.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
public class GameStart extends GameLocation {
	/**
	 * Constructor for Start
	 */
	public GameStart(GameEnvironment gameEnvironment) {
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

	public void setTeamName(String name) {
		getGameEnvironment().getPlayer().getTeam().setName(name);
	}

	/**
	 * Sets the number of weeks that the game can run for.
	 * 
	 * @param length The length of the game in weeks.
	 */
	public void setSeasonLength(int length) {
		getGameEnvironment().setSeasonLength(length);
	}

	/**
	 * Set the game environment's seed, for use in random number generation.
	 * 
	 * @param seed The seed to set.
	 */
	public void setSeed(int seed) {
		getGameEnvironment().setSeed(seed);
	}

	/**
	 * Set the game environment's difficulty setting.
	 * 
	 * @param difficulty An int representing the game difficulty. Can be 1, 2, or 3.
	 */
	public void setDifficulty(int difficulty) {
		getGameEnvironment().setDifficulty(difficulty);
	}
}
