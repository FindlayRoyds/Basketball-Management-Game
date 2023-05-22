package test.gametest;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enumeration.Position;
import enumeration.Statistic;
import game.Athlete;
import game.GameEnvironment;
import game.Player;
import game.Team;
import game.item.Steroid;
import userinterface.graphical.GUIEnvironment;

class AthleteTest {
	private GameEnvironment gameEnvironment;
	private Athlete athlete;

	@BeforeEach
	void setUp() {
		gameEnvironment = new GameEnvironment(false);
		gameEnvironment.setSeed(0);
		athlete = new Athlete("Test", Position.SHORT_SHOOTER, 0, gameEnvironment, 0);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) gameEnvironment.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		assertEquals("Test", athlete.getName());
		assertEquals(Position.SHORT_SHOOTER, athlete.getRole());
		assertEquals(0, athlete.getStamina());
		assertEquals(0, athlete.getPrice());
	}

	@Test
	void generatorTest() {
		athlete = (Athlete) Athlete.generateAthlete.apply(100, gameEnvironment);
		int minimum = 0;
		for (Statistic statistic : Statistic.values()) {
			minimum = Math.min(minimum, athlete.getStatistic(statistic));
		}
		assertTrue(minimum == 0);
		athlete = (Athlete) Athlete.generateAthlete.apply(100, gameEnvironment);
		int maximum = 0;
		for (Statistic statistic : Statistic.values()) {
			maximum = Math.max(maximum, athlete.getStatistic(statistic));
		}
		assertFalse(maximum == 0);
	}

	@Test
	void statisticTest() {
		athlete.setStatistic(Statistic.DEFENCE, 0);
		assertEquals(athlete.getStatistic(Statistic.DEFENCE), 0);
		athlete.setStatistic(Statistic.DEFENCE, 100);
		assertEquals(athlete.getStatistic(Statistic.DEFENCE), 100);
		athlete.setStatistic(Statistic.DEFENCE, -1);
		assertEquals(athlete.getStatistic(Statistic.DEFENCE), 0);
		athlete.setStatistic(Statistic.DEFENCE, 101);
		assertEquals(athlete.getStatistic(Statistic.DEFENCE), 100);
		athlete.setStatistic(Statistic.DEFENCE, 57);
		assertEquals(athlete.getStatistic(Statistic.DEFENCE), 57);
	}

	@Test
	void staminaTest() {
		athlete.setStamina(0);
		assertEquals(0, athlete.getStamina());
		assertTrue(athlete.isInjured());
		athlete.setStamina(1);
		assertFalse(athlete.isInjured());
		athlete.setStamina(100);
		assertEquals(100, athlete.getStamina());
		athlete.setStamina(-1);
		assertEquals(0, athlete.getStamina());
		athlete.setStamina(101);
		assertEquals(100, athlete.getStamina());
		athlete.setStamina(34);
		assertEquals(34, athlete.getStamina());
		athlete.setStatistic(Statistic.FITNESS, 0);
		athlete.setStamina(100);
		athlete.loseStamina(false);
		assertEquals(80, athlete.getStamina());
		athlete.loseStamina(true);
		assertEquals(50, athlete.getStamina());
		athlete.setStatistic(Statistic.FITNESS, 50);
		athlete.loseStamina(false);
		assertEquals(35, athlete.getStamina());
		athlete.setStatistic(Statistic.FITNESS, 100);
		athlete.loseStamina(true);
		assertEquals(15, athlete.getStamina());
		athlete.loseStamina(true);
		assertEquals(0, athlete.getStamina());
	}

	@Test
	void purchaseTest() {
		athlete.setPrice(100);
		Player player = gameEnvironment.getPlayer();
		assertFalse(athlete.purchase(player));
		player.giveMoney(99);
		assertFalse(athlete.purchase(player));
		player.giveMoney(1);
		assertTrue(athlete.purchase(player));
		assertEquals(0, player.getMoney());
		athlete.sell(player);
		player.giveMoney(10000);
		athlete.purchase(player);
	}

	@Test
	void purchaseFullActiveTest() {
		gameEnvironment.setSeed(0);
		Player player = gameEnvironment.getPlayer();
		Team team = player.getTeam();

		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DEFENDER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DRIBBLER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DUNKER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.LONG_SHOOTER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.SHORT_SHOOTER);
		assertTrue(athlete.purchase(player));
		assertFalse(team.getReserveAthletes().isEmpty());
	}

	@Test
	void purchaseFullReserveTest() {
		gameEnvironment.setSeed(0);
		Player player = gameEnvironment.getPlayer();
		Team team = player.getTeam();

		for (int i = 0; i < 5; i++)
			team.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment));

		assertEquals(5, team.getReserveAthletes().size());
		assertTrue(athlete.purchase(player));
		assertEquals(5, team.getReserveAthletes().size());
	}

	@Test
	void purchaseFullTeamTest() {
		gameEnvironment.setSeed(0);
		Player player = gameEnvironment.getPlayer();
		Team team = player.getTeam();

		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DEFENDER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DRIBBLER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.DUNKER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.LONG_SHOOTER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment), Position.SHORT_SHOOTER);
		for (int i = 0; i < 5; i++)
			team.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, gameEnvironment));

		assertFalse(athlete.purchase(player));
		assertEquals(5, team.getReserveAthletes().size());
	}

	@Test
	void sellTest() {
		Player player = gameEnvironment.getPlayer();
		Team team = player.getTeam();

		team.addAthleteToReserve(athlete);
		athlete.setPrice(107);
		athlete.sell(player);
		assertEquals(0, team.getReserveAthletes().size());
		assertEquals(107, player.getMoney());
		player.chargeMoney(107);

		team.addAthleteToActive(athlete, Position.SHORT_SHOOTER);
		athlete.sell(player);
		assertEquals(107, player.getMoney());
		assertNull(team.getActiveAthletes().get(Position.SHORT_SHOOTER));
	}

	@Test
	void descriptionTest() {
		assertEquals(String.class, athlete.getDescription().getClass());
		assertNotEquals("", athlete.getDescription());
	}

	@Test
	void detailsTest() {
		assertEquals(String.class, athlete.getDetails().getClass());
		assertNotEquals("", athlete.getDetails());
	}

	@Test
	void teamTest() {
		Player player = gameEnvironment.getPlayer();
		Team team = player.getTeam();
		assertNull(athlete.getTeam());
		team.addAthleteToReserve(athlete);
		assertEquals(team, athlete.getTeam());
		athlete.sell(player);
		team.addAthleteToActive(athlete, Position.SHORT_SHOOTER);
		assertEquals(team, athlete.getTeam());
	}

	@Test
	void matchScoreTest() {
		for (Statistic statistic : Statistic.values()) {
			athlete.setStatistic(statistic, 0);
		}
		assertEquals(0, athlete.getMatchScore(Position.SHORT_SHOOTER));
		for (Statistic statistic : Statistic.values()) {
			athlete.setStatistic(statistic, 100);
		}

		int boostedScore = athlete.getMatchScore(Position.SHORT_SHOOTER);
		Athlete athlete2 = new Athlete("", Position.DUNKER, 0, gameEnvironment, 0);
		for (Statistic statistic : Statistic.values()) {
			athlete2.setStatistic(statistic, 100);
		}
		int normalScore = athlete2.getMatchScore(Position.SHORT_SHOOTER);

		assertTrue(boostedScore > 0);
		assertTrue(boostedScore > normalScore);
	}

	@Test
	void steroidTest() {
		assertFalse(athlete.getHasUsedSteroids());
		athlete.setHasUsedSteroids(true);
		assertTrue(athlete.getHasUsedSteroids());
		athlete.setHasUsedSteroids(false);
		Steroid steroid = new Steroid("", 0, 0, gameEnvironment);
		steroid.applyItem(athlete);
		assertTrue(athlete.getHasUsedSteroids());
	}
}
