package userinterface.graphical.components;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import game.Purchasable;

@SuppressWarnings("serial")
public class PurchasableExplorer extends JPanel {
	private List<JPanel> purchasableInfoComponents;
	private List<Purchasable> purchasables;
	private ComponentList purchasableList;
	private Integer selectedPurchasableIndex;
	private Supplier<List<Purchasable>> purchasableSupplier;

	public void displayPurchasableDetailsPanel() {
		Purchasable selectedPurchasable = getSelected();
		PurchasableInfoLarge purchasableDetailsPanel = new PurchasableInfoLarge(selectedPurchasable, false);
		purchasableDetailsPanel.setBounds(360, 0, 350, 400);
		add(purchasableDetailsPanel);
		purchasableDetailsPanel.revalidate();
		purchasableDetailsPanel.repaint();
	}

	public void makePurchasableInfoComponents() {
		purchasables = purchasableSupplier.get();
		purchasableInfoComponents = purchasables.stream()
				.map(purchasable -> (JPanel) (new PurchasableInfoSmall(purchasable, false)))
				.collect(Collectors.toList());
	}

	public void refresh() {
		if (selectedPurchasableIndex >= purchasables.size())
			selectedPurchasableIndex = null;
		makePurchasableInfoComponents();
		purchasableList.refresh(purchasableInfoComponents, selectedPurchasableIndex, (i) -> onPurchasableSelect(i));
		displayPurchasableDetailsPanel();
	}

	private void onPurchasableSelect(int index) {
		selectedPurchasableIndex = index;
		refresh();
	}

	public Purchasable getSelected() {
		if (selectedPurchasableIndex == null)
			return null;
		if (selectedPurchasableIndex >= purchasables.size()) {
			selectedPurchasableIndex = null;
			return null;
		}
		return purchasables.get(selectedPurchasableIndex);
	}

	public PurchasableExplorer(Supplier<List<Purchasable>> purchasableSupplier) {
		this.purchasableSupplier = purchasableSupplier;
		setLayout(null);
		selectedPurchasableIndex = null;
		setBounds(0, 0, 800, 550);
		purchasableInfoComponents = new ArrayList<JPanel>();

		makePurchasableInfoComponents();
		purchasableList = new ComponentList(purchasableInfoComponents, 100, new Rectangle(0, 0, 350, 400));
		purchasableList.refresh(purchasableInfoComponents, selectedPurchasableIndex,
				(index) -> onPurchasableSelect(index));
		add(purchasableList);

		displayPurchasableDetailsPanel();
	}
}
