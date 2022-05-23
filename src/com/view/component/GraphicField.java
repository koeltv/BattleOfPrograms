package com.view.component;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serial;
import java.util.Arrays;
import java.util.Objects;

public class GraphicField extends JPanel implements PropertyChangeListener {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 368210040522610077L;

	private final JLabel upperLabel, bottomLabel;

	/**
	 * Create the panel.
	 */
	public GraphicField(FieldProperties fieldProperties) {
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
		upperLabel.setEnabled(true);
	}

	public void setBottomLabelText(String text) {
		bottomLabel.setText(text);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
			case "battleState" -> {
				if (evt.getNewValue() instanceof Boolean isBattleHappening) {
					if (isBattleHappening) setBottomLabelText("Bataille en cours...");
					else bottomLabel.setEnabled(false);
				}
			}
			case "soldierState" -> {
				if (evt.getNewValue() instanceof GraphicSoldier[] soldiers) {
					long soldiersLeft = Arrays.stream(soldiers).filter(GraphicSoldier::isDead).count();
					if (soldiers.length == soldiersLeft) upperLabel.setEnabled(false);
					else setUpperLabelText(soldiersLeft + "/" + soldiers.length + " soldats survivants");
				}
			}
		}
	}
}
