package com.view.panel;

import com.view.Resource;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

/**
 * Basic panel with the possibility to set a background.
 */
public class BasePanel extends JPanel {

	/**
	 * Transparency of the background image. Lower value means more transparency.
	 * For exemple an alpha of 0.1 correspond to the image being 90% transparent.
	 */
	private float alpha = 0.6f;

	/**
	 * The constant serialVersionUID.
	 */
	@Serial
	private static final long serialVersionUID = -5114453698893412532L;
	/**
	 * The Background image.
	 */
	private final Image background;

	/**
	 * Create the panel with default background.
	 */
	public BasePanel() {
		background = Resource.BACKGROUND.image.getImage();
	}

	/**
	 * Create the panel with custom background.
	 *
	 * @param background the background
	 */
	public BasePanel(Image background) {
		this.background = background;
	}

	/**
	 * Sets the alpha of the background panel.
	 *
	 * @param alpha the alpha
	 */
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Composite composite = ((Graphics2D) g).getComposite();
		((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		((Graphics2D) g).setComposite(composite);
	}

}
