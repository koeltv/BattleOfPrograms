package com.view;

import com.view.component.PlayerIndicator;
import com.view.panel.*;

import javax.swing.*;
import java.awt.*;

/**
 * Main View containing all panels.
 * The panels are added to the card layout and can be changed that way.
 * The menu bar always stay at the top.
 */
public class MainView { //TODO Global BattleField, individual battles, popups, (player transition)

	private JFrame frame;

	private static JPanel mainPanel;

	public static JLabel pointLabel;

	public static JButton confirmButton;
	public static PlayerIndicator playerIndicator;

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
		mainPanel.add(new FieldAttributionPanel(), PanelIdentifier.FIELD_ATTRIBUTION_PANEL.toString());
		mainPanel.add(new GlobalFieldPanel(), PanelIdentifier.GLOBAL_FIELD_PANEL.toString());
		mainPanel.add(new FieldPanel(), PanelIdentifier.FIELD_PANEL.toString());
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
		menuBar.setPreferredSize(new Dimension(10, 23));
		menuBar.setBackground(ColorPalette.BLACK.color);
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.setForeground(ColorPalette.MENU_BLUE.color);
		btnMenu.addActionListener(e -> {
			
		});
		SpringLayout sl_menuBar = new SpringLayout();
		sl_menuBar.putConstraint(SpringLayout.NORTH, btnMenu, 1, SpringLayout.NORTH, menuBar);
		sl_menuBar.putConstraint(SpringLayout.WEST, btnMenu, 1, SpringLayout.WEST, menuBar);
		menuBar.setLayout(sl_menuBar);
		menuBar.add(btnMenu);
		
		pointLabel = new JLabel("Points \u00E0 assigner : 400 pts");
		sl_menuBar.putConstraint(SpringLayout.NORTH, pointLabel, 1, SpringLayout.NORTH, menuBar);
		sl_menuBar.putConstraint(SpringLayout.WEST, pointLabel, 50, SpringLayout.EAST, btnMenu);
		sl_menuBar.putConstraint(SpringLayout.SOUTH, pointLabel, 22, SpringLayout.NORTH, menuBar);
		pointLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pointLabel.setForeground(Color.WHITE);
		menuBar.add(pointLabel);
		pointLabel.setVisible(false);
		
		JLabel statsLabel = new JLabel("F.R.I.C.D");
		sl_menuBar.putConstraint(SpringLayout.NORTH, statsLabel, 1, SpringLayout.NORTH, menuBar);
		sl_menuBar.putConstraint(SpringLayout.WEST, statsLabel, 51, SpringLayout.WEST, menuBar);
		sl_menuBar.putConstraint(SpringLayout.SOUTH, statsLabel, 22, SpringLayout.NORTH, menuBar);
		statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statsLabel.setForeground(ColorPalette.WHITE.color);
		menuBar.add(statsLabel);
		
		confirmButton = new JButton("Valider");
		sl_menuBar.putConstraint(SpringLayout.NORTH, confirmButton, 1, SpringLayout.NORTH, menuBar);
		menuBar.add(confirmButton);

		playerIndicator = new PlayerIndicator();
		sl_menuBar.putConstraint(SpringLayout.EAST, statsLabel, -20, SpringLayout.WEST, playerIndicator);
		sl_menuBar.putConstraint(SpringLayout.EAST, confirmButton, -25, SpringLayout.WEST, playerIndicator);
		sl_menuBar.putConstraint(SpringLayout.NORTH, playerIndicator, 1, SpringLayout.NORTH, menuBar);
		sl_menuBar.putConstraint(SpringLayout.SOUTH, playerIndicator, 22, SpringLayout.NORTH, menuBar);
		sl_menuBar.putConstraint(SpringLayout.EAST, playerIndicator, -10, SpringLayout.EAST, menuBar);
		menuBar.add(playerIndicator);

		playerIndicator.setVisible(false);
		confirmButton.setVisible(false);
		
		mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new CardLayout(0, 0));
	}

}
