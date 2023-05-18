package userinterface.commandline;

import game.location.GameLocation;
import game.location.End;

/**
 * 
 * 
 * @author Findlay Royds
 * @version 1.1, May 2023.
 */
public class CLIEnd extends CLILocation {
	/**
	 * The end game location the cli end location is linked to.
	 * Hides the gameLocation property in the CLILocation super class.
	 */
	private End gameLocation;
	
	/**
	 * @param gameLocation			The end screen game location
	 */
	public CLIEnd(GameLocation gameLocation) {
		super(gameLocation);
		this.gameLocation = (End) gameLocation;
	}
	
	@Override
	public String[] display() {
		System.out.println("GAME OVER");
		System.out.println("Team name: " + gameLocation.getTeamName());
		System.out.println("Season Duration: " + gameLocation.getSeasonLength());
		System.out.println("Money Gained: " + gameLocation.getMoney());
		System.out.println("Points Gained: " + gameLocation.getScore());
		return new String[0];
	}
	
	@Override
	public void processOption(int selectedOption) {
		// Included for compatability with CLILocation abstract class.
		// No input should be processed on the end screen.
	}
}
