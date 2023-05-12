/**
 * This class defines a player and the things they own and can do in the game.
 * 
 * @author Jake van Keulen
 * @version 1.0, May 2023.
 */
package game;

import java.util.HashSet;

public class Player {
	/**
	 * The team the Player owns.
	 */
	private Team team;
	/**
	 * The Player's inventory.
	 * Contains all their items.
	 */
	private HashSet<Item> inventory;
	/**
	 * The Player's score.
	 */
	private int score;
	/**
	 * The amount of money in dollars that the player has.
	 */
	private float money;
	
	/**
	 * Adds an item to the Player's inventory.
	 * 
	 * @param item		The item to be added.
	 */
	public void addToInventory(Item item) {
		inventory.add(item);
	}
	/**
	 * Removes a given amount from the Player's money.
	 * 
	 * @param amountOfMoney		The amount of money to be removed.
	 * @return					If the charge was successful
	 */
	public boolean chargeMoney(float amountOfMoney) {
		if (money >= amountOfMoney) {
			money -= amountOfMoney;
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Gets and returns the team owned by the player.
	 * 
	 * @return		The Player's team.
	 */
	public Team getTeam() {
		return team;
	}
	/**
	 * Gets and returns the set of items owned by the player.
	 * 
	 * @return		A HashSet of Items owned by the player
	 */
	public HashSet<Item> getInventory() {
		return inventory;
	}
}
