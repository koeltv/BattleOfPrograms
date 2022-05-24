package com.view.component;

import com.model.*;
import com.view.ColorPalette;
import com.view.panel.AttributePanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serial;
import java.net.URL;
import java.util.Objects;

public class GraphicSoldier extends JPanel implements PropertyChangeListener {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -5370972161337730381L;
	
	private static final URL soldierUrl = AttributePanel.class.getResource("/images/soldier.png");
	private static final URL transparentSoldierUrl = AttributePanel.class.getResource("/images/soldier-t.png");
	private static final URL eliteSoldierUrl = AttributePanel.class.getResource("/images/elite_soldier.png");
	private static final URL transparentEliteSoldierUrl = AttributePanel.class.getResource("/images/elite_soldier-t.png");
	private static final URL warMasterUrl = AttributePanel.class.getResource("/images/commander.png");
	private static final URL transparentWarMasterUrl = AttributePanel.class.getResource("/images/commander-t.png");

	private final Soldier soldier;

	private final JLabel soldierDisplay;

	private final JProgressBar lifeBar = new JProgressBar();

	private final JLabel statsLabel = new JLabel();

	private GraphicSoldier(WarMaster soldier) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.soldier = soldier;

		add(lifeBar);
		soldierDisplay = new JLabel(new ImageIcon(Objects.requireNonNull(transparentWarMasterUrl)));
		add(soldierDisplay);
		add(statsLabel);
	}

	private GraphicSoldier(EliteSoldier soldier) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.soldier = soldier;

		add(lifeBar);
		soldierDisplay = new JLabel(new ImageIcon(Objects.requireNonNull(transparentEliteSoldierUrl)));
		add(soldierDisplay);
		add(statsLabel);
	}

	/**
	 * @wbp.parser.constructor
	 */
	private GraphicSoldier(Soldier soldier) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.soldier = soldier;

		add(lifeBar);
		soldierDisplay = new JLabel(new ImageIcon(Objects.requireNonNull(transparentSoldierUrl)));
		add(soldierDisplay);
		add(statsLabel);
	}

	public static GraphicSoldier createGraphics(Soldier soldier) {
		GraphicSoldier graphicSoldier;
		if (soldier instanceof WarMaster warMaster) {
			graphicSoldier = new GraphicSoldier(warMaster);
		} else if (soldier instanceof EliteSoldier eliteSoldier) {
			graphicSoldier = new GraphicSoldier(eliteSoldier);
		} else {
			graphicSoldier = new GraphicSoldier(soldier);
		}

		soldier.addObserver(graphicSoldier);
		graphicSoldier.setOpaque(false);
		graphicSoldier.soldierDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		graphicSoldier.soldierDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
		graphicSoldier.lifeBar.setVisible(false);
		graphicSoldier.statsLabel.setVisible(false);
		return graphicSoldier;
	}

	public void setSelected(boolean selected) {
		URL url;
		if (soldier instanceof WarMaster) url = selected ? warMasterUrl : transparentWarMasterUrl;
		else if (soldier instanceof EliteSoldier) url = selected ? eliteSoldierUrl : transparentEliteSoldierUrl;
		else url = selected ? soldierUrl : transparentSoldierUrl;

		soldierDisplay.setIcon(new ImageIcon(Objects.requireNonNull(url)));
		repaint();
	}

	private String statsToString() {
		return soldier.getStrength() + " " + soldier.getResistance() + " " + soldier.getInitiative() + " " + soldier.getConstitution() + " " + soldier.getDexterity();
	}

	public void enableInfos() {
		lifeBar.setForeground(ColorPalette.RED.color);
		lifeBar.setMaximum(soldier.getMaxLifePoints() * 100);
		lifeBar.setValue((int) (soldier.getLifePoints() * 100));
		lifeBar.setAlignmentX(Component.CENTER_ALIGNMENT);
		lifeBar.setVisible(true);

		statsLabel.setText(statsToString());
		statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsLabel.setVisible(true);
	}

	///////////////////////////////////////////////////////////////////////////
	// Interface for soldier
	///////////////////////////////////////////////////////////////////////////

	public boolean isReservist() {
		return soldier.isReservist();
	}

	public void setReservist(boolean reservist) {
		soldier.setReservist(reservist);
	}

	public int getStrength() {
		return soldier.getStrength();
	}

	public boolean setStrength(int value) {
		if (soldier.setStrength(value)) {
			statsLabel.setText(statsToString());
			repaint();
			return true;
		}
		return false;
	}

	public int getDexterity() {
		return soldier.getDexterity();
	}

	public boolean setDexterity(int dexterity) {
		if (soldier.setDexterity(dexterity)) {
			statsLabel.setText(statsToString());
			repaint();
			return true;
		}
		return false;
	}

	public int getResistance() {
		return soldier.getResistance();
	}

	public boolean setResistance(int resistance) {
		if (soldier.setResistance(resistance)) {
			statsLabel.setText(statsToString());
			repaint();
			return true;
		}
		return false;
	}

	public int getConstitution() {
		return soldier.getConstitution();
	}

	public boolean setConstitution(int constitution) {
		if (soldier.setConstitution(constitution)) {
			statsLabel.setText(statsToString());
			repaint();
			return true;
		}
		return false;
	}

	public int getInitiative() {
		return soldier.getInitiative();
	}

	public boolean setInitiative(int initiative) {
		if (soldier.setInitiative(initiative)) {
			statsLabel.setText(statsToString());
			repaint();
			return true;
		}
		return false;
	}

	public AI getAi() {
		return soldier.getAi();
	}

	public void setAi(AI ai) {
		soldier.setAi(ai);
	}

	public FieldProperties getAssignedField() {
		return soldier.getAssignedField();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Soldier soldier) return this.soldier == soldier;
		else if (obj instanceof GraphicSoldier graphicSoldier) return this.soldier == graphicSoldier.soldier;
		else return false;
	}

	///////////////////////////////////////////////////////////////////////////
	// PropertyChange method
	///////////////////////////////////////////////////////////////////////////

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("damage") || evt.getPropertyName().equals("dead")) {
			lifeBar.setValue((int) ((Float) evt.getNewValue() * 100));
			repaint();
		}
	}
}
