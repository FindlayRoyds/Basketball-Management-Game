package userinterface.graphical;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import enumeration.Location;
import enumeration.Position;
import game.Athlete;
import game.location.GameStart;
import userinterface.graphical.components.ComponentList;
import userinterface.graphical.components.PurchasableInfoLarge;
import userinterface.graphical.components.PurchasableInfoSmall;

@SuppressWarnings("serial")
public class SetupForm2 extends GUILocation {
	private GameStart gameLocation;

	private List<JPanel> athleteInfoComponents;
	private List<Athlete> athletes;
	private ComponentList athleteList;
	private int selectedAthleteIndex;
	private JLabel remainingPositionsLabel;

	public void displayAthleteDetailsPanel() {
		PurchasableInfoLarge athleteDetailsPanel = new PurchasableInfoLarge(athletes.get(selectedAthleteIndex), false);
		athleteDetailsPanel.setBounds(360, 0, 350, 400);
		add(athleteDetailsPanel);
		athleteDetailsPanel.revalidate();
		athleteDetailsPanel.repaint();
	}

	public void makeAthleteInfoComponents() {
		athletes = new ArrayList<Athlete>(gameLocation.getStartingAthletes());
		athleteInfoComponents = athletes.stream().map(athlete -> (JPanel) (new PurchasableInfoSmall(athlete, false)))
				.collect(Collectors.toList());
		displayAthleteDetailsPanel();
	}

	private void updateRemainingPositionsLabel() {
		int numberOfRemainingPositions = gameLocation.getUnfilledTeamPositions().size();
		remainingPositionsLabel.setText("You have " + numberOfRemainingPositions + " athletes left to choose.");
	}

	private void onAthleteSelect(int index) {
		selectedAthleteIndex = index;
		makeAthleteInfoComponents();
		athleteList.refresh(athleteInfoComponents, selectedAthleteIndex, (i) -> onAthleteSelect(i));
	}

	private void chooseAthlete() {
		Athlete selectedAthlete = athletes.get(selectedAthleteIndex);

		String popupMessage = "Which position would you like to put " + selectedAthlete.getName() + " at?";

		List<Position> unfilledPositions = gameLocation.getUnfilledTeamPositions();
		String[] popupOptions = unfilledPositions.stream().map(Position -> Position.name()).toArray(String[]::new);

		int chosenPositionIndex = guiEnvironment.displayPopup(popupMessage, popupOptions);
		gameLocation.chooseAthlete(selectedAthlete, unfilledPositions.get(chosenPositionIndex));

		if (unfilledPositions.size() == 1) {
			gameLocation.changeLocation(Location.MAP);
		} else {
			makeAthleteInfoComponents();
			onAthleteSelect(selectedAthleteIndex);
			updateRemainingPositionsLabel();
		}
	}

	public SetupForm2(GameStart gameLocation, GUIEnvironment guiEnvironment, Runnable onSubmit) {
		super(guiEnvironment);
		this.gameLocation = gameLocation;

		selectedAthleteIndex = 0;
		makeAthleteInfoComponents();

		athleteList = new ComponentList(athleteInfoComponents, 100, new Rectangle(0, 0, 350, 400));
		athleteList.refresh(athleteInfoComponents, selectedAthleteIndex, (index) -> onAthleteSelect(index));
		add(athleteList);

		JButton chooseAthleteButton = new JButton("Add to team");
		chooseAthleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseAthlete();
			}
		});
		chooseAthleteButton.setBounds(450, 450, 120, 27);
		add(chooseAthleteButton);

		remainingPositionsLabel = new JLabel();
		remainingPositionsLabel.setBounds(200, 450, 500, 27);
		updateRemainingPositionsLabel();
		add(remainingPositionsLabel);

		displayAthleteDetailsPanel();
	}
}
