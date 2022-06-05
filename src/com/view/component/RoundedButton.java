package com.view.component;

import com.view.ColorPalette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;


/**
 * A rounded button.
 * Inspired from <a href="https://stackoverflow.com/q/1007116">https://stackoverflow.com/q/1007116</a>
 */
public class RoundedButton extends JButton implements MouseListener {

	/**
	 * Whether the mouse is hovering the button.
	 */
	private boolean mouseIn = false;

	/**
	 * Instantiates a new Rounded button.
	 *
	 * @param text the text to display
	 */
	public RoundedButton(String text) {
		super(text);
		setBorderPainted(false);
		addMouseListener(this);
		setContentAreaFilled(false);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (getModel().isPressed()) {
			g2d.setColor(ColorPalette.WHITE.color);
			g2d.fillRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 12, 8);
		}
		super.paintComponent(g);

		g2d.setColor(mouseIn ? ColorPalette.BLUE_BACKGROUND.color : ColorPalette.BLACK.color);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(1.2f));
		g2d.draw(new RoundRectangle2D.Double(1, 1, (getWidth() - 3), (getHeight() - 3), 12, 8));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseIn = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseIn = false;
	}
}