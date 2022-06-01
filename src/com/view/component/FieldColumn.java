package com.view.component;

import com.model.Field;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

/**
 * Represent a column to assign soldiers to a field.
 * Needed for {@link com.view.panel.FieldAttributionPanel}.
 */
public class FieldColumn extends JScrollPane {

	/**
	 * The constant serialVersionUID.
	 */
	@Serial
	private static final long serialVersionUID = 1762692385236278793L;

	/**
	 * The Field.
	 */
	private final Field field;

	/**
	 * The Panel.
	 */
	private final JPanel panel;

	/**
	 * Instantiates a new Field column.
	 *
	 * @param panel the content panel
	 * @param field the corresponding field
	 */
	private FieldColumn(JPanel panel, Field field) {
		super(panel);
		this.panel = panel;
		this.field = field;
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		add(new GraphicField(field));

		setPreferredSize(new Dimension(10, 10));
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getVerticalScrollBar().setUnitIncrement(16);

		setOpaque(false);
		getViewport().setOpaque(false);
		panel.setOpaque(false);
	}

	/**
	 * Create a field column.
	 *
	 * @param field the corresponding field
	 * @return the field column
	 */
	public static FieldColumn createColumn(Field field) {
		return new FieldColumn(new JPanel(), field);
	}

	/**
	 * Gets field.
	 *
	 * @return the field
	 */
	public Field getField() {
		return field;
	}

	@Override
	public Component add(Component comp) {
		((JComponent) comp).setAlignmentX(Component.CENTER_ALIGNMENT);
		return panel.add(comp);
	}

	@Override
	public void remove(Component comp) {
		panel.remove(comp);
	}

	@Override
	public Component[] getComponents() {
		return panel.getComponents();
	}
}
