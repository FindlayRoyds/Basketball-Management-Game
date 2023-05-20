package userinterface.graphical.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class PurchasableInfoList extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * The List of purchasables to display
	 */

	/**
	 * Create the panel.
	 */
	public PurchasableInfoList(int num, int height, Rectangle bounds) {
		setLayout(null);
		setBounds(bounds);

		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setBackground(Color.GREEN);
		backgroundPanel.setLayout(null);

		JScrollPane scrollPanel = new JScrollPane(backgroundPanel);
		scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setBounds(0, 0, bounds.width, bounds.height);
		backgroundPanel.setPreferredSize(new Dimension(bounds.width - 19, 6 + (height + 6) * num));

		for (int i = 0; i < num; i++) {
			PurchasableInfoSmall smallInfo = new PurchasableInfoSmall(false);
			smallInfo.setBounds(6, 6 + (height + 6) * i, bounds.width - 12 - 19, height);
			backgroundPanel.add(smallInfo);
		}

		add(scrollPanel);
	}

}
