package com.view.component;

import com.model.Player;
import com.view.ColorPalette;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

/**
 * The type Player indicator.
 */
public class PlayerIndicator extends JPanel {

	/**
	 * The constant serialVersionUID.
	 */
	@Serial
	private static final long serialVersionUID = 5731537562132378545L;
	/**
	 * The Player circle.
	 */
	private final JPanel colorCircle;
	/**
	 * The Player label.
	 */
	private final JLabel playerLabel;
	/**
	 * The Color.
	 */
	private Color color;
	/**
	 * The Player.
	 */
	private Player player;

	/**
	 * Instantiates a new Player indicator.
	 */
	public PlayerIndicator() {
		color = ColorPalette.RED.color;

		setLayout(new BorderLayout(0, 0));
		setBackground(null);

		colorCircle = new JPanel();
		add(colorCircle, BorderLayout.WEST);
		colorCircle.setBackground(null);
		colorCircle.setOpaque(false);

		playerLabel = new JLabel();
		add(playerLabel, BorderLayout.CENTER);

		colorCircle.setPreferredSize(new Dimension(20, 20));
	}

	/**
	 * Gets player.
	 *
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player to display.
	 *
	 * @param player the player
	 */
	public void setPlayer(Player player) {
		if (this.player != player) {
			this.player = player;
			playerLabel.setText("  " + player.getName() + " (" + player.getProgram() + ")");

			if (color == ColorPalette.RED.color) color = ColorPalette.MENU_BLUE.color;
			else color = ColorPalette.RED.color;
			playerLabel.setForeground(color);
			repaint();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int diameter = 20;
		g.setColor(color);
		g.fillOval(0, (colorCircle.getHeight() - diameter) / 2, diameter, diameter);
	}
}
