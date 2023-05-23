package userinterface.graphical.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * A component that displays a vertical, scrollable list of equally sized child
 * components, of which one at a time can be selected by clicking on that
 * component.
 * 
 * @author Findlay Royds
 * @version 1.0
 */
@SuppressWarnings("serial") // We aren't using serialisation in this project
public class ComponentList extends JPanel {
	/**
	 * The height each component in the list should be set to in pixels
	 */
	private int componentHeight;

	/**
	 * The background panel each component is put into
	 */
	private JPanel backgroundPanel;

	/**
	 * Constructor for the Component List component. Creates the scroll pane, adds a
	 * background panel with an absolute layout, and populates the background panel
	 * with JPanel components.
	 * 
	 * @param componentsToDisplay A list containing JPanels to be displayed in the
	 *                            component list
	 * @param componentHeight     The height each component should be set to in
	 *                            pixels
	 * @param bounds              The bounds of the component list. Cannot be set
	 *                            with setBounds().
	 * @param onSelect            A consumer that tells the GUILocation using the
	 *                            component what item was selected
	 */
	public ComponentList(List<JPanel> componentsToDisplay, int componentHeight, Rectangle bounds,
			Consumer<Integer> onSelect) {
		setLayout(null);
		setBounds(bounds);
		this.componentHeight = componentHeight;

		backgroundPanel = new JPanel();
		backgroundPanel.setLayout(null);
		backgroundPanel.setBackground(Color.white);
		backgroundPanel.setBorder(null);

		JScrollPane scrollPanel = new JScrollPane(backgroundPanel);
		scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setBorder(null);
		scrollPanel.setBounds(0, 0, bounds.width, bounds.height);

		refresh(componentsToDisplay, null, onSelect);

		add(scrollPanel);
	}

	/**
	 * Repopulates the background panel in the scroll panel with the correct
	 * components
	 * 
	 * componentsToDisplay: A list containing JPanels to be displayed in the
	 * component list indexToHighlight: The
	 */
	public void refresh(List<JPanel> componentsToDisplay, Integer indexToHighlight, Consumer<Integer> onSelect) {
		backgroundPanel.removeAll();
		backgroundPanel.revalidate();
		backgroundPanel.repaint();
		backgroundPanel.setPreferredSize(
				new Dimension(getWidth() - 19, 6 + (componentHeight + 6) * componentsToDisplay.size()));

		for (int index = 0; index < componentsToDisplay.size(); index++) {
			// Calculate bounds of the list Item
			Rectangle listItemBounds = new Rectangle(6, 6 + (componentHeight + 6) * index, getWidth() - 12 - 19,
					componentHeight);
			// Create a constant of the index to use in the button event listener
			final int INDEX_FINAL = index;

			JPanel listItem = componentsToDisplay.get(index);
			listItem.setBounds(listItemBounds);

			// Highlight the component if it is the correct index
			if (indexToHighlight != null && index == indexToHighlight) {
				listItem.setBorder(BorderFactory.createLineBorder(Color.black));
			}

			// Overlay with a transparent button to detect click input
			JButton listItemButton = new JButton();
			listItemButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					onSelect.accept(INDEX_FINAL);
				}
			});
			listItemButton.setBounds(listItemBounds);
			listItemButton.setContentAreaFilled(false);
			listItemButton.setBorderPainted(false);
			listItemButton.setFocusPainted(false);

			backgroundPanel.add(listItemButton);
			backgroundPanel.add(listItem);
		}
	}

	/**
	 * Overloaded constructor for the Component List component. Overloads
	 * constructor and passes an empty consumer.
	 * 
	 * @param componentsToDisplay A list containing JPanels to be displayed in the
	 *                            component list
	 * @param componentHeight     The height each component should be set to in
	 *                            pixels
	 * @param bounds              The bounds of the component list. Cannot be set
	 *                            with setBounds().
	 */
	public ComponentList(List<JPanel> componentsToDisplay, int componentHeight, Rectangle bounds) {
		this(componentsToDisplay, componentHeight, bounds, (index) -> {
		});
	}
}
