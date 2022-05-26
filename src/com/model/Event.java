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
    public String text;
    
    /**
     * The Display time.
     * This is the time the text will be displayed. It depends on the length of the String.
     */
    public int displayTime;

    public void setText(String text) {
        this.text = text;
        this.displayTime = (int) (text.length() * AVG_CHAR_PER_SECOND * 1000);
    }
}
