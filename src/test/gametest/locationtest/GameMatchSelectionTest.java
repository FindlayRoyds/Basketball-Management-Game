package test.gametest.locationtest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enumeration.Location;
import enumeration.Position;
import game.Athlete;
import game.GameEnvironment;
import game.Team;
import game.location.GameMatchSelection;
import userinterface.graphical.GUIEnvironment;

class GameMatchSelectionTest {
	private GameEnvironment ge;
	private GameMatchSelection ms;
	private Team tm;

	@BeforeEach
	void setUp() throws Exception {
		ge = new GameEnvironment(false);
		tm = ge.getPlayer().getTeam();
		ge.setSeed(0);
		ge.setSeasonLength(7);
		ge.setDifficulty(1);
		ms = (GameMatchSelection) ge.getGameLocation(Location.MATCH_SELECTION);
		ge.progressWeek();
	}

	@AfterEach
	void tearDown() throws Exception {
		JFrame frame = ((GUIEnvironment) ge.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		assertNotNull(ms.getTeams());
		assertNotEquals(0, ms.getTeams().size());
	}

	@Test
	void updateTest() {
		ArrayList<Team> teams1 = ms.getTeams();
		Team firstTeam = teams1.get(0);
		for (int i = 1; i < teams1.size(); i++) {
			assertNotEquals(firstTeam, teams1.get(i));
		}
		ge.progressWeek();
		ArrayList<Team> teams2 = ms.getTeams();
		assertNotEquals(teams1, teams2);
	}

	@Test
	void canStartMatchTest() {
		assertFalse(ms.canStartMatch());
		Athlete athlete = new Athlete("", Position.DUNKER, 0, ge, 0);
		tm.addAthleteToActive(athlete, Position.DUNKER);
		assertFalse(ms.canStartMatch());
		athlete.setStamina(1);
		assertFalse(ms.canStartMatch());

		tm.addAthleteToActive(new Athlete("", Position.DUNKER, 0, ge, 0), Position.DEFENDER);
		tm.addAthleteToActive(new Athlete("", Position.DUNKER, 0, ge, 0), Position.DRIBBLER);
		tm.addAthleteToActive(new Athlete("", Position.DUNKER, 0, ge, 0), Position.LONG_SHOOTER);
		tm.addAthleteToActive(new Athlete("", Position.DUNKER, 0, ge, 0), Position.SHORT_SHOOTER);
		assertTrue(ms.canStartMatch());
		athlete.setStamina(0);
		assertFalse(ms.canStartMatch());
		for (int i = 0; i < 5; i++)
			tm.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(100, ge));
		assertFalse(ms.canStartMatch());

	}

	@Test
	void playMatchTest() {
		tm.addAthleteToActive(new Athlete("", Position.DUNKER, 100, ge, 0), Position.DEFENDER);
		tm.addAthleteToActive(new Athlete("", Position.DUNKER, 100, ge, 0), Position.DRIBBLER);
		tm.addAthleteToActive(new Athlete("", Position.DUNKER, 100, ge, 0), Position.LONG_SHOOTER);
		tm.addAthleteToActive(new Athlete("", Position.DUNKER, 100, ge, 0), Position.SHORT_SHOOTER);
		tm.addAthleteToActive(new Athlete("", Position.DUNKER, 100, ge, 0), Position.DUNKER);

		Team opposingTeam = Team.generateTeam(100, ge);
		ms.playMatch(opposingTeam);
	}
}
