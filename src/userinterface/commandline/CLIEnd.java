package userinterface.commandline;

import enumeration.Location;
import game.location.GameEnd;
import game.location.GameLocation;

/**
 * This class is responsible for displaying information about the game on the
 * end screen. Shows the team name, the season duration, the player's money, and
 * the player's points.
 * 
 * @author Findlay Royds
 * @version 1.1, May 2023.
 */
public class CLIEnd extends CLILocation {
	/**
	 * The end game location the cli end location is linked to. Hides the
	 * gameLocation property in the CLILocation super class.
	 */
	private GameEnd gameLocation;

	/**
	 * The constructor for the CLIEnd class. Calls the constructor of CLILocation
	 * and sets the gameLocation property.
	 * 
	 * @param gameLocation The end screen game location
	 */
	public CLIEnd(GameLocation gameLocation, CLIEnvironment cliEnvironment) {
		super(cliEnvironment);
		this.gameLocation = (GameEnd) gameLocation;
	}

	@Override
	public Location display() {
		System.out.println("GAME OVER");
		System.out.println("Team name: " + gameLocation.getTeamName());
		System.out.println("Season Duration: " + gameLocation.getSeasonLength());
		System.out.println("Money Gained: " + gameLocation.getMoney());
		System.out.println("Points Gained: " + gameLocation.getScore());
		gameLocation.endGame();
		return null;
	}
}
