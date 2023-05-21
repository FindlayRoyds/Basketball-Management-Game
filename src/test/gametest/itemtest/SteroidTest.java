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
import game.item.Steroid;
import userinterface.graphical.GUIEnvironment;

class SteroidTest {
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
		Steroid steroid = new Steroid("Test", 0, 0, ge);
		assertEquals("Test", steroid.getDescription());
		assertFalse(steroid.getIsLegal());
		assertEquals(0, steroid.getPrice());
		steroid = new Steroid("", 100, 0, ge);
		assertEquals("", steroid.getDescription());
		assertEquals(100, steroid.getPrice());
	}

	@Test
	void generatorTest() {
		Steroid steroid = (Steroid) Steroid.generateSteroid.apply(100, ge);
		assertTrue(steroid instanceof Steroid);
	}

	@Test
	void applyTest() {
		Athlete athlete = new Athlete("", Position.DUNKER, 0, ge, 0);
		athlete.setStatistic(Statistic.HEIGHT, 0);
		Steroid steroid = new Steroid("Test", 0, 57, ge);
		assertEquals(0, athlete.getStatistic(Statistic.HEIGHT));
		assertFalse(athlete.getHasUsedSteroids());
		steroid.applyItem(athlete);
		for (Statistic statistic : Statistic.values())
			assertEquals(57, athlete.getStatistic(statistic));
		steroid = new Steroid("Test", 0, 3, ge);
		steroid.applyItem(athlete);
		for (Statistic statistic : Statistic.values())
			assertEquals(60, athlete.getStatistic(statistic));
		assertTrue(athlete.getHasUsedSteroids());
	}

	@Test
	void detailsTest() {
		Steroid steroid = (Steroid) Steroid.generateSteroid.apply(100, ge);
		assertTrue(steroid.getDetails() != null);
		assertTrue(steroid.getDetails() instanceof String);
	}
}
