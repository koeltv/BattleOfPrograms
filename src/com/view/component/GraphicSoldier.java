package com.view.component;

import com.model.*;
import com.view.ColorPalette;
import com.view.Resource;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serial;

/**
 * The type Graphic soldier.
 */
public class GraphicSoldier extends JPanel implements PropertyChangeListener {

	/**
	 * The constant serialVersionUID.
	 */
	@Serial
	private static final long serialVersionUID = -5370972161337730381L;

	/**
	 * The linked Soldier.
	 */
	private final Soldier soldier;

	/**
	 * The Soldier display.
	 */
	private final JLabel soldierDisplay;

	/**
	 * The Life bar.
	 */
	private final JProgressBar lifeBar = new JProgressBar();

	/**
	 * The Stats label.
	 */
	private final JLabel statsLabel = new JLabel();

	/**
	 * Instantiates a new Graphic soldier based on a WarMaster.
	 *
	 * @param soldier the soldier
	 */
	private GraphicSoldier(WarMaster soldier) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.soldier = soldier;

		add(lifeBar);
		soldierDisplay = new JLabel(Resource.TRANSPARENT_WAR_MASTER.image);
	}

	/**
	 * Instantiates a new Graphic soldier based on a EliteSoldier.
	 *
	 * @param soldier the soldier
	 */
	private GraphicSoldier(EliteSoldier soldier) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.soldier = soldier;

		add(lifeBar);
		soldierDisplay = new JLabel(Resource.TRANSPARENT_ELITE_SOLDIER.image);
	}

	/**
	 * Instantiates a new Graphic soldier based on a Soldier.
	 *
	 * @param soldier the soldier
	 */
	private GraphicSoldier(Soldier soldier) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.soldier = soldier;

		add(lifeBar);
		soldierDisplay = new JLabel(Resource.TRANSPARENT_SOLDIER.image);
	}

	/**
	 * Create the graphics corresponding to a soldier.
	 *
	 * @param soldier the soldier to create graphics for
	 * @return the corresponding graphic soldier
	 */
	public static GraphicSoldier createGraphics(Soldier soldier) {
		GraphicSoldier graphicSoldier;
		if (soldier instanceof WarMaster warMaster) {
			graphicSoldier = new GraphicSoldier(warMaster);
		} else if (soldier instanceof EliteSoldier eliteSoldier) {
			graphicSoldier = new GraphicSoldier(eliteSoldier);
		} else {
			graphicSoldier = new GraphicSoldier(soldier);
		}

		graphicSoldier.add(graphicSoldier.soldierDisplay);
		graphicSoldier.add(graphicSoldier.statsLabel);

		soldier.addObserver(graphicSoldier);
		graphicSoldier.setOpaque(false);
		graphicSoldier.soldierDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		graphicSoldier.soldierDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
		graphicSoldier.lifeBar.setVisible(false);
		graphicSoldier.statsLabel.setVisible(false);
		return graphicSoldier;
	}

	/**
	 * Sets selected.
	 *
	 * @param selected the selected
	 */
	public void setSelected(boolean selected) {
		ImageIcon image;
		if (soldier instanceof WarMaster)
			image = (selected ? Resource.WAR_MASTER : Resource.TRANSPARENT_WAR_MASTER).image;
		else if (soldier instanceof EliteSoldier)
			image = (selected ? Resource.ELITE_SOLDIER : Resource.TRANSPARENT_ELITE_SOLDIER).image;
		else
			image = (selected ? Resource.SOLDIER : Resource.TRANSPARENT_SOLDIER).image;

		soldierDisplay.setIcon(image);
		repaint();
	}

	/**
	 * Enable infos.
	 */
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

	/**
	 * Stats to string.
	 *
	 * @return the string
	 */
	private String statsToString() {
		return soldier.getStrength() + " " + soldier.getResistance() + " " + soldier.getInitiative() + " " + soldier.getConstitution() + " " + soldier.getDexterity();
	}

	/**
	 * Gets soldier.
	 *
	 * @return the soldier
	 */
	public Soldier getSoldier() {
		return soldier;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GraphicSoldier graphicSoldier) {
			return this.soldier == graphicSoldier.soldier;
		}
		return false;
	}

	///////////////////////////////////////////////////////////////////////////
	// Interface for soldier
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Is reservist boolean.
	 *
	 * @return the boolean
	 */
	public boolean isReservist() {
		return soldier.isReservist();
	}

	/**
	 * Sets reservist.
	 *
	 * @param reservist the reservist
	 */
	public void setReservist(boolean reservist) {
		soldier.setReservist(reservist);
	}

	/**
	 * Gets strength.
	 *
	 * @return the strength
	 */
	public int getStrength() {
		return soldier.getStrength();
	}

	/**
	 * Sets strength.
	 *
	 * @param value the value
	 * @return the strength
	 */
	public boolean setStrength(int value) {
		if (soldier.setStrength(value)) {
			statsLabel.setText(statsToString());
			repaint();
			return true;
		}
		return false;
	}

	/**
	 * Gets dexterity.
	 *
	 * @return the dexterity
	 */
	public int getDexterity() {
		return soldier.getDexterity();
	}

	/**
	 * Sets dexterity.
	 *
	 * @param dexterity the dexterity
	 * @return the dexterity
	 */
	public boolean setDexterity(int dexterity) {
		if (soldier.setDexterity(dexterity)) {
			statsLabel.setText(statsToString());
			repaint();
			return true;
		}
		return false;
	}

	/**
	 * Gets resistance.
	 *
	 * @return the resistance
	 */
	public int getResistance() {
		return soldier.getResistance();
	}

	/**
	 * Sets resistance.
	 *
	 * @param resistance the resistance
	 * @return the resistance
	 */
	public boolean setResistance(int resistance) {
		if (soldier.setResistance(resistance)) {
			statsLabel.setText(statsToString());
			repaint();
			return true;
		}
		return false;
	}

	/**
	 * Gets constitution.
	 *
	 * @return the constitution
	 */
	public int getConstitution() {
		return soldier.getConstitution();
	}

	/**
	 * Sets constitution.
	 *
	 * @param constitution the constitution
	 * @return the constitution
	 */
	public boolean setConstitution(int constitution) {
		if (soldier.setConstitution(constitution)) {
			statsLabel.setText(statsToString());
			repaint();
			return true;
		}
		return false;
	}

	/**
	 * Gets initiative.
	 *
	 * @return the initiative
	 */
	public int getInitiative() {
		return soldier.getInitiative();
	}

	/**
	 * Sets initiative.
	 *
	 * @param initiative the initiative
	 * @return the initiative
	 */
	public boolean setInitiative(int initiative) {
		if (soldier.setInitiative(initiative)) {
			statsLabel.setText(statsToString());
			repaint();
			return true;
		}
		return false;
	}

	/**
	 * Gets ai.
	 *
	 * @return the ai
	 */
	public AI getAi() {
		return soldier.getAi();
	}

	/**
	 * Sets ai.
	 *
	 * @param ai the ai
	 */
	public void setAi(AI ai) {
		soldier.setAi(ai);
	}

	/**
	 * Gets assigned field.
	 *
	 * @return the assigned field
	 */
	public Field getAssignedField() {
		return soldier.getAssignedField();
	}

	/**
	 * Can be moved boolean.
	 *
	 * @return the boolean
	 */
	public boolean canBeMoved() {
		return soldier.canBeMoved();
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
