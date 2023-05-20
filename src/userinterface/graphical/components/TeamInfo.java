package userinterface.graphical.components;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Team;

public class TeamInfo extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public TeamInfo(Rectangle bounds, Team team) {
		setLayout(null);

		JLabel nameLabel = new JLabel("New label");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(6, 6, bounds.width - 12, 32);
		add(nameLabel);

		JPanel athleteListPanel = new JPanel();
		athleteListPanel.setBackground(new Color(255, 228, 225));
		athleteListPanel.setBounds(6, 50, bounds.width - 12, bounds.height - 56);
		athleteListPanel.setLayout(null);
		add(athleteListPanel);

		List<JPanel> athleteInfoComponents = team.getActiveAthletes().values().stream()
				.map(athlete -> new PurchasableInfoSmall(athlete, false)).collect(Collectors.toList());
		ComponentList athleteList = new ComponentList(athleteInfoComponents, 100, athleteListPanel.getBounds());
		add(athleteList);
	}
}
