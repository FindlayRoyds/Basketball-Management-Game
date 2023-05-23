package userinterface.graphical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import enumeration.Location;
import enumeration.Position;
import game.Athlete;
import game.location.GameLocation;
import game.location.GameMatch;
import userinterface.graphical.components.PurchasableInfoLarge;
import userinterface.graphical.components.Title;

/**
 * A class that defines the match GUI location. Displays player match ups one by
 * one before displaying the final result of the match. Waits for user input to
 * continue at each step of the way.
 * 
 * @author Jake van Keulen, Findlay Royds
 * @version 1.0
 */
@SuppressWarnings("serial") // We are not using serialization in our project
public class GUIMatch extends GUILocation {
	/**
	 * The corresponding game location class. Acts as the point of communication for
	 * interacting with the backend game logic.
	 */
	private GameMatch gameLocation;

	/**
	 * Components shown on screen in GUI match.
	 */
	private JButton controlButton;
	private JPanel athleteInfoPanel;
	private Title titleLabel;

	/**
	 * The match position currently being shown
	 */
	private int positionIndex;

	/**
	 * Whether or not the current position match has been played (otherwise just
	 * being displayed)
	 */
	private boolean matchPlayed;
	private JLabel team1ScoreLabel;
	private JLabel team2ScoreLabel;

	/**
	 * A timer for making the winner border flash
	 */
	private Timer flashTimer;

	/**
	 * Count the number of times the winner's border has flashed
	 */
	private int flashCount;

	/**
	 * Constructor for GUIMatch. initializes the swing components for the design and
	 * starts the match.
	 * 
	 * @param gameLocation   The GUI location's corresponding game location class.
	 * @param guiEnvironment The GUI environment to which the GUI location belongs.
	 */
	public GUIMatch(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		this.gameLocation = (GameMatch) gameLocation;
		setPreferredSize(new Dimension(800, 600));

		team1ScoreLabel = new JLabel();
		team1ScoreLabel.setFont(new Font("Lucida Grande", Font.BOLD, 28));
		team1ScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		team1ScoreLabel.setBounds(31, 6, 50, 50);
		add(team1ScoreLabel);

		team2ScoreLabel = new JLabel();
		team2ScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		team2ScoreLabel.setFont(new Font("Lucida Grande", Font.BOLD, 28));
		team2ScoreLabel.setBounds(719, 6, 50, 50);
		add(team2ScoreLabel);

		athleteInfoPanel = new JPanel();
		athleteInfoPanel.setBackground(new Color(0, 0, 0, 0));
		athleteInfoPanel.setOpaque(false);
		athleteInfoPanel.setBounds(6, 68, 788, 470);
		add(athleteInfoPanel);
		athleteInfoPanel.setLayout(new GridLayout(0, 2, 6, 0));

		titleLabel = new Title("<dynamic> vs <dynamic>");
		add(titleLabel);

		controlButton = new JButton("PLAY MATCH");
		controlButton.setBackground(new Color(225, 222, 222));
		controlButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (flashCount == 0)
					refresh();
			}
		});
		controlButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		controlButton.setBounds(306, 544, 188, 50);
		add(controlButton);

		positionIndex = 0;
		matchPlayed = false;
	}

	/**
	 * Refreshes the content of the match screen.
	 */
	@Override
	public void refresh() {
		Map<Position, Athlete> team1Athletes = this.gameLocation.getTeam1().getActiveAthletes();
		Map<Position, Athlete> team2Athletes = this.gameLocation.getTeam2().getActiveAthletes();
		titleLabel.setText(gameLocation.getTeam1().getName() + " vs " + this.gameLocation.getTeam2().getName());

		team1ScoreLabel.setText(Integer.toString(gameLocation.getTeam1Score()));
		team2ScoreLabel.setText(Integer.toString(gameLocation.getTeam2Score()));

		if (positionIndex == Position.values().length) { // Match has ended
			positionIndex = 0;
			matchPlayed = false;
			gameLocation.changeLocation(Location.MAP);
			gameLocation.finish();
			gameLocation.changeLocation(Location.MAP);
		} else { // Play a position matchup
			Position positionPlayed = Position.values()[positionIndex];
			Athlete athlete1 = team1Athletes.get(positionPlayed);
			Athlete athlete2 = team2Athletes.get(positionPlayed);
			titleLabel.setText(positionPlayed.name().replaceAll("_", " ") + " MATCHUP");

			if (matchPlayed) {
				Athlete winner = gameLocation.getWinningAthlete(athlete1, athlete2);
				team1ScoreLabel.setText(Integer.toString(gameLocation.getTeam1Score()));
				team2ScoreLabel.setText(Integer.toString(gameLocation.getTeam2Score()));
				int winnerIndex = winner == athlete1 ? 0 : 1;
				JPanel winnerInfoPanel = (JPanel) athleteInfoPanel.getComponent(winnerIndex);

				ActionListener flashBorder = new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						if (winnerInfoPanel.getBorder() == null)
							winnerInfoPanel.setBorder(new LineBorder(new Color(184, 134, 11), 3, true));
						else
							winnerInfoPanel.setBorder(null);
						flashCount++;
						if (flashCount > 5) {
							flashCount = 0;
							flashTimer.stop();
							controlButton.setEnabled(true);
						}
					}
				};

				controlButton.setEnabled(false);
				flashTimer = new Timer(250, flashBorder);
				flashTimer.start();

				controlButton.setText("CONTINUE");
				positionIndex += 1;
			} else {
				athleteInfoPanel.removeAll();
				PurchasableInfoLarge athlete1Info = new PurchasableInfoLarge(athlete1, false);
				PurchasableInfoLarge athlete2Info = new PurchasableInfoLarge(athlete2, false);

				athleteInfoPanel.add(athlete1Info);
				athleteInfoPanel.add(athlete2Info);
				controlButton.setText("PLAY MATCH");
			}
			matchPlayed = !matchPlayed;
		}
	}
}
