package userinterface.graphical;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import enumeration.Location;
import game.location.GameLocation;
import game.location.GameMap;

public class GUIMap extends GUILocation {
	private static final long serialVersionUID = 1L;
	GameMap gameLocation;

	/**
	 * Create the panel.
	 */
	public GUIMap(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		this.gameLocation = (GameMap) gameLocation;
		setBackground(UIManager.getColor("TabbedPane.focus"));
		setLayout(null);

		JLabel mapTitleLabel = new JLabel("Map");
		mapTitleLabel.setForeground(UIManager.getColor("TabbedPane.highlight"));
		mapTitleLabel.setBounds(191, 22, 60, 17);
		mapTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(mapTitleLabel);

		Map<String, Runnable> buttonData = new HashMap<String, Runnable>();
		buttonData.put("Stadium", () -> this.gameLocation.changeLocation(Location.MATCH_SELECTION));
		buttonData.put("Inventory", () -> this.gameLocation.changeLocation(Location.INVENTORY));
		buttonData.put("Locker Room", () -> this.gameLocation.changeLocation(Location.LOCKER_ROOM));
		buttonData.put("Athlete Market", () -> this.gameLocation.changeLocation(Location.ATHLETE_MARKET));
		buttonData.put("Item Market", () -> this.gameLocation.changeLocation(Location.ITEM_MARKET));
		buttonData.put("Black Market", () -> this.gameLocation.changeLocation(Location.BLACK_MARKET));
		buttonData.put("Take a Bye", () -> this.gameLocation.takeABye());

		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("OptionPane.questionDialog.titlePane.background"));
		panel.setBounds(12, 51, 425, 225);
		add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		for (String buttonText : buttonData.keySet()) {
			JButton currentButton = new JButton(buttonText);
			currentButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buttonData.get(buttonText).run();
				}
			});
			currentButton.setPreferredSize(new Dimension(110, 30));
			currentButton.setBackground(UIManager.getColor("TabbedPane.highlight"));
			currentButton.setForeground(UIManager.getColor("TabbedPane.focus"));
			currentButton.setFont(new Font("Dialog", Font.BOLD, 10));
			panel.add(currentButton);
		}
	}

	@Override
	public Location refresh() {
		return null;
	}
}
