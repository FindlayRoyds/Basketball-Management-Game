package game;

import java.util.Random;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;
import java.util.EnumMap;

import enumeration.Location;
import game.item.Item;
import game.location.*;
import game.randomevent.RandomEvent;
import userinterface.UIEnvironment;
import userinterface.commandline.CLIEnvironment;

/**
 * This class implements the game environment. It handles setting up and running
 * a game.
 * 
 * @author Jake van Keulen, Findlay Royds
 * @version 1.2, May 2023.
 */
public class GameEnvironment {
	/**
	 * The player who is playing the game.
	 */
	private Player player;

	/**
	 * The location the player is currently at.
	 */
	private Location currentLocation;

	/**
	 * Map of location types (from the Location enum) to GameLocation classes that
	 * implement the given locations.
	 */
	private Map<Location, GameLocation> gameLocations;

	/**
	 * The number of the current week in the season. Starts from 1.
	 */
	private int currentWeek;

	/**
	 * The length of the season in weeks.
	 */
	private int seasonLength;

	/**
	 * Seed used for generating random events and match outcomes.
	 */
	private int gameSeed;

	/**
	 * All the random events that can happen. These have a chance of occurring each
	 * week.
	 */
	private Set<RandomEvent> randomEvents;

	/**
	 * Environment used for controlling the UI.
	 */
	private UIEnvironment uiEnvironment;

	/**
	 * Random object used for generating random numbers.
	 */
	private Random rng;

	/**
	 * The main method of game environment. Responsible for starting the game.
	 * 
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		GameEnvironment gameEnvironment = new GameEnvironment(1); // temporary random seed
		gameEnvironment.changeLocation(Location.MAP);
	}

	/**
	 * The constructor for game environment. Responsible for creating the objects required to start the game.
	 */
	public GameEnvironment(int randomSeed) {
		rng = new Random(randomSeed);
		player = new Player(this);
		
		// Create game locations
		gameLocations = new EnumMap<Location, GameLocation>(Location.class);
		gameLocations.put(Location.MAP, new GameMap(this));
		gameLocations.put(Location.END, new GameEnd(this));
		gameLocations.put(Location.INVENTORY, new GameInventory(this));
		gameLocations.put(Location.LOCKER_ROOM, new GameLocker(this));
		gameLocations.put(Location.MATCH, new GameMatch(this));
		gameLocations.put(Location.MATCH_SELECION, new GameMatchSelection(this));
		gameLocations.put(Location.ATHLETE_MARKET, new GameMarket(
				this,
				Athlete.generateAthlete,
				player.getTeam().getAllAthletes
		));
		gameLocations.put(Location.ITEM_MARKET, new GameMarket(
				this,
				Item.generateLegalItem,
				player.getPurchasables
		));
		
		uiEnvironment = new CLIEnvironment(gameLocations, this);
	}

	/**
	 * @return Random object used for generating random numbers.
	 */
	public Random getRng() {
		return rng;
	}

	/**
	 * @return The season length in weeks.
	 */
	public int getSeasonLength() {
		return seasonLength;
	}

	/**
	 * @return The number of the current week. Starts from 1.
	 */
	public int getWeek() {
		return currentWeek;
	}

	/**
	 * @return The GameEnvironment's current location.
	 */
	private Location getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * Gets the corresponding GameLocation object for a given Location.
	 * 
	 * @param location A value from the Location enum
	 * @return A GameLocation object
	 */
	public GameLocation getGameLocation(Location location) {
		return gameLocations.get(location);
	}

	/**
	 * Change the current game location.
	 * 
	 * @param newLocation The location to change to.
	 */
	public void changeLocation(Location newLocation) {
		this.currentLocation = newLocation;
		this.uiEnvironment.changeLocation(newLocation, getGameLocation(getCurrentLocation()));
	}

	/**
	 * Decides if the game is over or not. Depends on whether there are any weeks
	 * left and if the player is able to make a full team.
	 * 
	 * @return Whether or not the game is over.
	 */
	public boolean hasEnded() {
		if (currentWeek > seasonLength)
			return true;

		Team team = getPlayer().getTeam();
		int numberOfAthletesNeeded = 5 - (team.getReserveAthletes().size() + team.getActiveAthletes().size());

		// Get list of available athletes in the athlete market and sort by price.
		GameMarket athleteMarket = (GameMarket) getGameLocation(Location.ATHLETE_MARKET);
		ArrayList<Purchasable> availableAthletes = new ArrayList<Purchasable>(athleteMarket.getAvailablePurchasables());
		availableAthletes.sort((athlete1, athlete2) -> athlete1.getPrice() - athlete2.getPrice());

		// Greedily simulate purchasing the cheapest athletes first,
		// to see the maximum number of athletes the player can afford.
		int canPurchase = 0, remainingMoney = getPlayer().getMoney();
		for (Purchasable athlete : availableAthletes) {
			if (athlete.getPrice() <= remainingMoney) {
				++canPurchase;
				remainingMoney -= athlete.getPrice();
			} else
				break;
		}

		// Check if the player can purchase enough athletes to make a full team.
		if (canPurchase < numberOfAthletesNeeded)
			return true;

		return false;
	}

	/**
	 * Gets the player who is playing the game.
	 * 
	 * @return The game's player object.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the UI environment used by the game for I/O.
	 * 
	 * @return A UIEnvironment object.
	 */
	public UIEnvironment getUIEnvironment() {
		return this.uiEnvironment;
	}
}
