package userinterface.graphical.components;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import game.Purchasable;

/**
 * A GUI component that displays details about a given purchasable in a large,
 * spacious format.
 * 
 * @author Jake van Keulen
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class PurchasableInfoLarge extends JPanel {
	/**
	 * Constructor for PurchasableInfoLarge. Creates and renders all involved
	 * components.
	 * 
	 * @param purchasable The purchasable whose info is to be displayed.
	 * @param showPrice   Whether or not to display the price of the purchasable.
	 */
	public PurchasableInfoLarge(Purchasable purchasable, boolean showPrice) {
		setBackground(Color.PINK);
		setLayout(null);
		if (purchasable == null)
			return;

		JLabel nameLabel = new JLabel(purchasable.getName());
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(6, 28, 376, 16);
		add(nameLabel);

		JLabel descriptionLabel = new JLabel(purchasable.getDescription());
		descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		descriptionLabel.setBounds(6, 71, 376, 16);
		add(descriptionLabel);

		JTextPane detailsLabel = new JTextPane();
		detailsLabel.setEditable(false);
		detailsLabel.setText(purchasable.getDetails());
		detailsLabel.setBounds(6, 151, 376, 193);
		add(detailsLabel);

		if (showPrice) {
			JLabel priceLabel = new JLabel("$" + purchasable.getPrice());
			priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
			priceLabel.setBounds(6, 110, 376, 16);
			add(priceLabel);
		}
	}
}
