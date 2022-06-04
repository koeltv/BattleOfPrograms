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
public class EventPanel extends JPanel implements Runnable {
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
	 * The 2D Graphics.
	 */
	private Graphics2D graphics2D;

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
			} catch (InterruptedException ignored) {
			}
		}
		event.setText(text);
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Run.
	 */
	@Override
	public void run() {
		synchronized (event) {
			try {
				while (event.displayTime > 0 || fullScreenEvent) {
					event.displayTime -= 10;
					repaint();
					event.wait(10);
				}
			} catch (InterruptedException ignored) {
			}
			repaint();
			event.notifyAll();
		}
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

		thread = new Thread(this);
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

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		graphics2D = (Graphics2D) g;
		drawAction();
	}

	/**
	 * Display the current action if there is one.
	 *
	 * @see Event
	 */
	private void drawAction() {
		if (event.displayTime > 0 || fullScreenEvent) {
			int padding = getWidth() / 20;

			//We adapt the size to the current screen size
			Font font = getFont().deriveFont((float) padding);
			graphics2D.setFont(font);
			int width = graphics2D.getFontMetrics().stringWidth(event.text);
			int height = graphics2D.getFontMetrics().getAscent();

			//We do a background on which we put the text
			Graphics2D tempGraph = (Graphics2D) graphics2D.create();
			tempGraph.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fullScreenEvent ? 0.99f : 0.75f));
			tempGraph.setColor(ColorPalette.BLUE_BACKGROUND.color);

			if (fullScreenEvent) tempGraph.fillRect(0, 0, getWidth(), getHeight());
			else
				tempGraph.fillRect(getWidth() / 2 - width / 2 - padding, getHeight() / 2 - height / 2, width + 2 * padding, height);

			tempGraph.dispose();

			//We add the text
			graphics2D.setColor(Color.WHITE);
			drawXCenteredString(event.text, new Point(getWidth() / 2 - width / 2, getHeight() / 2 - height / 2 + (int) (height * 0.875)), width);
		}
	}

	/**
	 * Draw x centered string within [x; x + width].
	 *
	 * @param string      the string
	 * @param upperCenter the upper center of the drawn string
	 * @param width       the width in which to fit the string
	 */
	private void drawXCenteredString(String string, Point upperCenter, int width) {
		graphics2D.drawString(string, upperCenter.x + (width - getFontMetrics(graphics2D.getFont()).stringWidth(string)) / 2, upperCenter.y);
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
