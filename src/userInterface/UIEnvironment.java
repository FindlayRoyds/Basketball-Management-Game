package userInterface;

import java.util.List;

import game.location.GameLocation;

/**
 * Defines an interface through which the game environment can
 * interact with a UI environment.
 * This includes commands for showing messages and popups,
 * as well as changing the current UI location.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
public interface UIEnvironment {
	/**
	 * Change the current UI location to a given Location.
	 * 
	 * @param location		The Location to change to.
	 */
	public void changeLocation(GameLocation location);

	/**
	 * Display a popup in the UI.
	 * This popup contains a message of text followed by a series of
	 * options, defined by the buttons parameter, for the user to select one from.
	 * When one of these options is selected, the popup closes and the index of
	 * the selected option is returned.
	 * 
	 * @param message		A String containing the message to display in the popup.
	 * @param options		A List of Strings containing the text to display for each option.
	 * @return				An index i in the range [0, options.size()), such that options[i]
	 * 						was the selected option.
	 */
	public int displayPopup(String message, List<String> options);
	
	/**
	 * Display a popup in the UI.
	 * This popup contains only a message of text.
	 * This version of the method does not display options to select,
	 * and therefore does not return anything.
	 * 
	 * @param message		The message to display in the popup.
	 */
	public void displayPopup(String message);
}
