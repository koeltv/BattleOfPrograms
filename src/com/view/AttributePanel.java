package com.view;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class AttributePanel extends JPanel {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 6364406918777401302L;

	/**
	 * Create the panel.
	 */
	public AttributePanel() {
		setLayout(new GridLayout(1, 2));

		JPanel soldierPanel = new JPanel();
		add(soldierPanel);
		soldierPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel statPanel = new JPanel();
		add(statPanel);
		GridBagLayout gbl_statPanel = new GridBagLayout();
		gbl_statPanel.columnWidths = new int[]{0, 0};
		gbl_statPanel.rowHeights = new int[]{0, 0};
		gbl_statPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_statPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		statPanel.setLayout(gbl_statPanel);
		
		JLabel lblNewLabel = new JLabel("Reserviste");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		statPanel.add(lblNewLabel, gbc_lblNewLabel);
	}

}
