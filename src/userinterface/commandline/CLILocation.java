package userinterface.commandline;

import game.location.GameLocation;

/**
 * The CLILocation interface. Responsible for displaying the correct text on screen
 * and interpreting user input.
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public abstract class CLILocation {
	/**
	 * The matching game location that the CLILocation is linked to
	 */
	protected GameLocation gameLocation;

	/**
	 * The CLIEnvironment that the CLILocation belongs to.
	 */
	protected CLIEnvironment cliEnvironment;
	
	/**
	 * The constructor for CLILocation
	 * 
	 * @param gameLocation					The GameLocation the CLILocation is linked to.
	 */
	public CLILocation(GameLocation gameLocation) {
		this.gameLocation = gameLocation;
	}
	
	/**
	 * Displays text on the CLI depending on the state of the linked GameLocation.
	 * 
	 * @return								An ordered List of options to be displayed on the cli.
	 */
	public abstract void display();
}
