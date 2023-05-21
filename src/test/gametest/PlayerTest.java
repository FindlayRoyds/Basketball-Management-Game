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
	private GameEnvironment ge;
	private Player pl;

	@BeforeEach
	void setUp() throws Exception {
		ge = new GameEnvironment(false);
		ge.setSeed(0);
		pl = ge.getPlayer();
	}

	@AfterEach
	void tearDown() throws Exception {
		JFrame frame = ((GUIEnvironment) ge.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void ConstructorTest() {
		assertEquals(0, pl.getMoney());
		assertEquals(0, pl.getScore());
		assertTrue(pl.getInventory().size() == 0);
		assertTrue(pl.getTeam() instanceof Team);
	}

	@Test
	void inventoryTest() {
		Item steroid = (Item) Steroid.generateSteroid.apply(0, ge);
		Item statBoost = (Item) StatisticBoost.generateStatisticBoost(0, ge);
		pl.addToInventory(statBoost);
		assertEquals(1, pl.getInventory().size());
		pl.addToInventory(statBoost);
		assertEquals(1, pl.getInventory().size());
		pl.addToInventory(steroid);
		assertEquals(2, pl.getInventory().size());
		pl.removeFromInventory(statBoost);
		assertEquals(1, pl.getInventory().size());
		pl.removeFromInventory(steroid);
		assertEquals(0, pl.getInventory().size());
	}

	@Test
	void moneyTest() {
		assertEquals(0, pl.getMoney());
		pl.giveMoney(100);
		assertEquals(100, pl.getMoney());
		assertTrue(pl.chargeMoney(99));
		assertTrue(pl.chargeMoney(1));
		assertFalse(pl.chargeMoney(1));
		assertTrue(pl.chargeMoney(0));
		assertEquals(0, pl.getMoney());
	}

	@Test
	void pointsTest() {
		assertEquals(0, pl.getScore());
		pl.givePoints(100);
		assertEquals(100, pl.getScore());
		pl.givePoints(99);
		assertEquals(199, pl.getScore());
	}
}
