package userinterface.commandline;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import enumeration.Location;
import game.item.Item;
import game.location.GameInventory;
import game.location.GameLocation;

/**
 * The CLI map location. Responsible for displaying locations the player can
 * visit.
 * 
 * @author Jake van Keulen
 * @version 1.0, May 2023.
 */
public class CLIInventory extends CLILocation {
	/**
	 * The inventory game location the cli map location is linked to. Hides the
	 * gameLocation property in the CLILocation super class.
	 */
	private GameInventory gameLocation;

	/**
	 * Constructor for the map CLI location.
	 * 
	 * @param gameLocation the inventory GameLocation.
	 */
	public CLIInventory(GameLocation gameLocation, CLIEnvironment cliEnvironment) {
		super(cliEnvironment);
		this.gameLocation = (GameInventory) gameLocation;
	}

	/**
	 * Display all the items from the Player's inventory.
	 */
	private void displayItems() {
		Set<Item> items = gameLocation.getItems();
		int i = 1;
		for (Item item : items) {
			System.out.println(i + ". " + item.getName() + ": " + item.getDescription());
			++i;
		}
	}

	/**
	 * Get the user to select an item from the inventory and return it.
	 * 
	 * @return The selected Item.
	 */
	private Item selectItem() {
		List<Item> items = new ArrayList<Item>(gameLocation.getItems());
		String[] options = new String[items.size()];
		for (int i = 0; i < items.size(); ++i) {
			options[i] = items.get(i).getName();
		}
		int selectedIndex = cliEnvironment.displayOptions(options);
		return items.get(selectedIndex);
	}

	@Override
	public Location display() {
		while (true) {
			System.out.println("Inventory");
			String[] options = { "View items", "Use an item", "Exit to map" };
			int selectedOption = cliEnvironment.displayOptions(options);

			if (selectedOption == 0) {
				System.out.println("Your items:");
				displayItems();
			} else if (selectedOption == 1) {
				if (gameLocation.getItems().isEmpty()) {
					cliEnvironment.displayPopup("You have no items to use");
				} else {
					Item selectedItem = selectItem();
					gameLocation.useItem(selectedItem);
				}
			} else if (selectedOption == 2) {
				return Location.MAP;
			}
		}
	}
}