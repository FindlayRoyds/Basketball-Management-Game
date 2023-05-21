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
import userinterface.UIEnvironment;
import userinterface.commandline.CLIEnvironment;
import userinterface.graphical.GUIEnvironment;

class GameEnvironmentTest {

	private GameEnvironment ge;

	@BeforeEach
	void setUp() {
		// initialise gui game environment before each test case
		ge = new GameEnvironment(false);
		ge.setSeed(0);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) ge.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void rngTest() {
		ge.setSeed(0);
		Object rng1 = ge.getRng();
		assertTrue(rng1 instanceof Random);
		ge.setSeed(999);
		Object rng2 = ge.getRng();
		assertTrue(rng2 instanceof Random);
		assertTrue(rng1 != rng2);
	}

	@Test
	void seasonLengthTest() {
		ge.setSeasonLength(1);
		assertEquals(1, ge.getSeasonLength());
		ge.setSeasonLength(7);
		assertEquals(7, ge.getSeasonLength());
		ge.setSeasonLength(15);
		assertEquals(15, ge.getSeasonLength());
	}

	@Test
	void weekTest() {
		ge.setSeasonLength(5);
		ge.setSeed(0);
		assertEquals(0, ge.getWeek());
		ge.progressWeek();
		assertEquals(1, ge.getWeek());
	}

	@Test
	void difficultyTest() {
		ge.setDifficulty(1);
		assertEquals(1, ge.getDifficulty());
		ge.setDifficulty(2);
		assertEquals(2, ge.getDifficulty());
		ge.setDifficulty(3);
		assertEquals(3, ge.getDifficulty());
	}

	@Test
	void gameLocationTest() {
		Object gameLocation;
		gameLocation = ge.getGameLocation(Location.END);
		assertTrue(gameLocation instanceof GameEnd);
		gameLocation = ge.getGameLocation(Location.ATHLETE_MARKET);
		assertTrue(gameLocation instanceof GameMarket);

		for (Location location : Location.values()) {
			ge.changeLocation(location);
		}
	}

	@Test
	void hasEndedTest() {
		Team team = ge.getPlayer().getTeam();
		ge.setSeasonLength(5);
		assertTrue(ge.hasEnded());
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.DEFENDER);
		assertTrue(ge.hasEnded());
		ge.progressWeek();
		assertTrue(ge.hasEnded());
		ge.getPlayer().giveMoney(10000);
		assertFalse(ge.hasEnded());
		ge.getPlayer().chargeMoney(10000);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.DRIBBLER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.DUNKER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.LONG_SHOOTER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.SHORT_SHOOTER);
		assertFalse(ge.hasEnded());
		ge.progressWeek();
		ge.progressWeek();
		ge.progressWeek();
		ge.progressWeek();
		assertFalse(ge.hasEnded());
		ge.progressWeek();
		assertTrue(ge.hasEnded());
	}

	@Test
	void playerTest() {
		assertTrue(ge.getPlayer() instanceof Player);
	}

	@Test
	void uiEnvironmentTest() {
		assertTrue(ge.getUIEnvironment() instanceof UIEnvironment);
	}

	@Test
	void gameEnvironmentUITest() {
		GameEnvironment cliGameEnvironment = new GameEnvironment(true);
		assertTrue(ge.getUIEnvironment() instanceof GUIEnvironment);
		assertTrue(cliGameEnvironment.getUIEnvironment() instanceof CLIEnvironment);
	}
}