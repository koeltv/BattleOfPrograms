package com.model;

/**
 * The type Action.
 *
 * Gives all the methods related to action.
 */
public class Event {

	/**
	 * The average number of character to be displayed per second.
	 * This is obtained by multiplying the average number of character in a word in the english language (5) to the number of seconds in a minute (60)
	 * and dividing by the upper average number of words thoughts per minutes (3000).
	 */
	private static final double AVG_CHAR_PER_SECOND = (double) (5 * 60) / 3000;

	/**
	 * The Text.
	 */
	private final String text;
	/**
	 * The Full screen.
	 */
	private final boolean fullScreen;
	/**
	 * The Display time.
	 * This is the time the text will be displayed. It depends on the length of the String.
	 */
	private int displayTime;

	/**
	 * Create a new event.
	 *
	 * @param text       text to display
	 * @param fullScreen whether to display it full screen or not
	 */
	public Event(String text, boolean fullScreen) {
		this.text = text;
		this.fullScreen = fullScreen;
		this.displayTime = (int) (text.length() * AVG_CHAR_PER_SECOND * 300);
	}

	/**
	 * Gets text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gets display time.
	 *
	 * @return the display time
	 */
	public int getDisplayTime() {
		return displayTime;
	}

	/**
	 * Shorten the display time.
	 *
	 * @param time the time
	 */
	public void shortenDisplayTime(int time) {
		if (!fullScreen) displayTime -= time;
	}

	/**
	 * Whether the event needs to be shown full screen or not.
	 *
	 * @return the boolean
	 */
	public boolean isFullScreen() {
		return fullScreen;
	}
}
