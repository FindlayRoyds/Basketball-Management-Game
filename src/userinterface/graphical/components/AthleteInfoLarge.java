package userinterface.graphical.components;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import enumeration.Statistic;
import game.Athlete;

public class AthleteInfoLarge extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public AthleteInfoLarge(Athlete athlete) {
		setBackground(Color.PINK);
		setLayout(null);

		JLabel nameLabel = new JLabel(athlete.getName());
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(6, 28, 376, 16);
		add(nameLabel);

		JLabel roleLabel = new JLabel(athlete.getRole().name());
		roleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		roleLabel.setBounds(6, 71, 376, 16);
		add(roleLabel);

		String statisticsContent = "Stamina: " + athlete.getStamina();
		for (Statistic statistic : Statistic.values())
			statisticsContent += "\n" + statistic.name() + ": " + athlete.getStatistic(statistic);
		JTextPane statisticsLabel = new JTextPane();
		statisticsLabel.setText(statisticsContent);
		statisticsLabel.setBounds(6, 109, 376, 193);
		add(statisticsLabel);

	}

}
