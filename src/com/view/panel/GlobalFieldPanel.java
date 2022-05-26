package com.view.panel;

import com.model.Field;
import com.model.Player;
import com.view.MainView;
import com.view.component.FieldProperties;
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
	 *
	 */
	@Serial
	private static final long serialVersionUID = -2611728061732290991L;

	private int currentStep = -1;

	private final GraphicField[] graphicFields = new GraphicField[5];

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

		GraphicField sportField = new GraphicField(FieldProperties.SPORTS_HALL);
		springLayout.putConstraint(SpringLayout.NORTH, sportField, 58, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, sportField, 150, SpringLayout.WEST, this);
		add(sportField);
		graphicFields[4] = sportField;

		GraphicField bdeField = new GraphicField(FieldProperties.BDE);
		springLayout.putConstraint(SpringLayout.NORTH, bdeField, 250, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, bdeField, 450, SpringLayout.WEST, this);
		add(bdeField);
		graphicFields[1] = bdeField;

		GraphicField libraryField = new GraphicField(FieldProperties.LIBRARY);
		springLayout.putConstraint(SpringLayout.WEST, libraryField, 600, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, libraryField, -300, SpringLayout.SOUTH, this);
		add(libraryField);
		graphicFields[0] = libraryField;

		GraphicField administrativeField = new GraphicField(FieldProperties.ADMINISTRATIVE_QUARTER);
		springLayout.putConstraint(SpringLayout.NORTH, administrativeField, 200, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, administrativeField, -300, SpringLayout.EAST, this);
		add(administrativeField);
		graphicFields[2] = administrativeField;

		GraphicField industrialField = new GraphicField(FieldProperties.INDUSTRIAL_HALLS);
		springLayout.putConstraint(SpringLayout.SOUTH, industrialField, -150, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, industrialField, -200, SpringLayout.EAST, this);
		add(industrialField);
		graphicFields[3] = industrialField;

		setupFields();

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

	public void setupFields() {
		Field[] fields = GameController.getFields();
		for (int i = 0; i < graphicFields.length; i++) {
			GraphicField graphicField = graphicFields[i];

			Player controller = fields[i].getController();
			if (controller == null) graphicField.setBottomLabelText("Bataille en cours...");
			else graphicField.setBottomLabelText("Contrôlé par " + controller.getName());

			graphicField.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					MainView.confirmButton.removeActionListener(passAction);
					MainView.switchToPanel(graphicField.getFieldProperties());
				}
			});

			fields[i].addObserver(graphicField);
			graphicField.addObserver("battleState", this);
			FieldPanel associatedPanel = new FieldPanel(fields[i]);
			MainView.addPanel(associatedPanel, fields[i].fieldProperties);
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
