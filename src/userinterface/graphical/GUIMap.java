package userinterface.graphical;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import enumeration.Location;
import game.location.GameLocation;

public class GUIMap extends GUILocation {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public GUIMap(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Eeeh eeh");
		lblNewLabel.setBounds(242, 186, 61, 16);
		add(lblNewLabel);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameLocation.changeLocation(Location.START);
			}
		});
		btnNewButton.setBounds(121, 96, 117, 29);
		add(btnNewButton);
	}

	@Override
	public Location refresh() {
		return null;
	}
}
