package userinterface.graphical.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Purchasable;

public class PurchasableInfoSmall extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for small purchasable info. Creates a panel with the name of the
	 * purchasable and the price if showPrice = true.
	 * 
	 * @param purchasable The purchasable to display information about.
	 * @param showPrice   Whether or not the price of the purchasable should be
	 *                    displayed.
	 */
	public PurchasableInfoSmall(Purchasable purchasable, boolean showPrice) {
		setBackground(new Color(255, 250, 240));
		setLayout(null);

		// Create a label for the name of the purchasable
		JLabel nameLabel = new JLabel("<html>" + purchasable.getName() + "</html>");
		nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		nameLabel.setVerticalAlignment(SwingConstants.TOP);
		nameLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		nameLabel.setBounds(6, 6, 129, 48);
		add(nameLabel);

		// Only create price label if the price of the purchasable should be shown
		if (showPrice) {
			JLabel priceLabel = new JLabel("$" + purchasable.getPrice());
			priceLabel.setVerticalAlignment(SwingConstants.TOP);
			priceLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			priceLabel.setBounds(147, 6, 47, 48);
			add(priceLabel);
		}
	}
}
