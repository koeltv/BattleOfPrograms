package com.view.panel;

import com.model.Event;
import com.view.ColorPalette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

	/**
	 * The Full screen event.
	 */
	private boolean fullScreenEvent;

	/**
	 * The Thread printing the event.
	 */
	private Thread thread;

	/**
	 * Set next action to display.
	 *
	 * @param text the action
	 * @see Event
	 */
	public void setEvent(String text) {
		if (thread != null) {
			thread.interrupt();
			try {
				synchronized (event) {
					event.wait(500);
				}
			} catch (InterruptedException ignored) {}
		}
		event.setText(text);
		thread = new Thread(this::run);
		thread.start();
	}

	/**
	 * Sets a full window event.
	 *
	 * @param text the text to display
	 */
	public void setFullWindowEvent(String text) {
		event.displayTime = 0;
		fullScreenEvent = true;
		event.setText(text);

		thread = new Thread(this::run);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				removeMouseListener(this);
				thread.interrupt();
				fullScreenEvent = false;
				event.displayTime = 0;
				repaint();
			}
		});

		thread.start();
	}

	/**
	 * Draw x centered string within [x; x + width].
	 *
	 * @param g2D    the 2d Graphics
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
	 * @param g2D             the 2d Graphics
	 * @param fullScreenEvent the full screen event
	 * @see Event
	 */
	private void drawAction(Graphics2D g2D, boolean fullScreenEvent) {
		if (event.displayTime > 0 || fullScreenEvent) {
			int padding = getWidth() / 20;

			//We adapt the size to the current screen size
			Font font = getFont().deriveFont((float) padding);
			g2D.setFont(font);
			int width = g2D.getFontMetrics().stringWidth(event.text);
			int height = g2D.getFontMetrics().getAscent();

			//We do a background on which we put the text
			Graphics2D tempGraph = (Graphics2D) g2D.create();
			tempGraph.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fullScreenEvent ? 0.99f : 0.75f));
			tempGraph.setColor(ColorPalette.BLUE_BACKGROUND.color);

			if (fullScreenEvent) tempGraph.fillRect(0, 0, getWidth(), getHeight());
			else tempGraph.fillRect(getWidth() / 2 - width / 2 - padding, getHeight() / 2 - height / 2, width + 2 * padding, height);

			tempGraph.dispose();

			//We add the text
			g2D.setColor(Color.WHITE);
			drawXCenteredString(g2D, event.text, getWidth() / 2 - width / 2, getHeight() / 2 - height / 2 + (int) (height * 0.875), width);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawAction((Graphics2D) g, fullScreenEvent);
	}

	/**
	 * Run.
	 */
	public void run() {
		synchronized (event) {
			try {
				while (event.displayTime > 0 || fullScreenEvent) {
					event.displayTime -= 10;
					repaint();
					event.wait(10);
				}
			} catch (InterruptedException ignored) {}
			repaint();
			event.notifyAll();
		}
	}

	/**
	 * Stop event displaying.
	 */
	public void stopEvent() {
		event.displayTime = 0;
		fullScreenEvent = false;
	}

	/**
	 * Check if an event is displayed.
	 *
	 * @return false if an event is displayed, false otherwise
	 */
	public boolean noEvent() {
		return event.displayTime <= 0 && !fullScreenEvent;
	}
}
