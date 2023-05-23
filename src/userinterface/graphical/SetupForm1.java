package userinterface.graphical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import game.location.GameStart;

/**
 * A class that defines the first setup form to be displayed at the start of the
 * game. Consists of inputs for setting game preferences, including the team
 * name, game seed, season length and difficulty.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SetupForm1 extends GUILocation {
	/**
	 * Constructor for the SetupForm1 component. Provides the user with a graphical
	 * interface to choose game preferences before the game begins.
	 * 
	 * @param gameLocation   The GUI location's corresponding game location class.
	 * @param guiEnvironment The GUI environment to which the GUI location belongs.
	 */
	public SetupForm1(GameStart gameLocation, GUIEnvironment guiEnvironment, Runnable onSubmit) {
		super(guiEnvironment);

		setBounds(6, 64, 802, 500);
		setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("118px"), ColumnSpec.decode("231px"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(6dlu;default)"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("233px:grow"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("264px"), },
				new RowSpec[] { RowSpec.decode("51px"), RowSpec.decode("40px"), FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("54px"), FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("40px"),
						RowSpec.decode("41px"), FormSpecs.LINE_GAP_ROWSPEC, RowSpec.decode("40px"),
						FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("40px"), FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("40px"), FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, }));

		JTextField teamNameTextField;
		teamNameTextField = new JTextField();
		add(teamNameTextField, "6, 2, left, center");
		teamNameTextField.setColumns(10);

		JLabel teamNameLabel = new JLabel("Team Name");
		teamNameLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		teamNameLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		teamNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(teamNameLabel, "2, 2, right, center");
		teamNameLabel.setLabelFor(teamNameTextField);

		JSpinner seedSpinner = new JSpinner();
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(seedSpinner, "#");
		seedSpinner.setEditor(editor);
		seedSpinner.setValue((new Random(System.currentTimeMillis()).nextInt(10000)));
		seedSpinner.setPreferredSize(new Dimension(114, 22));
		seedSpinner.setMinimumSize(new Dimension(100, 22));
		add(seedSpinner, "6, 4, left, center");
		JLabel seedLabel = new JLabel("Seed");
		seedLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		seedLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		add(seedLabel, "2, 4, right, center");
		seedLabel.setLabelFor(seedSpinner);

		JLabel difficultySliderLabel = new JLabel("Difficulty");
		difficultySliderLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		add(difficultySliderLabel, "2, 6, right, top");

		JSlider difficultySlider = new JSlider();
		difficultySliderLabel.setLabelFor(difficultySlider);
		difficultySlider.setPaintTicks(true);
		difficultySlider.setSnapToTicks(true);
		difficultySlider.setMinorTickSpacing(1);
		difficultySlider.setMajorTickSpacing(1);
		difficultySlider.setMinimum(1);
		difficultySlider.setMaximum(3);
		difficultySlider.setValue(2);
		add(difficultySlider, "6, 6, fill, top");

		JPanel panel = new JPanel();
		panel.setForeground(Color.DARK_GRAY);
		panel.setBorder(null);
		panel.setPreferredSize(new Dimension(10, 5));
		add(panel, "6, 7, fill, fill");
		panel.setLayout(null);

		JLabel lblEasy = new JLabel("Easy");
		lblEasy.setForeground(Color.DARK_GRAY);
		lblEasy.setBounds(0, 0, 33, 15);
		panel.add(lblEasy);

		JLabel lblMedium = new JLabel("Medium");
		lblMedium.setForeground(Color.DARK_GRAY);
		lblMedium.setBounds(87, 0, 55, 15);
		panel.add(lblMedium);

		JLabel lblHard = new JLabel("Hard");
		lblHard.setForeground(Color.DARK_GRAY);
		lblHard.setBounds(199, 0, 34, 15);
		panel.add(lblHard);

		JLabel weeksLabel = new JLabel("Season Length (weeks)");
		weeksLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		weeksLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		add(weeksLabel, "2, 9, right, top");

		JSlider weeksSlider = new JSlider();
		weeksLabel.setLabelFor(weeksSlider);
		weeksSlider.setPaintTicks(true);
		weeksSlider.setSnapToTicks(true);
		weeksSlider.setMinorTickSpacing(1);
		weeksSlider.setMajorTickSpacing(5);
		weeksSlider.setPaintLabels(true);
		weeksSlider.setMinimum(5);
		weeksSlider.setMaximum(15);
		weeksSlider.setValue(10);
		add(weeksSlider, "6, 9, fill, top");

		JButton submitBtn = new JButton("Submit");
		submitBtn.setFont(new Font("Dialog", Font.BOLD, 16));
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String teamName = teamNameTextField.getText();
				// Make sure team name isn't empty.
				if (teamName.length() < 3 || 15 < teamName.length()) {
					guiEnvironment.displayPopup("Please enter a team name between 3 and 15 characters long.");
					return;
				}

				// Make sure the seed is a number.
				try {
					seedSpinner.commitEdit();
				} catch (java.text.ParseException exception) {
					guiEnvironment.displayPopup("Please enter a number for the seed.");
					return;
				}
				Integer seed = (Integer) seedSpinner.getValue();

				int difficulty = difficultySlider.getValue();
				int seasonLength = weeksSlider.getValue();

				// Pass the input values to the game location.
				gameLocation.setTeamName(teamName);
				gameLocation.setSeed(seed);
				gameLocation.setDifficulty(difficulty);
				gameLocation.setSeasonLength(seasonLength);

				onSubmit.run();
			}
		});
		add(submitBtn, "6, 13, left, fill");
	}
}
