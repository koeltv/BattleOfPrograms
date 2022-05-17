package com.view;

import javax.swing.*;
import java.awt.*;

/**
 * Main View containing all panels.
 * The panels are added to the card layout and can be changed that way.
 * The menu bar always stay at the top.
 */
public class MainView {

	private JFrame frame;

	private static JPanel mainPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				MainView window = new MainView();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public MainView() {
		initialize();
		mainPanel.add(new StartingPanel(), PanelIdentifier.STARTING_PANEL.toString());
		mainPanel.add(new PlayerInfoPanel(), PanelIdentifier.PLAYER_INFO_PANEL.toString());
	}

	public static void switchToPanel(PanelIdentifier identifier) {
		CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
		cardLayout.show(mainPanel, identifier.toString());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1536, 864);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel menuBar = new JPanel();
		menuBar.setBackground(new Color(24, 26, 27));
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		GridBagLayout gbl_menuBar = new GridBagLayout();
		gbl_menuBar.columnWidths = new int[]{426, 57, 0};
		gbl_menuBar.rowHeights = new int[]{21, 0};
		gbl_menuBar.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_menuBar.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		menuBar.setLayout(gbl_menuBar);
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.setForeground(new Color(95, 185, 246));
		btnMenu.addActionListener(e -> {
			
		});
		GridBagConstraints gbc_btnMenu = new GridBagConstraints();
		gbc_btnMenu.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnMenu.gridx = 0;
		gbc_btnMenu.gridy = 0;
		menuBar.add(btnMenu, gbc_btnMenu);
		
		JLabel lblNewLabel = new JLabel("F.R.I.C.D");
		lblNewLabel.setForeground(new Color(253, 253 ,253));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		menuBar.add(lblNewLabel, gbc_lblNewLabel);
		
		mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new CardLayout(0, 0));
	}

}
