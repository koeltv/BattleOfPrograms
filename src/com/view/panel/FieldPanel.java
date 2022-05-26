package com.view.panel;

import com.model.Field;
import com.model.Soldier;
import com.view.MainView;
import com.view.component.GraphicSoldier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serial;

/**
 * Panel used to display one specific battlefield.
 */
public class FieldPanel extends BasePanel implements PropertyChangeListener { //TODO Animations ?

	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = -6721489079020570444L;
	private final JPanel firstPlayerPanel;
	private final JPanel secondPlayerPanel;

	private final Field field;

	/**
	 * Create the panel.
	 *
	 * @param field the field
	 */
	public FieldPanel(Field field) {
		this.field = field;
		field.addObserver(this);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

		JLabel fieldNameLabel = new JLabel("Champ de bataille : " + field.fieldProperties.toString());
		fieldNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		fieldNameLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(fieldNameLabel);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		add(panel);
		GridLayout gridLayout = new GridLayout(1, 2);
		panel.setLayout(gridLayout);

		firstPlayerPanel = new JPanel();
		firstPlayerPanel.setOpaque(false);
		panel.add(firstPlayerPanel);
		firstPlayerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		secondPlayerPanel = new JPanel();
		secondPlayerPanel.setOpaque(false);
		panel.add(secondPlayerPanel);
		secondPlayerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		setupSoldiers();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				MainView.confirmButton.setText("Retour");
				MainView.confirmButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						MainView.confirmButton.removeActionListener(this);
						MainView.switchToPanel(PanelIdentifier.GLOBAL_FIELD_PANEL);
					}
				});
			}
		});
	}

	/**
	 * Set up the soldiers on both sides.
	 */
	private void setupSoldiers() {
		for (Soldier soldier : field.leftSide) {
			GraphicSoldier graphicSoldier = GraphicSoldier.createGraphics(soldier);
			graphicSoldier.setSelected(true);
			graphicSoldier.enableInfos();
			firstPlayerPanel.add(graphicSoldier);
		}
		for (Soldier soldier : field.rightSide) {
			GraphicSoldier graphicSoldier = GraphicSoldier.createGraphics(soldier);
			graphicSoldier.setSelected(true);
			graphicSoldier.enableInfos();
			secondPlayerPanel.add(graphicSoldier);
		}
		repaint();
		revalidate();

		field.setAttackOrder();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
			case "soldierRemoved" -> {
				for (Component component : firstPlayerPanel.getComponents()) {
					if (component.equals(evt.getOldValue())) {
						firstPlayerPanel.remove(component);
						return;
					}
				}
				for (Component component : secondPlayerPanel.getComponents()) {
					if (component.equals(evt.getOldValue())) {
						secondPlayerPanel.remove(component);
						return;
					}
				}
			}
			case "soldierP1Added" -> firstPlayerPanel.add(GraphicSoldier.createGraphics((Soldier) evt.getNewValue()));
			case "soldierP2Added" -> secondPlayerPanel.add(GraphicSoldier.createGraphics((Soldier) evt.getNewValue()));
		}
	}
}
