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
import game.location.GameLocation;
import game.location.GameMarket;
import userinterface.graphical.components.PurchasableExplorer;

public class GUIItemMarket extends GUILocation {
	private static final long serialVersionUID = 1L;
	private GameMarket gameLocation;

	private PurchasableExplorer purchasableExplorer;
	private JLabel marketTitle;
	private JButton chooseItemButton;
	private boolean showSellScreen;

	private void returnToMap() {
		gameLocation.changeLocation(Location.MAP);
	}

	public void refresh() {
		marketTitle.setText("Item Market - " + (showSellScreen ? "Sell" : "Purchase") + " Items");

		chooseItemButton.setText(showSellScreen ? "Sell item" : "Buy item");
		for (ActionListener oldListener : chooseItemButton.getActionListeners()) {
			chooseItemButton.removeActionListener(oldListener);
		}
		chooseItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item selectedItem = (Item) purchasableExplorer.getSelected();
				if (selectedItem != null) {
					if (showSellScreen) {
						gameLocation.sell(selectedItem);
					} else {
						gameLocation.purchase(selectedItem);
					}
				}
				refresh();
			}
		});

		purchasableExplorer
				.setSupplier(showSellScreen ? (() -> new ArrayList<Purchasable>(gameLocation.getOwnedAndAllowed()))
						: (() -> new ArrayList<Purchasable>(gameLocation.getAvailablePurchasables())));
		purchasableExplorer.refresh();

		revalidate();
		repaint();
	}

	/**
	 * Create the panel.
	 */
	public GUIItemMarket(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		this.gameLocation = (GameMarket) gameLocation;
		showSellScreen = false;
		setLayout(null);

		marketTitle = new JLabel("loading...");
		marketTitle.setFont(new Font("Dialog", Font.BOLD, 16));
		marketTitle.setBounds(169, 25, 107, 17);
		marketTitle.setHorizontalAlignment(SwingConstants.CENTER);
		add(marketTitle);

		chooseItemButton = new JButton();
		purchasableExplorer = new PurchasableExplorer(() -> new ArrayList<Purchasable>());
		purchasableExplorer.setBounds(0, 60, 1000, 1000);
		add(purchasableExplorer);

		chooseItemButton = new JButton("Loading...");
		chooseItemButton.setBounds(450, 500, 120, 27);
		add(chooseItemButton);

		JButton returnToMapButton = new JButton("Return to map");
		returnToMapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMap();
			}
		});
		returnToMapButton.setBounds(100, 500, 120, 27);
		add(returnToMapButton);

		refresh();
	}
}
