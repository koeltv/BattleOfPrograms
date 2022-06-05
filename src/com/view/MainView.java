package com.view;

import com.controller.GameController;
import com.model.FieldProperties;
import com.model.Player;
import com.view.component.PlayerIndicator;
import com.view.panel.EventPanel;
import com.view.panel.FieldAttributionPanel;
import com.view.panel.PanelIdentifier;
import com.view.panel.StartingPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Main View containing all panels.
 * The panels are added to the card layout and can be changed that way.
 * The menu bar always stay at the top.
 */
public class MainView {

	/**
	 * The constant confirmButton.
	 */
	public static JButton confirmButton;
	/**
	 * The constant instance.
	 */
	private static MainView instance;
	/**
	 * The constant mainPanel.
	 */
	private static EventPanel mainPanel;

	/**
	 * The constant pointLabel.
	 */
	private static JLabel pointLabel;

	/**
	 * The constant playerIndicator.
	 */
	private static PlayerIndicator playerIndicator;
	/**
	 * The Frame.
	 */
	private JFrame frame;

	/**
	 * Create the application.
	 *
	 * @param debug the debug
	 * @wbp.parser.entryPoint
	 */
	public MainView(boolean debug) {
		initialize();
		reset();

		if (debug) {
			mainPanel.add(new FieldAttributionPanel(), PanelIdentifier.FIELD_ATTRIBUTION_PANEL.toString());
			GameController.startDebugMode();
			switchToPanel(PanelIdentifier.FIELD_ATTRIBUTION_PANEL);
		}
	}

	/**
	 * Launch the application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				instance = new MainView(args.length > 0 && args[0].equalsIgnoreCase("debug"));
				instance.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Add a panel.
	 *
	 * @param <T>        the type of the identifier use, can be {@link FieldProperties} or {@link PanelIdentifier}
	 * @param panel      the panel
	 * @param identifier the identifier
	 */
	public static <T extends Enum<T>> void addPanel(JPanel panel, T identifier) {
		mainPanel.add(panel, identifier.toString());
	}

	/**
	 * Switch to panel.
	 *
	 * @param <T>        the type of the identifier use, can be {@link FieldProperties} or {@link PanelIdentifier}
	 * @param identifier the identifier
	 */
	public static <T extends Enum<T>> void switchToPanel(T identifier) {
		((CardLayout) mainPanel.getLayout()).show(mainPanel, identifier.toString());
	}

	/**
	 * Show point label.
	 *
	 * @param show whether to show the point label or not
	 */
	public static void showPointLabel(boolean show) {
		pointLabel.setVisible(show);
	}

	/**
	 * Sets points left.
	 *
	 * @param pointsLeft the points left
	 */
	public static void setPointsLeft(int pointsLeft) {
		pointLabel.setText("Points \u00E0 assigner : " + pointsLeft + " pts");
	}

	/**
	 * Sets current player.
	 *
	 * @param nextPlayer the player to set as current player
	 */
	public static void setPlayerIndicator(Player nextPlayer) {
		Player player = playerIndicator.getPlayer();
		if (player != null && nextPlayer != player) {
			mainPanel.setFullWindowEvent("Please pass to player " + nextPlayer.getName() + " and click");
		}
		playerIndicator.setPlayer(nextPlayer);
	}

	/**
	 * Show player indicator.
	 *
	 * @param show whether to show the player indicator or not
	 */
	public static void showPlayerIndicator(boolean show) {
		playerIndicator.setVisible(show);
	}

	/**
	 * Stops all events.
	 */
	public static void stopEvent() {
		mainPanel.stopEvent();
	}

	/**
	 * Sets event.
	 *
	 * @param text the text of the event
	 */
	public static void setEvent(String text) {
		mainPanel.setEvent(text);
	}

	/**
	 * Check if an event is displayed.
	 *
	 * @return false if an event is displayed, false otherwise
	 */
	public static boolean noEvent() {
		return mainPanel.noEvent();
	}

	/**
	 * Display dialog.
	 * Prepare a dialog and display it when possible.
	 *
	 * @param text           the text
	 * @param buttonsEnabled whether buttons should be enabled or not
	 */
	public static void displayDialog(String text, boolean buttonsEnabled) {
		Point locationOnScreen = instance.frame.getLocationOnScreen();
		Dialog.reposition(new Point(locationOnScreen.x + instance.frame.getWidth() / 2, locationOnScreen.y + instance.frame.getHeight() / 2));

		Dialog.enableButtons(buttonsEnabled);
		Dialog.setText(text);

		new Thread(MainView::showDialog).start();
	}

	/**
	 * Show dialog.
	 * Display the dialog when there is no event displayed.
	 */
	@SuppressWarnings("BusyWait")
	public static void showDialog() {
		try {
			while (!mainPanel.noEvent()) Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Dialog.display();
	}

	/**
	 * Reset.
	 */
	public static void reset() {
		mainPanel.stopEvent();
		mainPanel.removeAll();
		pointLabel.setVisible(false);
		playerIndicator.setVisible(false);
		confirmButton.setVisible(false);
		removeConfirmationListeners();
		GameController.reset();
		MainView.addPanel(new StartingPanel(), PanelIdentifier.STARTING_PANEL);
		MainView.switchToPanel(PanelIdentifier.STARTING_PANEL);
	}

	/**
	 * Remove confirmation listeners.
	 */
	public static void removeConfirmationListeners() {
		for (ActionListener listener : confirmButton.getActionListeners()) confirmButton.removeActionListener(listener);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(5, 5, 1536, 864);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setMinimumSize(new Dimension(1330, 748));
		frame.setTitle("La bataille des programmes");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainView.class.getResource("/images/soldier.png")));

		JPanel menuBar = new JPanel();
		menuBar.setPreferredSize(new Dimension(10, 23));
		menuBar.setBackground(ColorPalette.BLACK.color);
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);

		JButton btnMenu = new JButton("Menu");
		btnMenu.setForeground(ColorPalette.MENU_BLUE.color);
		btnMenu.setContentAreaFilled(false);
		SpringLayout sl_menuBar = new SpringLayout();
		sl_menuBar.putConstraint(SpringLayout.NORTH, btnMenu, 1, SpringLayout.NORTH, menuBar);
		sl_menuBar.putConstraint(SpringLayout.WEST, btnMenu, 1, SpringLayout.WEST, menuBar);
		menuBar.setLayout(sl_menuBar);
		menuBar.add(btnMenu);
		btnMenu.addActionListener(e -> reset());

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
	}
}
