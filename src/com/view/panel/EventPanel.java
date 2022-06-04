package com.view.panel;

import com.model.Event;
import com.view.ColorPalette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/**
 * Panel used as main panel to display actions.
 */
public class EventPanel extends JPanel implements Runnable {

	/**
	 * The delay between each repainting of the current event.
	 */
	private static final int MILLIS_BETWEEN_STEPS = 10;
	/**
	 * A stack of events to be displayed.
	 *
	 * @see Event
	 */
	private final LinkedList<Event> eventStack = new LinkedList<>();

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
				synchronized (eventStack) {
					eventStack.wait(500);
				}
			} catch (InterruptedException ignored) {
			}
		}
		eventStack.push(new Event(text, false));
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Run.
	 */
	@Override
	public void run() {
		synchronized (eventStack) {
			try {
				while (eventStack.size() > 0 && eventStack.peek().getDisplayTime() > 0) {
					if (eventStack.size() > 0) {
						eventStack.peek().shortenDisplayTime(MILLIS_BETWEEN_STEPS);
						repaint();
						if (eventStack.size() > 0 && eventStack.peek().getDisplayTime() < 1) {
							stopEvent();
						}
						eventStack.wait(MILLIS_BETWEEN_STEPS);
					}
				}
			} catch (InterruptedException ignored) {
			}
			repaint();
			eventStack.notifyAll();
		}
	}

	/**
	 * Stop event displaying.
	 */
	public void stopEvent() {
		if (eventStack.size() > 0)
			eventStack.pop();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		graphics2D = (Graphics2D) g;
		drawAction();
	}

	/**
	 * Sets a full window event.
	 *
	 * @param text the text to display
	 */
	public void setFullWindowEvent(String text) {
		eventStack.push(new Event(text, true));

		thread = new Thread(this);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				removeMouseListener(this);
				thread.interrupt();
				stopEvent();
				repaint();
			}
		});

		thread.start();
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
	 * Display the current action if there is one.
	 *
	 * @see Event
	 */
	private void drawAction() {
		Event event = eventStack.peek();
		if (event != null && event.getDisplayTime() > 0) {
			int padding = getWidth() / 20;

			//We adapt the size to the current screen size
			Font font = getFont().deriveFont((float) padding);
			graphics2D.setFont(font);
			int width = graphics2D.getFontMetrics().stringWidth(event.getText());
			int height = graphics2D.getFontMetrics().getAscent();

			//We do a background on which we put the text
			Graphics2D tempGraph = (Graphics2D) graphics2D.create();
			tempGraph.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, event.isFullScreen() ? 0.99f : 0.75f));
			tempGraph.setColor(ColorPalette.BLUE_BACKGROUND.color);

			if (event.isFullScreen()) tempGraph.fillRect(0, 0, getWidth(), getHeight());
			else
				tempGraph.fillRect(getWidth() / 2 - width / 2 - padding, getHeight() / 2 - height / 2, width + 2 * padding, height);

			tempGraph.dispose();

			//We add the text
			graphics2D.setColor(Color.WHITE);
			drawXCenteredString(event.getText(), new Point(getWidth() / 2 - width / 2, getHeight() / 2 - height / 2 + (int) (height * 0.875)), width);
		}
	}

	/**
	 * Check if an event is displayed.
	 *
	 * @return false if an event is displayed, false otherwise
	 */
	public boolean noEvent() {
		return eventStack.size() < 1;
	}
}
