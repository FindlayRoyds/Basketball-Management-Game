package userinterface.graphical.components;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import game.Purchasable;

/**
 * A GUI component to allow the user to "explore" a given list of purchasables.
 * Consists of a scrollable ComponentList of PurchasableInfo components on the
 * left half of the screen, and a purchasableInfoLarge component panel on the
 * right side of the screen, showing details for the currently selected
 * purchasable.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PurchasableExplorer extends JPanel {
	/**
	 * Components displaying information about the purchasables
	 */
	protected List<JPanel> purchasableInfoComponents;

	/**
	 * The list of available purchasables
	 */
	protected List<Purchasable> purchasables;

	/**
	 * The component list that displays the purchasables
	 */
	private ComponentList purchasableList;

	/**
	 * The supplier that returns the purchasables to display
	 */
	protected Supplier<List<Purchasable>> purchasableSupplier;

	/**
	 * The index of the selected purchasable in the purchasables list.
	 */
	private Integer selectedPurchasableIndex;

	/**
	 * Whether or not to show the prices of purchasables.
	 */
	protected boolean showPrices;

	/**
	 * Displays a purchasableInfoLarge component on the right side of the screen,
	 * populated with details about the currently selected purchasable.
	 */
	public void displayPurchasableDetailsPanel() {
		Purchasable selectedPurchasable = getSelected();
		PurchasableInfoLarge purchasableDetailsPanel = new PurchasableInfoLarge(selectedPurchasable, showPrices);
		purchasableDetailsPanel.setBounds(406, 6, 388, 450);
		add(purchasableDetailsPanel);
		purchasableDetailsPanel.revalidate();
		purchasableDetailsPanel.repaint();
	}

	/**
	 * Builds the PurchasableInfoSmall components to be displayed in the
	 * ComponentList on the left side of the screen.
	 */
	public void makePurchasableInfoComponents() {
		purchasables = purchasableSupplier.get();
		purchasableInfoComponents = purchasables.stream()
				.map(purchasable -> (JPanel) (new PurchasableInfoSmall(purchasable, showPrices)))
				.collect(Collectors.toList());
	}

	/**
	 * Refreshes the purchasables shown on the screen using data retrieved through
	 * the explorer's purchasableSupplier supplier.
	 */
	public void refresh() {
		if (selectedPurchasableIndex != null && selectedPurchasableIndex >= purchasables.size())
			selectedPurchasableIndex = null;
		makePurchasableInfoComponents();
		purchasableList.refresh(purchasableInfoComponents, selectedPurchasableIndex, (i) -> onPurchasableSelect(i));
		displayPurchasableDetailsPanel();
	}

	/**
	 * Sets the selected purchasable index and calls the explorer's refresh() method
	 * to update its components.
	 * 
	 * @param index The index of the new selected purchasable.
	 */
	private void onPurchasableSelect(int index) {
		selectedPurchasableIndex = index;
		refresh();
	}

	/**
	 * @return The currently selected purchasable, or null if none are selected.
	 */
	public Purchasable getSelected() {
		if (selectedPurchasableIndex == null)
			return null;
		if (selectedPurchasableIndex >= purchasables.size()) {
			selectedPurchasableIndex = null;
			return null;
		}
		return purchasables.get(selectedPurchasableIndex);
	}

	/**
	 * @param supplier A Supplier function that returns a List of Purchasables from
	 *                 which the purchasable explorer will get its data.
	 */
	public void setSupplier(Supplier<List<Purchasable>> supplier) {
		this.purchasableSupplier = supplier;
	}

	/**
	 * Constructor for PurchasableExplorer.
	 * 
	 * @param purchasableSupplier A Supplier function that returns a List of
	 *                            Purchasables from which the purchasable explorer
	 *                            will get its data.
	 * @param showPrices          Whether or not the price of each purchasable
	 *                            should be displayed.
	 */
	public PurchasableExplorer(Supplier<List<Purchasable>> purchasableSupplier, boolean showPrices) {
		this.purchasableSupplier = purchasableSupplier;
		this.showPrices = showPrices;
		setLayout(null);
		setOpaque(false);
		selectedPurchasableIndex = null;
		purchasableInfoComponents = new ArrayList<JPanel>();

		makePurchasableInfoComponents();
		purchasableList = new ComponentList(purchasableInfoComponents, 80, new Rectangle(6, 6, 394, 450));
		purchasableList.refresh(purchasableInfoComponents, selectedPurchasableIndex,
				(index) -> onPurchasableSelect(index));
		setBounds(0, 65, 800, 550);
		add(purchasableList);

		displayPurchasableDetailsPanel();
	}

	/**
	 * Constructor for PurchasableExplorer.
	 * 
	 * @param purchasableSupplier A Supplier function that returns a List of
	 *                            Purchasables from which the purchasable explorer
	 *                            will get its data.
	 */
	public PurchasableExplorer(Supplier<List<Purchasable>> purchasableSupplier) {
		this(purchasableSupplier, false);
	}
}
