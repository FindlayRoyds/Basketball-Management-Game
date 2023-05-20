package userinterface.graphical;

import java.util.EnumMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import enumeration.Location;
import game.GameEnvironment;
import game.location.GameLocation;
import userinterface.UIEnvironment;

/**
 * 
 * 
 * @author Findlay Royds
 * @version 1.1, May 2023.
 */
public class GUIEnvironment implements UIEnvironment {
	private JFrame frame;

	/**
	 * The location currently being displayed on the gui.
	 */
	private GUILocation currentLocation;

	/**
	 * Every location enum mapped to their respective GUILocation.
	 */
	private Map<Location, GUILocation> guiLocations;

	/**
	 * The constructor for the GUI environment. Creates all of the GUI locations and
	 * initialises the window.
	 * 
	 * @param gameLocations   A map that maps every location in the game to a game
	 *                        location object.
	 * @param gameEnvironment The game environment the gui environment interacts
	 *                        with.
	 */
	public GUIEnvironment(Map<Location, GameLocation> gameLocations, GameEnvironment gameEnvironment) {
		guiLocations = new EnumMap<Location, GUILocation>(Location.class);
		guiLocations.put(Location.START, new GUIStart(gameLocations.get(Location.START), this));
		guiLocations.put(Location.MAP, new GUIMap(gameLocations.get(Location.MAP), this));

		initialize();
	}

	@Override
	public void changeLocation(Location location, GameLocation gameLocation) {
		currentLocation = guiLocations.get(location);

		currentLocation.refresh();
		frame.setContentPane(currentLocation);
		frame.revalidate();
		frame.repaint();
	}

	@Override
	public int displayPopup(String message, String[] options) {
		return JOptionPane.showOptionDialog(null, message, "", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, null);
	}

	@Override
	public void displayPopup(String message) {
		displayPopup(message, new String[] { "Close" });
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	public JFrame getFrame() {
		return frame;
	}
}
