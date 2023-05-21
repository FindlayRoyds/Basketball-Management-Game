package userinterface.graphical;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import enumeration.Location;
import enumeration.Position;
import game.Athlete;
import game.Purchasable;
import game.location.GameLocation;
import game.location.GameLocker;
import userinterface.graphical.components.AthleteExplorer;
import userinterface.graphical.components.PurchasableExplorer;
import util.MiscUtil;

public class GUILocker extends GUILocation {
	private static final long serialVersionUID = 1L;
	GameLocker gameLocation;

	PurchasableExplorer purchasableExplorer;
	JLabel teamLayoutLabel;

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

	private void returnToMap() {
		gameLocation.changeLocation(Location.MAP);
	}

	private List<Purchasable> getAthletes() {
		return new ArrayList<Purchasable>(gameLocation.getAllAthletes());
	}

	public void refresh() {
		purchasableExplorer.refresh();
	}

	/**
	 * Create the panel.
	 */
	public GUILocker(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		this.gameLocation = (GameLocker) gameLocation;
		setPreferredSize(new Dimension(800, 600));
		setLayout(null);

		JLabel inventoryTitleLabel = new JLabel("Locker Room");
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
		returnToMapButton.setBounds(70, 500, 120, 27);
		add(returnToMapButton);

		JButton moveAthleteButton = new JButton("Move Athlete");
		moveAthleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveAthlete();
			}
		});
		moveAthleteButton.setBounds(450, 500, 120, 27);
		add(moveAthleteButton);

		purchasableExplorer = new AthleteExplorer(() -> getAthletes());
		purchasableExplorer.setBounds(0, 60, 800, 420);
		refresh();
		add(purchasableExplorer);
	}
}
