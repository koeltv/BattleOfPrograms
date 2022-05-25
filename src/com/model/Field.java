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

public class Field implements PropertyChangeListener {
	public final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	public final FieldProperties fieldProperties;

	public final List<Soldier> leftSide = new ArrayList<>();
	public final List<Soldier> rightSide = new ArrayList<>();

	private List<Soldier> attackOrder = new ArrayList<>();

	private boolean isControlled = false;

	public Field(FieldProperties fieldProperties) {
		this.fieldProperties = fieldProperties;
	}

	public List<Soldier> getPlayerSoldiers(Player player) {
		return player == GameController.players[0] ? leftSide : rightSide;
	}

	public void addSoldier(Soldier soldier) {
		soldier.sendToField(fieldProperties);
		if (Arrays.stream(GameController.players[0].soldiers).anyMatch(soldier1 -> soldier1 == soldier)) {
			leftSide.add(soldier);
			changeSupport.firePropertyChange("soldierP1Added", null, soldier);
		} else if (Arrays.stream(GameController.players[1].soldiers).anyMatch(soldier1 -> soldier1 == soldier)) {
			rightSide.add(soldier);
			changeSupport.firePropertyChange("soldierP2Added", null, soldier);
		}
	}

	public void removeSoldier(Soldier soldier) {
		soldier.sendToField(null);
		if (leftSide.stream().anyMatch(soldier1 -> soldier1 == soldier)) {
			leftSide.remove(soldier);
			changeSupport.firePropertyChange("soldierRemoved", soldier, null);
		} else if (rightSide.stream().anyMatch(soldier1 -> soldier1 == soldier)) {
			rightSide.remove(soldier);
			changeSupport.firePropertyChange("soldierRemoved", soldier, null);
		}
	}

	public void setAttackOrder() {
		attackOrder.addAll(leftSide.stream().toList());
		attackOrder.addAll(rightSide.stream().toList());
		attackOrder = attackOrder.stream().sorted(Comparator.comparingInt(Soldier::getInitiative)).collect(Collectors.toList());
		changeSupport.firePropertyChange("initialSoldierAmount", 0, attackOrder.size());
	}

	public boolean battle() {
		if (!isControlled) {
			attackOrder = attackOrder.stream().filter(Soldier::isAlive).collect(Collectors.toList());
			List<Soldier> left = leftSide.stream().filter(Soldier::isAlive).toList();
			List<Soldier> right = rightSide.stream().filter(Soldier::isAlive).toList();

			if (left.size() < 1 || right.size() < 1) {
				changeSupport.firePropertyChange("battleState", true, false);
				isControlled = true;
				return false;
			} else {
				Soldier soldier = attackOrder.get(0);
				if (left.contains(soldier)) {
					soldier.attack(right);
				} else {
					soldier.attack(left);
				}
				attackOrder.remove(soldier);
				attackOrder.add(soldier);

				return getController() == null;
			}
		}
		return true;
	}

	public Player getController() {
		if (leftSide.stream().noneMatch(Soldier::isAlive)) return GameController.players[1];
		else if (rightSide.stream().noneMatch(Soldier::isAlive)) return GameController.players[0];
		else return null;
	}

	public boolean contains(Soldier soldier) {
		return leftSide.contains(soldier) || rightSide.contains(soldier);
	}

	/**
	 * Check if soldiers can be assigned to a field.
	 * Soldiers cannot be added if :
	 * - The field is under control
	 * @return true if soldiers can be added, false otherwise
	 */
	public boolean isAssignable() {
		return GameController.step < 3 || !isControlled;
	}

	///////////////////////////////////////////////////////////////////////////
	// PropertyChange methods
	///////////////////////////////////////////////////////////////////////////

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
