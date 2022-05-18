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

	public static JLabel pointLabel;

	public static JButton confirmButton;

	public static JLabel playerLabel;

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
		mainPanel.add(new AttributePanel(), PanelIdentifier.ATTRIBUTE_PANEL.toString());
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
		menuBar.setBackground(ColorPalette.BLACK.color);
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		GridBagLayout gbl_menuBar = new GridBagLayout();
		gbl_menuBar.columnWidths = new int[]{426, 0, 57, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_menuBar.rowHeights = new int[]{21, 0};
		gbl_menuBar.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_menuBar.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		menuBar.setLayout(gbl_menuBar);
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.setForeground(ColorPalette.MENU_BLUE.color);
		btnMenu.addActionListener(e -> {
			
		});
		GridBagConstraints gbc_btnMenu = new GridBagConstraints();
		gbc_btnMenu.fill = GridBagConstraints.VERTICAL;
		gbc_btnMenu.insets = new Insets(0, 0, 0, 5);
		gbc_btnMenu.anchor = GridBagConstraints.WEST;
		gbc_btnMenu.gridx = 0;
		gbc_btnMenu.gridy = 0;
		menuBar.add(btnMenu, gbc_btnMenu);
		
		pointLabel = new JLabel("Points \u00E0 assigner : 400 pts");
		pointLabel.setForeground(Color.WHITE);
		GridBagConstraints gbc_pointLabel = new GridBagConstraints();
		gbc_pointLabel.fill = GridBagConstraints.VERTICAL;
		gbc_pointLabel.anchor = GridBagConstraints.WEST;
		gbc_pointLabel.insets = new Insets(0, 0, 0, 5);
		gbc_pointLabel.gridx = 1;
		gbc_pointLabel.gridy = 0;
		menuBar.add(pointLabel, gbc_pointLabel);
		pointLabel.setVisible(false);
		
		JLabel statsLabel = new JLabel("F.R.I.C.D");
		statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statsLabel.setForeground(ColorPalette.WHITE.color);
		GridBagConstraints gbc_statsLabel = new GridBagConstraints();
		gbc_statsLabel.anchor = GridBagConstraints.EAST;
		gbc_statsLabel.fill = GridBagConstraints.VERTICAL;
		gbc_statsLabel.insets = new Insets(0, 0, 0, 5);
		gbc_statsLabel.gridx = 2;
		gbc_statsLabel.gridy = 0;
		menuBar.add(statsLabel, gbc_statsLabel);
		
		confirmButton = new JButton("Valider");
		GridBagConstraints gbc_confirmButton = new GridBagConstraints();
		gbc_confirmButton.fill = GridBagConstraints.VERTICAL;
		gbc_confirmButton.anchor = GridBagConstraints.EAST;
		gbc_confirmButton.insets = new Insets(0, 0, 0, 5);
		gbc_confirmButton.gridx = 3;
		gbc_confirmButton.gridy = 0;
		menuBar.add(confirmButton, gbc_confirmButton);
		confirmButton.setVisible(false);
		
		playerLabel = new JLabel("       J1");
		GridBagConstraints gbc_playerLabel = new GridBagConstraints();
		gbc_playerLabel.fill = GridBagConstraints.VERTICAL;
		gbc_playerLabel.anchor = GridBagConstraints.EAST;
		gbc_playerLabel.insets = new Insets(0, 0, 0, 5);
		gbc_playerLabel.gridx = 4;
		gbc_playerLabel.gridy = 0;
		menuBar.add(playerLabel, gbc_playerLabel);
		playerLabel.setVisible(false);
		
		mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new CardLayout(0, 0));
	}

}
