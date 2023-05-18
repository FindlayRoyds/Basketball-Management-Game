package userinterface.commandline;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import game.location.GameLocation;
import userinterface.UIEnvironment;
import enumeration.Location;

/**
 * The command line interface ui environment.
 * Handles changing location in the cli and displaying popups.
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class CLIEnvironment implements UIEnvironment {
	/**
	 * The location currently being displayed on the cli.
	 */
	private CLILocation currentLocation;
	
	/**
	 * Every location enum mapped to their respective CLILocation.
	 */
	private Map<Location, CLILocation> locations;
	
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
	private int displayOptions(String[] options) {
		for (int i = 0; i < options.length; i++) {
			System.out.println(i + 1 + ": " + options[i]);
		}
		
		Scanner scan = new Scanner(System.in);
		System.out.print("Select option: ");
		
		int selectedOption = scan.nextInt();
		return selectedOption;
	}
	
	@Override
	public void changeLocation(Location location, GameLocation gameLocation) {
		currentLocation = locations.get(location);
		
		clearScreen();
		String[] options = currentLocation.display();
		int selectedOption = displayOptions(options);
		
		currentLocation.processOption(selectedOption);
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
