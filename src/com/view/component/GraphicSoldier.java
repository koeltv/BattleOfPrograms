package com.view.component;

import com.model.AI;
import com.model.EliteSoldier;
import com.model.Soldier;
import com.model.WarMaster;
import com.view.ColorPalette;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serial;
import java.util.Objects;

public class GraphicSoldier extends JPanel implements PropertyChangeListener {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -5370972161337730381L;

	private static final ImageIcon soldierImage = new ImageIcon(Objects.requireNonNull(GraphicSoldier.class.getResource("/images/soldier.png")));
	private static final ImageIcon transparentSoldierImage = new ImageIcon(Objects.requireNonNull(GraphicSoldier.class.getResource("/images/soldier-t.png")));
	private static final ImageIcon eliteSoldierImage = new ImageIcon(Objects.requireNonNull(GraphicSoldier.class.getResource("/images/elite_soldier.png")));
	private static final ImageIcon transparentEliteSoldierImage = new ImageIcon(Objects.requireNonNull(GraphicSoldier.class.getResource("/images/elite_soldier-t.png")));
	private static final ImageIcon warMasterImage = new ImageIcon(Objects.requireNonNull(GraphicSoldier.class.getResource("/images/commander.png")));
	private static final ImageIcon transparentWarMasterImage = new ImageIcon(Objects.requireNonNull(GraphicSoldier.class.getResource("/images/commander-t.png")));

	private final Soldier soldier;

	private final JLabel soldierDisplay;

	private final JProgressBar lifeBar = new JProgressBar();

	private final JLabel statsLabel = new JLabel();

	private GraphicSoldier(WarMaster soldier) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.soldier = soldier;

		add(lifeBar);
		soldierDisplay = new JLabel(transparentWarMasterImage);
	}

	private GraphicSoldier(EliteSoldier soldier) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.soldier = soldier;

		add(lifeBar);
		soldierDisplay = new JLabel(transparentEliteSoldierImage);
	}

	private GraphicSoldier(Soldier soldier) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.soldier = soldier;

		add(lifeBar);
		soldierDisplay = new JLabel(transparentSoldierImage);
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

	public void setSelected(boolean selected) {
		ImageIcon image;
		if (soldier instanceof WarMaster) image = selected ? warMasterImage : transparentWarMasterImage;
		else if (soldier instanceof EliteSoldier) image = selected ? eliteSoldierImage : transparentEliteSoldierImage;
		else image = selected ? soldierImage : transparentSoldierImage;

		soldierDisplay.setIcon(image);
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
