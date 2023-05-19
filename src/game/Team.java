package game;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

import enumeration.Position;
import game.randomevent.AthleteJoins;
import game.randomevent.RandomEvent;
import util.MiscUtil;
import util.NameGenerator;

/**
 * This class defines a team made up of Athletes, which can be active players or
 * reserves. Active players each occupy a different position on the team.
 * 
 * @author Jake van Keulen, Findlay Royds
 * @version 1.4
 */
public class Team {
	/**
	 * The game environment the team exists in.
	 */
	GameEnvironment gameEnvironment;

	/**
	 * Map of Positions to the athlete that has been assigned that position on the
	 * Team.
	 */
	private Map<Position, Athlete> activeAthletes;

	/**
	 * Set of Athletes who are reserves in the Team.
	 */
	private Set<Athlete> reserveAthletes;

	/**
	 * Name of the Team.
	 */
	private String name;

	/**
	 * The maximum number of reserve athletes permitted on a team.
	 */
	private final static int MAX_NUMBER_OF_RESERVES = 5;

	/**
	 * The random event that causes a new athlete to join the team.
	 */
	RandomEvent athleteJoinsRandomEvent;

	/**
	 * @return The maximum number of reserves that can be in a Team.
	 */
	public static int getMaxNumberOfReserves() {
		return MAX_NUMBER_OF_RESERVES;
	}

	/**
	 * Initializes an empty team with the given team name.
	 * 
	 * @param teamName The name of the team to be created.
	 */
	public Team(GameEnvironment gameEnvironment, String teamName) {
		this.gameEnvironment = gameEnvironment;
		name = teamName;
		activeAthletes = new EnumMap<Position, Athlete>(Position.class);
		reserveAthletes = new HashSet<Athlete>();
		athleteJoinsRandomEvent = new AthleteJoins(gameEnvironment, this);
	}

	/**
	 * Returns the Team name, which is a String
	 * 
	 * @return A String: the Team name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The Team name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns a Map containing all active Athletes organized by their positions.
	 * 
	 * @return Map of Positions to the Athlete that has been assigned that Position.
	 */
	public Map<Position, Athlete> getActiveAthletes() {
		return activeAthletes;
	}

	/**
	 * Returns the Athletes that are reserves on the team.
	 * 
	 * @return Set of the team's reserve Athletes.
	 */
	public Set<Athlete> getReserveAthletes() {
		return reserveAthletes;
	}

	/**
	 * Returns all the Purchasables belinging to the team (Athletes)
	 * 
	 * @return Set of purchasables of all Athletes on the Team.
	 */
	public Supplier<Set<Purchasable>> getAllPurchasables = () -> {
		// Cast result of getAllAthletes to a set of purchasables
		Set<Athlete> allAthletes = getAllAthletes();
		return new HashSet<>(allAthletes);
	};

	/**
	 * Returns all the athleletes that are reserves or active on the team
	 * 
	 * @return Set of all athletes on the team
	 */
	public Set<Athlete> getAllAthletes() {
		Set<Athlete> allAthletes = new HashSet<Athlete>();
		for (Athlete athlete : getReserveAthletes()) {
			allAthletes.add(athlete);
		}
		for (Athlete athlete : getActiveAthletes().values()) {
			allAthletes.add(athlete);
		}
		return allAthletes;
	}

	/**
	 * Inserts a given Athlete at a given Position in the Team's activeAthletes.
	 * Also set's the Athlete's team property to this Team.
	 * 
	 * @param athlete  The Athlete to be added.
	 * @param position The Athlete's Position on the team.
	 */
	public void addAthleteToActive(Athlete athlete, Position position) {
		activeAthletes.put(position, athlete);
		athlete.setTeam(this);
	}

	/**
	 * Inserts a given Athlete into the Team's reserveAthletes. Also set's the
	 * Athlete's team property to this Team.
	 * 
	 * @param athlete The Athlete to be added.
	 */
	public boolean addAthleteToReserve(Athlete athlete) {
		if (reserveAthletes.size() < MAX_NUMBER_OF_RESERVES) {
			reserveAthletes.add(athlete);
			athlete.setTeam(this);
			return true;
		}
		return false;
	}

	/**
	 * Removes an athlete from the Team. Iterates through first the Team's
	 * activeAthletes then its reserveAthletes, removing the athlete from the
	 * corresponding container if it is found.
	 * 
	 * @param athlete The Athlete to be removed.
	 */
	public void removeAthlete(Athlete athlete) {
		// Look for athlete in activeAthletes.
		for (Position position : Position.values()) {
			if (activeAthletes.get(position) == athlete) {
				activeAthletes.remove(position);
				return;
			}
		}

		// Look for athlete in reserveAthletes.
		for (Athlete currentAthlete : reserveAthletes) {
			if (currentAthlete == athlete) {
				reserveAthletes.remove(currentAthlete);
				return;
			}
		}
	}

	/**
	 * Looks for an Athlete in the active Team. If found, the Athlete is swapped
	 * with an arbitrary Athlete from the reserves
	 * 
	 * @param athlete The Athlete to move.
	 */
	public boolean moveToReserve(Athlete athlete) {
		// Look for athlete in activeAthletes.
		for (Position position : Position.values()) {
			if (activeAthletes.get(position) == athlete) {
				if (reserveAthletes.size() > 0) {
					Athlete swapWith = reserveAthletes.iterator().next();
					activeAthletes.put(position, swapWith);
					reserveAthletes.remove(swapWith);
				} else
					activeAthletes.remove(position);
				reserveAthletes.add(athlete);
				return true;
			}
		}
		return false;
	}

	/**
	 * First removes the given Athlete from the Team if it is found, then adds it to
	 * the Team's activeAthletes.
	 * 
	 * @param athlete The Athlete to move.
	 */
	public void moveToActive(Athlete athlete, Position position) {
		// Look for athlete in reserveAthletes.
		for (Athlete currentAthlete : reserveAthletes) {
			if (currentAthlete == athlete) {
				reserveAthletes.remove(athlete);
				Athlete swapWith = activeAthletes.get(position);
				if (swapWith != null)
					reserveAthletes.add(swapWith);
				activeAthletes.put(position, athlete);
				return;
			}
		}

		for (Position currentPosition : Position.values()) {
			if (activeAthletes.get(currentPosition) == athlete) {
				Athlete swapWith = activeAthletes.get(position);
				if (swapWith != null)
					activeAthletes.put(currentPosition, swapWith);
				activeAthletes.put(position, athlete);
				return;
			}
		}
	}

	/**
	 * Generates a random Team name. Generated names combine random words from two
	 * word lists such that they are alliterative.
	 * 
	 * @param seed The random seed used for picking random words.
	 * @return A randomly generated Team name.
	 */
	private static String generateTeamName(Random rng) {
		return "The " + NameGenerator.generateName("teamNameList1", "teamNameList2", rng);
	}

	/**
	 * Creates a new Team and fills all positions and reserve slots with randomly
	 * generated Athletes, using the generateAthlete() static method of Athlete.
	 * 
	 * @param qualityLevel    A float in the range 0-1 inclusive. A higher
	 *                        qualityLevel will make Athletes more likely to get
	 *                        higher stats.
	 * @param seed            The random seed used for generating Athletes.
	 * @param gameEnvironment The GameEnvironment to which the Team will belong.
	 *                        This is needed so that each Athlete can access the
	 *                        gameEnvironment.
	 * @return A randomly generated team.
	 */
	public static Team generateTeam(int qualityLevel, GameEnvironment gameEnvironment) {
		Random rng = gameEnvironment.getRng();
		Team resultingTeam = new Team(gameEnvironment, generateTeamName(rng));

		// Clamp the quality level in range [0, 100]
		qualityLevel = MiscUtil.clampValue(qualityLevel);

		// Generate the activeAthletes.
		for (Position position : Position.values()) {
			Athlete currentAthlete = (Athlete) Athlete.generateAthlete.apply(qualityLevel, gameEnvironment);
			resultingTeam.addAthleteToActive(currentAthlete, position);
		}
		// Generate the reserveAthletes.
		for (int i = 0; i < MAX_NUMBER_OF_RESERVES; ++i) {
			Athlete currentAthlete = (Athlete) Athlete.generateAthlete.apply(qualityLevel, gameEnvironment);
			resultingTeam.addAthleteToReserve(currentAthlete);
		}

		return resultingTeam;
	}

	/**
	 * Returns the number of free spaces in the team's reserves. Calculates the
	 * total number of reserves - how many reserve athlete the team has.
	 * 
	 * @return the number of free spaces in the team's reserves
	 */
	public int getNumberOfFreeReserveSlots() {
		return MAX_NUMBER_OF_RESERVES - getReserveAthletes().size();
	}

	/**
	 * Triggers the athlete joins random event.
	 */
	public void triggerRandomEvents() {
		athleteJoinsRandomEvent.trigger();
	}
}
