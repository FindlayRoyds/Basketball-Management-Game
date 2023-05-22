package test.gametest.locationtest;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enumeration.Location;
import enumeration.Position;
import game.Athlete;
import game.GameEnvironment;
import game.location.GameLocker;
import userinterface.graphical.GUIEnvironment;

class GameLockerTest {
	private GameEnvironment gameEnvironment;
	private GameLocker gameLocker;

	@BeforeEach
	void setUp() {
		gameEnvironment = new GameEnvironment(false);
		gameEnvironment.setSeed(0);
		gameLocker = (GameLocker) gameEnvironment.getGameLocation(Location.LOCKER_ROOM);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) gameEnvironment.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		gameLocker = new GameLocker(gameEnvironment);
	}

	@Test
	void updateTest() {
		for (int i = 0; i <= 15; ++i)
			gameLocker.update(i);
	}

	@Test
	void maxReservesTest() {
		assertEquals(5, gameLocker.getMaxNumberOfReserves());
	}

	@Test
	void teamNameTest() {
		assertEquals(gameEnvironment.getPlayer().getTeam().getName(), gameLocker.getTeamName());
		gameEnvironment.getPlayer().getTeam().setName("New name");
		assertEquals(gameEnvironment.getPlayer().getTeam().getName(), gameLocker.getTeamName());
		gameEnvironment.getPlayer().getTeam().setName("123");
		assertEquals(gameEnvironment.getPlayer().getTeam().getName(), gameLocker.getTeamName());
	}

	@Test
	void reservesTest() {
		assertEquals(gameEnvironment.getPlayer().getTeam().getReserveAthletes(), gameLocker.getReserves());
		gameEnvironment.getPlayer().getTeam().addAthleteToReserve(new Athlete("name", Position.DUNKER, 1, gameEnvironment, 1));
		assertEquals(gameEnvironment.getPlayer().getTeam().getReserveAthletes(), gameLocker.getReserves());
	}

	@Test
	void activeTest() {
		assertEquals(gameEnvironment.getPlayer().getTeam().getActiveAthletes(), gameLocker.getActive());
		gameEnvironment.getPlayer().getTeam().addAthleteToActive(new Athlete("name", Position.DUNKER, 1, gameEnvironment, 1), Position.DUNKER);
		assertEquals(gameEnvironment.getPlayer().getTeam().getActiveAthletes(), gameLocker.getActive());
	}

	@Test
	void allAthletesTest() {
		assertEquals(gameEnvironment.getPlayer().getTeam().getAllAthletes(), gameLocker.getAllAthletes());
		gameEnvironment.getPlayer().getTeam().addAthleteToActive(new Athlete("name", Position.DUNKER, 1, gameEnvironment, 1), Position.DUNKER);
		assertEquals(gameEnvironment.getPlayer().getTeam().getAllAthletes(), gameLocker.getAllAthletes());
		gameEnvironment.getPlayer().getTeam().addAthleteToReserve(new Athlete("name", Position.DUNKER, 1, gameEnvironment, 1));
		assertEquals(gameEnvironment.getPlayer().getTeam().getAllAthletes(), gameLocker.getAllAthletes());
	}

	@Test
	void moveTest() {
		assertNull(gameLocker.getActive().get(Position.DUNKER));
		assertEquals(0, gameLocker.getReserves().size());
		Athlete activeAthlete = (Athlete) Athlete.generateAthlete.apply(0, gameEnvironment);
		gameEnvironment.getPlayer().getTeam().addAthleteToActive(activeAthlete, Position.DUNKER);
		assertTrue(gameLocker.getActive().get(Position.DUNKER) != null);
		assertEquals(1, gameLocker.getActive().values().size());
		gameLocker.moveToReserve(activeAthlete);
		assertFalse(gameLocker.getActive().values().contains(activeAthlete));
		assertEquals(0, gameLocker.getActive().values().size());
		assertEquals(1, gameLocker.getReserves().size());
		gameEnvironment.getPlayer().getTeam().moveToActive(activeAthlete, Position.SHORT_SHOOTER);
		assertEquals(activeAthlete, gameLocker.getActive().get(Position.SHORT_SHOOTER));
	}
}
