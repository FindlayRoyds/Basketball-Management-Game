package userinterface.graphical.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Team;

/**
 * A GUI component that displays the name and members of a given team.
 * 
 * @author Findlay Royds
 *
 */
@SuppressWarnings("serial")
public class TeamInfo extends JPanel {
	/**
	 * Constructor for TeamInfo. Creates a team info component which shows the
	 * team's name as well as the athletes in the team.
	 * 
	 * @param bounds The position and dimensions of the component.
	 * @param team   The team whose info is to be displayed.
	 */
	public TeamInfo(Rectangle bounds, Team team) {
		setBackground(new Color(238, 238, 238));
		setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
		setLayout(null);

		JLabel nameLabel = new JLabel("<html><center>" + team.getName() + "</center></html>");
		nameLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		nameLabel.setForeground(new Color(60, 60, 60));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(6, 6, 238, 64);
		add(nameLabel);

		Rectangle listBounds = new Rectangle(6, 86, bounds.width - 12, 330);
		List<JPanel> athleteInfoComponents = team.getActiveAthletes().values().stream()
				.map(athlete -> new AthleteInfoSmall(athlete, false)).collect(Collectors.toList());
		ComponentList athleteList = new ComponentList(athleteInfoComponents, 80, listBounds);
		add(athleteList);
	}
}
