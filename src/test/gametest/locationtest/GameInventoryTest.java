package test.gametest.locationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enumeration.Location;
import enumeration.Position;
import game.Athlete;
import game.GameEnvironment;
import game.item.Item;
import game.location.GameInventory;
import userinterface.graphical.GUIEnvironment;

class GameInventoryTest {
	private GameEnvironment gameEnvironment;
	private GameInventory gameInventory;

	@BeforeEach
	void setUp() {
		gameEnvironment = new GameEnvironment(false);
		gameEnvironment.setSeed(0);
		gameInventory = (GameInventory) gameEnvironment.getGameLocation(Location.INVENTORY);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) gameEnvironment.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		gameInventory = new GameInventory(gameEnvironment);
	}

	@Test
	void updateTest() {
		for (int i = 0; i <= 15; ++i)
			gameInventory.update(i);
	}

	@Test
	void itemsTest() {
		assertEquals(gameEnvironment.getPlayer().getInventory(), gameInventory.getItems());
		Item newItem = (Item) Item.generateLegalItem.apply(50, gameEnvironment);
		gameEnvironment.getPlayer().addToInventory(newItem);
		assertEquals(gameEnvironment.getPlayer().getInventory(), gameInventory.getItems());

		int oldInventorySize = gameInventory.getItems().size();
		Athlete athlete = new Athlete("name", Position.DUNKER, 1, gameEnvironment, 1);
		gameEnvironment.getPlayer().getTeam().addAthleteToReserve(athlete);
		gameInventory.useItem(newItem);
		assertEquals(gameEnvironment.getPlayer().getInventory(), gameInventory.getItems());
		assertEquals(oldInventorySize - 1, gameInventory.getItems().size());
	}
}
