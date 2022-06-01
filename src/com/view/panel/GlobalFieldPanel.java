package com.view.panel;

import com.model.Field;
import com.model.Player;
import com.view.MainView;
import com.view.component.GraphicField;
import controller.GameController;

import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serial;

/**
 * Panel used to display the overhaul battlefield.
 */
public class GlobalFieldPanel extends BasePanel implements PropertyChangeListener {

	/**
	 * The constant serialVersionUID.
	 */
	@Serial
	private static final long serialVersionUID = -2611728061732290991L;

	/**
	 * The Current step.
	 */
	private int currentStep = -1;

	/**
	 * The Pass action.
	 */
	private final ActionListener passAction;

	/**
	 * Create the panel.
	 */
	public GlobalFieldPanel() { //TODO Positioning is temporary, it needs to be redone
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		changeBackground(FieldPanel.class.getResource("/images/interactive_map.PNG"));
		setAlpha(0.2f);
		MainView.showPlayerIndicator(false);

		passAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainView.confirmButton.removeActionListener(this);
				GameController.skipBattle();
			}
		};

		setupFields(springLayout);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				MainView.confirmButton.setText("Passer");
				MainView.confirmButton.addActionListener(passAction);

				if (currentStep != GameController.getStep()) {
					if (GameController.displayTutorial()) {
						MainView.displayDialog("""
							C'est ici que le combat se déroule. Tu peux cliquer sur les différents champs de bataille pour voir les batailles se dérouler.
							
							Une bataille se terminera par le contrôle d'un des champs par l'un des deux joueurs.
							
							Tu peux accélérer le déroulement de la bataille en cliquant sur le bouton "Passer".
							
							Le jeu sera terminé quand l'un des 2 joueurs aura le contrôle de la majorité des champs de bataille (3/5).
							""", false);
						GameController.passTutorial();
					}

					Thread thread = new Thread(GameController.getInstance());
					thread.start();
					currentStep = GameController.getStep();
				}
			}
		});
	}

	/**
	 * Sets fields.
	 *
	 * @param springLayout the layout of this panel
	 */
	public void setupFields(SpringLayout springLayout) {
		Field[] fields = GameController.getFields();
		for (Field field : fields) {
			GraphicField graphicField = new GraphicField(field);
			add(graphicField);

			switch (field.fieldProperties) {
				case LIBRARY -> {
					springLayout.putConstraint(SpringLayout.WEST, graphicField, 600, SpringLayout.WEST, this);
					springLayout.putConstraint(SpringLayout.SOUTH, graphicField, -300, SpringLayout.SOUTH, this);
				}
				case BDE -> {
					springLayout.putConstraint(SpringLayout.NORTH, graphicField, 250, SpringLayout.NORTH, this);
					springLayout.putConstraint(SpringLayout.WEST, graphicField, 450, SpringLayout.WEST, this);
				}
				case ADMINISTRATIVE_QUARTER -> {
					springLayout.putConstraint(SpringLayout.NORTH, graphicField, 200, SpringLayout.NORTH, this);
					springLayout.putConstraint(SpringLayout.EAST, graphicField, -300, SpringLayout.EAST, this);
				}
				case INDUSTRIAL_HALLS -> {
					springLayout.putConstraint(SpringLayout.SOUTH, graphicField, -150, SpringLayout.SOUTH, this);
					springLayout.putConstraint(SpringLayout.EAST, graphicField, -200, SpringLayout.EAST, this);
				}
				case SPORTS_HALL -> {
					springLayout.putConstraint(SpringLayout.NORTH, graphicField, 58, SpringLayout.NORTH, this);
					springLayout.putConstraint(SpringLayout.WEST, graphicField, 150, SpringLayout.WEST, this);
				}
			}

			Player controller = field.getController();
			if (controller == null) graphicField.setBottomLabelText("Bataille en cours...");
			else graphicField.setBottomLabelText("Contrôlé par " + controller.getName());

			graphicField.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					MainView.confirmButton.removeActionListener(passAction);
					MainView.switchToPanel(graphicField.getFieldProperties());
				}
			});

			field.addObserver(graphicField);
			graphicField.addObserver("battleState", this);
			FieldPanel associatedPanel = new FieldPanel(field);
			MainView.addPanel(associatedPanel, field.fieldProperties);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("battleState")) {
			MainView.setEvent(((Field) evt.getSource()).fieldProperties.toString() + " a été pris par " + ((Field) evt.getSource()).getController().getName() + " !");
			MainView.switchToPanel(PanelIdentifier.FIELD_ATTRIBUTION_PANEL);
			MainView.removeConfirmationListeners();
		}
	}
}
