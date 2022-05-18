package com.view;

import java.awt.*;

/**
 * Colors defined in the specifications. Only those should be used.
 */
public enum ColorPalette {
	WHITE (new Color(253, 253, 253)),
	BLACK (new Color(24, 26, 27)),
	RED (new Color(240, 0, 18)),
	BLUE_BACKGROUND (new Color(14, 99, 161)),
	MENU_BLUE (new Color(95, 185, 246)),
	ORANGE (new Color(236, 127, 33)),
	TEXT_BLUE (new Color(14, 99, 161)),
	LIFEBAR_GREY (new Color(196, 196, 196));

	final Color color;

	ColorPalette (Color color) {
		this.color = color;
	}
}
