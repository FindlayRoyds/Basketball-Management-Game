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
	private GameEnvironment gameEnvironment;
	private Team team;

	@BeforeEach
	void setUp() {
		gameEnvironment = new GameEnvironment(false);
		gameEnvironment.setSeed(0);
		team = gameEnvironment.getPlayer().getTeam();
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) gameEnvironment.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void maxReservesTest() {
		assertEquals(5, Team.getMaxNumberOfReserves());
	}

	@Test
	void unfilledPositionsTest() {
		assertEquals(5, team.getUnfilledTeamPositions().size());
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.SHORT_SHOOTER);
		assertEquals(4, team.getUnfilledTeamPositions().size());
		assertFalse(team.getUnfilledTeamPositions().contains(Position.SHORT_SHOOTER));
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DUNKER);
		assertEquals(3, team.getUnfilledTeamPositions().size());
		assertFalse(team.getUnfilledTeamPositions().contains(Position.DUNKER));
		assertTrue(team.getUnfilledTeamPositions().contains(Position.DRIBBLER));
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DUNKER);
		assertEquals(3, team.getUnfilledTeamPositions().size());
	}

	@Test
	void nameTest() {
		team.setName("Testing");
		assertEquals("Testing", team.getName());
		team.setName("123");
		assertEquals("123", team.getName());
	}

	/**
	 * Huge test case to test swapping athletes around in a team
	 */
	@Test
	void athletesTest() {
		assertNull(team.getActiveAthletes().get(Position.DUNKER));
		assertEquals(0, team.getReserveAthletes().size());
		Athlete activeAthlete = (Athlete) Athlete.generateAthlete.apply(0, gameEnvironment);
		team.addAthleteToActive(activeAthlete, Position.DUNKER);
		assertTrue(team.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment)));
		assertTrue(team.getActiveAthletes().get(Position.DUNKER) != null);
		assertEquals(1, team.getActiveAthletes().values().size());
		assertEquals(1, team.getReserveAthletes().size());
		assertEquals(4, team.getNumberOfFreeReserveSlots());
		assertEquals(2, team.getAllAthletes().size());
		assertEquals(2, team.getAllPurchasables.get().size());
		assertTrue(team.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment)));
		assertTrue(team.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment)));
		assertTrue(team.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment)));
		Athlete reserveAthlete = (Athlete) Athlete.generateAthlete.apply(0, gameEnvironment);
		assertTrue(team.addAthleteToReserve(reserveAthlete));
		assertFalse(team.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment)));
		team.removeAthlete(reserveAthlete);
		assertEquals(4, team.getReserveAthletes().size());
		assertEquals(1, team.getNumberOfFreeReserveSlots());
		assertTrue(team.addAthleteToReserve(reserveAthlete));
		team.removeAthlete(reserveAthlete);
		team.removeAthlete(activeAthlete);
		assertEquals(0, team.getActiveAthletes().values().size());
		team.addAthleteToActive(activeAthlete, Position.DUNKER);
		assertEquals(1, team.getActiveAthletes().values().size());
		assertEquals(activeAthlete, team.getActiveAthletes().get(Position.DUNKER));
		assertEquals(4, team.getReserveAthletes().size());
		assertTrue(team.getActiveAthletes().values().contains(activeAthlete));
		team.moveToReserve(activeAthlete);
		assertFalse(team.getActiveAthletes().values().contains(activeAthlete));
		assertEquals(0, team.getActiveAthletes().values().size());
		assertEquals(0, team.getActiveAthletes().values().size());
		assertEquals(5, team.getReserveAthletes().size());
		assertEquals(0, team.getNumberOfFreeReserveSlots());
		team.moveToActive(activeAthlete, Position.SHORT_SHOOTER);
		assertEquals(activeAthlete, team.getActiveAthletes().get(Position.SHORT_SHOOTER));
		assertTrue(team.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment)));
		team.moveToReserve(activeAthlete);
		assertTrue(team.getReserveAthletes().contains(activeAthlete));
		assertEquals(5, team.getReserveAthletes().size());
		assertFalse(team.moveToReserve((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment)));
	}

	@Test
	void swapActiveAthletesTest() {
		Athlete activeAthlete = (Athlete) Athlete.generateAthlete.apply(100, gameEnvironment);
		team.addAthleteToActive(activeAthlete, Position.DUNKER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DEFENDER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DRIBBLER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.LONG_SHOOTER);
		for (int i = 0; i < 5; i++)
			team.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment));

		team.moveToActive(activeAthlete, Position.DEFENDER);
		assertEquals(activeAthlete, team.getActiveAthletes().get(Position.DEFENDER));
		assertFalse(activeAthlete == team.getActiveAthletes().get(Position.DUNKER));
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.SHORT_SHOOTER);
		team.moveToActive(activeAthlete, Position.SHORT_SHOOTER);
		assertEquals(activeAthlete, team.getActiveAthletes().get(Position.SHORT_SHOOTER));
		assertFalse(activeAthlete == team.getActiveAthletes().get(Position.DEFENDER));
	}

	@Test
	void swapReserveAthleteIntoActiveTest() {
		Athlete reserveAthlete = (Athlete) Athlete.generateAthlete.apply(100, gameEnvironment);
		team.addAthleteToReserve(reserveAthlete);
		team.moveToActive(reserveAthlete, Position.LONG_SHOOTER);
	}

	@Test
	void removeAthleteNotInTeamTest() {
		Athlete athlete = (Athlete) Athlete.generateAthlete.apply(0, gameEnvironment);
		team.removeAthlete(athlete);
	}

	@Test
	void generateTeamTest() {
		Team randomTeam = Team.generateTeam(100, gameEnvironment);
		assertEquals(5, randomTeam.getReserveAthletes().size());
		assertEquals(5, randomTeam.getActiveAthletes().values().size());
	}
}
