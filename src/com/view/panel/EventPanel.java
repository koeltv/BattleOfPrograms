package com.view.panel;

import com.model.Event;
import com.view.ColorPalette;

import javax.swing.*;
import java.awt.*;

/**
 * Panel used as main panel to display actions.
 */
public class EventPanel extends JPanel {
	/**
	 * The next action to display.
	 *
	 * @see Event
	 */
	private final Event event = new Event();

	private Thread thread;

	/**
	 * Set next action to display.
	 *
	 * @param text the action
	 * @see Event
	 */
	public void setEvent(String text) {
		if (thread != null) thread.interrupt();
		try {
			synchronized (event) {
				event.wait();
			}
		} catch (InterruptedException ignored) {}
		event.setText(text);
		thread = new Thread(this::run);
		thread.start();
	}

	/**
	 * Draw x centered string within [x; x + width].
	 *
	 * @param string the string
	 * @param x      the x coordinate used as center
	 * @param y      the y coordinate
	 * @param width  the width in which to fit the string
	 */
	private void drawXCenteredString(Graphics2D g2D, String string, int x, int y, int width) {
		g2D.drawString(string, x + (width - getFontMetrics(g2D.getFont()).stringWidth(string)) / 2, y);
	}

	/**
	 * Display the current action if there is one.
	 *
	 * @see Event
	 */
	private void drawAction(Graphics2D g2D) {
		if (event.displayTime > 0) {
			int padding = getWidth() / 20;

			//We adapt the size to the current screen size
			Font font = getFont().deriveFont((float) padding);
			g2D.setFont(font);
			int width = g2D.getFontMetrics().stringWidth(event.text);
			int height = g2D.getFontMetrics().getAscent();

			//We do a background on which we put the text
			Graphics2D tempGraph = (Graphics2D) g2D.create();
			tempGraph.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
			tempGraph.setColor(ColorPalette.BLUE_BACKGROUND.color);
			tempGraph.fillRect(getWidth() / 2 - width / 2 - padding, getHeight() / 2 - height / 2, width + 2 * padding, height);
			tempGraph.dispose();

			//We add the text
			g2D.setColor(Color.WHITE);
			drawXCenteredString(g2D, event.text, getWidth() / 2 - width / 2, getHeight() / 2 - height / 2 + (int) (height * 0.875), width);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawAction((Graphics2D) g);
	}

	public void run() {
		synchronized (event) {
			try {
				while (event.displayTime > 0) {
					event.displayTime -= 10;
					repaint();
					wait(10);
				}
			} catch (InterruptedException ignored) {}
			repaint();
			event.notifyAll();
		}
	}
}
