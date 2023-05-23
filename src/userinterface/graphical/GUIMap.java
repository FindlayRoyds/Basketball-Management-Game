package userinterface.graphical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import enumeration.Location;
import game.location.GameLocation;
import game.location.GameMap;

/**
 * A class that defines the map GUI location. It provides a graphical interface
 * for choosing locations to travel to or to take a bye.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
@SuppressWarnings("serial") // We aren't using serialization in our project
public class GUIMap extends GUILocation {
	/**
	 * The corresponding game location class. Acts as the point of communication for
	 * interacting with the backend game logic.
	 */
	private GameMap gameLocation;

	/**
	 * The components of the GUI accessed by the refresh method
	 */
	JLabel moneyLabel;
	JLabel weeksRemainingLabel;
	JLabel weekNumberLabel;

	/**
	 * Constructor for the GUIMap component.
	 * 
	 * @param gameLocation   The GUI location's corresponding game location class.
	 * @param guiEnvironment The GUI environment to which the GUI location belongs.
	 */
	public GUIMap(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		this.gameLocation = (GameMap) gameLocation;
		setBackground(Color.white);
		setPreferredSize(new Dimension(800, 600));
		setLayout(null);

		moneyLabel = new JLabel("$1512");
		moneyLabel.setForeground(new Color(0, 100, 0));
		moneyLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		moneyLabel.setBounds(16, 8, 200, 50);
		add(moneyLabel);

		weeksRemainingLabel = new JLabel("12 weeks left");
		weeksRemainingLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		weeksRemainingLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		weeksRemainingLabel.setBounds(538, 6, 250, 50);
		add(weeksRemainingLabel);

		JLabel mapTitleLabel = new JLabel("Map");
		mapTitleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		mapTitleLabel.setForeground(new Color(0, 0, 0));
		mapTitleLabel.setBounds(6, 6, 788, 50);
		mapTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(mapTitleLabel);

		Map<String, Runnable> buttonData = new TreeMap<String, Runnable>();
		buttonData.put("1. Stadium", () -> this.gameLocation.changeLocation(Location.MATCH_SELECTION));
		buttonData.put("2. Inventory", () -> this.gameLocation.changeLocation(Location.INVENTORY));
		buttonData.put("3. Locker Room", () -> this.gameLocation.changeLocation(Location.LOCKER_ROOM));
		buttonData.put("4. Athlete Market", () -> this.gameLocation.changeLocation(Location.ATHLETE_MARKET));
		buttonData.put("5. Item Market", () -> this.gameLocation.changeLocation(Location.ITEM_MARKET));
		buttonData.put("6. Black Market", () -> this.gameLocation.changeLocation(Location.BLACK_MARKET));
		buttonData.put("7. Take a Bye", () -> this.takeABye());

		weekNumberLabel = new JLabel("Week 14");
		weekNumberLabel.setVerticalAlignment(SwingConstants.TOP);
		weekNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		weekNumberLabel.setBounds(6, 56, 788, 50);
		add(weekNumberLabel);

		JPanel locationsPanel = new JPanel();
		locationsPanel.setBackground(Color.white);
		locationsPanel.setBounds(6, 147, 788, 447);
		add(locationsPanel);
		locationsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		for (String buttonText : buttonData.keySet()) {
			JButton currentButton = new JButton(buttonText.substring(3));
			currentButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buttonData.get(buttonText).run();
				}
			});
			currentButton.setPreferredSize(new Dimension(200, 75));
			currentButton.setBackground(UIManager.getColor("TabbedPane.highlight"));
			currentButton.setForeground(UIManager.getColor("TabbedPane.focus"));
			currentButton.setFont(new Font("Dialog", Font.BOLD, 10));
			locationsPanel.add(currentButton);
		}
	}

	/**
	 * Instructs the game location to take a bye, then refreshes the GUI.
	 */
	private void takeABye() {
		gameLocation.takeABye();
		refresh();
	}

	/**
	 * Refreshes the content of the map screen.
	 */
	@Override
	public void refresh() {
		moneyLabel.setText("$" + gameLocation.getMoney());
		weeksRemainingLabel.setText(gameLocation.getWeeksRemaining() + " weeks remaining");
		weekNumberLabel.setText("week " + gameLocation.getWeek());
		gameLocation.checkForGameEnd();
	}
}
