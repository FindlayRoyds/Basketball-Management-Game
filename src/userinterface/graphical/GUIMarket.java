package userinterface.graphical;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

import game.Purchasable;
import game.location.GameLocation;
import game.location.GameMarket;
import userinterface.graphical.components.PurchasableExplorer;
import userinterface.graphical.components.ReturnToMapButton;
import userinterface.graphical.components.Title;

/**
 * A class that defines the market GUI location. It contains a
 * PurchasableExplorer component displaying purchasables that can be either (1)
 * bought or (2) sold, and a graphical interface to switch between these
 * options.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GUIMarket extends GUILocation {
	/**
	 * The corresponding game location class. Acts as the point of communication for
	 * interacting with the backend game logic.
	 */
	private GameMarket gameLocation;

	/**
	 * The explorer that displays the purchasables available to buy
	 */
	private PurchasableExplorer purchasableExplorer;

	/**
	 * The component that displays the market's name
	 */
	private Title marketTitle;

	/**
	 * The button to select a purchasable
	 */
	private JButton choosePurchasableButton;

	/**
	 * Whether the sell screen is being shown
	 */
	private boolean showSellScreen;

	/**
	 * The name of the market
	 */
	private String name;

	/**
	 * The button that swaps between sell and buy mode
	 */
	private JButton swapViewButton;

	/**
	 * The label that displays the player's money
	 */
	private JLabel moneyLabel;

	/**
	 * Refreshes the purchasable explorer to use the latest purchasable data.
	 */
	public void refresh() {
		marketTitle.setText(name + " - " + (showSellScreen ? "Sell" : "Buy"));

		swapViewButton.setText(showSellScreen ? "Change to buying" : "Change to selling");

		moneyLabel.setText("$" + gameLocation.getPlayerMoney());

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
	}

	/**
	 * Constructor for the GUIMarket component.
	 * 
	 * @param gameLocation   The GUI location's corresponding game location class.
	 * @param guiEnvironment The GUI environment to which the GUI location belongs.
	 * @param name           The name of the gui market to be displayed
	 */
	public GUIMarket(GameLocation gameLocation, GUIEnvironment guiEnvironment, String name) {
		super(guiEnvironment);
		this.gameLocation = (GameMarket) gameLocation;
		this.name = name;
		showSellScreen = false;
		setLayout(null);

		marketTitle = new Title("loading...");
		add(marketTitle);

		moneyLabel = new JLabel("Loading..");
		moneyLabel.setForeground(new Color(0, 100, 0));
		moneyLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		moneyLabel.setBounds(16, 8, 200, 50);
		add(moneyLabel);

		choosePurchasableButton = new JButton();
		purchasableExplorer = new PurchasableExplorer(() -> new ArrayList<Purchasable>(), true);
		add(purchasableExplorer);

		choosePurchasableButton = new JButton("Loading...");
		choosePurchasableButton.setBounds(510, 558, 170, 36);
		choosePurchasableButton.setBackground(new Color(225, 222, 222));
		add(choosePurchasableButton);

		ReturnToMapButton backButton = new ReturnToMapButton(gameLocation);
		add(backButton);

		swapViewButton = new JButton("Loading...");
		swapViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showSellScreen = !showSellScreen;
				refresh();
			}
		});
		swapViewButton.setBounds(300, 558, 170, 36);
		swapViewButton.setBackground(new Color(225, 222, 222));
		add(swapViewButton);

		refresh();
	}
}
