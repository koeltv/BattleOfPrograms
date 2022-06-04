package com.view;

import java.awt.*;

/**
 * Colors defined in the specifications. Only those should be used.
 */
public enum ColorPalette {
	/**
	 * The White.
	 */
	WHITE(new Color(253, 253, 253)),
	/**
	 * The Black.
	 */
	BLACK(new Color(24, 26, 27)),
	/**
	 * The Red.
	 */
	RED(new Color(240, 0, 18)),
	/**
	 * The Blue background.
	 */
	BLUE_BACKGROUND(new Color(14, 99, 161)),
	/**
	 * The Menu blue.
	 */
	MENU_BLUE(new Color(95, 185, 246)),
	/**
	 * The Orange.
	 */
	ORANGE(new Color(236, 127, 33)),
	/**
	 * The Text blue.
	 */
	TEXT_BLUE(new Color(14, 99, 161));

	/**
	 * The Color.
	 */
	public final Color color;

	/**
	 * Instantiates a new Color palette.
	 *
	 * @param color the color
	 */
	ColorPalette(Color color) {
		this.color = color;
	}
}
