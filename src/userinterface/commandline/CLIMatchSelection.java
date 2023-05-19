package userinterface.commandline;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import game.location.GameLocation;
import game.location.GameMatchSelection;
import game.Team;
import enumeration.Location;

/**
 * The command line match selection class.
 * Allows the user to view and select teams to play against.
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class CLIMatchSelection extends CLILocation {
	/**
	 * The game location this cli location is linked to.
	 */
	private GameMatchSelection gameLocation;
	
	/**
	 * @param gameLocation					The game location this cli location is linked to.
	 */
	public CLIMatchSelection(GameLocation gameLocation, CLIEnvironment cliEnvironment) {
		super(cliEnvironment);
		this.gameLocation = (GameMatchSelection) gameLocation;
	}

	@Override
	public Location display() {
		System.out.println("Stadium");
		
		ArrayList<Team> availableMatches = gameLocation.getTeams();
		List<String> teamNames = availableMatches.stream()
				.map(team -> team.getName())
				.collect(Collectors.toList());
		teamNames.add("Exit to map");
		int selectedOption = cliEnvironment.displayOptions(teamNames.toArray(new String[0]));
		
		// Test if user selected "Exit to map".
		if (selectedOption == teamNames.size() - 1) {
			return Location.MAP;
		}
		
		// Start a match against the selected team.
		gameLocation.playMatch(availableMatches.get(selectedOption));
		
		// Return to map after the match.
		return Location.MAP;
	}
}
