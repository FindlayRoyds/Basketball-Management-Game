package userinterface.commandline;

import game.location.Map;
import enumeration.Location;

/**
 * The CLI map location. Responsible for displaying locations the player can visit.
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class CLIMap extends CLILocation {
	/**
	 * The map game location the cli map location is linked to.
	 * Hides the gameLocation property in the CLILocation super class.
	 */
	private Map gameLocation;
	
	/**
	 * Constructor for the map CLI location.
	 * 
	 * @param gameLocation 				the map GameLocation.
	 */
	public CLIMap(Map gameLocation) {
		super(gameLocation);
		this.gameLocation = gameLocation;
	}

	@Override
	public String[] display() {
		System.out.println("Map");
		return new String[] {"Go to end of game"};
	}

	@Override
	public void processOption(int selectedOption) {
		gameLocation.changeLocation(Location.END_SCREEN);
	}

}
