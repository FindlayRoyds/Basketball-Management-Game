package userinterface.graphical.components;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TeamInfo extends JPanel {

	/**
	 * Create the panel.
	 */
	public TeamInfo() {
		setLayout(null);

		JLabel nameLabel = new JLabel("New label");
		nameLabel.setBounds(6, 6, 238, 28);
		add(nameLabel);

		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		panel.setBounds(6, 46, 238, 448);
		add(panel);
		panel.setLayout(null);

		for (int i = 0; i < 5; i++) {
			PurchasableInfoSmall c3 = new PurchasableInfoSmall(false);
			c3.setBounds(6, 106 * i + 6, 226, 100);
			panel.add(c3);
		}
	}
}
