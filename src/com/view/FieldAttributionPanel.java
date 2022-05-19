package com.view;

import com.model.EliteSoldier;
import com.model.Soldier;
import com.model.WarMaster;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FieldAttributionPanel extends JPanel {

	private final BasePanel soldierPanel;

	private final JPanel[] fields = new JPanel[5];

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

		JPanel field1 = new JPanel();
		fields[0] = field1;
		statPanel.add(field1);

		JPanel field2 = new JPanel();
		fields[1] = field2;
		statPanel.add(field2);

		JPanel field3 = new JPanel();
		fields[2] = field3;
		statPanel.add(field3);

		JPanel field4 = new JPanel();
		fields[3] = field4;
		statPanel.add(field4);

		JPanel field5 = new JPanel();
		fields[4] = field5;
		statPanel.add(field5);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				System.out.println("Now showing !");
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

						for (JPanel field : fields) {
							Rectangle fieldBounds = SwingUtilities.convertRectangle(field.getParent(), field.getBounds(), SwingUtilities.getRoot(field));

							if (intersects(soldierAbsoluteBounds, fieldBounds)) {
								graphicSoldier.getParent().remove(graphicSoldier);
								field.add(graphicSoldier);
								field.repaint();
								field.revalidate();
								return;
							}
						}

						graphicSoldier.getParent().remove(graphicSoldier);
						soldierPanel.add(graphicSoldier);
						soldierPanel.repaint();
						soldierPanel.revalidate();
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
