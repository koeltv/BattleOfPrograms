package com.view.panel;

import com.view.MainView;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

/**
 * Panel used as the very start to greet players.
 */
public class StartingPanel extends BasePanel {
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 4291077835233086734L;

	/**
	 * Create the panel.
	 */
	public StartingPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JLabel title = new JLabel("La bataille des programmes");
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewGame = new JButton("Nouveau Jeu");
		buttonPanel.add(btnNewGame);
		btnNewGame.addActionListener(e -> MainView.switchToPanel(PanelIdentifier.PLAYER_INFO_PANEL));
		
		JButton btnCredits = new JButton("Cr\u00E9dits");
		buttonPanel.add(btnCredits);
		
		JButton btnQuit = new JButton("Quitter le jeu");
		buttonPanel.add(btnQuit);
		btnQuit.addActionListener(e -> System.exit(0));
	}
}