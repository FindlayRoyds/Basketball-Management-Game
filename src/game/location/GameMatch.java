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
 * @author Jake van Keulen, Findlay Royds
 * @version 1.3
 */
public class GameMatch extends GameLocation {
	/**
	 * The first team that is playing in the match.
	 */
	private Team team1;

	/**
	 * The second team that is playing in the match.
	 */
	private Team team2;

	/**
	 * The score of the first team playing in the match.
	 */
	private int team1Score;

	/**
	 * The score of the second team playing in the match.
	 */
	private int team2Score;

	/**
	 * Constructor for GameMatch.
	 * 
	 * @param gameEnvironment The game environment to which the match location
	 *                        belongs.
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
	 * Corresponds with starting a match.
	 * 
	 * @param team1 The first team
	 * @param team2 The second team
	 */
	public void setTeams(Team team1, Team team2) {
		this.team1 = team1;
		this.team2 = team2;
		team1Score = 0;
		team2Score = 0;
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

		// Detect if athletes are injured
		if (athlete1.isInjured() && !athlete2.isInjured())
			winner = athlete2;
		else if (athlete2.isInjured() && !athlete1.isInjured())
			winner = athlete1;

		// Increase score of winning team
		if (winner.getTeam() == team1)
			team1Score += 1;
		else
			team2Score += 1;

		// remove stamina from player's athlete
		athlete1.loseStamina(athlete1 != winner);

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
	 * based on the difficulty of the game.
	 */
	public void finish() {
		Player player = getGameEnvironment().getPlayer();
		if (getWinningTeam() == player.getTeam()) {
			// Check if all of the player's athletes were injured
			boolean allAthletesInjured = true;
			for (Athlete athlete : player.getTeam().getActiveAthletes().values()) {
				if (!athlete.isInjured())
					allAthletesInjured = false;
			}

			if (allAthletesInjured) {
				getGameEnvironment().getUIEnvironment()
						.displayPopup("All of your athletes were injured! You have lost the match by default.");
			} else {
				int inverseDifficulty = (4 - getGameEnvironment().getDifficulty());
				int rewardMoney = 100 * inverseDifficulty * team1Score;
				int rewardPoints = 20 * getGameEnvironment().getDifficulty() * team1Score;
				player.giveMoney(rewardMoney);
				player.givePoints(rewardPoints);

				getGameEnvironment().getUIEnvironment()
						.displayPopup("You won the match " + team1Score + " - " + team2Score
								+ "! You have been awarded " + rewardMoney + " Money and " + rewardPoints + " Points.");
			}
		} else {
			getGameEnvironment().getUIEnvironment()
					.displayPopup("You lost the match " + team1Score + " - " + team2Score + ".");
		}
		getGameEnvironment().progressWeek();
	}
}
