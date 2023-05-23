package userinterface.graphical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import enumeration.Location;
import enumeration.Position;
import game.Athlete;
import game.Purchasable;
import game.location.GameLocation;
import game.location.GameLocker;
import userinterface.graphical.components.AthleteExplorer;
import userinterface.graphical.components.PurchasableExplorer;
import userinterface.graphical.components.ReturnToMapButton;
import userinterface.graphical.components.Title;
import util.MiscUtil;

/**
 * A class that defines the locker GUI location. It contains an AthleteExplorer
 * component displaying the athletes in the player's team and provides a
 * graphical interface for moving players between positions within the team.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GUILocker extends GUILocation {
	/**
	 * The corresponding game location class. Acts as the point of communication for
	 * interacting with the backend game logic.
	 */
	private GameLocker gameLocation;

	/**
	 * Components with dynamic content.
	 */
	private PurchasableExplorer purchasableExplorer;
	private Title inventoryTitleLabel;

	/**
	 * Gets the selected athlete from the athlete explorer and prompts the user
	 * asking what position to move them to. Then instructs the game location to
	 * handle moving the athlete to that position.
	 */
	private void moveAthlete() {
		Athlete selectedAthlete = (Athlete) purchasableExplorer.getSelected();
		if (selectedAthlete != null) {
			String[] options = { "Reserve Team", "Active Team" };
			if (guiEnvironment.displayPopup("Where would you like to move the athlete to?", options) == 0)
				gameLocation.moveToReserve(selectedAthlete);
			else {
				String[] positionOptions = MiscUtil.getEnumerationNames(Position.class);
				int selectedPositionIndex = guiEnvironment.displayPopup("And at what position?", positionOptions);
				gameLocation.moveToActive(selectedAthlete, Position.values()[selectedPositionIndex]);
			}
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
	 * @return A list of the athletes in the player's team.
	 */
	private List<Purchasable> getAthletes() {
		return new ArrayList<Purchasable>(gameLocation.getAllAthletes());
	}

	/**
	 * Refreshes the athlete explorer to use the latest athlete data.
	 */
	public void refresh() {
		purchasableExplorer.refresh();
		this.inventoryTitleLabel.setText("Locker Room - " + this.gameLocation.getTeamName());
	}

	/**
	 * Constructor for the GUILocker component.
	 * 
	 * @param gameLocation   The GUI location's corresponding game location class.
	 * @param guiEnvironment The GUI environment to which the GUI location belongs.
	 */
	public GUILocker(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		this.gameLocation = (GameLocker) gameLocation;
		setPreferredSize(new Dimension(800, 600));
		setLayout(null);

		inventoryTitleLabel = new Title("");
		add(inventoryTitleLabel);

		ReturnToMapButton backButton = new ReturnToMapButton(gameLocation);
		add(backButton);

		JButton moveAthleteButton = new JButton("Move Athlete");
		moveAthleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveAthlete();
			}
		});
		moveAthleteButton.setBounds(510, 558, 170, 36);
		moveAthleteButton.setBackground(new Color(225, 222, 222));

		add(moveAthleteButton);

		purchasableExplorer = new AthleteExplorer(() -> getAthletes());
		refresh();
		add(purchasableExplorer);
	}
}
