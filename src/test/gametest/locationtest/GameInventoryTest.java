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
	private GameEnvironment ge;
	private GameInventory gameInventory;

	@BeforeEach
	void setUp() {
		ge = new GameEnvironment(false);
		ge.setSeed(0);
		gameInventory = (GameInventory) ge.getGameLocation(Location.INVENTORY);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) ge.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		gameInventory = new GameInventory(ge);
	}

	@Test
	void updateTest() {
		gameInventory.update(10);
	}

	@Test
	void itemsTest() {
		assertEquals(ge.getPlayer().getInventory(), gameInventory.getItems());
		Item newItem = (Item) Item.generateLegalItem.apply(50, ge);
		ge.getPlayer().addToInventory(newItem);
		assertEquals(ge.getPlayer().getInventory(), gameInventory.getItems());

		int oldInventorySize = gameInventory.getItems().size();
		Athlete athlete = new Athlete("name", Position.DUNKER, 1, ge, 1);
		ge.getPlayer().getTeam().addAthleteToReserve(athlete);
		gameInventory.useItem(newItem);
		assertEquals(ge.getPlayer().getInventory(), gameInventory.getItems());
		assertEquals(oldInventorySize - 1, gameInventory.getItems().size());
	}
}
