package com.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serial;

/**
 * Used to display information to the players while interrupting the game.
 */
public class Dialog extends JDialog {

	/**
	 * The constant serialVersionUID.
	 */
	@Serial
	private static final long serialVersionUID = -5301893305124026332L;

	private static final Dialog instance = new Dialog();

	/**
	 * The Button pane.
	 */
	private final JPanel buttonPane;
	/**
	 * The Text pane.
	 */
	private final JTextPane textPane;

	/**
	 * Create the dialog.
	 */
	private Dialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setUndecorated(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		JPanel contentPanel = new JPanel();

		contentPanel.setOpaque(false);
		Color color = ColorPalette.BLUE_BACKGROUND.color;
		Color backgroundColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 240);
		setBackground(backgroundColor);

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);

		textPane = new JTextPane();

		sl_contentPanel.putConstraint(SpringLayout.NORTH, textPane, 5, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, textPane, 5, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, textPane, -5, SpringLayout.SOUTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, textPane, -5, SpringLayout.EAST, contentPanel);
		textPane.setPreferredSize(new Dimension(200, 19));
		textPane.setEditable(false);
		textPane.setForeground(ColorPalette.WHITE.color);
		textPane.setOpaque(false);
		contentPanel.add(textPane);

		{
			buttonPane = new JPanel();
			buttonPane.setOpaque(false);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton okButton = new JButton("Menu");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(e -> {
					MainView.reset();
					dispose();
				});
			}
			{
				JButton cancelButton = new JButton("Quitter le jeu");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(e -> System.exit(0));
			}
		}
	}

	public static void initialize(Point center) {
		int width = 800, height = 400;
		instance.setBounds(center.x - width / 2, center.y - height / 2, width, height);
	}

	/**
	 * Disable the buttons and replace them by a dialog-wide click event.
	 */
	public static void enableButtons(boolean enable) {
		instance.buttonPane.setVisible(enable);
		MouseListener dismiss = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				instance.dispose();
			}
		};

		if (enable) {
			instance.textPane.addMouseListener(dismiss);
		} else {
			instance.textPane.removeMouseListener(dismiss);
		}
	}

	public static void setText(String text) {
		if (!instance.textPane.getText().equals(text)) {
			//fontSize = (1 / textLength^0.25) * 90, used to scale text to fill dialog
			float fontSize = (float) ((float) 1 / Math.pow(text.length(), 0.25) * 90);
			instance.textPane.setFont(instance.textPane.getFont().deriveFont(fontSize));
			instance.textPane.setText(text);
		}
	}

	public static void display(boolean display) {
		instance.setVisible(display);
	}
}
