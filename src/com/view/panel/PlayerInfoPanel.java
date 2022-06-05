package com.view.panel;

import com.controller.GameController;
import com.model.Player;
import com.view.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

/**
 * Panel used to get players' information.
 */
public class PlayerInfoPanel extends BasePanel {
	/**
	 * The constant serialVersionUID.
	 */
	@Serial
	private static final long serialVersionUID = 6378665743080841415L;

	/**
	 * The Name field.
	 */
	private final JTextField[] nameFields = new JTextField[2];

	/**
	 * The Program list.
	 */
	@SuppressWarnings("unchecked")
	private final JList<String>[] programLists = new JList[2];

	/**
	 * Create the panel.
	 */
	public PlayerInfoPanel() {
		setLayout(new GridLayout(1, 2));
		setAlpha(0.4f);

		MainView.confirmButton.setVisible(true);
		MainView.confirmButton.setEnabled(false);

		String[] programs = {"ISI", "RT", "A2I", "GI", "GM", "MTE", "MM"};

		for (int i = 0, size = GameController.getPlayers().length; i < size; i++) {
			JPanel playerPanel = new JPanel();
			add(playerPanel);
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			gbl_panel_1.columnWidths = new int[]{36, 119, 289, 0};
			gbl_panel_1.rowHeights = new int[]{36, 19, 36, 105, 0};
			gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			playerPanel.setLayout(gbl_panel_1);

			JLabel nameLabel = new JLabel("Entrez le nom du joueur " + (i + 1));
			GridBagConstraints gbc_nameLabel = new GridBagConstraints();
			gbc_nameLabel.anchor = GridBagConstraints.WEST;
			gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
			gbc_nameLabel.gridx = 1;
			gbc_nameLabel.gridy = 1;
			playerPanel.add(nameLabel, gbc_nameLabel);

			nameFields[i] = new JTextField();
			GridBagConstraints gbc_nameField = new GridBagConstraints();
			gbc_nameField.anchor = GridBagConstraints.NORTH;
			gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
			gbc_nameField.insets = new Insets(0, 0, 5, 0);
			gbc_nameField.gridx = 2;
			gbc_nameField.gridy = 1;
			playerPanel.add(nameFields[i], gbc_nameField);
			nameFields[i].setColumns(10);

			JLabel programLabel = new JLabel("Choisissez un programme");
			GridBagConstraints gbc_programLabel = new GridBagConstraints();
			gbc_programLabel.fill = GridBagConstraints.HORIZONTAL;
			gbc_programLabel.insets = new Insets(0, 0, 0, 5);
			gbc_programLabel.gridx = 1;
			gbc_programLabel.gridy = 3;
			playerPanel.add(programLabel, gbc_programLabel);

			programLists[i] = new JList<>(programs);
			programLists[i].addListSelectionListener(e -> checkInformations());
			programLists[i].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			GridBagConstraints gbc_programList = new GridBagConstraints();
			gbc_programList.anchor = GridBagConstraints.NORTH;
			gbc_programList.fill = GridBagConstraints.HORIZONTAL;
			gbc_programList.gridx = 2;
			gbc_programList.gridy = 3;
			playerPanel.add(programLists[i], gbc_programList);

			playerPanel.setOpaque(false);
			add(playerPanel);
		}

		MainView.confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0, size = GameController.getPlayers().length; i < size; i++) {
					GameController.getPlayers()[i] = new Player(nameFields[i].getText().equals("") ? "J" + (i + 1) : nameFields[i].getText(), programLists[i].getSelectedValue());
				}

				MainView.confirmButton.removeActionListener(this);
				MainView.addPanel(new AttributePanel(), PanelIdentifier.ATTRIBUTE_PANEL);
				MainView.switchToPanel(PanelIdentifier.ATTRIBUTE_PANEL);
			}
		});
	}

	/**
	 * Check player information to enable or not the confirm button.
	 * Players should each select a different program.
	 */
	public void checkInformations() {
		MainView.confirmButton.setEnabled(
				programLists[0].getSelectedValue() != null &&
						programLists[1].getSelectedValue() != null &&
						!programLists[0].getSelectedValue().equals(programLists[1].getSelectedValue())
		);
	}

}
