package game.location;

import java.util.Set;

import game.Purchasable;
import game.item.Item;

/**
 * A class that extends the Market game location to allow for the sale of Items.
 * Adds the functionality for adding and removing Items from the Player's inventory,
 * to complete their purchase or sale.
 * Also provides access to view the Items in the Player's inventory.
 * 
 * @author Jake van Keulen
 * @version 1.0
 *
 */
public class ItemMarket extends Market {
	/**
	 * Whether or not illegal Items should be permitted for sale.
	 * This decides if the Player will be able to sell illegal items back to the Market or not.
	 */
	private boolean allowIllegalItems;

	/**
	 * Removes an Item from the Player's inventory.
	 * 
	 * @param purchasable		The Item to be removed.
	 */
	@Override
	protected void removePurchasableFromPlayer(Purchasable purchasable) {
		getGameEnvironment().getPlayer().removeFromInventory((Item)purchasable);
	}

	/**
	 * Adds an Item to the Player's inventory.
	 * 
	 * @param purchasable		The Item to be added.
	 */
	@Override
	protected void givePurchasableToPlayer(Purchasable purchasable) {
		getGameEnvironment().getPlayer().addToInventory((Item)purchasable);
	}

	/**
	 * Overrides the Market's sell method to only proceed with selling an Item
	 * back to the Market if:
	 * 		1. The item is legal, or
	 * 		2. Illegal items can be sold at this Market
	 */
	@Override
	public void sell(Purchasable purchasable) {
		if (((Item)purchasable).getIsLegal() || allowIllegalItems)
			super.sell(purchasable);
	}
	
	/**
	 * @return		The set of Items in the Player's inventory.
	 */
	public Set<Item> getInventory() {
		return getGameEnvironment().getPlayer().getInventory();
	}
}
