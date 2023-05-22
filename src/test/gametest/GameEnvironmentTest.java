package test.gametest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enumeration.Location;
import enumeration.Position;
import game.Athlete;
import game.GameEnvironment;
import game.Player;
import game.Team;
import game.location.GameEnd;
import game.location.GameMarket;
import game.location.GameMatch;
import userinterface.UIEnvironment;
import userinterface.commandline.CLIEnvironment;
import userinterface.graphical.GUIEnvironment;

class GameEnvironmentTest {

	private GameEnvironment gameEnvironment;

	@BeforeEach
	void setUp() {
		// initialise gui game environment before each test case
		gameEnvironment = new GameEnvironment(false);
		gameEnvironment.setSeed(0);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) gameEnvironment.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void rngTest() {
		gameEnvironment.setSeed(0);
		Object rng1 = gameEnvironment.getRng();
		assertTrue(rng1 instanceof Random);
		gameEnvironment.setSeed(999);
		Object rng2 = gameEnvironment.getRng();
		assertTrue(rng2 instanceof Random);
		assertTrue(rng1 != rng2);
	}

	@Test
	void seasonLengthTest() {
		gameEnvironment.setSeasonLength(1);
		assertEquals(1, gameEnvironment.getSeasonLength());
		gameEnvironment.setSeasonLength(7);
		assertEquals(7, gameEnvironment.getSeasonLength());
		gameEnvironment.setSeasonLength(15);
		assertEquals(15, gameEnvironment.getSeasonLength());
	}

	@Test
	void weekTest() {
		gameEnvironment.setSeasonLength(5);
		gameEnvironment.setSeed(0);
		assertEquals(0, gameEnvironment.getWeek());
		gameEnvironment.progressWeek();
		assertEquals(1, gameEnvironment.getWeek());
	}

	@Test
	void difficultyTest() {
		gameEnvironment.setDifficulty(1);
		assertEquals(1, gameEnvironment.getDifficulty());
		gameEnvironment.setDifficulty(2);
		assertEquals(2, gameEnvironment.getDifficulty());
		gameEnvironment.setDifficulty(3);
		assertEquals(3, gameEnvironment.getDifficulty());
	}

	@Test
	void gameLocationTest() {
		Object gameLocation;
		gameLocation = gameEnvironment.getGameLocation(Location.END);
		assertTrue(gameLocation instanceof GameEnd);
		gameLocation = gameEnvironment.getGameLocation(Location.ATHLETE_MARKET);
		assertTrue(gameLocation instanceof GameMarket);

		gameEnvironment.setSeasonLength(5);
		gameEnvironment.setDifficulty(1);
		Team team = gameEnvironment.getPlayer().getTeam();
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DEFENDER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DRIBBLER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DUNKER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.LONG_SHOOTER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.SHORT_SHOOTER);
		((GameMatch) gameEnvironment.getGameLocation(Location.MATCH)).setTeams(team, Team.generateTeam(100, gameEnvironment));
		gameEnvironment.progressWeek();

		for (Location location : Location.values()) {
			gameEnvironment.changeLocation(location);
		}
	}

	@Test
	void hasEndedTest() {
		Team team = gameEnvironment.getPlayer().getTeam();
		gameEnvironment.setSeasonLength(5);
		assertTrue(gameEnvironment.hasEnded());
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DEFENDER);
		assertTrue(gameEnvironment.hasEnded());
		gameEnvironment.progressWeek();
		assertTrue(gameEnvironment.hasEnded());
		gameEnvironment.getPlayer().giveMoney(10000);
		assertFalse(gameEnvironment.hasEnded());
		gameEnvironment.getPlayer().chargeMoney(10000);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DRIBBLER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DUNKER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.LONG_SHOOTER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.SHORT_SHOOTER);
		assertFalse(gameEnvironment.hasEnded());
		gameEnvironment.progressWeek();
		gameEnvironment.progressWeek();
		gameEnvironment.progressWeek();
		gameEnvironment.progressWeek();
		assertFalse(gameEnvironment.hasEnded());
		gameEnvironment.progressWeek();
		assertTrue(gameEnvironment.hasEnded());
	}

	@Test
	void playerTest() {
		assertTrue(gameEnvironment.getPlayer() instanceof Player);
	}

	@Test
	void uiEnvironmentTest() {
		assertTrue(gameEnvironment.getUIEnvironment() instanceof UIEnvironment);
	}

	@Test
	void gameEnvironmentUITest() {
		GameEnvironment cliGameEnvironment = new GameEnvironment(true);
		assertTrue(gameEnvironment.getUIEnvironment() instanceof GUIEnvironment);
		assertTrue(cliGameEnvironment.getUIEnvironment() instanceof CLIEnvironment);
	}
}