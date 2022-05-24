package com.model;

import com.view.component.FieldProperties;
import controller.GameController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Field implements PropertyChangeListener {
	public final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	public final FieldProperties fieldProperties;

	public final List<Fighter> leftSide = new ArrayList<>();
	public final List<Fighter> rightSide = new ArrayList<>();

	private List<Fighter> attackOrder = new ArrayList<>();

	public Field(FieldProperties fieldProperties) {
		this.fieldProperties = fieldProperties;
	}

	public void assignSoldiers() {
		Player[] players = GameController.players;
		for (int i = 0; i < players.length; i++) {
			for (Soldier soldier : players[i].soldiers) {
				if (soldier.assignedField == fieldProperties) {
					(i < 1 ? leftSide : rightSide).add(soldier);
					soldier.addObserver(this);
				}
			}
		}
	}

	public void setAttackOrder() {
		attackOrder.addAll(leftSide.stream().toList());
		attackOrder.addAll(rightSide.stream().toList());
		attackOrder = attackOrder.stream().sorted(Comparator.comparingInt(Fighter::getInitiative)).collect(Collectors.toList());
		changeSupport.firePropertyChange("initialSoldierAmount", 0, attackOrder.size());
	}

	public void battle() {
		attackOrder = attackOrder.stream().filter(Fighter::isAlive).collect(Collectors.toList());
		List<Fighter> left = leftSide.stream().filter(Fighter::isAlive).toList();
		List<Fighter> right = rightSide.stream().filter(Fighter::isAlive).toList();

		if (left.size() < 1 || right.size() < 1) {
			GameController.battleContinues = false;
			changeSupport.firePropertyChange("battleState", true, false);
		} else {
			Fighter fighter = attackOrder.get(0);
			if (left.contains(fighter)) {
				fighter.attack(right);
			} else {
				fighter.attack(left);
			}
			attackOrder.remove(fighter);
			attackOrder.add(fighter);
		}
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
			long soldiersLeft = attackOrder.stream().filter(Fighter::isAlive).count();
			changeSupport.firePropertyChange("soldierAmount", totalSoldiers, soldiersLeft);
		}
	}
}
