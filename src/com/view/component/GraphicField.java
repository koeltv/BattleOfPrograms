package com.view.component;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.Objects;

public class GraphicField extends JPanel {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 368210040522610077L;

	/**
	 * Create the panel.
	 */
	public GraphicField(FieldProperties fieldProperties) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

		JLabel IconLabel = new JLabel(new ImageIcon(Objects.requireNonNull(fieldProperties.url)));
		IconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		IconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(IconLabel);
		
		JLabel nameLabel = new JLabel(fieldProperties.name);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(nameLabel);
	}

}
