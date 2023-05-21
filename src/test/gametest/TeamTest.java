package test.gametest;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enumeration.Position;
import game.Athlete;
import game.GameEnvironment;
import game.Team;
import userinterface.graphical.GUIEnvironment;

class TeamTest {
	private GameEnvironment ge;
	private Team tm;

	@BeforeEach
	void setUp() {
		ge = new GameEnvironment(false);
		ge.setSeed(0);
		tm = ge.getPlayer().getTeam();
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) ge.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void maxReservesTest() {
		assertEquals(5, tm.getMaxNumberOfReserves());
	}

	@Test
	void unfilledPositionsTest() {
		assertEquals(5, tm.getUnfilledTeamPositions().size());
		tm.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.SHORT_SHOOTER);
		assertEquals(4, tm.getUnfilledTeamPositions().size());
		assertFalse(tm.getUnfilledTeamPositions().contains(Position.SHORT_SHOOTER));
		tm.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.DUNKER);
		assertEquals(3, tm.getUnfilledTeamPositions().size());
		assertFalse(tm.getUnfilledTeamPositions().contains(Position.DUNKER));
		assertTrue(tm.getUnfilledTeamPositions().contains(Position.DRIBBLER));
		tm.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.DUNKER);
		assertEquals(3, tm.getUnfilledTeamPositions().size());
	}

	@Test
	void nameTest() {
		tm.setName("Testing");
		assertEquals("Testing", tm.getName());
		tm.setName("123");
		assertEquals("123", tm.getName());
	}

	@Test
	void athletesTest() {
		assertNull(tm.getActiveAthletes().get(Position.DUNKER));
		assertEquals(0, tm.getReserveAthletes().size());
		Athlete activeAthlete = (Athlete) Athlete.generateAthlete.apply(0, ge);
		tm.addAthleteToActive(activeAthlete, Position.DUNKER);
		assertTrue(tm.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, ge)));
		assertTrue(tm.getActiveAthletes().get(Position.DUNKER) != null);
		assertEquals(1, tm.getActiveAthletes().values().size());
		assertEquals(1, tm.getReserveAthletes().size());
		assertEquals(4, tm.getNumberOfFreeReserveSlots());
		assertEquals(2, tm.getAllAthletes().size());
		assertEquals(2, tm.getAllPurchasables.get().size());
		assertTrue(tm.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, ge)));
		assertTrue(tm.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, ge)));
		assertTrue(tm.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, ge)));
		Athlete reserveAthlete = (Athlete) Athlete.generateAthlete.apply(0, ge);
		assertTrue(tm.addAthleteToReserve(reserveAthlete));
		assertFalse(tm.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, ge)));
		tm.removeAthlete(reserveAthlete);
		assertEquals(4, tm.getReserveAthletes().size());
		assertEquals(1, tm.getNumberOfFreeReserveSlots());
		assertTrue(tm.addAthleteToReserve(reserveAthlete));
		tm.removeAthlete(reserveAthlete);
		tm.removeAthlete(activeAthlete);
		assertEquals(0, tm.getActiveAthletes().values().size());
		tm.addAthleteToActive(activeAthlete, Position.DUNKER);
		assertEquals(1, tm.getActiveAthletes().values().size());
		assertEquals(activeAthlete, tm.getActiveAthletes().get(Position.DUNKER));
		assertEquals(4, tm.getReserveAthletes().size());
		assertTrue(tm.getActiveAthletes().values().contains(activeAthlete));
		tm.moveToReserve(activeAthlete);
		assertFalse(tm.getActiveAthletes().values().contains(activeAthlete));
		assertEquals(0, tm.getActiveAthletes().values().size());
		assertEquals(0, tm.getActiveAthletes().values().size());
		assertEquals(5, tm.getReserveAthletes().size());
		assertEquals(0, tm.getNumberOfFreeReserveSlots());
		tm.moveToActive(activeAthlete, Position.SHORT_SHOOTER);
		assertEquals(activeAthlete, tm.getActiveAthletes().get(Position.SHORT_SHOOTER));
	}

	@Test
	void generateTeamTest() {
		Team randomTeam = Team.generateTeam(100, ge);
		assertEquals(5, randomTeam.getReserveAthletes().size());
		assertEquals(5, randomTeam.getActiveAthletes().values().size());
	}
}
