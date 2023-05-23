package userinterface.graphical.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Purchasable;

/**
 * A GUI component that displays the name (and price if relevant) of a given
 * purchasable in a compact format.
 * 
 * @author Jake van Keulen
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class PurchasableInfoSmall extends JPanel {
	/**
	 * Constructor for small purchasable info. Creates a panel with the name of the
	 * purchasable and the price if showPrice = true.
	 * 
	 * @param purchasable The purchasable to display information about.
	 * @param showPrice   Whether or not the price of the purchasable should be
	 *                    displayed.
	 */
	public PurchasableInfoSmall(Purchasable purchasable, boolean showPrice) {
		setBackground(new Color(225, 222, 222));
		setLayout(new GridLayout(0, 1, 0, 0));

		// Create a label for the name of the purchasable
		// <html> makes the text go onto a new line if it's too long
		JLabel nameLabel = new JLabel("<html>" + purchasable.getName() + "</html>");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		nameLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		add(nameLabel);

		// Only create price label if the price of the purchasable should be shown
		// if (showPrice) {
		JLabel priceLabel = new JLabel("$" + purchasable.getPrice());
		priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		priceLabel.setForeground(new Color(0, 100, 0));
		priceLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		add(priceLabel);
		// }
	}
}
