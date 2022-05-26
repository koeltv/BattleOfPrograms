package com.view.panel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serial;
import java.net.URL;

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
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -5114453698893412532L;
	private Image background;

	/**
	 * Create the panel.
	 */
	public BasePanel() {
		URL backgroundUrl = BasePanel.class.getResource("/images/background_map.jpeg");
		if (backgroundUrl != null) {
			try {
				background = ImageIO.read(backgroundUrl);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			background = null;
		}
	}

	/**
	 * Change background image.
	 *
	 * @param url the url of the image
	 */
	public void changeBackground(URL url) {
		if (url != null) {
			try {
				background = ImageIO.read(url);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			background = null;
		}
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
