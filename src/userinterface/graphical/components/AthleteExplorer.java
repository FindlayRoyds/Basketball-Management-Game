package userinterface.graphical.components;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import game.Athlete;
import game.Purchasable;

/**
 * A class extending the PurchasableExplorer component to use AthleteInfoSmall
 * components instead of PurchasableInfoSmall components.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AthleteExplorer extends PurchasableExplorer {
	/**
	 * Build the small purchasableInfoComponents to be displayed in a component list
	 * on the left half of the window.
	 */
	@Override
	public void makePurchasableInfoComponents() {
		purchasables = purchasableSupplier.get();
		purchasableInfoComponents = purchasables.stream()
				.map(purchasable -> (JPanel) (new AthleteInfoSmall((Athlete) purchasable, showPrices)))
				.collect(Collectors.toList());
	}

	/**
	 * Constructor for AthleteExplorer. Calls the super class constructor.
	 * 
	 * @param purchasableSupplier A function that returns a list of purchasables to
	 *                            display in the athlete explorer.
	 */
	public AthleteExplorer(Supplier<List<Purchasable>> purchasableSupplier) {
		super(purchasableSupplier);
	}
}
