package com.view.panel;

import com.view.MainView;
import com.view.component.FieldProperties;
import com.view.component.GraphicField;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;

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
		springLayout.putConstraint(SpringLayout.WEST, sportField, 46, SpringLayout.WEST, this);
		add(sportField);
		sportField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainView.switchToPanel(FieldProperties.SPORTS_HALL);
			}
		});
		
		GraphicField bdeField = new GraphicField(FieldProperties.BDE);
		springLayout.putConstraint(SpringLayout.NORTH, bdeField, 118, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, bdeField, 93, SpringLayout.WEST, this);
		add(bdeField);
		bdeField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainView.switchToPanel(FieldProperties.BDE);
			}
		});
		
		GraphicField libraryField = new GraphicField(FieldProperties.LIBRARY);
		springLayout.putConstraint(SpringLayout.WEST, libraryField, 165, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, libraryField, -135, SpringLayout.SOUTH, this);
		add(libraryField);
		libraryField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainView.switchToPanel(FieldProperties.LIBRARY);
			}
		});

		GraphicField administrativeField = new GraphicField(FieldProperties.ADMINISTRATIVE_QUARTER);
		springLayout.putConstraint(SpringLayout.NORTH, administrativeField, 132, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, administrativeField, -92, SpringLayout.EAST, this);
		add(administrativeField);
		administrativeField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainView.switchToPanel(FieldProperties.ADMINISTRATIVE_QUARTER);
			}
		});

		GraphicField industrialField = new GraphicField(FieldProperties.INDUSTRIAL_HALLS);
		springLayout.putConstraint(SpringLayout.SOUTH, industrialField, -61, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, industrialField, -68, SpringLayout.EAST, this);
		add(industrialField);
		industrialField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainView.switchToPanel(FieldProperties.INDUSTRIAL_HALLS);
			}
		});

		changeBackground(FieldPanel.class.getResource("/images/interactive_map.PNG"));
		MainView.addPanel(new FieldPanel(FieldProperties.SPORTS_HALL), FieldProperties.SPORTS_HALL);
		MainView.addPanel(new FieldPanel(FieldProperties.BDE), FieldProperties.BDE);
		MainView.addPanel(new FieldPanel(FieldProperties.LIBRARY), FieldProperties.LIBRARY);
		MainView.addPanel(new FieldPanel(FieldProperties.ADMINISTRATIVE_QUARTER), FieldProperties.ADMINISTRATIVE_QUARTER);
		MainView.addPanel(new FieldPanel(FieldProperties.INDUSTRIAL_HALLS), FieldProperties.INDUSTRIAL_HALLS);
	}

}