package game;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import enumeration.Location;
import game.item.Item;
import game.item.Steroid;
import game.location.GameEnd;
import game.location.GameInventory;
import game.location.GameLocation;
import game.location.GameLocker;
import game.location.GameMap;
import game.location.GameMarket;
import game.location.GameMatch;
import game.location.GameMatchSelection;
import game.location.GameStart;
import game.randomevent.DrugTest;
import game.randomevent.RandomEvent;
import userinterface.UIEnvironment;
import userinterface.commandline.CLIEnvironment;
import userinterface.graphical.GUIEnvironment;

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
	 * The difficulty of the game. There are 3 possible values: {0, 1, 2}. These
	 * represent easy, medium and hard difficulty respectively.
	 */
	private int difficulty;
	/**
	 * The number of the current week in the season. Starts from 1.
	 */
	private int currentWeek;

	/**
	 * The length of the season in weeks.
	 */
	private int seasonLength;

	/**
	 * Environment used for controlling the UI.
	 */
	private UIEnvironment uiEnvironment;

	/**
	 * Random object used for generating random numbers.
	 */
	private Random rng;

	/**
	 * drug test random event for checking if any of the player's athletes have used
	 * steroids
	 */
	private RandomEvent drugTestRandomEvent;

	/**
	 * The main method of game environment. Responsible for starting the game.
	 * 
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		new GameEnvironment(false);
	}

	/**
	 * The constructor for game environment. Responsible for creating the objects
	 * required to start the game.
	 */
	public GameEnvironment(boolean useCLI) {
		seasonLength = 7;
		player = new Player(this);

		// Create game locations
		gameLocations = new EnumMap<Location, GameLocation>(Location.class);
		gameLocations.put(Location.START, new GameStart(this));
		gameLocations.put(Location.MAP, new GameMap(this));
		gameLocations.put(Location.END, new GameEnd(this));
		gameLocations.put(Location.INVENTORY, new GameInventory(this));
		gameLocations.put(Location.LOCKER_ROOM, new GameLocker(this));
		gameLocations.put(Location.MATCH, new GameMatch(this));
		gameLocations.put(Location.MATCH_SELECTION, new GameMatchSelection(this));
		gameLocations.put(Location.ATHLETE_MARKET,
				new GameMarket(this, Athlete.generateAthlete, player.getTeam().getAllPurchasables, false, 5));
		gameLocations.put(Location.ITEM_MARKET,
				new GameMarket(this, Item.generateLegalItem, player.getPurchasables, false, 8));
		gameLocations.put(Location.BLACK_MARKET,
				new GameMarket(this, Steroid.generateSteroid, player.getPurchasables, true, 2));

		drugTestRandomEvent = new DrugTest(this);
		currentWeek = 0;

		if (useCLI) {
			uiEnvironment = new CLIEnvironment(gameLocations, this);
		} else {
			uiEnvironment = new GUIEnvironment(gameLocations, this);
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						((GUIEnvironment) uiEnvironment).getFrame().setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		// Let the user start up the game
		changeLocation(Location.START);

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
	 * @param length The season length in weeks.
	 */
	public void setSeasonLength(int length) {
		seasonLength = length;
	}

	/**
	 * @param seed The seed of the game. Used for random number generation.
	 */
	public void setSeed(int seed) {
		rng = new Random(seed);
	}

	/**
	 * @return The difficulty level of the game. Can be 1, 2, or 3, meaning easy,
	 *         medium or hard respectively.
	 */
	public int getDifficulty() {
		return this.difficulty;
	}

	/**
	 * @param difficulty The difficulty of the game
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
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

	/**
	 * Progresses the game to the next week, checks if the game has ended, and
	 * triggers random events.
	 */
	public void progressWeek() {
		currentWeek += 1;

		// Trigger random events that effect the player. Only trigger if it isn't the
		// first week.
		if (currentWeek != 1) {
			Team playerTeam = player.getTeam();
			playerTeam.triggerRandomEvents();

			Set<Athlete> playerAthletes = playerTeam.getAllAthletes();
			for (Athlete athlete : playerAthletes) {
				athlete.triggerRandomEvents();
			}

			drugTestRandomEvent.trigger();
		}

		// Detect if the player is unable to progress in the game
		for (GameLocation gameLocation : gameLocations.values()) {
			gameLocation.update(currentWeek);
		}
		if (hasEnded()) {
			changeLocation(Location.END);
		}
	}
}
