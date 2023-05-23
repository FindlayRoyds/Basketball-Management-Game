package userinterface.graphical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import enumeration.Location;
import game.location.GameLocation;
import game.location.GameStart;

/**
 * A class that defines the start GUI location. It consists of two forms,
 * displayed one after the other. The first gets game configuration input from
 * the user, and the second allows for the selection of starting athletes to add
 * to the team, using a purchasable explorer component.
 * 
 * @author Jake van Keulen, Findlay Royds
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GUIStart extends GUILocation {
	/**
	 * The corresponding game location class. Acts as the point of communication for
	 * interacting with the backend game logic.
	 */
	private GameStart gameLocation;

	/**
	 * The two forms used for user input.
	 */
	private JPanel form1, form2;

	/**
	 * Hides the first setup form from view and shows the second one.
	 */
	private void showForm2() {
		form2 = new SetupForm2(this.gameLocation, guiEnvironment, () -> {
			gameLocation.progressWeek();
			gameLocation.changeLocation(Location.MAP);
		});
		form2.setBounds(0, 65, 800, 550);
		add(form2);

		form2.setVisible(true);
		form1.setVisible(false);
	}

	/**
	 * Constructor for the GUIStart component. Displays the first input form to
	 * begin with.
	 * 
	 * @param gameLocation   The GUI location's corresponding game location class.
	 * @param guiEnvironment The GUI environment to which the GUI location belongs.
	 */
	public GUIStart(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		this.gameLocation = (GameStart) gameLocation;
		setPreferredSize(new Dimension(800, 600));

		form1 = new SetupForm1(this.gameLocation, guiEnvironment, () -> showForm2());
		form1.setBounds(0, 70, 800, 550);
		add(form1);

		JLabel titleLabel = new JLabel("Game Setup");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		titleLabel.setBounds(12, 6, 788, 50);
		add(titleLabel);
	}
}
