package com.view;

import com.model.*;
import com.view.component.FieldProperties;
import com.view.component.PlayerIndicator;
import com.view.panel.*;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * Main View containing all panels.
 * The panels are added to the card layout and can be changed that way.
 * The menu bar always stay at the top.
 */
public class MainView { //TODO Player transition

	private static MainView instance;

	private JFrame frame;

	private static EventPanel mainPanel;

	public static JLabel pointLabel;

	public static JButton confirmButton;
	public static PlayerIndicator playerIndicator;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		boolean debug = args.length > 0 && args[0].equalsIgnoreCase("debug");

		EventQueue.invokeLater(() -> {
			try {
				instance = new MainView(debug);
				instance.frame.setVisible(true);
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
	}

	public MainView(boolean debug) {
		initialize();
		mainPanel.add(new StartingPanel(), PanelIdentifier.STARTING_PANEL.toString());
		mainPanel.add(new PlayerInfoPanel(), PanelIdentifier.PLAYER_INFO_PANEL.toString());
		mainPanel.add(new AttributePanel(), PanelIdentifier.ATTRIBUTE_PANEL.toString());

		if (debug) {
			mainPanel.add(new FieldAttributionPanel(), PanelIdentifier.FIELD_ATTRIBUTION_PANEL.toString());

			GameController.firstGame = false;
			GameController.step = 2;
			Player[] players = GameController.players;
			for (int i = 0; i < players.length; i++) {
				players[i] = new Player("P" + (i + 1), "");
				for (int j = 0; j < players[i].soldiers.length; j++) {
					if (j < 15) {
						players[i].soldiers[j] = new Soldier();
					} else if (j < 19) {
						players[i].soldiers[j] = new EliteSoldier();
					} else {
						players[i].soldiers[j] = new WarMaster();
					}
					players[i].soldiers[j].setAi(new Random().nextInt(2) > 0 ? new DefensiveAI() : new OffensiveAI());

					if (j < 5) players[i].soldiers[j].setReservist(true);
					else GameController.moveSoldierToField(players[i].soldiers[j], GameController.findFieldByProperties(FieldProperties.values()[new Random().nextInt(FieldProperties.values().length)]));
				}
			}

			switchToPanel(PanelIdentifier.FIELD_ATTRIBUTION_PANEL);
		}
	}

	public static <T extends Enum<T>> void addPanel(JPanel panel, T identifier) {
		mainPanel.add(panel, identifier.toString());
	}

	public static <T extends Enum<T>> void switchToPanel(T identifier) {
		((CardLayout) mainPanel.getLayout()).show(mainPanel, identifier.toString());
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
		sl_menuBar.putConstraint(SpringLayout.WEST, statsLabel, 200, SpringLayout.WEST, menuBar);
		sl_menuBar.putConstraint(SpringLayout.SOUTH, statsLabel, 22, SpringLayout.NORTH, menuBar);
		sl_menuBar.putConstraint(SpringLayout.EAST, statsLabel, -200, SpringLayout.EAST, menuBar);
		statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statsLabel.setForeground(ColorPalette.WHITE.color);
		menuBar.add(statsLabel);
		statsLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				displayDialog("""
								En-dessous de chaque combattant, vous pourrez voir 5 chiffres, ceux-ci correspondent aux statistiques F.R.I.C.D. :
        
								Force : Les points affectés ici augmentent les dégâts du combattant de 10% par point affecté. Par ex, si un combattant à 2 points, il frappera avec 20% de force en plus. Il fera 20% de dégâts en plus.
								        
								Résistance : Les points affectés ici diminuent les dégâts reçus par le combattant de 5% par point affecté. Par ex, si un combattant à 2 points, il « absorbera » 10% des dégâts qu’il reçoit.
								        
								Initiative : Lors d’un affrontement, c’est celui qui a la plus forte initiative qui porte le premier coup, puis c’est le combattant qui a la seconde meilleure initiative etc...
								        
								Constitution : Elle permet d’augmenter la constitution du combattant en lui donnant des points de vie supplémentaires. Par ex, 10 points de constitution feront augmenter les points de vie initiaux à 40 (au lieu de 30).
								        
								Dextérité : Les points affecté ici augmentent les chances de « toucher » son ennemi lors d’une attaque, ou d’esquiver lorsqu’on est attaqué. 1 point correspond à 3% de chance supplémentaire d’atteindre sa cible ou d’esquiver une attaque.
								""", false);
			}
		});
		
		confirmButton = new JButton("Valider");
		sl_menuBar.putConstraint(SpringLayout.NORTH, confirmButton, 1, SpringLayout.NORTH, menuBar);
		menuBar.add(confirmButton);

		playerIndicator = new PlayerIndicator();
		sl_menuBar.putConstraint(SpringLayout.EAST, confirmButton, -25, SpringLayout.WEST, playerIndicator);
		sl_menuBar.putConstraint(SpringLayout.NORTH, playerIndicator, 1, SpringLayout.NORTH, menuBar);
		sl_menuBar.putConstraint(SpringLayout.SOUTH, playerIndicator, 22, SpringLayout.NORTH, menuBar);
		sl_menuBar.putConstraint(SpringLayout.EAST, playerIndicator, -10, SpringLayout.EAST, menuBar);
		menuBar.add(playerIndicator);

		playerIndicator.setVisible(false);
		confirmButton.setVisible(false);
		
		mainPanel = new EventPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new CardLayout(0, 0));

		btnMenu.addActionListener(e -> reset());
	}

	public static void displayDialog(String text, boolean buttonsEnabled) {
		Dialog dialog = new Dialog(new Point(instance.frame.getLocationOnScreen().x + instance.frame.getWidth()/2, instance.frame.getLocationOnScreen().y + instance.frame.getHeight()/2), text);
		if (!buttonsEnabled) dialog.disableButtons();
		dialog.setVisible(true);
	}

	public static void reset() {
		mainPanel.removeAll();
		pointLabel.setVisible(false);
		playerIndicator.setVisible(false);
		confirmButton.setVisible(false);
		MainView.addPanel(new StartingPanel(), PanelIdentifier.STARTING_PANEL);
		MainView.switchToPanel(PanelIdentifier.STARTING_PANEL);
	}

	public static void removeConfirmationListeners() {
		for (ActionListener listener : confirmButton.getActionListeners()) confirmButton.removeActionListener(listener);
	}

	public static void setEvent(String text) {
		mainPanel.setEvent(text);
	}
}
