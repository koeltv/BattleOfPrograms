package com.view.utils;

import javax.swing.*;
import java.awt.*;

/**
 * This class contains some useful methods to deal with geometry.
 */
public class GeometryUtils {
	/**
	 * Check for intersection of 2 rectangles
	 * Strongly inspired by <a href="https://stackoverflow.com/a/39319801">https://stackoverflow.com/a/39319801</a>.
	 *
	 * @param r1 - first rectangle
	 * @param r2 - second rectangle
	 * @return true if the intersection exist, false otherwise
	 */
	public static boolean intersects(Rectangle r1, Rectangle r2) {
		int leftX = Math.max(r1.x, r2.x);
		int rightX = (int) Math.min(r1.getMaxX(), r2.getMaxX());
		int topY = Math.max(r1.y, r2.y);
		int botY = (int) Math.min(r1.getMaxY(), r2.getMaxY());

		return (rightX > leftX) && (botY > topY);
	}

	/**
	 * Get the absolute bounds of a component.
	 *
	 * @param component the component
	 * @return the absolute bounds of the component
	 */
	public static Rectangle getAbsoluteBounds(Component component) {
		return SwingUtilities.convertRectangle(component.getParent(), component.getBounds(), SwingUtilities.getRoot(component));
	}
}
