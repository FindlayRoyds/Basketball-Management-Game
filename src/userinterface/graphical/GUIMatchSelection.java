package userinterface.graphical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import enumeration.Location;
import game.Team;
import game.location.GameLocation;
import game.location.GameMatchSelection;
import userinterface.graphical.components.TeamInfo;

@SuppressWarnings("serial") // We are not using serialization in our project
public class GUIMatchSelection extends GUILocation {
	/**
	 * The gameLocation this gui location is linked to
	 */
	GameMatchSelection gameLocation;

	/**
	 * The panel the play buttons are stored in
	 */
	JPanel playButtonsPanel;

	/**
	 * Create the panel.
	 */
	public GUIMatchSelection(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		this.gameLocation = (GameMatchSelection) gameLocation;
		setPreferredSize(new Dimension(800, 600));

		playButtonsPanel = new JPanel();
		playButtonsPanel.setBounds(106, 504, 588, 40);
		add(playButtonsPanel);
		playButtonsPanel.setLayout(new GridLayout(0, 3, 112, 0));

		JButton backButton = new JButton("Map");
		backButton.setBackground(new Color(139, 0, 0));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameLocation.changeLocation(Location.MAP);
			}
		});
		backButton.setBounds(6, 558, 117, 36);
		add(backButton);
	}

	@Override
	public void refresh() {
		List<Team> matchTeams = gameLocation.getTeams();

		// Create team displays and play buttons
		for (int teamIndex = 0; teamIndex < matchTeams.size(); teamIndex++) {
			// Create a constant of team index to use in play button event listener
			Team team = matchTeams.get(teamIndex);

			TeamInfo teamDisplay = new TeamInfo(new Rectangle(6, 6 + teamIndex, 254, 588), team);
			add(teamDisplay);

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
