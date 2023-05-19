package userinterface.commandline;

import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

import game.GameEnvironment;
import game.location.GameLocation;
import userinterface.UIEnvironment;
import enumeration.Location;

/**
 * The command line interface ui environment.
 * Handles changing location in the cli and displaying popups.
 * 
 * @author Findlay Royds
 * @version 1.2, May 2023.
 */
public class CLIEnvironment implements UIEnvironment {
	/**
	 * The location currently being displayed on the cli.
	 */
	private CLILocation currentLocation;
	
	/**
	 * The game environment that the CLI should interact with.
	 */
	private GameEnvironment gameEnvironment;
	
	/**
	 * Scanner object used to get user input
	 */
	Scanner scanner;
	
	/**
	 * Every location enum mapped to their respective CLILocation.
	 */
	private Map<Location, CLILocation> CLILocations;
	
	public CLIEnvironment(Map<Location, GameLocation> gameLocations, GameEnvironment gameEnvironment) {
		this.gameEnvironment = gameEnvironment;
		scanner = new Scanner(System.in);
		
		CLILocations = new EnumMap<Location, CLILocation>(Location.class);
		CLILocations.put(Location.MAP, new CLIMap(gameLocations.get(Location.MAP), this));
		CLILocations.put(Location.INVENTORY, new CLIInventory(gameLocations.get(Location.INVENTORY), this));
		CLILocations.put(Location.END, new CLIEnd(gameLocations.get(Location.END), this));
	}
	
	/**
	 * Clears all text on the console screen.
	 */
	private void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	/**
	 * Displays a numbered list of options, and returns the option selected by the user.
	 * 
	 * @param options				An ordered list of options to be displayed to the user.
	 * @return						The index of the option selected by the user in range: [0, length of options).
	 */
	public int displayOptions(String[] options) {
		// If no options are available don't output anything or get any input
		if (options.length == 0) {
			return -1;
		}
		
		// Display options
		for (int i = 0; i < options.length; i++) {
			System.out.println(i + 1 + ": " + options[i]);
		}
		
		// Get user input
		int selectedOption = -1;
		while (selectedOption >= options.length || selectedOption < 0) {
			System.out.print("Select option: ");
			// Catch an input mismatch exception
			try {
				selectedOption = scanner.nextInt() - 1;
				// Detect an input outside the valid range and alert user
				if (selectedOption >= options.length || selectedOption < 0) {
					System.out.println("Please enter a valid option");
				}
			} catch(java.util.InputMismatchException exception) {
				System.out.println("Please enter an integer");
			}
		}
		
		return selectedOption;
	}
	
	@Override
	public void changeLocation(Location location, GameLocation gameLocation) {
		currentLocation = CLILocations.get(location);
		
		clearScreen();
		Location nextLocation = currentLocation.display();
		gameEnvironment.changeLocation(nextLocation);
	}
	
	/**
	 * Display a message on screen and allow the user to select from a list of options.
	*/
	@Override
	public int displayPopup(String message, String[] options) {
		System.out.println(message);
		return displayOptions(options);
	}

	/**
	 * displays a message on the cli with no options.
	 */
	@Override
	public void displayPopup(String message) {
		System.out.println(message);
	}

}
