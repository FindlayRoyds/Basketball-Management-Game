package test.gametest.locationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enumeration.Location;
import game.GameEnvironment;
import game.location.GameMap;
import userinterface.graphical.GUIEnvironment;

class GameMapTest {
	private GameEnvironment ge;
	private GameMap gameMap;

	@BeforeEach
	void setUp() {
		ge = new GameEnvironment(false);
		ge.setSeed(0);
		gameMap = (GameMap) ge.getGameLocation(Location.MAP);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) ge.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		gameMap = new GameMap(ge);
	}

	@Test
	void updateTest() {
		for (int i = 0; i <= 15; ++i)
			gameMap.update(i);
	}

	@Test
	void byeTest() {
		int seasonLength = 5;
		ge.setSeasonLength(seasonLength);
		for (int i = 0; i < seasonLength; ++i) {
			gameMap.takeABye();
			assertEquals(i + 1, ge.getWeek());
			assertEquals(i + 1, gameMap.getWeek());
			assertEquals(seasonLength - i - 1, gameMap.getWeeksRemaining());
		}
	}

	@Test
	void moneyTest() {
		assertEquals(ge.getPlayer().getMoney(), gameMap.getMoney());
		ge.getPlayer().giveMoney(10);
		assertEquals(ge.getPlayer().getMoney(), gameMap.getMoney());
		ge.getPlayer().giveMoney(60);
	}
}
