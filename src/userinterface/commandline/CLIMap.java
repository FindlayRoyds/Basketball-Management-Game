package userinterface.commandline;

import game.location.GameMap;
import game.location.GameLocation;
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
	private GameMap gameLocation;
	
	/**
	 * Locations the user can travel to from the map
	 */
	private Location[] accessableLocations = new Location[] {
		Location.MATCH_SELECION, Location.INVENTORY, Location.LOCKER_ROOM,
		Location.ATHLETE_MARKET, Location.ITEM_MARKET, Location.BLACK_MARKET
	};
	
	/**
	 * Constructor for the map CLI location.
	 * 
	 * @param gameLocation 				the map GameLocation.
	 */
	public CLIMap(GameLocation gameLocation) {
		super(gameLocation);
		this.gameLocation = (GameMap) gameLocation;
	}
	
	@Override
	public void display() {
		System.out.println("Map");
		String[] options = new String[] {"Stadium", "Inventory", "Locker Room", "Athlete Market", "Item Market", "Black Market"};
		int selectedOption = cliEnvironment.displayOptions(options);
		gameLocation.changeLocation(accessableLocations[selectedOption]);
	}

}
