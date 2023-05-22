package test.gametest.itemtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import game.item.Steroid;
import userinterface.graphical.GUIEnvironment;

class SteroidTest {
	private GameEnvironment gameEnvironment;

	@BeforeEach
	void setUp() {
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
	void constructorTest() {
		Steroid steroid = new Steroid("Test", 0, 0, gameEnvironment);
		assertEquals("Test", steroid.getDescription());
		assertFalse(steroid.getIsLegal());
		assertEquals(0, steroid.getPrice());
		steroid = new Steroid("", 100, 0, gameEnvironment);
		assertEquals("", steroid.getDescription());
		assertEquals(100, steroid.getPrice());
	}

	@Test
	void generatorTest() {
		Steroid steroid = (Steroid) Steroid.generateSteroid.apply(100, gameEnvironment);
		assertTrue(steroid instanceof Steroid);
	}

	@Test
	void applyTest() {
		Athlete athlete = new Athlete("", Position.DUNKER, 0, gameEnvironment, 0);
		athlete.setStatistic(Statistic.HEIGHT, 0);
		Steroid steroid = new Steroid("Test", 0, 57, gameEnvironment);
		assertEquals(0, athlete.getStatistic(Statistic.HEIGHT));
		assertFalse(athlete.getHasUsedSteroids());
		steroid.applyItem(athlete);
		for (Statistic statistic : Statistic.values())
			assertEquals(57, athlete.getStatistic(statistic));
		steroid = new Steroid("Test", 0, 3, gameEnvironment);
		steroid.applyItem(athlete);
		for (Statistic statistic : Statistic.values())
			assertEquals(60, athlete.getStatistic(statistic));
		assertTrue(athlete.getHasUsedSteroids());
	}

	@Test
	void purchaseTest() {
		Steroid steroid = new Steroid("Test", 100, 0, gameEnvironment);
		Player player = gameEnvironment.getPlayer();
		player.giveMoney(99);
		assertFalse(steroid.purchase(player));
		player.giveMoney(1);
		assertTrue(steroid.purchase(player));
		assertEquals(0, player.getMoney());
	}

	@Test
	void sellTest() {
		Steroid steroid = new Steroid("Test", 100, 0, gameEnvironment);
		Player player = gameEnvironment.getPlayer();
		steroid.sell(player);
		assertEquals(100, player.getMoney());
		steroid.sell(player);
		assertEquals(200, player.getMoney());
	}

	@Test
	void detailsTest() {
		Steroid steroid = (Steroid) Steroid.generateSteroid.apply(100, gameEnvironment);
		assertTrue(steroid.getDetails() != null);
		assertTrue(steroid.getDetails() instanceof String);
	}
}
