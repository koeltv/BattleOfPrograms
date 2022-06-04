package com.view.component;

import com.model.Field;
import com.model.FieldProperties;
import com.view.ColorPalette;
import com.view.MainView;
import com.view.Resource;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serial;

/**
 * The type Graphic field.
 */
public class GraphicField extends JPanel implements PropertyChangeListener {

	/**
	 * The constant serialVersionUID.
	 */
	@Serial
	private static final long serialVersionUID = 368210040522610077L;

	/**
	 * The Change support.
	 */
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	/**
	 * The corresponding field.
	 */
	private final Field field;

	/**
	 * The Upper label.
	 */
	private final JLabel upperLabel, /**
	 * The Bottom label.
	 */
	bottomLabel;

	/**
	 * Create the panel.
	 *
	 * @param field the corresponding field
	 */
	public GraphicField(Field field) {
		this.field = field;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

		upperLabel = new JLabel();
		upperLabel.setHorizontalAlignment(SwingConstants.CENTER);
		upperLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		upperLabel.setVisible(false);
		add(upperLabel);

		JLabel iconLabel = new JLabel(field.fieldProperties.resource.image);
		iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(iconLabel);

		bottomLabel = new JLabel(field.fieldProperties.toString());
		bottomLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(bottomLabel);
	}

	/**
	 * Gets field properties.
	 *
	 * @return the field properties
	 */
	public FieldProperties getFieldProperties() {
		return field.fieldProperties;
	}

	/**
	 * Add an observer.
	 *
	 * @param property the property to listen to
	 * @param listener the listener
	 */
	public void addObserver(String property, PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(property, listener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
			case "battleState" -> {
				if ((boolean) evt.getNewValue()) setBottomLabelText("Bataille en cours...");
				else {
					bottomLabel.setVisible(false);
					changeSupport.firePropertyChange(evt);
				}
			}
			case "soldierAmount" ->
					setUpperLabelText(evt.getNewValue() + "/" + evt.getOldValue() + " soldats survivants");
			case "initialSoldierAmount" -> {
				int highBound = (int) ((int) evt.getOldValue() == -1 ? evt.getNewValue() : evt.getOldValue());
				setUpperLabelText(evt.getNewValue() + "/" + highBound + " soldats survivants");
			}
		}
		repaint();
		revalidate();
	}

	/**
	 * Sets the bottom label text.
	 *
	 * @param text the text
	 */
	public void setBottomLabelText(String text) {
		bottomLabel.setText(text);
		bottomLabel.setForeground(ColorPalette.TEXT_BLUE.color);
		bottomLabel.setVisible(true);
	}

	/**
	 * Sets the upper label text.
	 *
	 * @param text the text
	 */
	public void setUpperLabelText(String text) {
		upperLabel.setText(text);
		upperLabel.setForeground(ColorPalette.ORANGE.color);
		upperLabel.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (MainView.noEvent()) {
			Image displayImage = null;
			if (field.getController() != null) {
				displayImage = Resource.WHITE_FLAG.image.getImage();
			} else if (GameController.getStep() > 2) {
				displayImage = Resource.DUST_CLOUD.image.getImage();
			}

			g.drawImage(displayImage, 0, 0, getWidth(), getHeight(), this);
		}
	}
}
