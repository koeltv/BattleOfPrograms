package com.view.panel;

import com.model.Fighter;
import com.model.Player;
import com.model.Soldier;
import com.view.MainView;
import com.view.component.FieldProperties;
import com.view.component.GraphicSoldier;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Panel used to display one specific battlefield.
 */
public class FieldPanel extends BasePanel { //TODO Animations ? + Separate Model

	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = -6721489079020570444L;
	private final FieldProperties fieldProperties;
	private final JPanel firstPlayerPanel;
	private final JPanel secondPlayerPanel;

	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	private List<Fighter> leftSide = new ArrayList<>();
	private List<Fighter> rightSide = new ArrayList<>();

	private List<Fighter> attackOrder = new ArrayList<>();

	/**
	 * Create the panel.
	 *
	 * @param fieldProperties the field properties
	 */
	public FieldPanel(FieldProperties fieldProperties) {
		this.fieldProperties = fieldProperties;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

		JLabel fieldNameLabel = new JLabel("Champ de bataille : " + fieldProperties.name);
		fieldNameLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(fieldNameLabel);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		add(panel);
		GridLayout gridLayout = new GridLayout(1, 2);
		panel.setLayout(gridLayout);

		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		panel.add(leftPanel);
		GridBagLayout gbl_firstPlayerPanel = new GridBagLayout();
		gbl_firstPlayerPanel.columnWidths = new int[]{0, 0};
		gbl_firstPlayerPanel.rowHeights = new int[]{0, 0};
		gbl_firstPlayerPanel.columnWeights = new double[]{};
		gbl_firstPlayerPanel.rowWeights = new double[]{};
		leftPanel.setLayout(gbl_firstPlayerPanel);

		firstPlayerPanel = new JPanel();
		firstPlayerPanel.setOpaque(false);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		leftPanel.add(firstPlayerPanel, gbc_panel_1);
		firstPlayerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		panel.add(rightPanel);
		GridBagLayout gbl_secondPlayerPanel = new GridBagLayout();
		gbl_secondPlayerPanel.columnWidths = new int[]{0, 0};
		gbl_secondPlayerPanel.rowHeights = new int[]{0, 0};
		gbl_secondPlayerPanel.columnWeights = new double[]{};
		gbl_secondPlayerPanel.rowWeights = new double[]{};
		rightPanel.setLayout(gbl_secondPlayerPanel);

		secondPlayerPanel = new JPanel();
		secondPlayerPanel.setOpaque(false);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		rightPanel.add(secondPlayerPanel, gbc_panel_2);
		secondPlayerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		setupSoldiers();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				MainView.confirmButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						MainView.confirmButton.removeActionListener(this);
						MainView.switchToPanel(PanelIdentifier.GLOBAL_FIELD_PANEL);
					}
				});

				battle();
			}
		});
	}

	public void battle() {
		attackOrder = attackOrder.stream().filter(fighter -> !fighter.isDead()).collect(Collectors.toList());
		leftSide = leftSide.stream().filter(fighter -> !fighter.isDead()).collect(Collectors.toList());
		rightSide = rightSide.stream().filter(fighter -> !fighter.isDead()).collect(Collectors.toList());

		if (leftSide.size() < 1 || rightSide.size() < 1) {
			GameController.battleContinues = false;
			changeSupport.firePropertyChange("battleState", true, false);
		} else {
			Fighter fighter = attackOrder.get(0);
			if (leftSide.contains(fighter)) {
				fighter.attack(rightSide);
			} else {
				fighter.attack(leftSide);
			}
			attackOrder.remove(fighter);
			attackOrder.add(fighter);
		}
			//		changeSupport.firePropertyChange("soldierState", "oldValue", "newValue"); TODO Trigger update when a soldier dies
	}

	/**
	 * Set up the soldiers on both sides.
	 */
	private void setupSoldiers() {
		Player[] players = GameController.players;
		for (int i = 0; i < players.length; i++) {
			for (Soldier soldier : players[i].soldiers) {
				if (soldier.assignedField == fieldProperties) {
					GraphicSoldier graphicSoldier = GraphicSoldier.createGraphics(soldier);
					graphicSoldier.setSelected(true);
					graphicSoldier.enableInfos();
					if (i < 1) {
						firstPlayerPanel.add(graphicSoldier);
						leftSide.add(graphicSoldier);
					} else {
						secondPlayerPanel.add(graphicSoldier);
						rightSide.add(graphicSoldier);
					}
				}
			}
		}
		repaint();
		revalidate();

		attackOrder.addAll(leftSide.stream().toList());
		attackOrder.addAll(rightSide.stream().toList());
		attackOrder = attackOrder.stream().sorted(Comparator.comparingInt(Fighter::getInitiative)).collect(Collectors.toList());
	}

	public void addObserver(PropertyChangeListener propertyChangeListener) {
		this.changeSupport.addPropertyChangeListener(propertyChangeListener);
	}
}
