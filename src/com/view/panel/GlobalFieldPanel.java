package com.view.panel;

import com.model.Field;
import com.view.MainView;
import com.view.component.FieldProperties;
import com.view.component.GraphicField;
import controller.GameController;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;

/**
 * Panel used to display the overhaul battlefield.
 */
public class GlobalFieldPanel extends BasePanel {

	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = -2611728061732290991L;

	private boolean playing = false;

	private final GraphicField[] graphicFields = new GraphicField[5];

	/**
	 * Create the panel.
	 */
	public GlobalFieldPanel() { //TODO Positioning is temporary, it needs to be redone
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		changeBackground(FieldPanel.class.getResource("/images/interactive_map.PNG"));
		setAlpha(0.4f);
		MainView.playerIndicator.setVisible(false);

		GraphicField sportField = new GraphicField(FieldProperties.SPORTS_HALL);
		springLayout.putConstraint(SpringLayout.NORTH, sportField, 58, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, sportField, 150, SpringLayout.WEST, this);
		add(sportField);
		graphicFields[0] = sportField;

		GraphicField bdeField = new GraphicField(FieldProperties.BDE);
		springLayout.putConstraint(SpringLayout.NORTH, bdeField, 250, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, bdeField, 450, SpringLayout.WEST, this);
		add(bdeField);
		graphicFields[1] = bdeField;

		GraphicField libraryField = new GraphicField(FieldProperties.LIBRARY);
		springLayout.putConstraint(SpringLayout.WEST, libraryField, 600, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, libraryField, -300, SpringLayout.SOUTH, this);
		add(libraryField);
		graphicFields[2] = libraryField;

		GraphicField administrativeField = new GraphicField(FieldProperties.ADMINISTRATIVE_QUARTER);
		springLayout.putConstraint(SpringLayout.NORTH, administrativeField, 200, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, administrativeField, -300, SpringLayout.EAST, this);
		add(administrativeField);
		graphicFields[3] = administrativeField;

		GraphicField industrialField = new GraphicField(FieldProperties.INDUSTRIAL_HALLS);
		springLayout.putConstraint(SpringLayout.SOUTH, industrialField, -150, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, industrialField, -200, SpringLayout.EAST, this);
		add(industrialField);
		graphicFields[4] = industrialField;

		setupFields();

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				MainView.confirmButton.setText("Passer");

				if (!playing) {
					Thread thread = new Thread(GameController.getInstance());
					thread.start();
					playing = true;
				}
			}
		});
	}

	public void setupFields() {
		Field[] fields = new Field[5];
		for (int i = 0; i < graphicFields.length; i++) {
			GraphicField graphicField = graphicFields[i];
			graphicField.setBottomLabelText("Bataille en cours...");
			graphicField.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					MainView.switchToPanel(graphicField.getFieldProperties());
				}
			});

			fields[i] = new Field(graphicField.getFieldProperties());
			fields[i].addObserver(graphicField);
			FieldPanel associatedPanel = new FieldPanel(fields[i]);
			MainView.addPanel(associatedPanel, graphicField.getFieldProperties());
		}

		GameController.addFieldPanels(fields);
	}

}
