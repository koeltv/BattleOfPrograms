package com.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;

/**
 * Used to display information to the players while interrupting the game.
 */
public class Dialog extends JDialog {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -5301893305124026332L;
	
	private final JPanel buttonPane;
	private final JTextPane textPane;

	/**
	 * Create the dialog.
	 */
	public Dialog(Point center, String text) {
		int width = 800, height = 400;

		setModalityType(ModalityType.APPLICATION_MODAL);
		setUndecorated(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(center.x - width/2, center.y - height/2, width, height);
		getContentPane().setLayout(new BorderLayout());
		JPanel contentPanel = new JPanel();

		contentPanel.setOpaque(false);
		Color color = ColorPalette.BLUE_BACKGROUND.color;
		Color backgroundColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 220);
		setBackground(backgroundColor);

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);

		textPane = new JTextPane();

		//fontSize = (1 / textLength^0.25) * 90, used to scale text to fill dialog
		float fontSize = (float) ((float) 1 / Math.pow(text.length(), 0.25) * 90);
		textPane.setFont(textPane.getFont().deriveFont(fontSize));

		sl_contentPanel.putConstraint(SpringLayout.NORTH, textPane, 5, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, textPane, 5, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, textPane, -5, SpringLayout.SOUTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, textPane, -5, SpringLayout.EAST, contentPanel);
		textPane.setPreferredSize(new Dimension(200, 19));
		textPane.setEditable(false);
		textPane.setText(text);
		textPane.setForeground(ColorPalette.WHITE.color);
		textPane.setOpaque(false);
		contentPanel.add(textPane);

		{
			buttonPane = new JPanel();
			buttonPane.setOpaque(false);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton okButton = new JButton("Nouveau Jeu");
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

	public void disableButtons() {
		buttonPane.setVisible(false);
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		};
		this.addMouseListener(mouseAdapter);
		textPane.addMouseListener(mouseAdapter);
	}

}
