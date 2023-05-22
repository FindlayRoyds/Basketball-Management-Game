package test.utiltest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

import util.NameGenerator;

class NameGeneratorTest {

	@Test
	void test() {
		Random rng = new Random();
		rng.setSeed(12);
		String athlete1 = NameGenerator.generateName("playerFirstNames", "playerLastNames", rng);
		String team1 = NameGenerator.generateName("teamNameList1", "teamNameList2", rng);
		assertTrue(athlete1 != null);
		assertTrue(team1 != null);
	}

}
