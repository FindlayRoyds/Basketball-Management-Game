package userinterface.graphical;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import enumeration.Location;
import game.Purchasable;
import game.item.Item;
import game.location.GameInventory;
import game.location.GameLocation;
import userinterface.graphical.components.PurchasableExplorer;

/**
 * A class that defines the inventory GUI location. It contains a
 * PurchasableExplorer component displaying the items in the player's inventory
 * and provides a graphical interface for using items.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GUIInventory extends GUILocation {
	/**
	 * The corresponding game location class. Acts as the point of communication for
	 * interacting with the backend game logic.
	 */
	private GameInventory gameLocation;

	/**
	 * The purchasable explorer component to display. Provides a means to view and
	 * select items.
	 */
	private PurchasableExplorer purchasableExplorer;

	/**
	 * Instructs the game location to use the item currently selected in the
	 * purchasable explorer, if any. Then refreshes the purchasable explorer
	 * content.
	 */
	private void useItem() {
		Item selectedItem = (Item) purchasableExplorer.getSelected();
		if (selectedItem != null) {
			gameLocation.useItem(selectedItem);
			purchasableExplorer.refresh();
		}
	}

	/**
	 * Instructs the game location to change the current location to the map.
	 */
	private void returnToMap() {
		gameLocation.changeLocation(Location.MAP);
	}

	/**
	 * @return A list of the purchasable items in the player's inventory.
	 */
	private List<Purchasable> getItems() {
		return new ArrayList<Purchasable>(gameLocation.getItems());
	}

	/**
	 * Refreshes the purchasableExplorer to use the latest item data.
	 */
	@Override
	public void refresh() {
		purchasableExplorer.refresh();
	}

	/**
	 * Constructor for the GUIInventory component.
	 * 
	 * @param gameLocation   The GUI location's corresponding game location class.
	 * @param guiEnvironment The GUI environment to which the GUI location belongs.
	 */
	public GUIInventory(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		this.gameLocation = (GameInventory) gameLocation;
		setLayout(null);

		JLabel inventoryTitleLabel = new JLabel("Inventory");
		inventoryTitleLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		inventoryTitleLabel.setBounds(250, 15, 200, 17);
		inventoryTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(inventoryTitleLabel);

		JButton returnToMapButton = new JButton("Return to map");
		returnToMapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMap();
			}
		});
		returnToMapButton.setBounds(100, 500, 120, 27);
		add(returnToMapButton);

		JButton useItemButton = new JButton("Use item");
		useItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				useItem();
			}
		});
		useItemButton.setBounds(450, 500, 120, 27);
		add(useItemButton);

		purchasableExplorer = new PurchasableExplorer(() -> getItems());
		purchasableExplorer.setBounds(0, 60, 1000, 1000);
		purchasableExplorer.refresh();
		add(purchasableExplorer);
	}
}
