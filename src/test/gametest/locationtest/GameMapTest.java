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
	private GameEnvironment gameEnvironment;
	private GameMap gameMap;

	@BeforeEach
	void setUp() {
		gameEnvironment = new GameEnvironment(false);
		gameEnvironment.setSeed(0);
		gameMap = (GameMap) gameEnvironment.getGameLocation(Location.MAP);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) gameEnvironment.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		gameMap = new GameMap(gameEnvironment);
	}

	@Test
	void updateTest() {
		for (int i = 0; i <= 15; ++i)
			gameMap.update(i);
	}

	@Test
	void byeTest() {
		int seasonLength = 5;
		gameEnvironment.setSeasonLength(seasonLength);
		for (int i = 0; i < seasonLength; ++i) {
			gameMap.takeABye();
			assertEquals(i + 1, gameEnvironment.getWeek());
			assertEquals(i + 1, gameMap.getWeek());
			assertEquals(seasonLength - i - 1, gameMap.getWeeksRemaining());
		}
	}

	@Test
	void moneyTest() {
		assertEquals(gameEnvironment.getPlayer().getMoney(), gameMap.getMoney());
		gameEnvironment.getPlayer().giveMoney(10);
		assertEquals(gameEnvironment.getPlayer().getMoney(), gameMap.getMoney());
		gameEnvironment.getPlayer().giveMoney(60);
	}

	@Test
	void gameEndTest() {
		gameMap.checkForGameEnd();
	}
}
