package com.view.component;

import javax.swing.*;
import java.awt.*;

/**
 * Represent a column to assign soldiers to a field.
 * Needed for {@link com.view.panel.FieldAttributionPanel}.
 */
public class FieldColumn extends JScrollPane {

	private final JPanel panel;

	public FieldColumn(JPanel panel, FieldProperties fieldProperties) {
		super(panel);
		this.panel = panel;
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		add(new GraphicField(fieldProperties));

		setPreferredSize(new Dimension(10, 10));
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getVerticalScrollBar().setUnitIncrement(16);

		setOpaque(false);
		getViewport().setOpaque(false);
		panel.setOpaque(false);
	}

	@Override
	public Component add(Component comp) {
		((JComponent) comp).setAlignmentX(Component.CENTER_ALIGNMENT);
		return panel.add(comp);
	}
}
