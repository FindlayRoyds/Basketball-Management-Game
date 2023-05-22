package userinterface.commandline;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import enumeration.Location;
import game.Purchasable;
import game.item.Item;
import game.location.GameLocation;
import game.location.GameMarket;

/**
 * The class that displays the black market on the cli.
 * 
 * @author Jake van Keulen
 * @version 1.0, May 2023.
 */
public class CLIBlackMarket extends CLILocation {
	/**
	 * The game location this cli location is linked to.
	 */
	private GameMarket gameLocation;

	/**
	 * Constructor for CLIBlackMarket
	 * 
	 * @param cliEnvironment
	 */
	public CLIBlackMarket(GameLocation gameLocation, CLIEnvironment cliEnvironment) {
		super(cliEnvironment);
		this.gameLocation = (GameMarket) gameLocation;
	}

	/**
	 * Displays a list of item names for the user to select one from. Returns the
	 * list index of the selected item.
	 * 
	 * @param items List of Items that can be selected.
	 * @return The index of the selected Item in the items List.
	 */
	Purchasable getItemSelection(Set<Purchasable> givenItems) {
		List<Purchasable> items = new ArrayList<Purchasable>(givenItems);
		String[] itemNames = new String[items.size()];
		for (int i = 0; i < items.size(); ++i) {
			itemNames[i] = ((Item) items.get(i)).getName();
		}
		return items.get(cliEnvironment.displayOptions(itemNames));
	}

	@Override
	public Location display() {
		while (true) {
			System.out.println("Black Market");
			System.out.println("What would you like to do?");
			String[] options = { "Purchase an item", "Sell an item", "Return to map" };
			int selectedIndex = cliEnvironment.displayOptions(options);

			if (selectedIndex == 0) {
				if (gameLocation.getAvailablePurchasables().isEmpty())
					System.out.println("You already bought everything!\n");
				else
					gameLocation.purchase(getItemSelection(gameLocation.getAvailablePurchasables()));
			} else if (selectedIndex == 1) {
				if (gameLocation.getOwnedAndAllowed().isEmpty())
					System.out.println("You have no items to sell!\n");
				else
					gameLocation.sell(getItemSelection(gameLocation.getOwnedAndAllowed()));
			} else if (selectedIndex == 2) {
				return Location.MAP;
			}
		}
	}

}
