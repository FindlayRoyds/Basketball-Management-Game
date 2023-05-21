package userinterface.graphical.components;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import game.Athlete;
import game.Purchasable;

public class AthleteExplorer extends PurchasableExplorer {
	@Override
	public void makePurchasableInfoComponents() {
		purchasables = purchasableSupplier.get();
		purchasableInfoComponents = purchasables.stream()
				.map(purchasable -> (JPanel) (new AthleteInfoSmall((Athlete) purchasable, showPrices)))
				.collect(Collectors.toList());
	}

	public AthleteExplorer(Supplier<List<Purchasable>> purchasableSupplier) {
		super(purchasableSupplier);
		// TODO Auto-generated constructor stub
	}
}
