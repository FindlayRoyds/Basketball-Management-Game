package game.location;

import java.util.ArrayList;

import game.Athlete;
import game.Team;

/**
 * A class defining the Match location, where matches take place between 2 teams.
 * A involves pairing up players between teams based on their positions,
 * then deciding who wins each individual pairing.
 * The team that wins the most pairings wins the match overall
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
public class Match extends GameLocation {
	/**
	 * The 2 teams that are playing in the match.
	 */
	private Team team1;
	private Team team2;
	
	/**
	 * A list of Athletes that win their individual pairings in the match.
	 */
	private ArrayList<Athlete> individualWinners;
	
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
	 * Updates the corresponding team member variables with the given values.
	 * 
	 * @param team1			The first team
	 * @param team2			The second team
	 */
	public void setTeams(Team team1, Team team2) {
		this.team1 = team1;
		this.team2 = team2;
	}
	
	/**
	 * @return			The first team in the match.
	 */
	public Team getTeam1() {
		return team1;
	}
	/**
	 * @return			The second team in the match.
	 */
	public Team getTeam2() {
		return team2;
	}
	
	/**
	 * Pair off two given Athletes in the Match and decide who wins.
	 * This is decided based on individual scores calculated from their statistics and positions.
	 * Deciding these scores is implemented in Athlete.getMatchScore()
	 * 
	 * @param athlete1			The first Athlete in the pairing.
	 * @param athlete2			The second Athlete in the pairing.
	 * @return					The Athlete who won the pairing.
	 */
	public Athlete getIndividualWinner(Athlete athlete1, Athlete athlete2) {
		// Get the score for each athlete.
		int athlete1Score = athlete1.getMatchScore(athlete1.getRole());
		int athlete2Score = athlete2.getMatchScore(athlete2.getRole());
		
		// Pick the winner based on who has the higher score.
		Athlete winner = null;
		if (athlete1Score > athlete2Score)
			winner = athlete1;
		else if (athlete1Score < athlete2Score)
			winner = athlete2;
		else  // If the scores are equal, pick an athlete at random.
			winner = getGameEnvironment().getRng().nextBoolean() ? athlete1 : athlete2;
		
		// Save who won for later reference.
		individualWinners.add(winner);
		return winner;
	}
	
	/**
	 * Decides which Team wins the Match overall,
	 * based on who won the most individual pairings.
	 * These results are accessed from the individualWinners list.
	 * 
	 * @return			The Team that wins the Match overall.
	 */
	public Team getOverallWinner() {
		// Count how many individual wins each team got.
		int team1Wins = 0, team2Wins = 0;
		for (Athlete winner: individualWinners) {
			if (winner.getTeam() == getTeam1()) 
				++team1Wins;
			else
				++team2Wins;
		}
		
		// Decide which team wins overall
		// There should be an odd number of individual pairings (5),
		// so no tiebreaker is required
		return team1Wins > team2Wins ? team1 : team2;
	}
	
	public void finish() {
		// TODO: decide if we actually need this or not?
	}
}
