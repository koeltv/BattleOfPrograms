package com.view.panel;

import com.model.Player;
import com.view.MainView;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;

/**
 * Panel used to get players' information.
 */
public class PlayerInfoPanel extends BasePanel {
	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = 6378665743080841415L;
	
	private final JTextField nameField;

	private final JButton btnConfirm;

	private final JList<String> programList;

	private int playerId = 0;

	/**
	 * Create the panel.
	 */
	public PlayerInfoPanel() {
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnConfirm = new JButton("Confirm");
		btnConfirm.setEnabled(false);
		panel.add(btnConfirm);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{36, 119, 289, 0};
		gbl_panel_1.rowHeights = new int[]{36, 19, 36, 105, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);

		JLabel nameLabel = new JLabel("Entrez le nom du joueur 1");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.WEST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 1;
		gbc_nameLabel.gridy = 1;
		panel_1.add(nameLabel, gbc_nameLabel);

		String[] programs = {"ISI", "RT", "A2I", "GI", "GM", "MTE", "MM"};

		nameField = new JTextField();
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.anchor = GridBagConstraints.NORTH;
		gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameField.insets = new Insets(0, 0, 5, 0);
		gbc_nameField.gridx = 2;
		gbc_nameField.gridy = 1;
		panel_1.add(nameField, gbc_nameField);
		nameField.setColumns(10);
		nameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) { checkInformations();}
		});

		JLabel programLabel = new JLabel("Choisissez un programme");
		GridBagConstraints gbc_programLabel = new GridBagConstraints();
		gbc_programLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_programLabel.insets = new Insets(0, 0, 0, 5);
		gbc_programLabel.gridx = 1;
		gbc_programLabel.gridy = 3;
		panel_1.add(programLabel, gbc_programLabel);
		//noinspection rawtypes,unchecked
		programList = new JList(programs);
		programList.addListSelectionListener(e -> checkInformations());
		programList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		GridBagConstraints gbc_programList = new GridBagConstraints();
		gbc_programList.anchor = GridBagConstraints.NORTH;
		gbc_programList.fill = GridBagConstraints.HORIZONTAL;
		gbc_programList.gridx = 2;
		gbc_programList.gridy = 3;
		panel_1.add(programList, gbc_programList);

		setOpaque(false);
		panel.setOpaque(false);
		panel_1.setOpaque(false);

		btnConfirm.addActionListener(e -> {
			GameController.players[playerId] = new Player(nameField.getText().equals("") ? "J" + (playerId + 1) : nameField.getText(), programList.getSelectedValue());
			if(++playerId == 1) {
				nameField.setText(null);
				btnConfirm.setEnabled(false);
				programList.setSelectedIndex(0);
				nameLabel.setText("Entrer le nom du joueur " + (playerId + 1));
			} else if (playerId > 1) {
				MainView.switchToPanel(PanelIdentifier.ATTRIBUTE_PANEL);
			}
		});

	}

	public void checkInformations() {
		btnConfirm.setEnabled(playerId < 1 || !programList.getSelectedValue().equals(GameController.players[0].program));
	}

}
