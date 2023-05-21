package test.gametest.itemtest;

import static org.junit.jupiter.api.Assertions.fail;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.GameEnvironment;
import userinterface.graphical.GUIEnvironment;

class BandaidTest {
	private GameEnvironment ge;

	@BeforeEach
	void setUp() {
		ge = new GameEnvironment(false);
		ge.setSeed(0);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) ge.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
