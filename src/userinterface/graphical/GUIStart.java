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

public class GUIStart extends GUILocation {
	private static final long serialVersionUID = 1L;
	private GameStart gameLocation;

	private JPanel form1, form2;

	/**
	 * Hides the first setup form from view and shows the second one.
	 */
	private void showForm2() {
		form2 = new SetupForm2(this.gameLocation, guiEnvironment, () -> {
			gameLocation.progressWeek();
			gameLocation.changeLocation(Location.MAP);
		});
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
		setPreferredSize(new Dimension(800, 600));

		form1 = new SetupForm1(this.gameLocation, guiEnvironment, () -> showForm2());
		form1.setBounds(211, 68, 414, 600);
		add(form1);

		JLabel titleLabel = new JLabel("Game Setup");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		titleLabel.setBounds(12, 6, 788, 50);
		add(titleLabel);
	}
}
