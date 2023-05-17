package game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Defines miscellaneous utilities for use throughout the game module.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
public class Utils {
	/**
	 * Reads lines (words) from a file and returns them in a 2D ArrayList format,
	 * where result[i] contains all lines in the file starting with the ith
	 * letter of the alphabet.
	 * The file must be located within the /src/game/nameGenerationWords/ directory
	 * and all lines must start with a capital letter.
	 * 
	 * @param filename		The name of the text file to be read.
	 * 						Files are accessed using the following filepath:
	 * 						"src/game/nameGenerationWords/{filename}.txt"
	 * @return				2D ArrayList of words, where the ith entry is an
	 * 						ArrayList containing all words beginning with the
	 * 						ith letter of the alphabet (capitalized).
	 */
	private static ArrayList<ArrayList<String>> readWordList(String filename) {
		final String WORD_LIST_DIR = "src/game/nameGenerationWords/";
		String filepath = WORD_LIST_DIR + filename + ".txt";

		// Build the empty result 2D array
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>(26);
		for (int i = 0; i <= 'Z'-'A'; ++i) {
			result.add(new ArrayList<String>(1));
		}

		// Attempt to read lines from the file and add them to the result.
		try {
			List<String> lines = Files.readAllLines(Paths.get(filepath));
			for (String line: lines) {
				result.get(line.charAt(0)-'A').add(line);
			}
			return result;
		} catch (IOException e) {
			System.out.println("File " + filepath + " may not exist.");
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * Generates 2 word alliterative names, where the first word is randomly chosen 
	 * from the file wordList1 and the second word from the file wordList2.
	 * wordList1 and wordList2 must be located within /src/game/nameGenerationWords/
	 * with each word on a new line, starting with a capital letter.
	 * 
	 * @param wordList1			The filename to choose the first word from.
	 * @param wordList2			The filename to choose the second word from.
	 * @param rng				A Random object used for random number generation.
	 * @return					An alliterative name in the format "word1 word1"
	 */
	static String generateName(String wordList1, String wordList2, Random rng) {
		ArrayList<ArrayList<String>> words1 = readWordList(wordList1);
		ArrayList<ArrayList<String>> words2 = readWordList(wordList2);

		// pick a random letter from the alphabet for the words to start with
		int startLetterIndex = rng.nextInt(26);
		// get all words from the two lists that start with that letter
		ArrayList<String> word1Candidates = words1.get(startLetterIndex);
		ArrayList<String> word2Candidates = words2.get(startLetterIndex);
		// randomly pick a word from each
		String word1 = word1Candidates.get(rng.nextInt(word1Candidates.size()));
		String word2 = word2Candidates.get(rng.nextInt(word2Candidates.size()));
		
		return word1 + " " + word2;
	}
	
	/**
	 * Clamp an integer between the values 0 and 100 (inclusive)
	 * Used for setting an athletes statistic or stamina
	 * 
	 * @param value					the integer value to be clamped
	 * @return 						the integer value clamped between 0 and 100
	 */
	public static int clampValue(int value) {
		return Math.max(0, Math.min(100, value));
	}
}
