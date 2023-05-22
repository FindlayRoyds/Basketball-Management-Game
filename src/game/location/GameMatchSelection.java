package game.location;

import java.util.ArrayList;
import java.util.Map;

import enumeration.Location;
import enumeration.Position;
import game.Athlete;
import game.GameEnvironment;
import game.Team;
import util.MiscUtil;

/**
 * A class defining the MatchSelection location. The MatchSelection location
 * provides a list of randomly generated Teams that the Player can choose to
 * play against. This list is refreshed each week to have a new set of Teams.
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
	 * Update the week to a given week. This involves refreshing the opposition
	 * teams so that they are different each week.
	 */
	@Override
	public void update(int week) {
		float seasonProgression = getGameEnvironment().getWeek() / (float) getGameEnvironment().getSeasonLength();
		int startQuality = getGameEnvironment().getDifficulty() * 10 + 10;
		int endQuality = getGameEnvironment().getDifficulty() * 15 + 55;
		int qualityLevel = MiscUtil.integerLerp(startQuality, endQuality, seasonProgression);

		teams = new ArrayList<Team>();
		for (int i = 0; i < NUMBER_OF_TEAMS; ++i) {
			Team generatedTeam = Team.generateTeam(qualityLevel, getGameEnvironment());
			teams.add(Team.generateTeam(qualityLevel, getGameEnvironment()));

			// Increase active athlete's stamina to balance matches
			for (Athlete athlete : generatedTeam.getActiveAthletes().values())
				athlete.setStamina(Math.max(40, athlete.getStamina()));
		}
	}

	/**
	 * @return The teams that are available to play against.
	 */
	public ArrayList<Team> getTeams() {
		return teams;
	}

	/**
	 * Starts a match between the Player's team and the given opposingTeam. Sets up
	 * the match by updating the Match location with the corresponding teams, then
	 * changes the current location to the Match location. This is all done through
	 * calling methods in the game environment.
	 * 
	 * @param opposingTeam The team for the Player to play against.
	 */
	public void playMatch(Team opposingTeam) {
		GameMatch matchGameLocation = (GameMatch) getGameEnvironment().getGameLocation(Location.MATCH);
		Team playerTeam = getGameEnvironment().getPlayer().getTeam();

		matchGameLocation.setTeams(playerTeam, opposingTeam);
		getGameEnvironment().changeLocation(Location.MATCH);
	}

	/**
	 * Get whether a match is able to start. A match can only start when the
	 * player's team has 5 active athletes,
	 */
	public boolean canStartMatch() {
		Team playerTeam = getGameEnvironment().getPlayer().getTeam();
		Map<Position, Athlete> activeAthletes = playerTeam.getActiveAthletes();
		boolean canStart = false;

		// Make sure that the player has a full team,
		// and not all of the athletes are injured
		for (Position position : Position.values()) {
			Athlete athlete = activeAthletes.get(position);
			if (athlete == null)
				return false;
			if (!athlete.isInjured())
				canStart = true;
		}
		return canStart;
	}
}