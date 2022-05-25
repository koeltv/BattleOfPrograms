package com.view.panel;

import com.model.Soldier;
import com.view.ColorPalette;
import com.view.MainView;
import com.view.component.FieldColumn;
import com.view.component.FieldProperties;
import com.view.component.GraphicSoldier;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.HashMap;

/**
 * Panel used to attribute the soldiers to a field.
 */
public class FieldAttributionPanel extends JPanel {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -2108193670223851849L;

	private final BasePanel soldierPanel;

	private final HashMap<FieldProperties, FieldColumn> fields = new HashMap<>(5);

	private int currentPlayerIndex = 0;

	/**
	 * Create the panel.
	 */
	public FieldAttributionPanel() {
		GridLayout gridLayout = new GridLayout(1, 2);
		setLayout(gridLayout);

		soldierPanel = new BasePanel();
		soldierPanel.changeBackground(BasePanel.class.getResource("/images/camo.jpg"));
		add(soldierPanel);
		soldierPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel statPanel = new JPanel();
		statPanel.setLayout(new GridLayout(1, 5));
		statPanel.setBackground(ColorPalette.BLUE_BACKGROUND.color);
		add(statPanel);

		FieldColumn fieldColumn1 = new FieldColumn(new JPanel(), FieldProperties.LIBRARY);
		statPanel.add(fieldColumn1);
		fields.put(FieldProperties.LIBRARY, fieldColumn1);

		FieldColumn fieldColumn2 = new FieldColumn(new JPanel(), FieldProperties.BDE);
		statPanel.add(fieldColumn2);
		fields.put(FieldProperties.BDE, fieldColumn2);

		FieldColumn fieldColumn3 = new FieldColumn(new JPanel(), FieldProperties.ADMINISTRATIVE_QUARTER);
		statPanel.add(fieldColumn3);
		fields.put(FieldProperties.ADMINISTRATIVE_QUARTER, fieldColumn3);

		FieldColumn fieldColumn4 = new FieldColumn(new JPanel(), FieldProperties.INDUSTRIAL_HALLS);
		statPanel.add(fieldColumn4);
		fields.put(FieldProperties.INDUSTRIAL_HALLS, fieldColumn4);

		FieldColumn fieldColumn5 = new FieldColumn(new JPanel(), FieldProperties.SPORTS_HALL);
		statPanel.add(fieldColumn5);
		fields.put(FieldProperties.SPORTS_HALL, fieldColumn5);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				currentPlayerIndex = 0;
				setupSoldiers();
				MainView.playerIndicator.setVisible(true);
				MainView.playerIndicator.setPlayer(GameController.players[0]);

				MainView.confirmButton.setText("Valider");
				MainView.confirmButton.setEnabled(checkAttribution());
				MainView.confirmButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (currentPlayerIndex < 1) {
							currentPlayerIndex++;
							soldierPanel.removeAll();
							fields.values().forEach(field -> {
								for (Component component : field.getComponents()) {
									if (component instanceof GraphicSoldier) field.remove(component);
								}
								field.repaint();
								field.revalidate();
							});
							MainView.playerIndicator.setPlayer(GameController.players[currentPlayerIndex]);
							setupSoldiers();
						} else {
							GameController.step++;
							MainView.confirmButton.removeActionListener(this);
							MainView.addPanel(new GlobalFieldPanel(), PanelIdentifier.GLOBAL_FIELD_PANEL);
							MainView.switchToPanel(PanelIdentifier.GLOBAL_FIELD_PANEL);
						}
					}
				});
			}
		});
	}

	/**
	 * Add all the soldiers that needs to be attributed to the left side.
	 * If a soldier is a reservist he won't be displayed on the first round.
	 */
	private void setupSoldiers() {
		soldierPanel.removeAll();
		fields.values().forEach(column -> {
			for (Component component : column.getComponents()) {
				if (component instanceof GraphicSoldier soldier) column.remove(soldier);
			}
		});

		for (Soldier soldier : GameController.players[currentPlayerIndex].soldiers) {
			if (!soldier.isReservist() || GameController.step > 3) {
				GraphicSoldier graphicSoldier = GraphicSoldier.createGraphics(soldier);
				graphicSoldier.enableInfos();

				if (graphicSoldier.getAssignedField() == null) soldierPanel.add(graphicSoldier);
				else fields.get(graphicSoldier.getAssignedField()).add(graphicSoldier);

				if (soldierCanBeMoved(graphicSoldier)) {
					graphicSoldier.setSelected(true);
					graphicSoldier.addMouseMotionListener(new MouseAdapter() {
						@Override
						public void mouseDragged(MouseEvent E) {
							int x = graphicSoldier.getX(), y = graphicSoldier.getY();

							x += E.getX() - graphicSoldier.getWidth() / 2;
							y += E.getY() - graphicSoldier.getHeight() / 2;
							graphicSoldier.setBounds(x, y, graphicSoldier.getWidth(), graphicSoldier.getHeight());

							Rectangle soldierAbsoluteBounds = SwingUtilities.convertRectangle(graphicSoldier.getParent(), graphicSoldier.getBounds(), SwingUtilities.getRoot(graphicSoldier));

							boolean fieldFound = false;
							for (FieldProperties field : fields.keySet()) {
								Rectangle fieldBounds = SwingUtilities.convertRectangle(fields.get(field).getParent(), fields.get(field).getBounds(), SwingUtilities.getRoot(fields.get(field)));
								if (!fieldFound && intersects(soldierAbsoluteBounds, fieldBounds)) {
									fields.get(field).setOpaque(true);
									fieldFound = true;
								} else {
									fields.get(field).setOpaque(false);
								}
								fields.get(field).repaint();
							}
						}
					});
					graphicSoldier.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							Rectangle soldierAbsoluteBounds = SwingUtilities.convertRectangle(graphicSoldier.getParent(), graphicSoldier.getBounds(), SwingUtilities.getRoot(graphicSoldier));
							boolean intersectionFound = false;

							for (FieldProperties field : fields.keySet()) {
								Rectangle fieldBounds = SwingUtilities.convertRectangle(fields.get(field).getParent(), fields.get(field).getBounds(), SwingUtilities.getRoot(fields.get(field)));

								if (intersects(soldierAbsoluteBounds, fieldBounds)) {
									intersectionFound = true;
									graphicSoldier.getParent().remove(graphicSoldier);
									fields.get(field).setOpaque(false);

									GameController.moveSoldierToField(soldier, GameController.findFieldByProperties(field));
									fields.get(field).add(graphicSoldier);
									break;
								}
							}

							if (!intersectionFound) {
								GameController.moveSoldierToField(soldier, null);
								graphicSoldier.getParent().remove(graphicSoldier);
								soldierPanel.add(graphicSoldier);
							}

							MainView.confirmButton.setEnabled(checkAttribution());

							repaint();
							revalidate();
						}
					});
				}
			}
		}
	}

	private boolean soldierCanBeMoved(GraphicSoldier graphicSoldier) { //TODO Check if a soldier can be moved from a field
		return true;
	}

	private boolean checkAttribution() { //TODO Enable verify if all soldiers are attributed
//		if (soldierPanel.getComponents() != null) return false;

		for (FieldColumn column : fields.values()) {
			Component[] components = column.getComponents();
			for (int i = 0; i < components.length; i++) {
				if (components[i] instanceof GraphicSoldier) break;
				else if (i == components.length - 1) return false;
			}
		}

		return true;
	}

	/**
	 * Check for intersection of 2 rectangles
	 * Strongly inspired by <a href="https://stackoverflow.com/a/39319801">https://stackoverflow.com/a/39319801</a>.
	 * @param r1 - first rectangle
	 * @param r2 - second rectangle
	 * @return true if the intersection exist, false otherwise
	 */
	private boolean intersects(Rectangle r1, Rectangle r2) {
		int leftX = Math.max(r1.x, r2.x);
		int rightX = (int) Math.min(r1.getMaxX(), r2.getMaxX());
		int topY = Math.max(r1.y,r2.y);
		int botY =  (int) Math.min(r1.getMaxY(), r2.getMaxY());

		return (rightX > leftX) && (botY > topY);
	}

}
