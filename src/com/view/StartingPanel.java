package com.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Panel used as the very start to greet players.
 */
public class StartingPanel extends JPanel {
	private final Image background;

	/**
	 * Create the panel.
	 */
	public StartingPanel() {
		URL backgroundUrl = StartingPanel.class.getResource("/images/background_map.jpeg");
		if (backgroundUrl != null) {
			try {
				background = ImageIO.read(backgroundUrl);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			background = null;
		}

		setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("La bataille des programmes");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton = new JButton("Nouveau Jeu");
		panel.add(btnNewButton);
		btnNewButton.addActionListener(e -> MainView.switchToPanel(PanelIdentifier.PLAYER_INFO_PANEL));
		
		JButton btnNewButton_1 = new JButton("Cr\u00E9dits");
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Quitter le jeu");
		panel.add(btnNewButton_2);
		btnNewButton_2.addActionListener(e -> System.exit(0));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Composite composite = ((Graphics2D) g).getComposite();
		((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		((Graphics2D) g).setComposite(composite);
	}
}
