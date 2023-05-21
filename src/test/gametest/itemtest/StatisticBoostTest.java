package test.gametest.itemtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enumeration.Position;
import enumeration.Statistic;
import game.Athlete;
import game.GameEnvironment;
import game.item.StatisticBoost;
import userinterface.graphical.GUIEnvironment;

class StatisticBoostTest {
	private GameEnvironment ge;

	@BeforeEach
	void setUp() {
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
	void constructorTest() {
		StatisticBoost statisticBoost = new StatisticBoost("Test", "Test2", 0, 0, Statistic.DEFENCE, ge);
		assertTrue(statisticBoost.getIsLegal());
		assertEquals("Test", statisticBoost.getName());
		assertEquals("Test2", statisticBoost.getDescription());
		assertEquals(0, statisticBoost.getPrice());
		statisticBoost = new StatisticBoost("", "", 100, 0, Statistic.DEFENCE, ge);
		assertEquals("", statisticBoost.getDescription());
		assertEquals("", statisticBoost.getName());
		assertEquals(100, statisticBoost.getPrice());
	}

	@Test
	void generatorTest() {
		StatisticBoost statisticBoost = (StatisticBoost) StatisticBoost.generateStatisticBoost(100, ge);
		assertTrue(statisticBoost instanceof StatisticBoost);
	}

	@Test
	void applyTest() {
		Athlete athlete = new Athlete("", Position.DUNKER, 0, ge, 0);
		StatisticBoost statisticBoost = new StatisticBoost("Test", "Test", 0, 57, Statistic.DEFENCE, ge);
		assertEquals(0, athlete.getStatistic(Statistic.DEFENCE));
		statisticBoost.applyItem(athlete);
		assertEquals(57, athlete.getStatistic(Statistic.DEFENCE));
		statisticBoost.applyItem(athlete);
		assertEquals(100, athlete.getStatistic(Statistic.DEFENCE));
	}

	@Test
	void detailsTest() {
		StatisticBoost statisticBoost = (StatisticBoost) StatisticBoost.generateStatisticBoost(100, ge);
		assertTrue(statisticBoost.getDetails() != null);
		assertTrue(statisticBoost.getDetails() instanceof String);
	}
}
