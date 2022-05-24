package com.view.component;

import com.view.ColorPalette;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serial;
import java.util.Objects;

public class GraphicField extends JPanel implements PropertyChangeListener {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 368210040522610077L;

	private final FieldProperties fieldProperties;

	private final JLabel upperLabel, bottomLabel;

	/**
	 * Create the panel.
	 */
	public GraphicField(FieldProperties fieldProperties) {
		this.fieldProperties = fieldProperties;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

		upperLabel = new JLabel();
		upperLabel.setHorizontalAlignment(SwingConstants.CENTER);
		upperLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		upperLabel.setVisible(false);
		add(upperLabel);

		JLabel iconLabel = new JLabel(new ImageIcon(Objects.requireNonNull(fieldProperties.url)));
		iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(iconLabel);
		
		bottomLabel = new JLabel(fieldProperties.name);
		bottomLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(bottomLabel);
	}

	public void setUpperLabelText(String text) {
		upperLabel.setText(text);
		upperLabel.setForeground(ColorPalette.ORANGE.color);
		upperLabel.setVisible(true);
	}

	public void setBottomLabelText(String text) {
		bottomLabel.setText(text);
		bottomLabel.setForeground(ColorPalette.TEXT_BLUE.color);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
			case "battleState" -> {
				if (evt.getNewValue() instanceof Boolean isBattleHappening) {
					if (isBattleHappening) setBottomLabelText("Bataille en cours...");
					else bottomLabel.setVisible(false);
				}
			}
			case "soldierAmount" -> setUpperLabelText(evt.getNewValue() + "/" + evt.getOldValue() + " soldats survivants");
			case "initialSoldierAmount" -> setUpperLabelText(evt.getNewValue() + "/" + evt.getNewValue() + " soldats survivants");
		}
		repaint();
		revalidate();
	}

	public FieldProperties getFieldProperties() {
		return fieldProperties;
	}
}
