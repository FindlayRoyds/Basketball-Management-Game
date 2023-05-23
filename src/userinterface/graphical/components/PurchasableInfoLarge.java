package userinterface.graphical.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

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
		setBackground(new Color(255, 255, 255));
		setPreferredSize(new Dimension(388, 450));
		setLayout(null);
		if (purchasable == null)
			return;

		JLabel nameLabel = new JLabel(purchasable.getName());
		nameLabel.setFont(new Font("Lucida Grande", Font.BOLD, 26));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(6, 6, 376, 47);
		add(nameLabel);

		JLabel descriptionLabel = new JLabel(purchasable.getDescription());
		descriptionLabel.setFont(new Font("Lucida Grande", Font.ITALIC, 18));
		descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		descriptionLabel.setBounds(6, 65, 376, 25);
		add(descriptionLabel);

		JTextPane detailsLabel = new JTextPane();
		detailsLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		detailsLabel.setMargin(new Insets(6, 6, 6, 6));
		detailsLabel.setBorder(null);
		detailsLabel.setEditable(false);
		detailsLabel.setText(purchasable.getDetails());
		detailsLabel.setBounds(18, 114, 352, 318);
		add(detailsLabel);

		JPanel borderPanel = new JPanel();
		borderPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		borderPanel.setBounds(6, 102, 376, 342);
		borderPanel.setBackground(new Color(0, 0, 0, 0));
		borderPanel.setOpaque(false);
		add(borderPanel);

		if (showPrice) {
			JLabel priceLabel = new JLabel("$" + purchasable.getPrice());
			priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
			priceLabel.setBounds(6, 110, 376, 16);
			add(priceLabel);
		}
	}
}
