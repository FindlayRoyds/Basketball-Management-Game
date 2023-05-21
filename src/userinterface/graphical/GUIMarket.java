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
import game.location.GameLocation;
import game.location.GameMarket;
import userinterface.graphical.components.PurchasableExplorer;

public class GUIMarket extends GUILocation {
	private static final long serialVersionUID = 1L;
	private GameMarket gameLocation;

	private PurchasableExplorer purchasableExplorer;
	private JLabel marketTitle;
	private JButton choosePurchasableButton;
	private boolean showSellScreen;
	private String name;
	private JButton swapViewButton;

	private void returnToMap() {
		gameLocation.changeLocation(Location.MAP);
	}

	public void refresh() {
		marketTitle.setText(name + " - " + (showSellScreen ? "Sell" : "Buy"));

		swapViewButton.setText(showSellScreen ? "Change to buying" : "Change to selling");

		choosePurchasableButton.setText(showSellScreen ? "Sell" : "Buy");
		for (ActionListener oldListener : choosePurchasableButton.getActionListeners()) {
			choosePurchasableButton.removeActionListener(oldListener);
		}
		choosePurchasableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Purchasable selectedPurchasable = (Purchasable) purchasableExplorer.getSelected();
				if (selectedPurchasable != null) {
					if (showSellScreen) {
						gameLocation.sell(selectedPurchasable);
					} else {
						gameLocation.purchase(selectedPurchasable);
					}
				}
				refresh();
			}
		});

		purchasableExplorer
				.setSupplier(showSellScreen ? (() -> new ArrayList<Purchasable>(gameLocation.getOwnedAndAllowed()))
						: (() -> new ArrayList<Purchasable>(gameLocation.getAvailablePurchasables())));
		purchasableExplorer.refresh();

		// revalidate();
		// repaint();
	}

	/**
	 * Create the panel.
	 */
	public GUIMarket(GameLocation gameLocation, GUIEnvironment guiEnvironment, String name) {
		super(guiEnvironment);
		this.gameLocation = (GameMarket) gameLocation;
		this.name = name;
		showSellScreen = false;
		setLayout(null);

		marketTitle = new JLabel("loading...");
		marketTitle.setFont(new Font("Dialog", Font.BOLD, 16));
		marketTitle.setBounds(169, 25, 300, 17);
		marketTitle.setHorizontalAlignment(SwingConstants.CENTER);
		add(marketTitle);

		choosePurchasableButton = new JButton();
		purchasableExplorer = new PurchasableExplorer(() -> new ArrayList<Purchasable>(), true);
		purchasableExplorer.setBounds(0, 60, 800, 420);
		add(purchasableExplorer);

		choosePurchasableButton = new JButton("Loading...");
		choosePurchasableButton.setBounds(450, 500, 120, 27);
		add(choosePurchasableButton);

		JButton returnToMapButton = new JButton("Return to map");
		returnToMapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMap();
			}
		});
		returnToMapButton.setBounds(100, 500, 120, 27);
		add(returnToMapButton);

		swapViewButton = new JButton("Loading...");
		swapViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showSellScreen = !showSellScreen;
				refresh();
			}
		});
		swapViewButton.setBounds(250, 500, 200, 27);
		add(swapViewButton);

		refresh();
	}
}
