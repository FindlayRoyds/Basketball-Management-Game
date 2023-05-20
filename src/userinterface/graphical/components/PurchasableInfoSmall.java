package userinterface.graphical.components;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Purchasable;

public class PurchasableInfoSmall extends JPanel {

	/**
	 * Create the panel.
	 */
	public PurchasableInfoSmall(Purchasable purchasable, boolean showPrice) {
		setLayout(null);

		JLabel nameLabel = new JLabel(purchasable.getName());
		nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		nameLabel.setVerticalAlignment(SwingConstants.TOP);
		nameLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		nameLabel.setBounds(6, 6, 129, 48);
		add(nameLabel);

		if (showPrice) {
			JLabel priceLabel = new JLabel("$" + purchasable.getPrice());
			priceLabel.setVerticalAlignment(SwingConstants.TOP);
			priceLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			priceLabel.setBounds(147, 6, 47, 48);
			add(priceLabel);
		}
	}
}
