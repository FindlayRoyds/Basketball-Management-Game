package test.utiltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.Rule;
import org.junit.jupiter.api.Test;

import util.NameGenerator;

class NameGeneratorTest {
	@Rule

	@Test
	public void noSystemExit() {
		// passes
	}

	@Test
	void testGenerating() {
		Random rng = new Random();
		rng.setSeed(12);
		String athlete1 = NameGenerator.generateName("playerFirstNames", "playerLastNames", rng);
		String team1 = NameGenerator.generateName("teamNameList1", "teamNameList2", rng);
		assertTrue(athlete1 != null);
		assertTrue(team1 != null);
	}

	@Test
	void testIncorrectFileName() {
		Random rng = new Random();
		rng.setSeed(12);
		assertEquals("<name generation error>", NameGenerator.generateName("fake", "playerLastNames", rng));
		assertEquals("<name generation error>", NameGenerator.generateName("playerFirstNames", "fake", rng));
		assertEquals("<name generation error>", NameGenerator.generateName("fake", "fake", rng));
		assertEquals("<name generation error>", NameGenerator.generateName("teamNameList1", "fake", rng));
	}

}
