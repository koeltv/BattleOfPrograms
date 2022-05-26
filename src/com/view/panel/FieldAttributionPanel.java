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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

	private final List<FieldColumn> fieldColumns = new ArrayList<>(5);

	private int currentPlayerIndex = 0;

	/**
	 * Create the panel.
	 */
	public FieldAttributionPanel() { //TODO Add randomize button
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
		fieldColumns.add(fieldColumn1);

		FieldColumn fieldColumn2 = new FieldColumn(new JPanel(), FieldProperties.BDE);
		statPanel.add(fieldColumn2);
		fieldColumns.add(fieldColumn2);

		FieldColumn fieldColumn3 = new FieldColumn(new JPanel(), FieldProperties.ADMINISTRATIVE_QUARTER);
		statPanel.add(fieldColumn3);
		fieldColumns.add(fieldColumn3);

		FieldColumn fieldColumn4 = new FieldColumn(new JPanel(), FieldProperties.INDUSTRIAL_HALLS);
		statPanel.add(fieldColumn4);
		fieldColumns.add(fieldColumn4);

		FieldColumn fieldColumn5 = new FieldColumn(new JPanel(), FieldProperties.SPORTS_HALL);
		statPanel.add(fieldColumn5);
		fieldColumns.add(fieldColumn5);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				currentPlayerIndex = 0;
				setupSoldiers();
				MainView.showPlayerIndicator(true);
				MainView.setPlayerIndicator(GameController.getPlayers()[0]);

				MainView.confirmButton.setVisible(true);
				MainView.confirmButton.setText("Valider");
				MainView.confirmButton.setEnabled(GameController.checkAttribution(currentPlayerIndex));
				MainView.confirmButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (currentPlayerIndex < 1) {
							MainView.setPlayerIndicator(GameController.getPlayers()[++currentPlayerIndex]);
							MainView.confirmButton.setEnabled(GameController.checkAttribution(currentPlayerIndex));
							setupSoldiers();
						} else {
							GameController.nextStep();
							MainView.confirmButton.removeActionListener(this);
							MainView.addPanel(new GlobalFieldPanel(), PanelIdentifier.GLOBAL_FIELD_PANEL);
							MainView.switchToPanel(PanelIdentifier.GLOBAL_FIELD_PANEL);
						}
					}
				});

				if (GameController.displayTutorial()) {
					MainView.displayDialog("""
							Sur cette interface, tu peux assigner les soldats aux différents champs de batailles.
														
							Pour choisir un soldat il suffit de cliquer dessus, puis de le faire glisser sur un champ de bataille.
														
							Attention ! Il faut que tous les soldats soient attribués à un champ pour continuer !
							""", false);
				}
			}
		});
	}

	/**
	 * Add all the soldiers that needs to be attributed to the left side.
	 * If a soldier is a reservist he won't be displayed on the first round.
	 */
	private void setupSoldiers() {
		soldierPanel.removeAll();
		for (FieldColumn column : fieldColumns) {
			for (Component component : column.getComponents()) {
				if (component instanceof GraphicSoldier) column.remove(component);
			}
		}

		for (Soldier soldier : GameController.getPlayers()[currentPlayerIndex].soldiers) {
			if (!soldier.isReservist() || GameController.getStep() > 3) {
				GraphicSoldier graphicSoldier = GraphicSoldier.createGraphics(soldier);
				graphicSoldier.enableInfos();

				FieldColumn column = getColumn(soldier.getAssignedField());
				if (column == null) soldierPanel.add(graphicSoldier);
				else column.add(graphicSoldier);
				addSoldierListeners(graphicSoldier);
			}
		}

		repaint();
		revalidate();
	}

	/**
	 * Add the listeners for the drag&drop.
	 * @param graphicSoldier the component to add listeners on
	 */
	private void addSoldierListeners(GraphicSoldier graphicSoldier) {
		MouseAdapter dragSoldier = new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getComponent().getX(), y = e.getComponent().getY();

				x += e.getX() - e.getComponent().getWidth() / 2;
				y += e.getY() - e.getComponent().getHeight() / 2;
				e.getComponent().setBounds(x, y, e.getComponent().getWidth(), e.getComponent().getHeight());

				Rectangle soldierAbsoluteBounds = SwingUtilities.convertRectangle(e.getComponent().getParent(), e.getComponent().getBounds(), SwingUtilities.getRoot(e.getComponent()));

				boolean fieldFound = false;
				for (FieldColumn column : fieldColumns) {
					Rectangle fieldBounds = SwingUtilities.convertRectangle(column.getParent(), column.getBounds(), SwingUtilities.getRoot(column));
					if (!fieldFound && intersects(soldierAbsoluteBounds, fieldBounds)) {
						column.setOpaque(true);
						fieldFound = true;
					} else {
						column.setOpaque(false);
					}
					column.repaint();
				}
			}
		};

		MouseAdapter dropSoldier = new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Rectangle soldierAbsoluteBounds = SwingUtilities.convertRectangle(e.getComponent().getParent(), e.getComponent().getBounds(), SwingUtilities.getRoot(e.getComponent()));
				boolean intersectionFound = false;

				for (FieldColumn column : fieldColumns) {
					Rectangle fieldBounds = SwingUtilities.convertRectangle(column.getParent(), column.getBounds(), SwingUtilities.getRoot(column));

					if (intersects(soldierAbsoluteBounds, fieldBounds)) {
						intersectionFound = true;
						column.setOpaque(false);
						if (Objects.requireNonNull(GameController.findFieldByProperties(column.fieldProperties)).isAssignable()) {
							e.getComponent().getParent().remove(e.getComponent());

							GameController.moveSoldierToField(((GraphicSoldier) e.getComponent()).getSoldier(), GameController.findFieldByProperties(column.fieldProperties));
							column.add(e.getComponent());
						}
						break;
					}
				}

				if (!intersectionFound && GameController.getStep() < 3) {
					GameController.moveSoldierToField(((GraphicSoldier) e.getComponent()).getSoldier(), null);
					e.getComponent().getParent().remove(e.getComponent());
					soldierPanel.add(e.getComponent());
				}

				MainView.confirmButton.setEnabled(GameController.checkAttribution(currentPlayerIndex));

				repaint();
				revalidate();
			}
		};

		if (graphicSoldier.canBeMoved()) {
			graphicSoldier.setSelected(true);
			graphicSoldier.addMouseMotionListener(dragSoldier);
			graphicSoldier.addMouseListener(dropSoldier);
		}

		graphicSoldier.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				FieldColumn fieldColumn = getColumn(((GraphicSoldier) e.getComponent()).getAssignedField());
				if (fieldColumn == null) {
					addListeners((GraphicSoldier) e.getComponent());
				} else {
					for (FieldColumn column : fieldColumns) {
						for (Component component : column.getComponents()) {
							if (!component.equals(e.getComponent()) && component instanceof GraphicSoldier columnSoldier) {
								if (columnSoldier.canBeMoved()) addListeners(columnSoldier);
								else removeListeners(columnSoldier);
							}
						}
					}
				}

				if (!((GraphicSoldier) e.getComponent()).canBeMoved()) {
					removeListeners((GraphicSoldier) e.getComponent());
				}

				repaint();
				revalidate();
			}

			public void addListeners(GraphicSoldier soldier) {
				soldier.setSelected(true);
				for (MouseListener listener : soldier.getMouseListeners()) soldier.removeMouseListener(listener);
				for (MouseMotionListener listener : soldier.getMouseMotionListeners()) soldier.removeMouseMotionListener(listener);
				soldier.addMouseMotionListener(dragSoldier);
				soldier.addMouseListener(dropSoldier);
				soldier.addMouseListener(this);
			}

			public void removeListeners(GraphicSoldier soldier) {
				soldier.setSelected(false);
				for (MouseListener listener : soldier.getMouseListeners()) soldier.removeMouseListener(listener);
				for (MouseMotionListener listener : soldier.getMouseMotionListeners()) soldier.removeMouseMotionListener(listener);
				soldier.addMouseListener(this);
			}
		});
	}

	/**
	 * Get the column corresponding to a field.
	 * @param fieldProperties the properties of the field
	 * @return the corresponding column
	 */
	private FieldColumn getColumn(FieldProperties fieldProperties) {
		for (FieldColumn column : fieldColumns) {
			if (column.fieldProperties == fieldProperties) return column;
		}
		return null;
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
