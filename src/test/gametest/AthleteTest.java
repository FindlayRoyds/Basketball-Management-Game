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
	private GameEnvironment ge;
	private Athlete aht;

	@BeforeEach
	void setUp() {
		ge = new GameEnvironment(false);
		ge.setSeed(0);
		aht = new Athlete("Test", Position.SHORT_SHOOTER, 0, ge, 0);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) ge.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		assertEquals("Test", aht.getName());
		assertEquals(Position.SHORT_SHOOTER, aht.getRole());
		assertEquals(0, aht.getStamina());
		assertEquals(0, aht.getPrice());
	}

	@Test
	void generatorTest() {
		aht = (Athlete) Athlete.generateAthlete.apply(100, ge);
		int minimum = 0;
		for (Statistic statistic : Statistic.values()) {
			minimum = Math.min(minimum, aht.getStatistic(statistic));
		}
		assertTrue(minimum == 0);
		aht = (Athlete) Athlete.generateAthlete.apply(100, ge);
		int maximum = 0;
		for (Statistic statistic : Statistic.values()) {
			maximum = Math.max(maximum, aht.getStatistic(statistic));
		}
		assertFalse(maximum == 0);
	}

	@Test
	void statisticTest() {
		aht.setStatistic(Statistic.DEFENCE, 0);
		assertEquals(aht.getStatistic(Statistic.DEFENCE), 0);
		aht.setStatistic(Statistic.DEFENCE, 100);
		assertEquals(aht.getStatistic(Statistic.DEFENCE), 100);
		aht.setStatistic(Statistic.DEFENCE, -1);
		assertEquals(aht.getStatistic(Statistic.DEFENCE), 0);
		aht.setStatistic(Statistic.DEFENCE, 101);
		assertEquals(aht.getStatistic(Statistic.DEFENCE), 100);
		aht.setStatistic(Statistic.DEFENCE, 57);
		assertEquals(aht.getStatistic(Statistic.DEFENCE), 57);
	}

	@Test
	void staminaTest() {
		aht.setStamina(0);
		assertEquals(0, aht.getStamina());
		assertTrue(aht.isInjured());
		aht.setStamina(1);
		assertFalse(aht.isInjured());
		aht.setStamina(100);
		assertEquals(100, aht.getStamina());
		aht.setStamina(-1);
		assertEquals(0, aht.getStamina());
		aht.setStamina(101);
		assertEquals(100, aht.getStamina());
		aht.setStamina(34);
		assertEquals(34, aht.getStamina());
		aht.setStatistic(Statistic.FITNESS, 100);
		aht.loseStamina();
		assertEquals(34, aht.getStamina());
	}

	@Test
	void purchaseTest() {
		aht.setPrice(100);
		Player player = ge.getPlayer();
		assertFalse(aht.purchase(player));
		player.giveMoney(99);
		assertFalse(aht.purchase(player));
		player.giveMoney(1);
		assertTrue(aht.purchase(player));
		assertEquals(0, player.getMoney());
		aht.sell(player);
		player.giveMoney(10000);
		aht.purchase(player);
	}

	@Test
	void purchaseFullActiveTest() {
		ge.setSeed(0);
		Player player = ge.getPlayer();
		Team team = player.getTeam();

		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.DEFENDER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.DRIBBLER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.DUNKER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.LONG_SHOOTER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.SHORT_SHOOTER);
		assertTrue(aht.purchase(player));
		assertFalse(team.getReserveAthletes().isEmpty());
	}

	@Test
	void purchaseFullReserveTest() {
		ge.setSeed(0);
		Player player = ge.getPlayer();
		Team team = player.getTeam();

		for (int i = 0; i < 5; i++)
			team.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, ge));

		assertEquals(5, team.getReserveAthletes().size());
		assertTrue(aht.purchase(player));
		assertEquals(5, team.getReserveAthletes().size());
	}

	@Test
	void purchaseFullTeamTest() {
		ge.setSeed(0);
		Player player = ge.getPlayer();
		Team team = player.getTeam();

		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.DEFENDER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.DRIBBLER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.DUNKER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.LONG_SHOOTER);
		team.addAthleteToActive((Athlete) Athlete.generateAthlete.apply(0, ge), Position.SHORT_SHOOTER);
		for (int i = 0; i < 5; i++)
			team.addAthleteToReserve((Athlete) Athlete.generateAthlete.apply(0, ge));

		assertFalse(aht.purchase(player));
		assertEquals(5, team.getReserveAthletes().size());
	}

	@Test
	void sellTest() {
		Player player = ge.getPlayer();
		Team team = player.getTeam();

		team.addAthleteToReserve(aht);
		aht.setPrice(107);
		aht.sell(player);
		assertEquals(0, team.getReserveAthletes().size());
		assertEquals(107, player.getMoney());
		player.chargeMoney(107);

		team.addAthleteToActive(aht, Position.SHORT_SHOOTER);
		aht.sell(player);
		assertEquals(107, player.getMoney());
		assertNull(team.getActiveAthletes().get(Position.SHORT_SHOOTER));
	}

	@Test
	void descriptionTest() {
		assertEquals(String.class, aht.getDescription().getClass());
		assertNotEquals("", aht.getDescription());
	}

	@Test
	void detailsTest() {
		assertEquals(String.class, aht.getDetails().getClass());
		assertNotEquals("", aht.getDetails());
	}

	@Test
	void teamTest() {
		Player player = ge.getPlayer();
		Team team = player.getTeam();
		assertNull(aht.getTeam());
		team.addAthleteToReserve(aht);
		assertEquals(team, aht.getTeam());
		aht.sell(player);
		team.addAthleteToActive(aht, Position.SHORT_SHOOTER);
		assertEquals(team, aht.getTeam());
	}

	@Test
	void matchScoreTest() {
		for (Statistic statistic : Statistic.values()) {
			aht.setStatistic(statistic, 0);
		}
		assertEquals(0, aht.getMatchScore(Position.SHORT_SHOOTER));
		for (Statistic statistic : Statistic.values()) {
			aht.setStatistic(statistic, 100);
		}

		int boostedScore = aht.getMatchScore(Position.SHORT_SHOOTER);
		Athlete athlete2 = new Athlete("", Position.DUNKER, 0, ge, 0);
		for (Statistic statistic : Statistic.values()) {
			athlete2.setStatistic(statistic, 100);
		}
		int normalScore = athlete2.getMatchScore(Position.SHORT_SHOOTER);

		assertTrue(boostedScore > 0);
		assertTrue(boostedScore > normalScore);
	}

	@Test
	void steroidTest() {
		assertFalse(aht.getHasUsedSteroids());
		aht.setHasUsedSteroids(true);
		assertTrue(aht.getHasUsedSteroids());
		aht.setHasUsedSteroids(false);
		Steroid steroid = new Steroid("", 0, 0, ge);
		steroid.applyItem(aht);
		assertTrue(aht.getHasUsedSteroids());
	}
}
