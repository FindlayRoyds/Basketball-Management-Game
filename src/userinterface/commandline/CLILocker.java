package userinterface.commandline;

import game.location.GameLocker;
import enumeration.Location;
import game.location.GameLocation;

/**
 * 
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class CLILocker extends CLILocation {
	/**
	 * The game location this cli location is linked to.
	 */
	private GameLocker gameLocation;

	/**
	 * @param cliEnvironment
	 */
	public CLILocker(GameLocation gameLocation, CLIEnvironment cliEnvironment) {
		super(cliEnvironment);
		this.gameLocation = (GameLocker) gameLocation;
	}

	@Override
	public Location display() {
		// TODO Auto-generated method stub
		return null;
	}

}
