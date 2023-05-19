package userinterface.commandline;

import game.location.GameMatch;
import enumeration.Location;
import game.location.GameLocation;

/**
 * 
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class CLIMatch extends CLILocation {
	/**
	 * The game location this cli location is linked to.
	 */
	private GameMatch gameLocation;

	/**
	 * @param cliEnvironment
	 */
	public CLIMatch(GameLocation gameLocation, CLIEnvironment cliEnvironment) {
		super(cliEnvironment);
		this.gameLocation = (GameMatch) gameLocation;
	}

	@Override
	public Location display() {
		// TODO Auto-generated method stub
		return null;
	}

}
