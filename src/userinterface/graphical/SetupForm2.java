package userinterface.graphical;

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

@SuppressWarnings("serial")
public class SetupForm2 extends GUILocation {
	private GameStart gameLocation;

	private JLabel remainingPositionsLabel;
	private PurchasableExplorer purchasableExplorer;
	private Runnable onSubmit;

	private void updateRemainingPositionsLabel() {
		int numberOfRemainingPositions = gameLocation.getUnfilledTeamPositions().size();
		remainingPositionsLabel.setText("You have " + numberOfRemainingPositions + " athletes left to choose.");
	}

	private void chooseAthlete(Athlete selectedAthlete) {
		String popupMessage = "Which position would you like to put " + selectedAthlete.getName() + " into?";

		List<Position> unfilledPositions = gameLocation.getUnfilledTeamPositions();
		String[] popupOptions = unfilledPositions.stream().map(Position -> Position.name())
				.map(name -> name.replaceAll("_", " ").toLowerCase()).toArray(String[]::new);

		int chosenPositionIndex = guiEnvironment.displayPopup(popupMessage, popupOptions);
		gameLocation.chooseAthlete(selectedAthlete, unfilledPositions.get(chosenPositionIndex));

		if (unfilledPositions.size() == 1) {
			onSubmit.run();
		} else {
			updateRemainingPositionsLabel();
		}
	}

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
		chooseAthleteButton.setBounds(450, 450, 120, 27);
		add(chooseAthleteButton);

		remainingPositionsLabel = new JLabel();
		remainingPositionsLabel.setBounds(200, 450, 500, 27);
		updateRemainingPositionsLabel();
		add(remainingPositionsLabel);

		purchasableExplorer = new PurchasableExplorer(
				() -> new ArrayList<Purchasable>(gameLocation.getStartingAthletes()));
		// purchasableExplorer.setBounds(0, 0, 500, 500);
		add(purchasableExplorer);
	}
}
