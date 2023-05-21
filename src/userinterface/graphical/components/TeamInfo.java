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
	 * Creates a team info component which shows the team's name as well as the
	 * athletes in the team.
	 */
	public TeamInfo(Rectangle bounds, Team team) {
		setBackground(new Color(173, 255, 47));
		setLayout(null);

		JLabel nameLabel = new JLabel("New label");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(6, 6, 238, 32);
		add(nameLabel);
		
		Rectangle listBounds = new Rectangle(6, 50, bounds.width - 12, bounds.height - 56);
		List<JPanel> athleteInfoComponents = team.getActiveAthletes().values().stream()
				.map(athlete -> new PurchasableInfoSmall(athlete, false)).collect(Collectors.toList());
		ComponentList athleteList = new ComponentList(athleteInfoComponents, 100, listBounds);
		add(athleteList);
	}
}
