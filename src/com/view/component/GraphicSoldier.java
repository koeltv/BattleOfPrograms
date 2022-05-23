package com.view.component;

import com.model.AI;
import com.model.EliteSoldier;
import com.model.Soldier;
import com.model.WarMaster;
import com.view.ColorPalette;
import com.view.panel.AttributePanel;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.net.URL;
import java.util.Objects;

public class GraphicSoldier extends JPanel { //TODO Implement life-bar and stats

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

	public boolean selected;

	private final Soldier soldier;

	private final JLabel soldierDisplay;

	private final JProgressBar lifeBar = new JProgressBar();

	private final JLabel statsLabel = new JLabel();

	GraphicSoldier(WarMaster soldier) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);
		this.soldier = soldier;

		lifeBar.setVisible(false);
		add(lifeBar);

		soldierDisplay = new JLabel(new ImageIcon(Objects.requireNonNull(transparentWarMasterUrl)));
		soldierDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		soldierDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(soldierDisplay);

		statsLabel.setVisible(false);
		add(statsLabel);
	}

	GraphicSoldier(EliteSoldier soldier) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);
		this.soldier = soldier;

		lifeBar.setVisible(false);
		add(lifeBar);

		soldierDisplay = new JLabel(new ImageIcon(Objects.requireNonNull(transparentEliteSoldierUrl)));
		soldierDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		soldierDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(soldierDisplay);

		statsLabel.setVisible(false);
		add(statsLabel);
	}

	/**
	 * @wbp.parser.constructor
	 */
	GraphicSoldier(Soldier soldier) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);
		this.soldier = soldier;

		lifeBar.setVisible(false);
		add(lifeBar);

		soldierDisplay = new JLabel(new ImageIcon(Objects.requireNonNull(transparentSoldierUrl)));
		soldierDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		soldierDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(soldierDisplay);

		statsLabel.setVisible(false);
		add(statsLabel);
	}

	public static GraphicSoldier createGraphics(Soldier soldier) {
		if (soldier instanceof WarMaster warMaster) {
			return new GraphicSoldier(warMaster);
		} else if (soldier instanceof EliteSoldier eliteSoldier) {
			return new GraphicSoldier(eliteSoldier);
		} else {
			return new GraphicSoldier(soldier);
		}
	}

	public void setSelected(boolean selected) {
		this.selected = selected;

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
		lifeBar.setMaximum(soldier.getMaxLifePoints());
		lifeBar.setValue(soldier.getLifePoints());
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

	public void sendToField(FieldProperties field) {
		soldier.sendToField(field);
	}
}
