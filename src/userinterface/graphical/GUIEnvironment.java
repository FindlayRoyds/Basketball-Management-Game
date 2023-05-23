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
 * A class defining the environment controlling all GUI displays. Handles
 * swapping JPanels to switch between locations.
 * 
 * @author Findlay Royds, Jake van Keulen
 * @version 1.1, May 2023.
 */
public class GUIEnvironment implements UIEnvironment {
	/**
	 * The container frame in which the different UI panels are swapped in and out.
	 */
	private JFrame frame;

	/**
	 * The location currently being displayed on the GUI.
	 */
	private GUILocation currentLocation;

	/**
	 * Every location enum mapped to their respective GUILocation.
	 */
	private Map<Location, GUILocation> guiLocations;

	/**
	 * The constructor for the GUI environment. Creates all of the GUI locations and
	 * initializes the window.
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
		guiLocations.put(Location.MATCH_SELECTION,
				new GUIMatchSelection(gameLocations.get(Location.MATCH_SELECTION), this));
		guiLocations.put(Location.MATCH, new GUIMatch(gameLocations.get(Location.MATCH), this));
		guiLocations.put(Location.END, new GUIEnd(gameLocations.get(Location.END), this));
		guiLocations.put(Location.INVENTORY, new GUIInventory(gameLocations.get(Location.INVENTORY), this));
		guiLocations.put(Location.ITEM_MARKET,
				new GUIMarket(gameLocations.get(Location.ITEM_MARKET), this, "Item Market"));
		guiLocations.put(Location.BLACK_MARKET,
				new GUIMarket(gameLocations.get(Location.BLACK_MARKET), this, "Black Market"));
		guiLocations.put(Location.ATHLETE_MARKET,
				new GUIMarket(gameLocations.get(Location.ATHLETE_MARKET), this, "Athlete Market"));
		guiLocations.put(Location.LOCKER_ROOM, new GUILocker(gameLocations.get(Location.LOCKER_ROOM), this));

		initialize();
	}

	/**
	 * Swaps out the current location panel to change between GUI locations. Also
	 * calls the refresh method of the new location to update its content.
	 */
	@Override
	public void changeLocation(Location location, GameLocation gameLocation) {
		currentLocation = guiLocations.get(location);

		currentLocation.refresh();
		frame.setContentPane(currentLocation);
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * Display a popup box on the screen, consisting of a given message and options
	 * for the user to click one of.
	 */
	@Override
	public int displayPopup(String message, String[] options) {
		return JOptionPane.showOptionDialog(null, message, "", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, null);
	}

	/**
	 * Display a popup box on the screen, consisting of a given message and a
	 * default "close" button.
	 */
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
		frame.setBounds(100, 100, 800, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	/**
	 * @return The GUIEnvironment's main content frame, used for containing the
	 *         currently visible GUI location.
	 */
	public JFrame getFrame() {
		return frame;
	}
}
