package userinterface.graphical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import enumeration.Location;
import enumeration.Position;
import game.Athlete;
import game.Team;
import game.location.GameLocation;
import game.location.GameMatch;
import userinterface.graphical.components.PurchasableInfoLarge;

@SuppressWarnings("serial") // We are not using serialization in our project
public class GUIMatch extends GUILocation {
	/**
	 * The gameLocation this gui location is linked to
	 */
	private GameMatch gameLocation;

	/**
	 * Components shown on screen in GUI match
	 */
	private JPanel matchupPanel;
	private JButton controlButton;
	private JLabel matchupTitleLabel;
	private JPanel athleteInfoPanel;
	private JLabel winnerLabel;

	/**
	 * Each team's athlete's by position
	 */
	private Map<Position, Athlete> team1Athletes;
	private Map<Position, Athlete> team2Athletes;

	/**
	 * The match position currently being shown
	 */
	private int positionIndex;

	/**
	 * Whether or not the current position match has been played (otherwise just
	 * being displayed)
	 */
	private boolean matchPlayed;

	/**
	 * Constructor for GUIMatchSelection. initializes the swing components for the
	 * design and starts the match.
	 */
	public GUIMatch(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		this.gameLocation = (GameMatch) gameLocation;
		setPreferredSize(new Dimension(800, 600));

		winnerLabel = new JLabel("<dynamic> have won the match!");
		winnerLabel.setVisible(false);
		winnerLabel.setForeground(new Color(218, 165, 32));
		winnerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		winnerLabel.setBounds(6, 300, 788, 50);
		add(winnerLabel);

		matchupPanel = new JPanel();
		matchupPanel.setBackground(new Color(255, 228, 225));
		matchupPanel.setBounds(6, 68, 788, 470);
		add(matchupPanel);
		matchupPanel.setLayout(null);

		matchupTitleLabel = new JLabel("<dynamic> MATCHUP");
		matchupTitleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		matchupTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		matchupTitleLabel.setBounds(6, 6, 776, 50);
		matchupPanel.add(matchupTitleLabel);

		athleteInfoPanel = new JPanel();
		athleteInfoPanel.setBackground(new Color(224, 255, 255));
		athleteInfoPanel.setBounds(0, 68, 788, 402);
		matchupPanel.add(athleteInfoPanel);
		athleteInfoPanel.setLayout(new GridLayout(0, 2, 0, 12));

		JLabel titleLabel = new JLabel(
				this.gameLocation.getTeam1().getName() + " vs " + this.gameLocation.getTeam2().getName());
		titleLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 30));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(6, 6, 788, 50);
		add(titleLabel);

		controlButton = new JButton("PLAY MATCH");
		controlButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		controlButton.setBounds(306, 544, 188, 50);
		add(controlButton);

		team1Athletes = this.gameLocation.getTeam1().getActiveAthletes();
		team2Athletes = this.gameLocation.getTeam2().getActiveAthletes();
		positionIndex = 0;
		matchPlayed = false;
	}

	@Override
	public void refresh() {
		if (positionIndex == Position.values().length + 1) { // Match has ended
			gameLocation.finish();
			gameLocation.changeLocation(Location.MAP);
		} else if (positionIndex == Position.values().length) { // Display winning team
			matchupPanel.setVisible(false);

			Team winningTeam = gameLocation.getWinningTeam();

			winnerLabel.setText(winningTeam + " have won the match!");
			winnerLabel.setVisible(true);
		} else { // Play a position matchup
			Position positionPlayed = Position.values()[positionIndex];
			Athlete athlete1 = team1Athletes.get(positionPlayed);
			Athlete athlete2 = team2Athletes.get(positionPlayed);

			if (matchPlayed) {
				Athlete winner = gameLocation.getWinningAthlete(athlete1, athlete2);
				int winnerIndex = winner == athlete1 ? 0 : 1;
				JPanel winnerInfoPanel = (JPanel) athleteInfoPanel.getComponent(winnerIndex);
				winnerInfoPanel.setBorder(BorderFactory.createLineBorder(Color.yellow));
			} else {
				athleteInfoPanel.removeAll();
				PurchasableInfoLarge athlete1Info = new PurchasableInfoLarge(athlete1, false);
				PurchasableInfoLarge athlete2Info = new PurchasableInfoLarge(athlete2, false);

				athleteInfoPanel.add(athlete1Info);
				athleteInfoPanel.add(athlete2Info);
			}
			matchPlayed = !matchPlayed;
		}

		positionIndex += 1;
	}
}
