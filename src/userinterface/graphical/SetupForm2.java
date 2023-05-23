package userinterface.graphical;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;

import enumeration.Position;
import game.Athlete;
import game.Purchasable;
import game.location.GameStart;
import userinterface.graphical.components.PurchasableExplorer;

/**
 * A class that defines the second setup form to be displayed at the start of
 * the game. Uses a purchasable explorer component to allow the user to select
 * their starting athletes from a given list.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SetupForm2 extends GUILocation {
	/**
	 * The corresponding start game location class. Acts as the point of
	 * communication for interacting with the backend game logic.
	 */
	private GameStart gameLocation;

	/**
	 * Components with dynamic content.
	 */
	private JLabel remainingPositionsLabel;

	/**
	 * The purchasable explorer component that displays the available athletes
	 */
	private PurchasableExplorer purchasableExplorer;

	/**
	 * The function to run when all athletes have been selected.
	 */
	private Runnable onSubmit;

	/**
	 * Updates the label showing how many positions remain to assign athletes to.
	 */
	private void updateRemainingPositionsLabel() {
		int numberOfRemainingPositions = gameLocation.getUnfilledTeamPositions().size();
		remainingPositionsLabel.setText("You have " + numberOfRemainingPositions + " athletes left to choose.");
	}

	/**
	 * Prompts the user asking what position to add the given athlete to. Then
	 * instructs the game location to handle adding the athlete to that position.
	 * 
	 * @param selectedAthlete The athlete to add to the team.
	 */
	private void chooseAthlete(Athlete selectedAthlete) {
		String popupMessage = "Which position would you like to put " + selectedAthlete.getName() + " into?";

		List<Position> unfilledPositions = gameLocation.getUnfilledTeamPositions();
		String[] popupOptions = unfilledPositions.stream().map(position -> position.name())
				.map(name -> name.replaceAll("_", " ").toLowerCase()).toArray(String[]::new);

		int chosenPositionIndex = guiEnvironment.displayPopup(popupMessage, popupOptions);
		gameLocation.chooseAthlete(selectedAthlete, unfilledPositions.get(chosenPositionIndex));

		if (unfilledPositions.size() == 1) {
			onSubmit.run();
		} else {
			updateRemainingPositionsLabel();
		}
	}

	/**
	 * Constructor for the SetupForm2 component. Provides the user with a graphical
	 * interface to select their starting athletes.
	 * 
	 * @param gameLocation   The GUI location's corresponding game location.
	 * @param guiEnvironment The GUI environment to which the GUI location belongs.
	 * @param onSubmit       A function to be run when the user has finished
	 *                       selecting all their starting athletes.
	 */
	public SetupForm2(GameStart gameLocation, GUIEnvironment guiEnvironment, Runnable onSubmit) {
		super(guiEnvironment);
		this.gameLocation = gameLocation;
		setLayout(null);
		this.onSubmit = onSubmit;

		JButton chooseAthleteButton = new JButton("Add to team");
		chooseAthleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Athlete selectedAthlete = (Athlete) purchasableExplorer.getSelected();
				if (selectedAthlete != null) {
					chooseAthlete(selectedAthlete);
					purchasableExplorer.refresh();
				}
			}
		});
		chooseAthleteButton.setBounds(510, 480, 170, 36);
		chooseAthleteButton.setBackground(new Color(225, 222, 222));
		add(chooseAthleteButton);

		remainingPositionsLabel = new JLabel();
		remainingPositionsLabel.setBounds(220, 485, 500, 27);
		updateRemainingPositionsLabel();
		add(remainingPositionsLabel);

		purchasableExplorer = new PurchasableExplorer(
				() -> new ArrayList<Purchasable>(gameLocation.getStartingAthletes()));
		purchasableExplorer.setBounds(0, 0, 800, 550);
		add(purchasableExplorer);
	}
}
