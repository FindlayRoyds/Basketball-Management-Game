package test.gametest.locationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import enumeration.Location;
import enumeration.Position;
import enumeration.Statistic;
import game.Athlete;
import game.GameEnvironment;
import game.Team;
import game.location.GameMatch;
import userinterface.graphical.GUIEnvironment;

class GameMatchTest {
	private GameEnvironment gameEnvironment;
	private GameMatch gameMatch;

	@BeforeEach
	void setUp() {
		gameEnvironment = new GameEnvironment(false);
		gameEnvironment.setSeed(0);
		gameEnvironment.setSeasonLength(5);
		gameMatch = (GameMatch) gameEnvironment.getGameLocation(Location.MATCH);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) gameEnvironment.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		gameMatch = new GameMatch(gameEnvironment);
	}

	@Test
	void updateTest() {
		for (int i = 0; i <= 15; ++i)
			gameMatch.update(i);
	}

	@Test
	void teamsTest() {
		Team team1 = Team.generateTeam(50, gameEnvironment);
		Team team2 = Team.generateTeam(50, gameEnvironment);
		gameMatch.setTeams(team1, team2);

		assertEquals(team1, gameMatch.getTeam1());
		assertEquals(team2, gameMatch.getTeam2());

		assertEquals(0, gameMatch.getTeam1Score());
		assertEquals(0, gameMatch.getTeam2Score());
	}

	@RepeatedTest(5)
	void playTest() {
		Team team1 = Team.generateTeam(50, gameEnvironment);
		Team team2 = Team.generateTeam(50, gameEnvironment);
		gameMatch.setTeams(team1, team2);

		int score1 = 0, score2 = 0;
		for (Position position : Position.values()) {
			Athlete winner = gameMatch.getWinningAthlete(team1.getActiveAthletes().get(position),
					team2.getActiveAthletes().get(position));
			assertNotNull(winner);
			if (winner.getTeam() == team1)
				++score1;
			else
				++score2;
		}

		assertEquals(score1 > score2, gameMatch.getWinningTeam() == team1);

		int week = gameEnvironment.getWeek();
		gameMatch.finish();
		assertEquals(week + 1, gameEnvironment.getWeek());
	}

	@Test
	void sameTeamTest() {
		Team team = Team.generateTeam(50, gameEnvironment);
		gameMatch.setTeams(team, team);
		for (Position position : Position.values()) {
			Athlete athlete = team.getActiveAthletes().get(position);
			gameMatch.getWinningAthlete(athlete, athlete);
		}
		int money = gameEnvironment.getPlayer().getMoney();
		int score = gameEnvironment.getPlayer().getScore();
		gameMatch.finish();
		if (gameMatch.getWinningTeam() == gameEnvironment.getPlayer().getTeam()) {
			assertTrue(gameEnvironment.getPlayer().getMoney() > money);
			assertTrue(gameEnvironment.getPlayer().getScore() > score);
		}
	}

	@Test
	void allAthletesInjuredTest() {
		Team team1 = gameEnvironment.getPlayer().getTeam();
		Team team2 = Team.generateTeam(45, gameEnvironment);
		for (Position position : Position.values()) {
			Athlete newAthlete = new Athlete("", position, 10, gameEnvironment, 0);
			for (Statistic statistic : Statistic.values()) {
				newAthlete.setStatistic(statistic, 100);
				team1.addAthleteToActive(newAthlete, position);
			}
		}

		gameMatch.setTeams(team1, team2);
		for (Position position : Position.values())
			gameMatch.getWinningAthlete(team1.getActiveAthletes().get(position),
					team2.getActiveAthletes().get(position));
		int money = gameEnvironment.getPlayer().getMoney();
		int score = gameEnvironment.getPlayer().getScore();
		assertEquals(team1, gameMatch.getWinningTeam());
		gameMatch.finish();
		assertTrue(gameEnvironment.getPlayer().getMoney() == money);
		assertTrue(gameEnvironment.getPlayer().getScore() == score);
	}

	@Test
	void playerWinningTest() {
		Team team1 = gameEnvironment.getPlayer().getTeam();
		Team team2 = Team.generateTeam(0, gameEnvironment);
		gameEnvironment.setDifficulty(2);
		for (Position position : Position.values()) {
			Athlete newAthlete = new Athlete("", position, 100, gameEnvironment, 0);
			team1.addAthleteToActive(newAthlete, position);
			for (Statistic statistic : Statistic.values()) {
				newAthlete.setStatistic(statistic, 100);
			}
		}

		gameMatch.setTeams(team1, team2);
		for (Position position : Position.values())
			gameMatch.getWinningAthlete(team1.getActiveAthletes().get(position),
					team2.getActiveAthletes().get(position));
		int money = gameEnvironment.getPlayer().getMoney();
		int score = gameEnvironment.getPlayer().getScore();
		assertEquals(team1, gameMatch.getWinningTeam());
		gameMatch.finish();
		assertTrue(gameEnvironment.getPlayer().getMoney() > money);
		assertTrue(gameEnvironment.getPlayer().getScore() > score);
	}

	@Test
	void playerLosingTest() {
		Team team1 = gameEnvironment.getPlayer().getTeam();
		Team team2 = Team.generateTeam(100, gameEnvironment);
		gameEnvironment.setDifficulty(2);
		for (Position position : Position.values()) {
			Athlete newAthlete = new Athlete("", position, 100, gameEnvironment, 0);
			team1.addAthleteToActive(newAthlete, position);
		}

		gameMatch.setTeams(team1, team2);
		for (Position position : Position.values())
			gameMatch.getWinningAthlete(team1.getActiveAthletes().get(position),
					team2.getActiveAthletes().get(position));
		int money = gameEnvironment.getPlayer().getMoney();
		int score = gameEnvironment.getPlayer().getScore();
		assertNotEquals(team1, gameMatch.getWinningTeam());
		gameMatch.finish();
		assertTrue(gameEnvironment.getPlayer().getMoney() == money);
		assertTrue(gameEnvironment.getPlayer().getScore() == score);
	}
}
