package game.location;

import game.Athlete;
import game.GameEnvironment;
import game.Player;
import game.Team;

/**
 * A class defining the Match location, where matches take place between 2
 * teams. A involves pairing up players between teams based on their positions,
 * then deciding who wins each individual pairing. The team that wins the most
 * pairings wins the match overall
 * 
 * @author Jake van Keulen, Findlay
 * @version 1.3
 */
public class GameMatch extends GameLocation {
	/**
	 * The 2 teams that are playing in the match.
	 */
	private Team team1;
	private Team team2;

	/**
	 * The Scores of the teams
	 */
	private int team1Score;
	private int team2Score;

	/**
	 * Constructor for Map
	 */
	public GameMatch(GameEnvironment gameEnvironment) {
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
	 * Updates the corresponding team member variables with the given values.
	 * 
	 * @param team1 The first team
	 * @param team2 The second team
	 */
	public void setTeams(Team team1, Team team2) {
		this.team1 = team1;
		this.team2 = team2;
	}

	/**
	 * @return The first team in the match.
	 */
	public Team getTeam1() {
		return team1;
	}

	/**
	 * @return The second team in the match.
	 */
	public Team getTeam2() {
		return team2;
	}

	/**
	 * @return The score of the first team
	 */
	public int getTeam1Score() {
		return team1Score;
	}

	/**
	 * @return The score of the second team
	 */
	public int getTeam2Score() {
		return team2Score;
	}

	/**
	 * Pair off two given Athletes in the Match and decide who wins. This is decided
	 * based on individual scores calculated from their statistics and positions.
	 * Deciding these scores is implemented in Athlete.getMatchScore()
	 * 
	 * @param athlete1 The first Athlete in the pairing.
	 * @param athlete2 The second Athlete in the pairing.
	 * @return The Athlete who won the pairing.
	 */
	public Athlete getWinningAthlete(Athlete athlete1, Athlete athlete2) {
		// Get the score for each athlete.
		int athlete1Score = athlete1.getMatchScore(athlete1.getRole());
		int athlete2Score = athlete2.getMatchScore(athlete2.getRole());

		// Pick the winner based on who has the higher score.
		Athlete winner = null;
		if (athlete1Score > athlete2Score)
			winner = athlete1;
		else if (athlete1Score < athlete2Score)
			winner = athlete2;
		else // If the scores are equal, pick an athlete at random.
			winner = getGameEnvironment().getRng().nextBoolean() ? athlete1 : athlete2;

		// Use up some of the athletes' stamina
		athlete1.loseStamina();
		athlete2.loseStamina();

		// Detect if athletes have been injured
		if (athlete1.isInjured() && !athlete2.isInjured())
			winner = athlete2;
		else if (athlete2.isInjured() && !athlete1.isInjured())
			winner = athlete1;

		// Increase score of winning team
		if (winner.getTeam() == team1)
			team1Score += 1;
		else
			team2Score += 1;
		return winner;
	}

	/**
	 * Decides which Team wins the Match overall, based on who won the most
	 * individual pairings. These results are accessed from the individualWinners
	 * list.
	 * 
	 * @return The Team that wins the Match overall.
	 */
	public Team getWinningTeam() {
		// Decide which team wins overall
		// There should be an odd number of individual pairings (5),
		// so no tiebreaker is required
		return team1Score > team2Score ? team1 : team2;
	}

	/**
	 * If the player's team won reward the player appropriately. The reward given is
	 * based on the difficulty of the
	 */
	public void finish() {
		Player player = getGameEnvironment().getPlayer();
		if (getWinningTeam() == player.getTeam()) {
			int difficultyMutliplier = (4 - getGameEnvironment().getDifficulty());
			int rewardMoney = 500 * difficultyMutliplier;
			int rewardPoints = 100 * difficultyMutliplier;
			player.giveMoney(rewardMoney);
			player.givePoints(rewardPoints);

			getGameEnvironment().getUIEnvironment().displayPopup("You won the match! You have been awarded "
					+ rewardMoney + " Money and " + rewardPoints + " Points.");
		}
	}
}
