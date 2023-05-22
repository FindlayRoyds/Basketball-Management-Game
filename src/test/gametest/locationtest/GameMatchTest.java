package test.gametest.locationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import enumeration.Location;
import enumeration.Position;
import game.Athlete;
import game.GameEnvironment;
import game.Team;
import game.location.GameMatch;
import userinterface.graphical.GUIEnvironment;

class GameMatchTest {
	private GameEnvironment ge;
	private GameMatch gameMatch;

	@BeforeEach
	void setUp() {
		ge = new GameEnvironment(false);
		ge.setSeed(0);
		ge.setSeasonLength(5);
		gameMatch = (GameMatch) ge.getGameLocation(Location.MATCH);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) ge.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		gameMatch = new GameMatch(ge);
	}

	@Test
	void updateTest() {
		for (int i = 0; i <= 15; ++i)
			gameMatch.update(i);
	}

	@Test
	void teamsTest() {
		Team team1 = Team.generateTeam(50, ge);
		Team team2 = Team.generateTeam(50, ge);
		gameMatch.setTeams(team1, team2);

		assertEquals(team1, gameMatch.getTeam1());
		assertEquals(team2, gameMatch.getTeam2());

		assertEquals(0, gameMatch.getTeam1Score());
		assertEquals(0, gameMatch.getTeam1Score());
	}

	@RepeatedTest(5)
	void playTest() {
		Team team1 = Team.generateTeam(50, ge);
		Team team2 = Team.generateTeam(50, ge);
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

		int week = ge.getWeek();
		int money = ge.getPlayer().getMoney();
		int score = ge.getPlayer().getScore();
		gameMatch.finish();
		assertEquals(week + 1, ge.getWeek());
		if (gameMatch.getWinningTeam() == ge.getPlayer().getTeam()) {
			assertTrue(ge.getPlayer().getMoney() > money);
			assertTrue(ge.getPlayer().getScore() > score);
		}
	}
}
