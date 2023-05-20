package userinterface.graphical.components;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import game.Purchasable;

public class PurchasableInfoLarge extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PurchasableInfoLarge(Purchasable purchasable) {
		setBackground(Color.PINK);
		setLayout(null);

		JLabel nameLabel = new JLabel(purchasable.getName());
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(6, 28, 376, 16);
		add(nameLabel);

		JLabel descriptionLabel = new JLabel(purchasable.getDescription());
		descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		descriptionLabel.setBounds(6, 71, 376, 16);
		add(descriptionLabel);

		JTextPane detailsLabel = new JTextPane();
		detailsLabel.setText(purchasable.getDetails());
		detailsLabel.setBounds(6, 109, 376, 193);
		add(detailsLabel);

	}

}
