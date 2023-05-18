package userinterface.commandline;

import game.location.GameLocation;

/**
 * The CLILocation interface. Responsible for displaying the correct text on screen
 * and interpreting user input.
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public interface CLILocation {
	/**
	 * Displays text on the CLI depending on the state of the linked GameLocation.
	 * 
	 * @param gameLocation					The GameLocation the CLILocation is linked to.
	 * @return								An ordered List of options to be displayed on the cli.
	 */
	public String[] display(GameLocation gameLocation);
	
	/**
	 * Abstract method for CLILocation.
	 * Allows the CLILocation to do UI logic and call methods in the given gameLocation.
	 * 
	 * @param selectedOption				The index of the selected option
	 */
	public void processOption(int selectedOption);
}
