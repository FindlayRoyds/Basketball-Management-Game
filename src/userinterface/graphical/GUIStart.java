package userinterface.graphical;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import enumeration.Location;
import game.location.GameLocation;
import game.location.GameStart;

public class GUIStart extends GUILocation {
	private static final long serialVersionUID = 1L;
	private GameStart gameLocation;

	private JPanel form1, form2;

	/**
	 * Hides the first setup form from view and shows the second one.
	 */
	private void showForm2() {
		form2 = new SetupForm2(this.gameLocation, guiEnvironment, () -> gameLocation.changeLocation(Location.MAP));
		form2.setBounds(0, 50, 800, 600);
		add(form2);

		form2.setVisible(true);
		form1.setVisible(false);
	}

	/**
	 * Create the panel.
	 */
	public GUIStart(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		this.gameLocation = (GameStart) gameLocation;

		JLabel startTitle = new JLabel("Game Setup");
		startTitle.setFont(new Font("Dialog", Font.BOLD, 16));
		startTitle.setBounds(169, 25, 107, 17);
		startTitle.setHorizontalAlignment(SwingConstants.CENTER);
		add(startTitle);

		form1 = new SetupForm1(this.gameLocation, guiEnvironment, () -> showForm2());
		form1.setBounds(0, 50, 800, 600);
		add(form1);
	}
}
