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
	private GameEnvironment gameEnvironment;
	private GameMatchSelection gameMatchSelection;
	private Team team;

	@BeforeEach
	void setUp() throws Exception {
		gameEnvironment = new GameEnvironment(false);
		team = gameEnvironment.getPlayer().getTeam();
		gameEnvironment.setSeed(0);
		gameEnvironment.setSeasonLength(7);
		gameEnvironment.setDifficulty(1);
		gameMatchSelection = (GameMatchSelection) gameEnvironment.getGameLocation(Location.MATCH_SELECTION);
		gameEnvironment.progressWeek();
	}

	@AfterEach
	void tearDown() throws Exception {
		JFrame frame = ((GUIEnvironment) gameEnvironment.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		assertNotNull(gameMatchSelection.getTeams());
		assertNotEquals(0, gameMatchSelection.getTeams().size());
	}

	@Test
	void updateTest() {
		ArrayList<Team> teams1 = gameMatchSelection.getTeams();
		Team firstTeam = teams1.get(0);
		for (int i = 1; i < teams1.size(); i++) {
			assertNotEquals(firstTeam, teams1.get(i));
		}
		gameEnvironment.progressWeek();
		ArrayList<Team> teams2 = gameMatchSelection.getTeams();
		assertNotEquals(teams1, teams2);
	}

	@Test
	void canStartMatchTest() {
		assertFalse(gameMatchSelection.canStartMatch());
		Athlete athlete = new Athlete("", Position.DUNKER, 0, gameEnvironment, 0);
		team.addAthleteToActive(athlete, Position.DUNKER);
		assertFalse(gameMatchSelection.canStartMatch());
		athlete.setStamina(1);
		assertFalse(gameMatchSelection.canStartMatch());

		team.addAthleteToActive(new Athlete("", Position.DUNKER, 0, gameEnvironment, 0), Position.DEFENDER);
		team.addAthleteToActive(new Athlete("", Position.DUNKER, 0, gameEnvironment, 0), Position.DRIBBLER);
		team.addAthleteToActive(new Athlete("", Position.DUNKER, 0, gameEnvironment, 0), Position.LONG_SHOOTER);
		team.addAthleteToActive(new Athlete("", Position.DUNKER, 0, gameEnvironment, 0), Position.SHORT_SHOOTER);
		assertTrue(gameMatchSelection.canStartMatch());
		athlete.setStamina(0);
		assertFalse(gameMatchSelection.canStartMatch());
		for (int i = 0; i < 5; i++)
			team.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(100, gameEnvironment));
		assertFalse(gameMatchSelection.canStartMatch());

	}

	@Test
	void playMatchTest() {
		team.addAthleteToActive(new Athlete("", Position.DUNKER, 100, gameEnvironment, 0), Position.DEFENDER);
		team.addAthleteToActive(new Athlete("", Position.DUNKER, 100, gameEnvironment, 0), Position.DRIBBLER);
		team.addAthleteToActive(new Athlete("", Position.DUNKER, 100, gameEnvironment, 0), Position.LONG_SHOOTER);
		team.addAthleteToActive(new Athlete("", Position.DUNKER, 100, gameEnvironment, 0), Position.SHORT_SHOOTER);
		team.addAthleteToActive(new Athlete("", Position.DUNKER, 100, gameEnvironment, 0), Position.DUNKER);

		Team opposingTeam = Team.generateTeam(100, gameEnvironment);
		gameMatchSelection.playMatch(opposingTeam);
	}
}
