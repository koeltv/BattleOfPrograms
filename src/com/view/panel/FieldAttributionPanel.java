package com.view.panel;

import com.model.Soldier;
import com.view.ColorPalette;
import com.view.MainView;
import com.view.component.DragAndDrop;
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

import static com.view.utils.GeometryUtils.getAbsoluteBounds;
import static com.view.utils.GeometryUtils.intersects;

/**
 * Panel used to attribute the soldiers to a field.
 */
public class FieldAttributionPanel extends JPanel {

	/**
	 * The constant serialVersionUID.
	 */
	@Serial
	private static final long serialVersionUID = -2108193670223851849L;

	/**
	 * The Soldier panel.
	 */
	private final BasePanel soldierPanel;

	/**
	 * The Field columns.
	 */
	private final List<FieldColumn> fieldColumns = new ArrayList<>(5);

	/**
	 * The Current player index.
	 */
	private int currentPlayerIndex = 0;

	private final DragAndDrop dragAndDrop = new DragAndDrop();

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

		setupDragAndDrop();

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				currentPlayerIndex = 0;
				setupSoldiers();
				MainView.showPlayerIndicator(true);
				MainView.setPlayerIndicator(GameController.getPlayers()[currentPlayerIndex]);

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

		for (Soldier soldier : GameController.getPlayers()[currentPlayerIndex].getSoldiers()) {
			if (!soldier.isReservist() || GameController.getStep() > 3) {
				GraphicSoldier graphicSoldier = GraphicSoldier.createGraphics(soldier);
				graphicSoldier.enableInfos();

				FieldColumn column = getColumn(soldier.getAssignedField());
				if (column == null) soldierPanel.add(graphicSoldier);
				else column.add(graphicSoldier);
				addDragAndDrop(graphicSoldier);
			}
		}

		repaint();
		revalidate();
	}

	private void setupDragAndDrop() {
		/*
		  Change the opacity of the columns.
		  The first column that intersects with the component is the one that will be opaque, the others are transparent.
		 */
		dragAndDrop.setOnDrag((componentBounds) -> {
			boolean intersectingFieldFound = false;
			for (FieldColumn column : fieldColumns) {
				if (!intersectingFieldFound && intersects(componentBounds, getAbsoluteBounds(column))) {
					intersectingFieldFound = true;
					column.setOpaque(true);
				} else {
					column.setOpaque(false);
				}
				column.repaint();
			}
		});

		dragAndDrop.setOnDrop(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				GraphicSoldier graphicSoldier = (GraphicSoldier) e.getComponent();
				boolean intersectionFound = false;

				for (FieldColumn column : fieldColumns) {
					if (intersects(getAbsoluteBounds(graphicSoldier), getAbsoluteBounds(column))) {
						intersectionFound = true;
						column.setOpaque(false);
						if (GameController.findFieldByProperties(column.fieldProperties).isAssignable(graphicSoldier.getSoldier())) {
							graphicSoldier.getParent().remove(graphicSoldier);
							GameController.moveSoldierToField(graphicSoldier.getSoldier(), GameController.findFieldByProperties(column.fieldProperties));
							column.add(graphicSoldier);
						}
						break;
					}
				}

				if (!intersectionFound && GameController.getStep() < 3) { //Move soldier back to left panel
					GameController.moveSoldierToField(graphicSoldier.getSoldier(), null);
					graphicSoldier.getParent().remove(graphicSoldier);
					soldierPanel.add(graphicSoldier);
				}

				MainView.confirmButton.setEnabled(GameController.checkAttribution(currentPlayerIndex));

				repaint();
				revalidate();
			}
		});

		dragAndDrop.setDragAndDropConditions((soldierComponent) -> {
			GraphicSoldier graphicSoldier = (GraphicSoldier) soldierComponent;
			if (getColumn(graphicSoldier.getAssignedField()) == null) {
				return true;
			} else {
				for (FieldColumn column : fieldColumns) {
					for (Component component : column.getComponents()) {
						if (!component.equals(graphicSoldier) && component instanceof GraphicSoldier columnSoldier) {
							dragAndDrop.allowDragAndDrop(columnSoldier, columnSoldier.canBeMoved());
							columnSoldier.setSelected(columnSoldier.canBeMoved());
						}
					}
				}
			}

			graphicSoldier.setSelected(graphicSoldier.getSoldier().canBeMoved());
			return graphicSoldier.canBeMoved();
		});
	}

	/**
	 * Add the listeners for the drag and drop.
	 *
	 * @param graphicSoldier the component to add drag and drop to
	 */
	private void addDragAndDrop(GraphicSoldier graphicSoldier) {
		if (graphicSoldier.canBeMoved()) graphicSoldier.setSelected(true);
		dragAndDrop.addDragAndDrop(graphicSoldier);
	}

	/**
	 * Get the column corresponding to a field.
	 *
	 * @param fieldProperties the properties of the field
	 * @return the corresponding column
	 */
	private FieldColumn getColumn(FieldProperties fieldProperties) {
		for (FieldColumn column : fieldColumns) {
			if (column.fieldProperties == fieldProperties) return column;
		}
		return null;
	}
}
