package com.view.panel;

import com.view.MainView;
import com.view.component.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

/**
 * Panel used as the very start to greet players.
 */
public class StartingPanel extends BasePanel {
	/**
	 * The constant serialVersionUID.
	 */
	@Serial
	private static final long serialVersionUID = 4291077835233086734L;

	/**
	 * Create the panel.
	 */
	public StartingPanel() {
		setLayout(new BorderLayout(0, 0));

		JLabel title = new JLabel("La bataille des programmes");
		title.setFont(new Font("Tahoma", Font.BOLD, 50));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

		RoundedButton btnNewGame = new RoundedButton("Nouveau Jeu");
		buttonPanel.add(btnNewGame);
		btnNewGame.addActionListener(e -> {
			MainView.addPanel(new PlayerInfoPanel(), PanelIdentifier.PLAYER_INFO_PANEL);
			MainView.switchToPanel(PanelIdentifier.PLAYER_INFO_PANEL);
		});

		RoundedButton btnCredits = new RoundedButton("Cr\u00E9dits");
		buttonPanel.add(btnCredits);
		btnCredits.addActionListener(e ->
				MainView.displayDialog("""
						Crédits :
							   
						 - Valentin Koeltgen, étudiant en ISI1
						 - Yichen Liu, étudiant en ISI3
						""", false)
		);

		RoundedButton btnQuit = new RoundedButton("Quitter le jeu");
		buttonPanel.add(btnQuit);
		btnQuit.addActionListener(e -> System.exit(0));
	}
}
