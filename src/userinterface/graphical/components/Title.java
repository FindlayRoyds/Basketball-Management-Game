package userinterface.graphical.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * A GUI component that extends JLabel to display a nicely styled title at the
 * top of the screen.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Title extends JLabel {
	public Title(String text) {
		setText(text);
		setHorizontalAlignment(SwingConstants.CENTER);
		setForeground(new Color(0, 0, 0));
		setFont(new Font("Dialog", Font.BOLD, 24));
		setBounds(6, 6, 788, 50);
	}
}
