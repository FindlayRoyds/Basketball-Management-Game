package game;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import game.item.Item;

/**
 * This class defines a player and the things they own and can do in the game.
 * 
 * @author Jake van Keulen, Findlay Royds
 * @version 1.4, May 2023.
 */
public class Player {
	/**
	 * The game environment the player instance belongs to
	 */
	private GameEnvironment gameEnvironment;

	/**
	 * The team the Player owns.
	 */
	private Team team;

	/**
	 * The Player's inventory. Contains all their items.
	 */
	private HashSet<Item> inventory;

	/**
	 * The Player's score.
	 */
	private int score;

	/**
	 * The amount of money in dollars that the player has.
	 */
	private int money;

	/**
	 * Lambda function that returns the items in the Player's inventory as a set of
	 * Purchasables.
	 */
	public Supplier<Set<Purchasable>> getPurchasables = () -> new HashSet<Purchasable>(inventory);

	/**
	 * Constructor for the player class
	 * 
	 * @param gameEnvironment The game environment the player object exists in
	 */
	public Player(GameEnvironment gameEnvironment) {
		this.money = 0;
		this.score = 0;
		this.inventory = new HashSet<Item>();
		this.team = new Team(gameEnvironment, "");
		this.gameEnvironment = gameEnvironment;
	}

	/**
	 * Adds an item to the Player's inventory.
	 * 
	 * @param item The item to be added.
	 */
	public void addToInventory(Item item) {
		inventory.add(item);
	}

	/**
	 * Removes a given amount from the Player's money.
	 * 
	 * @param amountOfMoney The amount of money to be removed.
	 * @return If the charge was successful
	 */
	public boolean chargeMoney(int amountOfMoney) {
		if (money >= amountOfMoney) {
			money -= amountOfMoney;
			return true;
		} else {
			gameEnvironment.getUIEnvironment().displayPopup("You are unable to afford that!");
			return false;
		}
	}

	/**
	 * Adds the specified amount of money to the player's money
	 * 
	 * @param amountOfMoney The amount of money to give the player
	 */
	public void giveMoney(int amountOfMoney) {
		money += amountOfMoney;
	}

	/**
	 * Adds the specified amount of points to the player's total score
	 * 
	 * @param amountOfPoints The amount of points to give the player
	 */
	public void givePoints(int amountOfPoints) {
		score += amountOfPoints;
	}

	/**
	 * Removes and item from the player's inventory
	 * 
	 * @param item The item to remove from the inventory
	 */
	public void removeFromInventory(Item item) {
		inventory.remove(item);
	}

	/**
	 * @return The Player's team.
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * @return The amount of money in dollars that the Player has.
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @return The Player's score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return A HashSet of Items owned by the player
	 */
	public HashSet<Item> getInventory() {
		return inventory;
	}
}
