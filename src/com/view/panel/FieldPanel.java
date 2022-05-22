package com.view.panel;

import com.model.Player;
import com.model.Soldier;
import com.view.component.FieldProperties;
import com.view.component.GraphicSoldier;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.Serial;

/**
 * Panel used to display one specific battlefield.
 */
public class FieldPanel extends BasePanel { //TODO Battle (with animation ?)

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -6721489079020570444L;
	private final FieldProperties fieldProperties;
	private final JPanel firstPlayerPanel;
	private final JPanel secondPlayerPanel;

	/**
	 * Create the panel.
	 *
	 * @param fieldProperties the field properties
	 */
	public FieldPanel(FieldProperties fieldProperties) {
		this.fieldProperties = fieldProperties;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);
		
		JLabel fieldNameLabel = new JLabel("Champ de bataille : " + fieldProperties.name);
		fieldNameLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(fieldNameLabel);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		add(panel);
		GridLayout gridLayout = new GridLayout(1, 2);
		panel.setLayout(gridLayout);

		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		panel.add(leftPanel);
		GridBagLayout gbl_firstPlayerPanel = new GridBagLayout();
		gbl_firstPlayerPanel.columnWidths = new int[]{0, 0};
		gbl_firstPlayerPanel.rowHeights = new int[]{0, 0};
		gbl_firstPlayerPanel.columnWeights = new double[]{};
		gbl_firstPlayerPanel.rowWeights = new double[]{};
		leftPanel.setLayout(gbl_firstPlayerPanel);
		
		firstPlayerPanel = new JPanel();
		firstPlayerPanel.setOpaque(false);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		leftPanel.add(firstPlayerPanel, gbc_panel_1);
		firstPlayerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		panel.add(rightPanel);
		GridBagLayout gbl_secondPlayerPanel = new GridBagLayout();
		gbl_secondPlayerPanel.columnWidths = new int[]{0, 0};
		gbl_secondPlayerPanel.rowHeights = new int[]{0, 0};
		gbl_secondPlayerPanel.columnWeights = new double[]{};
		gbl_secondPlayerPanel.rowWeights = new double[]{};
		rightPanel.setLayout(gbl_secondPlayerPanel);
		
		secondPlayerPanel = new JPanel();
		secondPlayerPanel.setOpaque(false);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		rightPanel.add(secondPlayerPanel, gbc_panel_2);
		secondPlayerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				setupSoldiers();
			}
		});
	}

	/**
	 * Set up the soldiers on both sides.
	 */
	private void setupSoldiers() {
		Player[] players = GameController.players;
		for (int i = 0; i < players.length; i++) {
			for (Soldier soldier : players[i].soldiers) {
				if (soldier.assignedField == fieldProperties) {
					GraphicSoldier graphicSoldier = GraphicSoldier.createGraphics(soldier);
					graphicSoldier.setSelected(true);
					(i < 1 ? firstPlayerPanel : secondPlayerPanel).add(graphicSoldier);
				}
			}
		}
		repaint();
		revalidate();
	}
}
