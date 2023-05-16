package game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import enumeration.Position;


/**
 * This class defines a team made up of Athletes,
 * which can be active players or reserves.
 * Active players each occupy a different position on the team.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
public class Team {
	/**
	 * Map of Positions to the athlete that has been assigned that position on the Team.
	 */
	private HashMap<Position, Athlete> activeAthletes;

	/**
	 * Set of Athletes who are reserves in the Team.
	 */
	private HashSet<Athlete> reserveAthletes;

	/**
	 * Name of the Team.
	 */
	private String name;

	/**
	 * The maximum number of reserve athletes permitted on a team.
	 */
	private final static int MAX_NUMBER_OF_RESERVES = 4;
	
	/**
	 * Initializes an empty team with the given team name.
	 * 
	 * @param teamName		The name of the team to be created.
	 */
	public Team(String teamName) {
		name = teamName;
	}
	
	/**
	 * Returns the Team name, which is a String
	 * 
	 * @return		A String: the Team name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns a Map containing all active Athletes organized by their positions.
	 * 
	 * @return		Map of Positions to the Athlete that has been assigned that Position.
	 */
	public HashMap<Position, Athlete> getActiveAthletes() {
		return activeAthletes;
	}

	/**
	 * Returns the Athletes that are reserves on the team.
	 * 
	 * @return		Set of the team's reserve Athletes.
	 */
	public HashSet<Athlete> getReserveAthletes() {
		return reserveAthletes;
	}

	/**
	 * Inserts a given Athlete at a given Position in the Team's activeAthletes.
	 * Also set's the Athlete's team property to this Team.
	 * 
	 * @param athlete		The Athlete to be added.
	 * @param position		The Athlete's Position on the team.
	 */
	public void addAthleteToActive(Athlete athlete, Position position) {
		activeAthletes.put(position, athlete);
		athlete.setTeam(this);
	}

	/**
	 * Inserts a given Athlete into the Team's reserveAthletes.
	 * Also set's the Athlete's team property to this Team.
	 * 
	 * @param athlete		The Athlete to be added.
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
	 * Removes an athlete from the Team.
	 * Iterates through first the Team's activeAthletes then its reserveAthletes,
	 * removing the athlete from the corresponding container if it is found.
	 * 
	 * @param athlete		The Athlete to be removed.
	 */
	public void removeAthlete(Athlete athlete) {
		// Look for athlete in activeAthletes.
		for (Position position: Position.values()) {
			if (activeAthletes.get(position) == athlete) {
				activeAthletes.remove(position);
			}
		}
		
		// Look for athlete in reserveAthletes.
		for (Athlete currentAthlete: reserveAthletes) {
			if (currentAthlete == athlete) {
				reserveAthletes.remove(currentAthlete);
			}
		}
	}

	/**
	 * First removes the given Athlete from the Team if it is found, 
	 * then adds it to the Team's reserveAthletes.
	 * 
	 * @param athlete		The Athlete to move.
	 */
	public boolean moveToReserve(Athlete athlete) {
		removeAthlete(athlete);
		return addAthleteToReserve(athlete);
	}

	/**
	 * First removes the given Athlete from the Team if it is found,
	 * then adds it to the Team's activeAthletes.
	 * 
	 * @param athlete		The Athlete to move.
	 */
	public void moveToActive(Athlete athlete, Position position) {
		removeAthlete(athlete);
		addAthleteToActive(athlete, position);
	}

	/**
	 * Generates a random Team name.
	 * Generated names combine random words from two word lists such that they are alliterative.
	 * 
	 * @param seed		The random seed used for picking random words.
	 * @return			A randomly generated Team name.
	 */
	private static String generateTeamName(Random rng) {
		return "The " + Utils.generateName("teamNameList1", "teamNameList2", rng);
	}
	
	/**
	 * Creates a new Team and fills all positions and reserve slots with
	 * randomly generated Athletes, using the generateAthlete() static method of Athlete.
	 * 
	 * @param qualityLevel		A float in the range 0-1 inclusive.
	 * 							A higher qualityLevel will make Athletes more likely to get higher stats.
	 * @param seed				The random seed used for generating Athletes.
	 * @param gameEnvironment	The GameEnvironment to which the Team will belong.
	 * 							This is needed so that each Athlete can access the gameEnvironment.
	 * @return					A randomly generated team.
	 */
	public static Team generateTeam(int qualityLevel, Random rng, GameEnvironment gameEnvironment) {
		Team resultingTeam = new Team(generateTeamName(rng));

		// Generate the activeAthletes.
		for (Position position: Position.values()) {
			Athlete currentAthlete = Athlete.generateAthlete(qualityLevel, rng, resultingTeam, gameEnvironment);
			resultingTeam.addAthleteToActive(currentAthlete, position);
		}
		// Generate the reserveAthletes.
		for (int i = 0; i < MAX_NUMBER_OF_RESERVES; ++i) {
			Athlete currentAthlete = Athlete.generateAthlete(qualityLevel, rng, resultingTeam, gameEnvironment);
			resultingTeam.addAthleteToReserve(currentAthlete);
		}

		return resultingTeam;
	}
}
