package com.view.panel;

import com.view.MainView;
import com.view.component.FieldProperties;
import com.view.component.GraphicField;

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

	/**
	 * Create the panel.
	 */
	public GlobalFieldPanel() { //TODO Positioning is temporary, it needs to be redone
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		GraphicField sportField = new GraphicField(FieldProperties.SPORTS_HALL);
		springLayout.putConstraint(SpringLayout.NORTH, sportField, 58, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, sportField, 150, SpringLayout.WEST, this);
		add(sportField);
		sportField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainView.switchToPanel(FieldProperties.SPORTS_HALL);
				sportField.removeMouseListener(this);
			}
		});
		FieldPanel sportPanel = new FieldPanel(FieldProperties.SPORTS_HALL);
		sportPanel.addObserver(sportField);
		MainView.addPanel(sportPanel, FieldProperties.SPORTS_HALL);

		GraphicField bdeField = new GraphicField(FieldProperties.BDE);
		springLayout.putConstraint(SpringLayout.NORTH, bdeField, 250, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, bdeField, 450, SpringLayout.WEST, this);
		add(bdeField);
		bdeField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainView.switchToPanel(FieldProperties.BDE);
				bdeField.removeMouseListener(this);
			}
		});
		FieldPanel bdePanel = new FieldPanel(FieldProperties.BDE);
		bdePanel.addObserver(bdeField);
		MainView.addPanel(bdePanel, FieldProperties.BDE);

		GraphicField libraryField = new GraphicField(FieldProperties.LIBRARY);
		springLayout.putConstraint(SpringLayout.WEST, libraryField, 600, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, libraryField, -300, SpringLayout.SOUTH, this);
		add(libraryField);
		libraryField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainView.switchToPanel(FieldProperties.LIBRARY);
				libraryField.removeMouseListener(this);
			}
		});
		FieldPanel libraryPanel = new FieldPanel(FieldProperties.LIBRARY);
		libraryPanel.addObserver(libraryField);
		MainView.addPanel(libraryPanel, FieldProperties.LIBRARY);

		GraphicField administrativeField = new GraphicField(FieldProperties.ADMINISTRATIVE_QUARTER);
		springLayout.putConstraint(SpringLayout.NORTH, administrativeField, 200, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, administrativeField, -300, SpringLayout.EAST, this);
		add(administrativeField);
		administrativeField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainView.switchToPanel(FieldProperties.ADMINISTRATIVE_QUARTER);
				administrativeField.removeMouseListener(this);
			}
		});
		FieldPanel administrativePanel = new FieldPanel(FieldProperties.ADMINISTRATIVE_QUARTER);
		administrativePanel.addObserver(administrativeField);
		MainView.addPanel(administrativePanel, FieldProperties.ADMINISTRATIVE_QUARTER);

		GraphicField industrialField = new GraphicField(FieldProperties.INDUSTRIAL_HALLS);
		springLayout.putConstraint(SpringLayout.SOUTH, industrialField, -150, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, industrialField, -200, SpringLayout.EAST, this);
		add(industrialField);
		industrialField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainView.switchToPanel(FieldProperties.INDUSTRIAL_HALLS);
				industrialField.removeMouseListener(this);
			}
		});
		FieldPanel industrialPanel = new FieldPanel(FieldProperties.INDUSTRIAL_HALLS);
		industrialPanel.addObserver(industrialField);
		MainView.addPanel(industrialPanel, FieldProperties.INDUSTRIAL_HALLS);

		changeBackground(FieldPanel.class.getResource("/images/interactive_map.PNG"));

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				MainView.confirmButton.setText("Passer");
			}
		});
	}

}
