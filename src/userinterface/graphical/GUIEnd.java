package userinterface.graphical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import game.location.GameEnd;
import game.location.GameLocation;

@SuppressWarnings("serial") // We aren't using serialization in this project
public class GUIEnd extends GUILocation {
	GameEnd gameLocation;

	/**
	 * Components accessed by the refresh method
	 */
	private JLabel teamNameLabel;
	private JLabel pointsLabel;
	private JLabel moneyLabel;
	private JLabel seasonLengthLabel;

	/**
	 * Creates the GUIEnd component.
	 */
	public GUIEnd(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		setBackground(new Color(0, 0, 0));
		this.gameLocation = (GameEnd) gameLocation;
		setPreferredSize(new Dimension(800, 600));
		setLayout(null);

		JLabel titleLabel = new JLabel("Game Over");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(new Color(255, 255, 255));
		titleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		titleLabel.setBounds(6, 6, 788, 50);
		add(titleLabel);

		teamNameLabel = new JLabel();
		teamNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		teamNameLabel.setForeground(Color.WHITE);
		teamNameLabel.setFont(new Font("Dialog", Font.ITALIC, 45));
		teamNameLabel.setBounds(6, 165, 788, 75);
		add(teamNameLabel);

		pointsLabel = new JLabel();
		pointsLabel.setVerticalAlignment(SwingConstants.TOP);
		pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pointsLabel.setForeground(Color.WHITE);
		pointsLabel.setFont(new Font("Dialog", Font.PLAIN, 22));
		pointsLabel.setBounds(6, 252, 788, 30);
		add(pointsLabel);

		moneyLabel = new JLabel();
		moneyLabel.setVerticalAlignment(SwingConstants.TOP);
		moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		moneyLabel.setForeground(Color.WHITE);
		moneyLabel.setFont(new Font("Dialog", Font.PLAIN, 22));
		moneyLabel.setBounds(6, 282, 788, 30);
		add(moneyLabel);

		seasonLengthLabel = new JLabel();
		seasonLengthLabel.setVerticalAlignment(SwingConstants.TOP);
		seasonLengthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		seasonLengthLabel.setForeground(Color.WHITE);
		seasonLengthLabel.setFont(new Font("Dialog", Font.PLAIN, 22));
		seasonLengthLabel.setBounds(6, 312, 788, 30);
		add(seasonLengthLabel);
	}

	@Override
	public void refresh() {
		teamNameLabel.setText(gameLocation.getTeamName());
		pointsLabel.setText("Score: " + gameLocation.getScore());
		moneyLabel.setText("Money: " + gameLocation.getMoney());
		seasonLengthLabel.setText("Season length: " + gameLocation.getSeasonLength() + " weeks");
	}
}
