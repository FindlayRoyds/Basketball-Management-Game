package userinterface.graphical.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import enumeration.Location;
import game.location.GameLocation;

/**
 * A GUI component that displays a button that when clicked takes the user back
 * to the map.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ReturnToMapButton extends JButton {
	/**
	 * Constructor for ReturnToMapButton
	 * 
	 * @param gameLocation The game location to which the parent GUI location
	 *                     belongs.
	 */
	public ReturnToMapButton(GameLocation gameLocation) {
		setText("Return to Map");
		setBackground(new Color(100, 180, 100));
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameLocation.changeLocation(Location.MAP);
			}
		});
		setBounds(6, 558, 170, 36);
	}
}
