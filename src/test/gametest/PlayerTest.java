package test.gametest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.GameEnvironment;
import game.Player;
import game.Team;
import game.item.Item;
import game.item.StatisticBoost;
import game.item.Steroid;
import userinterface.graphical.GUIEnvironment;

class PlayerTest {
	private GameEnvironment gameEnvironment;
	private Player player;

	@BeforeEach
	void setUp() throws Exception {
		gameEnvironment = new GameEnvironment(false);
		gameEnvironment.setSeed(0);
		player = gameEnvironment.getPlayer();
	}

	@AfterEach
	void tearDown() throws Exception {
		JFrame frame = ((GUIEnvironment) gameEnvironment.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		assertEquals(0, player.getMoney());
		assertEquals(0, player.getScore());
		assertTrue(player.getInventory().size() == 0);
		assertTrue(player.getTeam() instanceof Team);
	}

	@Test
	void inventoryTest() {
		Item steroid = (Item) Steroid.generateSteroid.apply(0, gameEnvironment);
		Item statBoost = (Item) StatisticBoost.generateStatisticBoost(0, gameEnvironment);
		player.addToInventory(statBoost);
		assertEquals(1, player.getInventory().size());
		player.addToInventory(statBoost);
		assertEquals(1, player.getInventory().size());
		player.addToInventory(steroid);
		assertEquals(2, player.getInventory().size());
		player.removeFromInventory(statBoost);
		assertEquals(1, player.getInventory().size());
		player.removeFromInventory(steroid);
		assertEquals(0, player.getInventory().size());
	}

	@Test
	void moneyTest() {
		assertEquals(0, player.getMoney());
		player.giveMoney(100);
		assertEquals(100, player.getMoney());
		assertTrue(player.chargeMoney(99));
		assertTrue(player.chargeMoney(1));
		assertFalse(player.chargeMoney(1));
		assertTrue(player.chargeMoney(0));
		assertEquals(0, player.getMoney());
	}

	@Test
	void pointsTest() {
		assertEquals(0, player.getScore());
		player.givePoints(100);
		assertEquals(100, player.getScore());
		player.givePoints(99);
		assertEquals(199, player.getScore());
	}
}
