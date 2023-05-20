package userinterface.graphical;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import game.location.GameLocation;
import game.location.GameStart;

public class GUIStart extends GUILocation {
	private static final long serialVersionUID = 1L;
	private GameStart gameLocation;

	private JTextField teamNameTextField;
	private JSpinner seedSpinner;
	private JPanel formPanel1, formPanel2;

	private void setupFormPanel1() {
		formPanel1 = new JPanel();
		formPanel1.setBounds(12, 64, 764, 504);
		add(formPanel1);
		formPanel1.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("58px"), ColumnSpec.decode("102px"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("146px"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("114px"), },
				new RowSpec[] { FormSpecs.LINE_GAP_ROWSPEC, RowSpec.decode("21px"), FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel teamNameLabel = new JLabel("Team Name");
		teamNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		formPanel1.add(teamNameLabel, "2, 2, right, center");

		teamNameTextField = new JTextField();
		teamNameLabel.setLabelFor(teamNameTextField);
		formPanel1.add(teamNameTextField, "4, 2, left, top");
		teamNameTextField.setColumns(10);

		JLabel seedLabel = new JLabel("Seed");
		formPanel1.add(seedLabel, "2, 4, right, default");

		seedSpinner = new JSpinner();
		seedSpinner.setPreferredSize(new Dimension(114, 22));
		seedSpinner.setMinimumSize(new Dimension(100, 22));
		seedLabel.setLabelFor(seedSpinner);
		formPanel1.add(seedSpinner, "4, 4, left, top");

		JLabel difficultyLabel = new JLabel("Difficulty");
		formPanel1.add(difficultyLabel, "2, 6, right, default");

		JSlider difficultySlider = new JSlider();
		difficultyLabel.setLabelFor(difficultySlider);
		difficultySlider.setPaintTicks(true);
		difficultySlider.setSnapToTicks(true);
		difficultySlider.setMinorTickSpacing(1);
		difficultySlider.setMajorTickSpacing(1);
		difficultySlider.setMinimum(1);
		difficultySlider.setMaximum(3);
		formPanel1.add(difficultySlider, "4, 6");

		JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String teamName = teamNameTextField.getText();
				// Make sure team name isn't empty.
				if (teamName.length() == 0) {
					guiEnvironment.displayPopup("Please enter a team name");
					return;
				}

				// Check if the seed is a number.
				try {
					seedSpinner.commitEdit();
				} catch (java.text.ParseException exception) {
					guiEnvironment.displayPopup("Please enter a number for the seed.");
					return;
				}
				Integer seed = (Integer) seedSpinner.getValue();

				int difficulty = difficultySlider.getValue();

				// Pass the input values to the game location.
				gameLocation.setTeamName(teamName);
				gameLocation.setSeed(seed);
				gameLocation.setDifficulty(difficulty);

				// Hide the first panel and show the second.
				formPanel1.setVisible(false);
				formPanel2.setVisible(true);
			}
		});
		formPanel1.add(submitBtn, "4, 10");
	}

	private void setupFormPanel2() {
		formPanel2 = new JPanel();
		formPanel2.setBounds(12, 64, 764, 504);
		add(formPanel2);

		JLabel lblNewLabel = new JLabel("Team Setup");
		formPanel2.add(lblNewLabel);
	}

	/**
	 * Create the panel.
	 */
	public GUIStart(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		this.gameLocation = (GameStart) gameLocation;

		JLabel startTitle = new JLabel("Game Setup");
		startTitle.setFont(new Font("Dialog", Font.BOLD, 16));
		startTitle.setBounds(169, 25, 107, 17);
		startTitle.setHorizontalAlignment(SwingConstants.CENTER);
		add(startTitle);

		setupFormPanel1();
		setupFormPanel2();
		formPanel1.setVisible(true);
		formPanel2.setVisible(false);
	}

}
