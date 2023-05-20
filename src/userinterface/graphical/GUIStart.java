package userinterface.graphical;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import enumeration.Location;
import game.location.GameLocation;

public class GUIStart extends GUILocation {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public GUIStart(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(242, 186, 61, 16);
		add(lblNewLabel);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameLocation.changeLocation(Location.MAP);
			}
		});
		btnNewButton.setBounds(231, 276, 117, 29);
		add(btnNewButton);

		JButton PopupMaker = new JButton("Popup");
		PopupMaker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiEnvironment.displayPopup("e");
			}
		});
		PopupMaker.setBounds(385, 111, 117, 29);
		add(PopupMaker);
	}

	@Override
	public Location refresh() {
		// TODO Auto-generated method stub
		return null;
	}
}
