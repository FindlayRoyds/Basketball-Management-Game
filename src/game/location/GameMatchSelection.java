package game.location;

import java.util.ArrayList;

import enumeration.Location;
import game.GameEnvironment;
import game.Team;

/**
 * A class defining the MatchSelection location.
 * The MatchSelection location provides a list of randomly generated Teams that
 * the Player can choose to play against. This list is refreshed each week to
 * have a new set of Teams.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
public class GameMatchSelection extends GameLocation {
	/**
	 * The number of teams to be generated and shown.
	 */
	static final int NUMBER_OF_TEAMS = 3;

	/**
	 * The list of Teams that are available to play a match against.
	 */
	private ArrayList<Team> teams;
	
	/**
	 * Constructor for Map
	 */
	public GameMatchSelection(GameEnvironment gameEnvironment) {
		super(gameEnvironment);
	}
	
	/**
	 * Update the week to a given week.
	 * This involves refreshing the opposition teams so that they are different each week.
	 */
	@Override
	public void update(int week) {
		teams = new ArrayList<Team>();
		int qualityLevel = 50;
		for (int i = 0; i < NUMBER_OF_TEAMS; ++i) {
			teams.add(Team.generateTeam(qualityLevel, getGameEnvironment()));
		}
	}

	/**
	 * @return		The teams that are available to play against.
	 */
	public ArrayList<Team> getTeams() {
		return teams;
	}
	
	/**
	 * Starts a match between the Player's team and the given opposingTeam.
	 * Sets up the match by updating the Match location with the corresponding teams,
	 * then changes the current location to the Match location.
	 * This is all done through calling methods in the game environment.
	 * 
	 * @param opposingTeam		The team for the Player to play against.
	 */
	public void playMatch(Team opposingTeam) {
		GameMatch matchGameLocation = (GameMatch) getGameEnvironment().getGameLocation(Location.MATCH);
		Team playerTeam = getGameEnvironment().getPlayer().getTeam();

		matchGameLocation.setTeams(playerTeam, opposingTeam);
		getGameEnvironment().changeLocation(Location.MATCH);
	}
}