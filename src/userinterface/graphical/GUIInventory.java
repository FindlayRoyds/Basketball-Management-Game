package userinterface.graphical;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import enumeration.Location;
import game.Purchasable;
import game.item.Item;
import game.location.GameInventory;
import game.location.GameLocation;
import userinterface.graphical.components.PurchasableExplorer;

public class GUIInventory extends GUILocation {
	private static final long serialVersionUID = 1L;
	GameInventory gameLocation;

	PurchasableExplorer purchasableExplorer;

	private void useItem() {
		Item selectedItem = (Item) purchasableExplorer.getSelected();
		if (selectedItem != null) {
			gameLocation.useItem(selectedItem);
			purchasableExplorer.refresh();
		}
	}

	private void returnToMap() {
		gameLocation.changeLocation(Location.MAP);
	}

	/**
	 * Create the panel.
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

		purchasableExplorer = new PurchasableExplorer(() -> new ArrayList<Purchasable>(this.gameLocation.getItems()));
		purchasableExplorer.setBounds(0, 60, 1000, 1000);
		add(purchasableExplorer);
	}
}
