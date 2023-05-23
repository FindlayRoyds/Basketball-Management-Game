package userinterface.graphical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import enumeration.Location;
import game.Team;
import game.location.GameLocation;
import game.location.GameMatchSelection;
import userinterface.graphical.components.TeamInfo;

/**
 * A class that defines the match selection GUI location. It displays 3 teams
 * for the user to select one of to play a match against.
 * 
 * @author Jake van Keulen, Findlay Royds
 * @version 1.0
 */
@SuppressWarnings("serial") // We are not using serialization in our project
public class GUIMatchSelection extends GUILocation {
	/**
	 * The gameLocation this GUI location is linked to
	 */
	private GameMatchSelection gameLocation;

	/**
	 * The components accessed by the refresh method.
	 */
	private JPanel playButtonsPanel;
	private JPanel teamDisplayPanel;
	private JLabel titleLabel;
	JLabel canStartWarningLabel;

	/**
	 * Constructor for GUIMatch. initializes the swing components for the design and
	 * starts the match.
	 * 
	 * @param gameLocation   The GUI location's corresponding game location class.
	 * @param guiEnvironment The GUI environment to which the GUI location belongs.
	 */
	public GUIMatchSelection(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		setBackground(new Color(255, 255, 255));
		this.gameLocation = (GameMatchSelection) gameLocation;
		setPreferredSize(new Dimension(800, 600));

		canStartWarningLabel = new JLabel("Your team is unable to play!");
		canStartWarningLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		canStartWarningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		canStartWarningLabel.setBounds(6, 504, 788, 40);
		add(canStartWarningLabel);

		playButtonsPanel = new JPanel();
		playButtonsPanel.setBounds(66, 504, 628, 40);
		playButtonsPanel.setBackground(new Color(0, 0, 0, 0));
		playButtonsPanel.setOpaque(false);
		add(playButtonsPanel);
		playButtonsPanel.setLayout(new GridLayout(0, 3, 122, 0));

		JButton backButton = new JButton("Map");
		backButton.setBackground(new Color(139, 0, 0));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameLocation.changeLocation(Location.MAP);
			}
		});
		backButton.setBounds(6, 558, 117, 36);
		add(backButton);

		teamDisplayPanel = new JPanel();
		teamDisplayPanel.setBounds(6, 68, 788, 424);
		teamDisplayPanel.setBackground(new Color(0, 0, 0, 0));
		teamDisplayPanel.setOpaque(false);
		add(teamDisplayPanel);
		teamDisplayPanel.setLayout(new GridLayout(0, 3, 12, 0));

		titleLabel = new JLabel("Stadium");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(new Color(0, 0, 0));
		titleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		titleLabel.setBounds(6, 6, 788, 50);
		add(titleLabel);
	}

	/**
	 * Refreshes the content of the match screen.
	 */
	@Override
	public void refresh() {
		playButtonsPanel.removeAll();
		teamDisplayPanel.removeAll();
		List<Team> matchTeams = gameLocation.getTeams();
		boolean canStart = gameLocation.canStartMatch();

		// Show warning if the player's team is not eligible to start
		canStartWarningLabel.setVisible(!canStart);

		// Create team displays and play buttons
		for (int teamIndex = 0; teamIndex < matchTeams.size(); teamIndex++) {
			// Create a constant of team index to use in play button event listener
			Team team = matchTeams.get(teamIndex);

			TeamInfo teamDisplay = new TeamInfo(new Rectangle(6, 6 + teamIndex, 254, 538), team);
			teamDisplayPanel.add(teamDisplay);

			if (canStart) {
				JButton playButton = new JButton("Play");
				playButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						gameLocation.playMatch(team);
					}
				});
				playButtonsPanel.add(playButton);
			}
		}
	}
}
