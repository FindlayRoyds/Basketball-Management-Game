package test.gametest.locationtest;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enumeration.Location;
import enumeration.Position;
import game.Athlete;
import game.GameEnvironment;
import game.location.GameStart;
import userinterface.graphical.GUIEnvironment;

class GameStartTest {
	private GameEnvironment gameEnvironment;
	private GameStart gameStart;

	@BeforeEach
	void setUp() {
		gameEnvironment = new GameEnvironment(false);
		gameEnvironment.setSeed(0);
		gameStart = (GameStart) gameEnvironment.getGameLocation(Location.START);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) gameEnvironment.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		gameStart = new GameStart(gameEnvironment);
	}

	@Test
	void updateTest() {
		for (int i = 0; i <= 15; ++i)
			gameStart.update(i);
	}

	@Test
	void athletesTest() {
		Set<Athlete> startingAthletes = gameStart.getStartingAthletes();
		assertEquals(10, startingAthletes.size());

		Athlete chosenAthlete = new ArrayList<Athlete>(startingAthletes).get(0);
		gameStart.chooseAthlete(chosenAthlete, Position.DUNKER);
		assertEquals(9, gameStart.getStartingAthletes().size());
		assertFalse(gameStart.getStartingAthletes().contains(chosenAthlete));
		assertTrue(gameEnvironment.getPlayer().getTeam().getAllAthletes().contains(chosenAthlete));
	}

	@Test
	void positionTest() {
		for (Position position : gameStart.getUnfilledTeamPositions()) {
			assertNull(gameEnvironment.getPlayer().getTeam().getActiveAthletes().get(position));
		}
	}

	@Test
	void teamNameTest() {
		String name = "My name";
		gameStart.setTeamName(name);
		assertEquals(name, gameStart.getTeamName());
		assertEquals(name, gameEnvironment.getPlayer().getTeam().getName());
	}

	@Test
	void seasonLengthTest() {
		for (int length = 5; length <= 15; ++length) {
			gameStart.setSeasonLength(length);
			assertEquals(length, gameEnvironment.getSeasonLength());
		}
	}

	@Test
	void seedTest() {
		gameStart.setSeed(10);
		gameStart.setSeed(2413);
	}

	@Test
	void difficultyTest() {
		for (int difficulty = 1; difficulty <= 3; ++difficulty) {
			gameStart.setDifficulty(difficulty);
			assertEquals(difficulty, gameEnvironment.getDifficulty());
		}
	}

	@Test
	void progressWeek() {
		gameEnvironment.setSeasonLength(5);
		for (int i = 0; i < 5; ++i) {
			gameStart.progressWeek();
			assertEquals(i + 1, gameEnvironment.getWeek());
		}
	}
}
