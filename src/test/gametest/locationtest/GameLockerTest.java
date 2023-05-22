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
	private GameEnvironment ge;
	private GameLocker gameLocker;

	@BeforeEach
	void setUp() {
		ge = new GameEnvironment(false);
		ge.setSeed(0);
		gameLocker = (GameLocker) ge.getGameLocation(Location.LOCKER_ROOM);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) ge.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		gameLocker = new GameLocker(ge);
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
		assertEquals(ge.getPlayer().getTeam().getName(), gameLocker.getTeamName());
		ge.getPlayer().getTeam().setName("New name");
		assertEquals(ge.getPlayer().getTeam().getName(), gameLocker.getTeamName());
		ge.getPlayer().getTeam().setName("123");
		assertEquals(ge.getPlayer().getTeam().getName(), gameLocker.getTeamName());
	}

	@Test
	void reservesTest() {
		assertEquals(ge.getPlayer().getTeam().getReserveAthletes(), gameLocker.getReserves());
		ge.getPlayer().getTeam().addAthleteToReserve(new Athlete("name", Position.DUNKER, 1, ge, 1));
		assertEquals(ge.getPlayer().getTeam().getReserveAthletes(), gameLocker.getReserves());
	}

	@Test
	void activeTest() {
		assertEquals(ge.getPlayer().getTeam().getActiveAthletes(), gameLocker.getActive());
		ge.getPlayer().getTeam().addAthleteToActive(new Athlete("name", Position.DUNKER, 1, ge, 1), Position.DUNKER);
		assertEquals(ge.getPlayer().getTeam().getActiveAthletes(), gameLocker.getActive());
	}

	@Test
	void allAthletesTest() {
		assertEquals(ge.getPlayer().getTeam().getAllAthletes(), gameLocker.getAllAthletes());
		ge.getPlayer().getTeam().addAthleteToActive(new Athlete("name", Position.DUNKER, 1, ge, 1), Position.DUNKER);
		assertEquals(ge.getPlayer().getTeam().getAllAthletes(), gameLocker.getAllAthletes());
		ge.getPlayer().getTeam().addAthleteToReserve(new Athlete("name", Position.DUNKER, 1, ge, 1));
		assertEquals(ge.getPlayer().getTeam().getAllAthletes(), gameLocker.getAllAthletes());
	}

	@Test
	void moveTest() {
		assertNull(gameLocker.getActive().get(Position.DUNKER));
		assertEquals(0, gameLocker.getReserves().size());
		Athlete activeAthlete = (Athlete) Athlete.generateAthlete.apply(0, ge);
		ge.getPlayer().getTeam().addAthleteToActive(activeAthlete, Position.DUNKER);
		assertTrue(gameLocker.getActive().get(Position.DUNKER) != null);
		assertEquals(1, gameLocker.getActive().values().size());
		gameLocker.moveToReserve(activeAthlete);
		assertFalse(gameLocker.getActive().values().contains(activeAthlete));
		assertEquals(0, gameLocker.getActive().values().size());
		assertEquals(1, gameLocker.getReserves().size());
		ge.getPlayer().getTeam().moveToActive(activeAthlete, Position.SHORT_SHOOTER);
		assertEquals(activeAthlete, gameLocker.getActive().get(Position.SHORT_SHOOTER));
	}
}
