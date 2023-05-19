package game.location;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import game.GameEnvironment;
import game.Purchasable;
import util.Function3;

/**
 * An abstract class defining the Market location. From the Market, the Player
 * can buy and sell purchasables. The purchasables that are available to buy are
 * randomly generated each week.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
public class GameMarket extends GameLocation {
	/**
	 * Whether or not illegal purchasables can be bought or sold at this location
	 */
	private boolean allowIllegalPurchasables;

	/**
	 * The set of Purchasables that are available for the Player to purchase from
	 * the Market.
	 */
	private Set<Purchasable> availablePurchasables;

	/**
	 * The number of Purchasables that should be available for the Player to be able
	 * to purchase from the Market each week.
	 */
	private int amountToDisplay;

	/**
	 * A function to generate a single purchasable.
	 */
	private Function3<Integer, GameEnvironment, Purchasable> generatePurchasable;

	/**
	 * A function to get the purchasables relevant to the market that are owned by
	 * the player.
	 */
	private Supplier<Set<Purchasable>> getOwned;

	/**
	 * Gets the set of purchasables that are allowed at the Market and owned by the
	 * player. Allowed purchasables are those whose legality matches that of the
	 * Market.
	 * 
	 * @return A set of all owned purchasables that are also allowed at the Market.
	 */
	public Set<Purchasable> getOwnedAndAllowed() {
		Set<Purchasable> purchasables = getOwned.get();
		purchasables.removeIf(purchasable -> purchasable.getIsLegal() != allowIllegalPurchasables);
		return purchasables;
	}

	/**
	 * Constructor
	 */
	public GameMarket(GameEnvironment gameEnvironment,
			Function3<Integer, GameEnvironment, Purchasable> generatePurchasable, Supplier<Set<Purchasable>> getOwned,
			boolean allowIllegalPurchasables) {
		super(gameEnvironment);
		this.generatePurchasable = generatePurchasable;
		this.getOwned = getOwned;
		this.allowIllegalPurchasables = allowIllegalPurchasables;
	}

	/**
	 * Update the week to a given week. This involves randomly generating a new set
	 * of purchasables to be available to buy.
	 * 
	 * @param week The number of the current week in the season. Starts from 1.
	 */
	@Override
	public void update(int week) {
		availablePurchasables.clear();
		for (int i = 0; i < amountToDisplay; ++i) {
			availablePurchasables.add(generatePurchasable.apply(1, getGameEnvironment()));
		}
	}

	/**
	 * Allow the Player to purchase a purchasable from the market, but only if they
	 * have enough money to afford it. First the Player is charges for the price of
	 * the given Purchasable. Then the purchasable is removed from the Market's set
	 * of available Purchasables and given to the Player.
	 * 
	 * @param purchasable The purchasable to be purchased.
	 */
	public void purchase(Purchasable purchasable) {
		purchasable.purchase(getGameEnvironment().getPlayer());
	}

	/**
	 * Allow the Player to sell a purchasable back to the Market. First the Player
	 * is given a sum of money equal to the price of the Purchasable. Then the
	 * Purchasable is taken away from the Player and added to the Market's set of
	 * available Purchasables.
	 * 
	 * @param purchasable The purchasable to be sold.
	 */
	public void sell(Purchasable purchasable) {
		if (purchasable.getIsLegal() || allowIllegalPurchasables)
			purchasable.sell(getGameEnvironment().getPlayer());
		availablePurchasables.add(purchasable);
	}

	/**
	 * @return The set of Purchasables available for purchase in the Market.
	 */
	public Set<Purchasable> getAvailablePurchasables() {
		return availablePurchasables;
	}
}