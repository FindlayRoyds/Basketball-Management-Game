package userinterface.commandline;

import game.location.GameMarket;
import enumeration.Location;
import game.location.GameLocation;

/**
 * 
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class CLIItemMarket extends CLILocation {
	/**
	 * The game location this cli location is linked to.
	 */
	private GameMarket gameLocation;

	/**
	 * @param cliEnvironment
	 */
	public CLIItemMarket(GameLocation gameLocation, CLIEnvironment cliEnvironment) {
		super(cliEnvironment);
		this.gameLocation = (GameMarket) gameLocation;
	}

	@Override
	public Location display() {
		// TODO Auto-generated method stub
		return null;
	}

}
