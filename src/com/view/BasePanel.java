package com.view;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.Serial;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Basic panel with the possibility to set a background.
 */
public class BasePanel extends JPanel {
	
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
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Composite composite = ((Graphics2D) g).getComposite();
		((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		((Graphics2D) g).setComposite(composite);
	}

}
