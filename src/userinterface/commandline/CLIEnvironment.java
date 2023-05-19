package userinterface.commandline;

import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

import enumeration.Location;
import game.GameEnvironment;
import game.location.GameLocation;
import userinterface.UIEnvironment;

/**
 * The command line interface ui environment. Handles changing location in the
 * cli and displaying popups.
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
		CLILocations.put(Location.START, new CLIStart(gameLocations.get(Location.START), this));
		CLILocations.put(Location.MAP, new CLIMap(gameLocations.get(Location.MAP), this));
		CLILocations.put(Location.INVENTORY, new CLIInventory(gameLocations.get(Location.INVENTORY), this));
		CLILocations.put(Location.END, new CLIEnd(gameLocations.get(Location.END), this));
		CLILocations.put(Location.LOCKER_ROOM, new CLILocker(gameLocations.get(Location.LOCKER_ROOM), this));
		CLILocations.put(Location.MATCH_SELECION,
				new CLIMatchSelection(gameLocations.get(Location.MATCH_SELECION), this));
		CLILocations.put(Location.MATCH, new CLIMatch(gameLocations.get(Location.MATCH), this));
		CLILocations.put(Location.ATHLETE_MARKET,
				new CLIAthleteMarket(gameLocations.get(Location.ATHLETE_MARKET), this));
		CLILocations.put(Location.ITEM_MARKET, new CLIItemMarket(gameLocations.get(Location.ITEM_MARKET), this));
		CLILocations.put(Location.BLACK_MARKET, new CLIBlackMarket(gameLocations.get(Location.BLACK_MARKET), this));
	}

	/**
	 * Clears all text on the console screen.
	 */
	public void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	/**
	 * Displays a numbered list of options, and returns the option selected by the
	 * user.
	 * 
	 * @param options An ordered list of options to be displayed to the user.
	 * @return The index of the option selected by the user in range: [0, length of
	 *         options).
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
			} catch (java.util.InputMismatchException exception) {
				System.out.println("Please enter an integer");
			}
		}

		return selectedOption;
	}

	/**
	 * Get an integer input from the user.
	 * 
	 * @param minAllowedValue The minimum input value allowed.
	 * @param maxAllowedValue The maximum input value allowed.
	 * @return An integer: The user's input.
	 */
	public Integer getIntInput(int minAllowedValue, int maxAllowedValue) {
		System.out.print("Enter a number between " + minAllowedValue + " and " + maxAllowedValue + ": ");
		if (minAllowedValue > maxAllowedValue)
			return -1;

		Integer input = null;
		while (input == null || input < minAllowedValue || input > maxAllowedValue) {
			try {
				input = scanner.nextInt();
				if (input < minAllowedValue || maxAllowedValue < input) {
					System.out.println("Please enter a number between " + minAllowedValue + " and " + maxAllowedValue);
				}
			} catch (java.util.InputMismatchException exception) {
				System.out.println("Please enter a number");
			}
		}

		return input;
	}

	/**
	 * Get a string as input from the user. Continues prompting until a string of
	 * length between 1 and maxLength.
	 * 
	 * @param maxLength The maximum allowed length of the string.
	 * @return The string inputed by the user.
	 */
	public String getStringInput(int maxLength) {
		System.out.print("Type your input here: ");

		String input = "";
		while (input.length() == 0 || input.length() > maxLength) {
			input = scanner.next();
			if (input.length() == 0 || input.length() > maxLength) {
				System.out.println("Please enter a string with length between 1 and " + maxLength);
			}
		}

		return input;
	}

	@Override
	public void changeLocation(Location location, GameLocation gameLocation) {
		currentLocation = CLILocations.get(location);

		clearScreen();
		Location nextLocation = currentLocation.display();

		// Handle null return to delete stack frame
		if (nextLocation != null)
			gameEnvironment.changeLocation(nextLocation);
	}

	/**
	 * Display a message on screen and allow the user to select from a list of
	 * options.
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
