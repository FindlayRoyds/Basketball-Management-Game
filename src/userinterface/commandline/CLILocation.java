package userinterface.commandline;

import enumeration.Location;

/**
 * The CLILocation interface. Responsible for displaying the correct text on
 * screen and interpreting user input.
 * 
 * @author Findlay Royds, Jake van Keulen
 * @version 1.1, May 2023.
 */
public abstract class CLILocation {

	/**
	 * The CLIEnvironment that the CLILocation belongs to.
	 */
	protected CLIEnvironment cliEnvironment;

	/**
	 * The constructor for CLILocation
	 * 
	 * @param cliEnvironment The ui environment responsible for displaying the cli.
	 */
	public CLILocation(CLIEnvironment cliEnvironment) {
		this.cliEnvironment = cliEnvironment;
	}

	/**
	 * Displays text on the CLI depending on the state of the linked GameLocation.
	 * 
	 * @return An ordered List of options to be displayed on the cli.
	 */
	public abstract Location display();
}
