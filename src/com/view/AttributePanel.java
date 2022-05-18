package com.view;

import com.model.*;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;
import java.net.URL;
import java.util.Arrays;

public class AttributePanel extends JPanel {

	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = 6364406918777401302L;

	private final BasePanel soldierPanel;

	private int assignablePoints = 400;

	private GraphicSoldier currentGraphicSoldier;


	private final JCheckBox reservistCheckBox;
	private final JSlider strengthSlider;
	private final JSlider resistanceSlider;
	private final JSlider initiativeSlider;
	private final JSlider constitutionSlider;
	private final JSlider dexteritySlider;
	private final JRadioButton defensiveRadioButton;
	private final JRadioButton offensiveRadioButton;
	private final JRadioButton randomRadioButton;

	/**
	 * Create the panel.
	 */
	public AttributePanel() {
		GridLayout gridLayout = new GridLayout(1, 2);
		setLayout(gridLayout);

		soldierPanel = new BasePanel();
		soldierPanel.changeBackground(BasePanel.class.getResource("/images/camo.jpg"));
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
			currentGraphicSoldier.soldier.setReservist(reservistCheckBox.isSelected());
			long numberOfReservist = Arrays.stream(GameController.players[0].soldiers).filter(Soldier::isReservist).count();
			reservistCheckBox.setEnabled(numberOfReservist < 5  || reservistCheckBox.isSelected());
			reservistCheckBox.setText(numberOfReservist + "/5");
		});

		JLabel strengthLabel = new JLabel("Force");
		GridBagConstraints gbc_strengthLabel = new GridBagConstraints();
		gbc_strengthLabel.insets = new Insets(0, 0, 5, 5);
		gbc_strengthLabel.gridx = 0;
		gbc_strengthLabel.gridy = 1;
		statPanel.add(strengthLabel, gbc_strengthLabel);

		strengthSlider = new JSlider();
		strengthSlider.setPaintLabels(true);
		strengthSlider.setMinorTickSpacing(1);
		strengthSlider.setMajorTickSpacing(10);
		strengthSlider.setValue(0);
		strengthSlider.setPaintTicks(true);
		strengthSlider.setSnapToTicks(true);
		strengthSlider.setMaximum(10);
		GridBagConstraints gbc_strengthSlider = new GridBagConstraints();
		gbc_strengthSlider.anchor = GridBagConstraints.NORTH;
		gbc_strengthSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_strengthSlider.insets = new Insets(0, 0, 5, 5);
		gbc_strengthSlider.gridx = 1;
		gbc_strengthSlider.gridy = 1;
		statPanel.add(strengthSlider, gbc_strengthSlider);
		strengthSlider.addChangeListener(e -> {
			int difference = strengthSlider.getValue() - currentGraphicSoldier.soldier.getStrength();
			if (assignablePoints - difference >= 0 && currentGraphicSoldier.soldier.setStrength(currentGraphicSoldier.soldier.getStrength() + difference)) {
				updatePoints(difference);
			} else {
				strengthSlider.setValue(currentGraphicSoldier.soldier.getStrength());
			}
		});

		JLabel resistanceLabel = new JLabel("R\u00E9sistance");
		GridBagConstraints gbc_resistanceLabel = new GridBagConstraints();
		gbc_resistanceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_resistanceLabel.gridx = 0;
		gbc_resistanceLabel.gridy = 2;
		statPanel.add(resistanceLabel, gbc_resistanceLabel);

		resistanceSlider = new JSlider();
		resistanceSlider.setPaintLabels(true);
		resistanceSlider.setMajorTickSpacing(10);
		resistanceSlider.setMinorTickSpacing(1);
		resistanceSlider.setValue(0);
		resistanceSlider.setPaintTicks(true);
		resistanceSlider.setSnapToTicks(true);
		resistanceSlider.setMaximum(10);
		GridBagConstraints gbc_resistanceSlider = new GridBagConstraints();
		gbc_resistanceSlider.anchor = GridBagConstraints.NORTH;
		gbc_resistanceSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_resistanceSlider.insets = new Insets(0, 0, 5, 5);
		gbc_resistanceSlider.gridx = 1;
		gbc_resistanceSlider.gridy = 2;
		statPanel.add(resistanceSlider, gbc_resistanceSlider);
		resistanceSlider.addChangeListener(e -> {
			int difference = resistanceSlider.getValue() - currentGraphicSoldier.soldier.getResistance();
			if (assignablePoints - difference >= 0 && currentGraphicSoldier.soldier.setResistance(currentGraphicSoldier.soldier.getResistance() + difference)) {
				updatePoints(difference);
			} else {
				resistanceSlider.setValue(currentGraphicSoldier.soldier.getResistance());
			}
		});

		JLabel initiativeLabel = new JLabel("Initiative");
		GridBagConstraints gbc_initiativeLabel = new GridBagConstraints();
		gbc_initiativeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_initiativeLabel.gridx = 0;
		gbc_initiativeLabel.gridy = 3;
		statPanel.add(initiativeLabel, gbc_initiativeLabel);

		initiativeSlider = new JSlider();
		initiativeSlider.setPaintLabels(true);
		initiativeSlider.setMajorTickSpacing(10);
		initiativeSlider.setMinorTickSpacing(1);
		initiativeSlider.setPaintTicks(true);
		initiativeSlider.setSnapToTicks(true);
		initiativeSlider.setValue(0);
		initiativeSlider.setMaximum(10);
		GridBagConstraints gbc_initiativeSlider = new GridBagConstraints();
		gbc_initiativeSlider.anchor = GridBagConstraints.NORTH;
		gbc_initiativeSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_initiativeSlider.insets = new Insets(0, 0, 5, 5);
		gbc_initiativeSlider.gridx = 1;
		gbc_initiativeSlider.gridy = 3;
		statPanel.add(initiativeSlider, gbc_initiativeSlider);
		initiativeSlider.addChangeListener(e -> {
			int difference = initiativeSlider.getValue() - currentGraphicSoldier.soldier.getInitiative();
			if (assignablePoints - difference >= 0 && currentGraphicSoldier.soldier.setInitiative(currentGraphicSoldier.soldier.getInitiative() + difference)) {
				updatePoints(difference);
			} else {
				initiativeSlider.setValue(currentGraphicSoldier.soldier.getInitiative());
			}
		});

		JLabel constitutionLabel = new JLabel("Constitution");
		GridBagConstraints gbc_constitutionLabel = new GridBagConstraints();
		gbc_constitutionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_constitutionLabel.gridx = 0;
		gbc_constitutionLabel.gridy = 4;
		statPanel.add(constitutionLabel, gbc_constitutionLabel);

		constitutionSlider = new JSlider();
		constitutionSlider.setMajorTickSpacing(5);
		constitutionSlider.setMinorTickSpacing(1);
		constitutionSlider.setPaintLabels(true);
		constitutionSlider.setPaintTrack(true);
		constitutionSlider.setValue(0);
		constitutionSlider.setSnapToTicks(true);
		constitutionSlider.setPaintTicks(true);
		constitutionSlider.setMaximum(30);
		GridBagConstraints gbc_constitutionSlider = new GridBagConstraints();
		gbc_constitutionSlider.anchor = GridBagConstraints.NORTH;
		gbc_constitutionSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_constitutionSlider.gridwidth = 3;
		gbc_constitutionSlider.insets = new Insets(0, 0, 5, 0);
		gbc_constitutionSlider.gridx = 1;
		gbc_constitutionSlider.gridy = 4;
		statPanel.add(constitutionSlider, gbc_constitutionSlider);
		constitutionSlider.addChangeListener(e -> {
			int difference = constitutionSlider.getValue() - currentGraphicSoldier.soldier.getConstitution();
			if (assignablePoints - difference >= 0 && currentGraphicSoldier.soldier.setConstitution(currentGraphicSoldier.soldier.getConstitution() + difference)) {
				updatePoints(difference);
			} else {
				constitutionSlider.setValue(currentGraphicSoldier.soldier.getConstitution());
			}
		});

		JLabel dexterityLabel = new JLabel("Dexterit\u00E9");
		GridBagConstraints gbc_dexterityLabel = new GridBagConstraints();
		gbc_dexterityLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dexterityLabel.gridx = 0;
		gbc_dexterityLabel.gridy = 5;
		statPanel.add(dexterityLabel, gbc_dexterityLabel);

		dexteritySlider = new JSlider();
		dexteritySlider.setPaintLabels(true);
		dexteritySlider.setMajorTickSpacing(10);
		dexteritySlider.setMinorTickSpacing(1);
		dexteritySlider.setValue(0);
		dexteritySlider.setPaintTicks(true);
		dexteritySlider.setSnapToTicks(true);
		dexteritySlider.setMaximum(10);
		GridBagConstraints gbc_dexteritySlider = new GridBagConstraints();
		gbc_dexteritySlider.anchor = GridBagConstraints.NORTH;
		gbc_dexteritySlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_dexteritySlider.insets = new Insets(0, 0, 5, 5);
		gbc_dexteritySlider.gridx = 1;
		gbc_dexteritySlider.gridy = 5;
		statPanel.add(dexteritySlider, gbc_dexteritySlider);
		dexteritySlider.addChangeListener(e -> {
			int difference = dexteritySlider.getValue() - currentGraphicSoldier.soldier.getDexterity();
			if (assignablePoints - difference >= 0 && currentGraphicSoldier.soldier.setDexterity(currentGraphicSoldier.soldier.getDexterity() + difference)) {
				updatePoints(difference);
			} else {
				dexteritySlider.setValue(currentGraphicSoldier.soldier.getDexterity());
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
			if (defensiveRadioButton.isSelected()) currentGraphicSoldier.soldier.setAi(new DefensiveAI());
		});

		offensiveRadioButton = new JRadioButton("Offensif");
		buttonGroup.add(offensiveRadioButton);
		aiSelectionPanel.add(offensiveRadioButton);
		offensiveRadioButton.addActionListener(l -> {
			if (offensiveRadioButton.isSelected()) currentGraphicSoldier.soldier.setAi(new OffensiveAI());
		});

		randomRadioButton = new JRadioButton("Al\u00E9atoire");
		randomRadioButton.setSelected(true);
		buttonGroup.add(randomRadioButton);
		aiSelectionPanel.add(randomRadioButton);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				System.out.println("Now showing !");
				setupSoldiers();
				MainView.pointLabel.setVisible(true);
				MainView.confirmButton.setVisible(true);
			}
		});
	}

	private void updatePoints(int difference) {
		assignablePoints -= difference;
		MainView.pointLabel.setText("Points \u00E0 assigner : " + assignablePoints + " pts");
	}

	public void setupSoldiers() {
		URL soldierUrl = AttributePanel.class.getResource("/images/soldier.png");
		URL eliteSoldierUrl = AttributePanel.class.getResource("/images/elite_soldier.png");
		URL warMasterUrl = AttributePanel.class.getResource("/images/commander.png");

		if (soldierUrl != null && eliteSoldierUrl != null && warMasterUrl != null) {
			for (int i = 0; i < 20; i++) {
				GraphicSoldier graphicSoldier;
				if (i < 15) {
					graphicSoldier = new GraphicSoldier(new Soldier());
				} else if (i < 19) {
					graphicSoldier = new GraphicSoldier(new EliteSoldier());
				} else {
					graphicSoldier = new GraphicSoldier(new WarMaster());
				}

				GameController.players[0].soldiers[i] = graphicSoldier.soldier;
				soldierPanel.add(graphicSoldier);

				if (i == 0) {
					currentGraphicSoldier = graphicSoldier;
					currentGraphicSoldier.soldier = graphicSoldier.soldier;
				}

				graphicSoldier.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (currentGraphicSoldier.soldier != null) currentGraphicSoldier.setBackground(null);
						currentGraphicSoldier = (GraphicSoldier) e.getComponent();
						graphicSoldier.setBackground(Color.RED);

						reservistCheckBox.setSelected(currentGraphicSoldier.soldier.isReservist());
						long numberOfReservist = Arrays.stream(GameController.players[0].soldiers).filter(Soldier::isReservist).count();
						reservistCheckBox.setEnabled(numberOfReservist < 5 || reservistCheckBox.isSelected());

						strengthSlider.setValue(currentGraphicSoldier.soldier.getStrength());
						resistanceSlider.setValue(currentGraphicSoldier.soldier.getResistance());
						initiativeSlider.setValue(currentGraphicSoldier.soldier.getInitiative());
						constitutionSlider.setValue(currentGraphicSoldier.soldier.getConstitution());
						dexteritySlider.setValue(currentGraphicSoldier.soldier.getDexterity());

						if (currentGraphicSoldier.soldier.ai instanceof DefensiveAI) {
							defensiveRadioButton.doClick();
						} else if (currentGraphicSoldier.soldier.ai instanceof OffensiveAI) {
							offensiveRadioButton.doClick();
						} else {
							randomRadioButton.doClick();
						}
					}
				});
			}
		}

		MainView.confirmButton.addActionListener(e -> {
			for (Soldier soldier : GameController.players[0].soldiers) {
				if (soldier.getAi() == null) soldier.setAi(Math.random() > 0.5 ? new OffensiveAI() : new DefensiveAI());
			}

			//TODO Pass to next player, then next panel
		});
	}

}