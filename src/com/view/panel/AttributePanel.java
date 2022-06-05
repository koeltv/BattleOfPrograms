package com.view.panel;

import com.controller.GameController;
import com.model.*;
import com.view.ColorPalette;
import com.view.MainView;
import com.view.Resource;
import com.view.component.GraphicSoldier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.Arrays;

/**
 * Panel used to attribute stats to each soldier.
 */
public class AttributePanel extends JPanel {

	/**
	 * The constant serialVersionUID.
	 */
	@Serial
	private static final long serialVersionUID = 6364406918777401302L;

	/**
	 * The Soldier panel.
	 */
	private final BasePanel soldierPanel;
	/**
	 * The Reservist check box.
	 */
	private final JCheckBox reservistCheckBox;
	/**
	 * The Strength slider.
	 */
	private final JSlider strengthSlider;
	/**
	 * The Resistance slider.
	 */
	private final JSlider resistanceSlider;
	/**
	 * The Initiative slider.
	 */
	private final JSlider initiativeSlider;
	/**
	 * The Constitution slider.
	 */
	private final JSlider constitutionSlider;
	/**
	 * The Dexterity slider.
	 */
	private final JSlider dexteritySlider;
	/**
	 * The Defensive radio button.
	 */
	private final JRadioButton defensiveRadioButton;
	/**
	 * The Offensive radio button.
	 */
	private final JRadioButton offensiveRadioButton;
	/**
	 * The Random radio button.
	 */
	private final JRadioButton randomRadioButton;
	/**
	 * The Assignable points.
	 */
	private int assignablePoints = 400;
	/**
	 * The Current graphic soldier.
	 */
	private GraphicSoldier currentGraphicSoldier;
	/**
	 * The Current player index.
	 */
	private int currentPlayerIndex = 0;

	/**
	 * Create the panel.
	 */
	public AttributePanel() {
		GridLayout gridLayout = new GridLayout(1, 2);
		setLayout(gridLayout);

		soldierPanel = new BasePanel(Resource.CAMOUFLAGE.image.getImage());
		add(soldierPanel);
		soldierPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel statPanel = new JPanel();
		statPanel.setBackground(ColorPalette.BLUE_BACKGROUND.color);
		add(statPanel);
		GridBagLayout gbl_statPanel = new GridBagLayout();
		gbl_statPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_statPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_statPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_statPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		statPanel.setLayout(gbl_statPanel);

		JLabel reservistLabel = new JLabel("R\u00E9serviste ?");
		GridBagConstraints gbc_reservistLabel = new GridBagConstraints();
		gbc_reservistLabel.insets = new Insets(0, 0, 5, 5);
		gbc_reservistLabel.gridx = 0;
		gbc_reservistLabel.gridy = 0;
		statPanel.add(reservistLabel, gbc_reservistLabel);

		reservistCheckBox = new JCheckBox("0/5");
		GridBagConstraints gbc_reservistCheckBox = new GridBagConstraints();
		gbc_reservistCheckBox.anchor = GridBagConstraints.NORTHWEST;
		gbc_reservistCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_reservistCheckBox.gridx = 1;
		gbc_reservistCheckBox.gridy = 0;
		statPanel.add(reservistCheckBox, gbc_reservistCheckBox);
		reservistCheckBox.addActionListener(e -> {
			currentGraphicSoldier.setReservist(reservistCheckBox.isSelected());
			long numberOfReservist = Arrays.stream(GameController.getPlayers()[currentPlayerIndex].getSoldiers()).filter(Soldier::isReservist).count();
			reservistCheckBox.setEnabled(numberOfReservist < 5 || reservistCheckBox.isSelected());
			reservistCheckBox.setText(numberOfReservist + "/5");

			MainView.confirmButton.setEnabled(numberOfReservist >= 5);
		});

		JLabel strengthLabel = new JLabel("Force");
		GridBagConstraints gbc_strengthLabel = new GridBagConstraints();
		gbc_strengthLabel.insets = new Insets(0, 0, 5, 5);
		gbc_strengthLabel.gridx = 0;
		gbc_strengthLabel.gridy = 1;
		statPanel.add(strengthLabel, gbc_strengthLabel);

		strengthSlider = new JSlider();
		setupSlider(strengthSlider, 10, 10);
		GridBagConstraints gbc_strengthSlider = new GridBagConstraints();
		gbc_strengthSlider.anchor = GridBagConstraints.NORTH;
		gbc_strengthSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_strengthSlider.insets = new Insets(0, 0, 5, 5);
		gbc_strengthSlider.gridx = 1;
		gbc_strengthSlider.gridy = 1;
		statPanel.add(strengthSlider, gbc_strengthSlider);
		strengthSlider.addChangeListener(e -> {
			if (currentGraphicSoldier != null) {
				int difference = strengthSlider.getValue() - currentGraphicSoldier.getStrength();
				if (assignablePoints - difference >= 0 && currentGraphicSoldier.setStrength(currentGraphicSoldier.getStrength() + difference)) {
					updatePoints(difference);
				} else {
					strengthSlider.setValue(currentGraphicSoldier.getStrength());
				}
			}
		});

		JLabel resistanceLabel = new JLabel("R\u00E9sistance");
		GridBagConstraints gbc_resistanceLabel = new GridBagConstraints();
		gbc_resistanceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_resistanceLabel.gridx = 0;
		gbc_resistanceLabel.gridy = 2;
		statPanel.add(resistanceLabel, gbc_resistanceLabel);

		resistanceSlider = new JSlider();
		setupSlider(resistanceSlider, 10, 10);
		GridBagConstraints gbc_resistanceSlider = new GridBagConstraints();
		gbc_resistanceSlider.anchor = GridBagConstraints.NORTH;
		gbc_resistanceSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_resistanceSlider.insets = new Insets(0, 0, 5, 5);
		gbc_resistanceSlider.gridx = 1;
		gbc_resistanceSlider.gridy = 2;
		statPanel.add(resistanceSlider, gbc_resistanceSlider);
		resistanceSlider.addChangeListener(e -> {
			if (currentGraphicSoldier != null) {
				int difference = resistanceSlider.getValue() - currentGraphicSoldier.getResistance();
				if (assignablePoints - difference >= 0 && currentGraphicSoldier.setResistance(currentGraphicSoldier.getResistance() + difference)) {
					updatePoints(difference);
				} else {
					resistanceSlider.setValue(currentGraphicSoldier.getResistance());
				}
			}
		});

		JLabel initiativeLabel = new JLabel("Initiative");
		GridBagConstraints gbc_initiativeLabel = new GridBagConstraints();
		gbc_initiativeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_initiativeLabel.gridx = 0;
		gbc_initiativeLabel.gridy = 3;
		statPanel.add(initiativeLabel, gbc_initiativeLabel);

		initiativeSlider = new JSlider();
		setupSlider(initiativeSlider, 10, 10);
		GridBagConstraints gbc_initiativeSlider = new GridBagConstraints();
		gbc_initiativeSlider.anchor = GridBagConstraints.NORTH;
		gbc_initiativeSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_initiativeSlider.insets = new Insets(0, 0, 5, 5);
		gbc_initiativeSlider.gridx = 1;
		gbc_initiativeSlider.gridy = 3;
		statPanel.add(initiativeSlider, gbc_initiativeSlider);
		initiativeSlider.addChangeListener(e -> {
			if (currentGraphicSoldier != null) {
				int difference = initiativeSlider.getValue() - currentGraphicSoldier.getInitiative();
				if (assignablePoints - difference >= 0 && currentGraphicSoldier.setInitiative(currentGraphicSoldier.getInitiative() + difference)) {
					updatePoints(difference);
				} else {
					initiativeSlider.setValue(currentGraphicSoldier.getInitiative());
				}
			}
		});

		JLabel constitutionLabel = new JLabel("Constitution");
		GridBagConstraints gbc_constitutionLabel = new GridBagConstraints();
		gbc_constitutionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_constitutionLabel.gridx = 0;
		gbc_constitutionLabel.gridy = 4;
		statPanel.add(constitutionLabel, gbc_constitutionLabel);

		constitutionSlider = new JSlider();
		setupSlider(constitutionSlider, 30, 5);
		GridBagConstraints gbc_constitutionSlider = new GridBagConstraints();
		gbc_constitutionSlider.anchor = GridBagConstraints.NORTH;
		gbc_constitutionSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_constitutionSlider.gridwidth = 3;
		gbc_constitutionSlider.insets = new Insets(0, 0, 5, 0);
		gbc_constitutionSlider.gridx = 1;
		gbc_constitutionSlider.gridy = 4;
		statPanel.add(constitutionSlider, gbc_constitutionSlider);
		constitutionSlider.addChangeListener(e -> {
			if (currentGraphicSoldier != null) {
				int difference = constitutionSlider.getValue() - currentGraphicSoldier.getConstitution();
				if (assignablePoints - difference >= 0 && currentGraphicSoldier.setConstitution(currentGraphicSoldier.getConstitution() + difference)) {
					updatePoints(difference);
				} else {
					constitutionSlider.setValue(currentGraphicSoldier.getConstitution());
				}
			}
		});

		JLabel dexterityLabel = new JLabel("Dexterit\u00E9");
		GridBagConstraints gbc_dexterityLabel = new GridBagConstraints();
		gbc_dexterityLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dexterityLabel.gridx = 0;
		gbc_dexterityLabel.gridy = 5;
		statPanel.add(dexterityLabel, gbc_dexterityLabel);

		dexteritySlider = new JSlider();
		setupSlider(dexteritySlider, 10, 10);
		GridBagConstraints gbc_dexteritySlider = new GridBagConstraints();
		gbc_dexteritySlider.anchor = GridBagConstraints.NORTH;
		gbc_dexteritySlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_dexteritySlider.insets = new Insets(0, 0, 5, 5);
		gbc_dexteritySlider.gridx = 1;
		gbc_dexteritySlider.gridy = 5;
		statPanel.add(dexteritySlider, gbc_dexteritySlider);
		dexteritySlider.addChangeListener(e -> {
			if (currentGraphicSoldier != null) {
				int difference = dexteritySlider.getValue() - currentGraphicSoldier.getDexterity();
				if (assignablePoints - difference >= 0 && currentGraphicSoldier.setDexterity(currentGraphicSoldier.getDexterity() + difference)) {
					updatePoints(difference);
				} else {
					dexteritySlider.setValue(currentGraphicSoldier.getDexterity());
				}
			}
		});

		JLabel aiLabel = new JLabel("IA");
		GridBagConstraints gbc_aiLabel = new GridBagConstraints();
		gbc_aiLabel.insets = new Insets(0, 0, 0, 5);
		gbc_aiLabel.gridx = 0;
		gbc_aiLabel.gridy = 6;
		statPanel.add(aiLabel, gbc_aiLabel);

		JPanel aiSelectionPanel = new JPanel();
		aiSelectionPanel.setBackground(null);
		GridBagConstraints gbc_aiSelectionPanel = new GridBagConstraints();
		gbc_aiSelectionPanel.anchor = GridBagConstraints.WEST;
		gbc_aiSelectionPanel.gridwidth = 3;
		gbc_aiSelectionPanel.insets = new Insets(0, 0, 0, 5);
		gbc_aiSelectionPanel.gridx = 1;
		gbc_aiSelectionPanel.gridy = 6;
		statPanel.add(aiSelectionPanel, gbc_aiSelectionPanel);
		aiSelectionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		defensiveRadioButton = new JRadioButton("D\u00E9fensif");
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(defensiveRadioButton);
		aiSelectionPanel.add(defensiveRadioButton);
		defensiveRadioButton.addActionListener(l -> {
			if (defensiveRadioButton.isSelected()) currentGraphicSoldier.setAi(new DefensiveAI());
		});

		offensiveRadioButton = new JRadioButton("Offensif");
		buttonGroup.add(offensiveRadioButton);
		aiSelectionPanel.add(offensiveRadioButton);
		offensiveRadioButton.addActionListener(l -> {
			if (offensiveRadioButton.isSelected()) currentGraphicSoldier.setAi(new OffensiveAI());
		});

		randomRadioButton = new JRadioButton("Al\u00E9atoire");
		randomRadioButton.setSelected(true);
		buttonGroup.add(randomRadioButton);
		aiSelectionPanel.add(randomRadioButton);
		offensiveRadioButton.addActionListener(l -> {
			if (offensiveRadioButton.isSelected()) currentGraphicSoldier.setAi(new RandomAI());
		});

		reservistLabel.setForeground(ColorPalette.WHITE.color);
		strengthLabel.setForeground(ColorPalette.WHITE.color);
		resistanceLabel.setForeground(ColorPalette.WHITE.color);
		initiativeLabel.setForeground(ColorPalette.WHITE.color);
		constitutionLabel.setForeground(ColorPalette.WHITE.color);
		dexterityLabel.setForeground(ColorPalette.WHITE.color);
		aiLabel.setForeground(ColorPalette.WHITE.color);

		reservistCheckBox.setForeground(ColorPalette.WHITE.color);
		defensiveRadioButton.setForeground(ColorPalette.WHITE.color);
		offensiveRadioButton.setForeground(ColorPalette.WHITE.color);
		randomRadioButton.setForeground(ColorPalette.WHITE.color);

		reservistCheckBox.setBackground(null);
		defensiveRadioButton.setBackground(null);
		offensiveRadioButton.setBackground(null);
		randomRadioButton.setBackground(null);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				setupSoldiers();
				MainView.showPointLabel(true);
				MainView.setPlayerIndicator(GameController.getPlayers()[currentPlayerIndex]);
				MainView.showPlayerIndicator(true);
				MainView.confirmButton.setVisible(true);
				MainView.confirmButton.setEnabled(false);

				MainView.confirmButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for (Soldier soldier : GameController.getPlayers()[currentPlayerIndex].getSoldiers()) {
							if (soldier.getAi() == null) soldier.setAi(new RandomAI());
						}

						if (currentPlayerIndex < 1) {
							currentPlayerIndex++;
							soldierPanel.removeAll();

							resetPoints();
							currentGraphicSoldier = null;
							reservistCheckBox.setSelected(false);
							reservistCheckBox.setText("0/5");
							strengthSlider.setValue(0);
							resistanceSlider.setValue(0);
							initiativeSlider.setValue(0);
							constitutionSlider.setValue(0);
							dexteritySlider.setValue(0);
							randomRadioButton.doClick();

							setupSoldiers();
							MainView.confirmButton.setEnabled(false);
							MainView.setPlayerIndicator(GameController.getPlayers()[currentPlayerIndex]);
						} else {
							GameController.nextStep();
							MainView.showPointLabel(false);
							MainView.confirmButton.removeActionListener(this);
							MainView.addPanel(new FieldAttributionPanel(), PanelIdentifier.FIELD_ATTRIBUTION_PANEL);
							MainView.switchToPanel(PanelIdentifier.FIELD_ATTRIBUTION_PANEL);
						}
					}
				});

				if (GameController.displayTutorial()) {
					MainView.displayDialog("""
							Sur cette interface, tu peux assigner des points aux statistiques des différents soldats (voir F.R.I.C.D. ci-dessus).
														
							Pour choisir un soldat il suffit de cliquer dessus, tu pourras ensuite bouger les sliders pour changer ces stats.
														
							Tu as un indicateur en haut à gauche de la fenêtre pour savoir le nombre de points qu'il te reste.
														
							Attention ! Il te faut sélectionner 5 réservistes qui ne participerons pas à la 1ère bataille pour continuer !
							""", false);
				}
			}
		});
	}

	/**
	 * Set up a slider.
	 *
	 * @param slider           the slider
	 * @param maximum          the maximum value
	 * @param majorTickSpacing the major tick spacing
	 */
	private void setupSlider(JSlider slider, int maximum, int majorTickSpacing) {
		slider.setValue(0);
		slider.setMaximum(maximum);

		slider.setMajorTickSpacing(majorTickSpacing);
		slider.setMinorTickSpacing(1);

		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		if (maximum > 10) slider.setPaintTrack(true);

		slider.setForeground(ColorPalette.WHITE.color);
		slider.setBackground(null);
	}

	/**
	 * Update assignables points.
	 *
	 * @param difference the difference
	 */
	private void updatePoints(int difference) {
		assignablePoints -= difference;
		MainView.setPointsLeft(assignablePoints);
	}

	/**
	 * Set up the soldiers on the left side.
	 */
	private void setupSoldiers() {
		assignablePoints = 400;

		for (int i = 0; i < 20; i++) {
			Soldier soldier;
			if (i < 15) soldier = new Soldier();
			else if (i < 19) soldier = new EliteSoldier();
			else soldier = new WarMaster();

			GraphicSoldier graphicSoldier = GraphicSoldier.createGraphics(soldier);
			GameController.getPlayers()[currentPlayerIndex].getSoldiers()[i] = soldier;
			soldierPanel.add(graphicSoldier);

			if (i == 0) {
				currentGraphicSoldier = graphicSoldier;
				currentGraphicSoldier.setSelected(true);
			}

			graphicSoldier.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					currentGraphicSoldier.setSelected(false);
					currentGraphicSoldier = (GraphicSoldier) e.getComponent();
					currentGraphicSoldier.setSelected(true);

					reservistCheckBox.setSelected(currentGraphicSoldier.isReservist());
					long numberOfReservist = Arrays.stream(GameController.getPlayers()[currentPlayerIndex].getSoldiers()).filter(Soldier::isReservist).count();
					reservistCheckBox.setEnabled(numberOfReservist < 5 || reservistCheckBox.isSelected());

					strengthSlider.setValue(currentGraphicSoldier.getStrength());
					resistanceSlider.setValue(currentGraphicSoldier.getResistance());
					initiativeSlider.setValue(currentGraphicSoldier.getInitiative());
					constitutionSlider.setValue(currentGraphicSoldier.getConstitution());
					dexteritySlider.setValue(currentGraphicSoldier.getDexterity());

					if (currentGraphicSoldier.getAi() instanceof DefensiveAI) {
						defensiveRadioButton.doClick();
					} else if (currentGraphicSoldier.getAi() instanceof OffensiveAI) {
						offensiveRadioButton.doClick();
					} else {
						randomRadioButton.doClick();
					}
				}
			});
		}
	}

	/**
	 * Reset assignables points.
	 */
	private void resetPoints() {
		assignablePoints = 400;
		MainView.setPointsLeft(assignablePoints);
	}
}
