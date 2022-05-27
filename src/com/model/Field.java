package com.model;

import com.view.component.FieldProperties;
import controller.GameController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represent one of the possibles battle zone.
 */
public class Field implements PropertyChangeListener {
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	/**
	 * The Field properties.
	 */
	public final FieldProperties fieldProperties;

	/**
	 * The Left side corresponding to player 1.
	 */
	public final List<Soldier> leftSide = new ArrayList<>();

	/**
	 * The Right side corresponding to player 2.
	 */
	public final List<Soldier> rightSide = new ArrayList<>();

	private List<Soldier> attackOrder = new ArrayList<>();

	private boolean isControlled = false;

	/**
	 * Instantiates a new Field.
	 *
	 * @param fieldProperties the field properties
	 */
	public Field(FieldProperties fieldProperties) {
		this.fieldProperties = fieldProperties;
	}

	/**
	 * Gets soldiers linked to the given player.
	 *
	 * @param player the player
	 * @return the player soldiers
	 */
	public List<Soldier> getPlayerSoldiers(Player player) {
		return player == GameController.getPlayers()[0] ? leftSide : rightSide;
	}

	/**
	 * Add a soldier to the field.
	 *
	 * @param soldier - soldier to add
	 */
	public void addSoldier(Soldier soldier) {
		soldier.addObserver("dead", this);
		soldier.sendToField(fieldProperties);
		if (Arrays.stream(GameController.getPlayers()[0].getSoldiers()).anyMatch(soldier1 -> soldier1 == soldier)) {
			leftSide.add(soldier);
			changeSupport.firePropertyChange("soldierP1Added", null, soldier);
		} else if (Arrays.stream(GameController.getPlayers()[1].getSoldiers()).anyMatch(soldier1 -> soldier1 == soldier)) {
			rightSide.add(soldier);
			changeSupport.firePropertyChange("soldierP2Added", null, soldier);
		}
	}

	/**
	 * Remove a soldier from the field
	 *
	 * @param soldier - soldier to remove
	 */
	public void removeSoldier(Soldier soldier) {
		soldier.sendToField(null);
		soldier.removeObserver(this);
		if (leftSide.stream().anyMatch(soldier1 -> soldier1 == soldier)) {
			leftSide.remove(soldier);
			changeSupport.firePropertyChange("soldierRemoved", soldier, null);
		} else if (rightSide.stream().anyMatch(soldier1 -> soldier1 == soldier)) {
			rightSide.remove(soldier);
			changeSupport.firePropertyChange("soldierRemoved", soldier, null);
		}
	}

	/**
	 * Sets the attack order based on the initiative stat.
	 */
	public void setAttackOrder() {
		attackOrder.clear();
		attackOrder.addAll(leftSide.stream().toList());
		attackOrder.addAll(rightSide.stream().toList());
		int initialSize = attackOrder.size();
		attackOrder = attackOrder.stream().filter(Soldier::isAlive).sorted(Comparator.comparingInt(Soldier::getInitiative)).collect(Collectors.toList());
		changeSupport.firePropertyChange("initialSoldierAmount", (initialSize == attackOrder.size() ? -1 : initialSize), attackOrder.size());
	}

	/**
	 * Launch the next attack.
	 * The next soldier to attack (based on the initiative stat) will launch an attack on his chosen target.
	 *
	 * @return true if the battle is still going, false otherwise
	 */
	public boolean battle() {
		if (!isControlled) {
			attackOrder = attackOrder.stream().filter(Soldier::isAlive).collect(Collectors.toList());
			List<Soldier> left = leftSide.stream().filter(Soldier::isAlive).toList();
			List<Soldier> right = rightSide.stream().filter(Soldier::isAlive).toList();

			if (left.size() >= 1 && right.size() >= 1) {
				Soldier soldier = attackOrder.get(0);
				soldier.attack(left.contains(soldier) ? right : left);
				attackOrder.remove(soldier);
				attackOrder.add(soldier);
			}

			if (getController() != null) {
				changeSupport.firePropertyChange("battleState", true, false);
				isControlled = true;
				return false;
			} else
				return true;
		}
		return true;
	}

	/**
	 * Gets the current player controlling the field.
	 *
	 * @return the controller of the field or null if there isn't any
	 */
	public Player getController() {
		if (leftSide.stream().noneMatch(Soldier::isAlive) && GameController.getStep() > 2) return GameController.getPlayers()[1];
		else if (rightSide.stream().noneMatch(Soldier::isAlive) && GameController.getStep() > 2) return GameController.getPlayers()[0];
		else return null;
	}

	/**
	 * Check if the field contain a soldier.
	 *
	 * @param soldier the soldier
	 * @return true if it does, false otherwise
	 */
	public boolean contains(Soldier soldier) {
		return leftSide.contains(soldier) || rightSide.contains(soldier);
	}

	/**
	 * Check if soldiers can be assigned to a field.
	 * Soldiers cannot be added if :
	 * - The field is under control
	 *
	 * @return true if soldiers can be added, false otherwise
	 */
	public boolean isAssignable() {
		return GameController.getStep() < 3 || !isControlled;
	}

	///////////////////////////////////////////////////////////////////////////
	// PropertyChange methods
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Add an observer.
	 *
	 * @param propertyChangeListener - the property change listener
	 */
	public void addObserver(PropertyChangeListener propertyChangeListener) {
		this.changeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("dead")) {
			long totalSoldiers = leftSide.size() + rightSide.size();
			long soldiersLeft = attackOrder.stream().filter(Soldier::isAlive).count();
			changeSupport.firePropertyChange("soldierAmount", totalSoldiers, soldiersLeft);
		}
	}
}
