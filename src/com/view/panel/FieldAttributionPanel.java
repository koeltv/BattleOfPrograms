package com.view.panel;

import com.model.EliteSoldier;
import com.model.Soldier;
import com.model.WarMaster;
import com.view.ColorPalette;
import com.view.MainView;
import com.view.component.FieldColumn;
import com.view.component.FieldProperties;
import com.view.component.GraphicSoldier;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;

public class FieldAttributionPanel extends JPanel {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -2108193670223851849L;

	private final BasePanel soldierPanel;

	private final FieldColumn[] fields = new FieldColumn[5];

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
		fields[0] = fieldColumn1;

		FieldColumn fieldColumn2 = new FieldColumn(new JPanel(), FieldProperties.BDE);
		statPanel.add(fieldColumn2);
		fields[1] = fieldColumn2;

		FieldColumn fieldColumn3 = new FieldColumn(new JPanel(), FieldProperties.ADMINISTRATIVE_QUARTER);
		statPanel.add(fieldColumn3);
		fields[2] = fieldColumn3;

		FieldColumn fieldColumn4 = new FieldColumn(new JPanel(), FieldProperties.INDUSTRIAL_HALLS);
		statPanel.add(fieldColumn4);
		fields[3] = fieldColumn4;

		FieldColumn fieldColumn5 = new FieldColumn(new JPanel(), FieldProperties.SPORTS_HALL);
		statPanel.add(fieldColumn5);
		fields[4] = fieldColumn5;

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				setupSoldiers();
				MainView.playerIndicator.setPlayer(GameController.players[0]);
			}
		});
	}

	public void setupSoldiers() {
		for (Soldier soldier : GameController.players[0].soldiers) {
			if (!soldier.isReservist()) {
				GraphicSoldier graphicSoldier;
				if (soldier instanceof WarMaster) {
					graphicSoldier = new GraphicSoldier(new WarMaster());
				} else if (soldier instanceof EliteSoldier) {
					graphicSoldier = new GraphicSoldier(new EliteSoldier());
				} else {
					graphicSoldier = new GraphicSoldier(new Soldier());
				}

				graphicSoldier.setSelected(true);
				soldierPanel.add(graphicSoldier);

				graphicSoldier.addMouseMotionListener(new MouseAdapter() {
					@Override
					public void mouseDragged(MouseEvent E) {
						int x = graphicSoldier.getX(), y = graphicSoldier.getY();

						x += E.getX() - graphicSoldier.getWidth() / 2;
						y += E.getY() - graphicSoldier.getHeight() / 2;
						graphicSoldier.setBounds(x, y, graphicSoldier.getWidth(), graphicSoldier.getHeight());
					}
				});
				graphicSoldier.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						Rectangle soldierAbsoluteBounds = SwingUtilities.convertRectangle(graphicSoldier.getParent(), graphicSoldier.getBounds(), SwingUtilities.getRoot(graphicSoldier));
						boolean intersectionFound = false;

						for (JComponent field : fields) {
							Rectangle fieldBounds = SwingUtilities.convertRectangle(field.getParent(), field.getBounds(), SwingUtilities.getRoot(field));

							if (intersects(soldierAbsoluteBounds, fieldBounds)) {
								intersectionFound = true;
								graphicSoldier.getParent().remove(graphicSoldier);
								field.add(graphicSoldier);
								break;
							}
						}

						if (!intersectionFound) {
							graphicSoldier.getParent().remove(graphicSoldier);
							soldierPanel.add(graphicSoldier);
						}
						repaint();
						revalidate();
					}
				});
			}
		}
	}

	/**
	 * Check for intersection of 2 rectangles
	 * Strongly inspired by <a href="https://stackoverflow.com/a/39319801">https://stackoverflow.com/a/39319801</a>.
	 * @param r1 - first rectangle
	 * @param r2 - second rectangle
	 * @return true if the intersection exist, false otherwise
	 */
	public boolean intersects(Rectangle r1, Rectangle r2) {
		int leftX = Math.max(r1.x, r2.x);
		int rightX = (int) Math.min(r1.getMaxX(), r2.getMaxX());
		int topY = Math.max(r1.y,r2.y);
		int botY =  (int) Math.min(r1.getMaxY(), r2.getMaxY());

		return (rightX > leftX) && (botY > topY);
	}

}
