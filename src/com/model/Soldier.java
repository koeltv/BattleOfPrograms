package com.model;

import com.view.component.FieldProperties;
import controller.GameController;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class Soldier {
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	protected boolean reservist = false;

	protected final int maxLifePoints = 30;
	protected float lifePoints = maxLifePoints;
	protected int strength;
	protected int dexterity;
	protected int resistance;
	protected int constitution;
	protected int initiative;

	public FieldProperties assignedField;

	private boolean recentlyDeployed = false;

	/**
	 * Defensive, offensive, random
	 */
	private AI ai;

	public Soldier () {

	}

	public boolean isReservist() {
		return reservist;
	}

	public void setReservist(boolean reservist) {
		this.reservist = reservist;
	}

	public int getStrength() {
		return strength;
	}

	public boolean setStrength(int strength) {
		this.strength = strength;
		return true;
	}

	public int getDexterity() {
		return dexterity;
	}

	public boolean setDexterity(int dexterity) {
		this.dexterity = dexterity;
		return true;
	}

	public int getResistance() {
		return resistance;
	}

	public boolean setResistance(int resistance) {
		this.resistance = resistance;
		return true;
	}

	public int getConstitution() {
		return constitution;
	}

	public boolean setConstitution(int constitution) {
		this.constitution = constitution;
		return true;
	}

	public int getInitiative() {
		return initiative;
	}

	public boolean setInitiative(int initiative) {
		this.initiative = initiative;
		return true;
	}

	public AI getAi() {
		return ai;
	}

	public void setAi(AI ai) {
		this.ai = ai;
	}

	public int getMaxLifePoints() {
		return maxLifePoints;
	}

	public float getLifePoints() {
		return lifePoints;
	}
	
	public void attack(List<Soldier> fighters) {
		recentlyDeployed = false;
		float attackValue = 1;
		attackValue += attackValue * ((double) strength / 10);
		
		float hitChance = 1;
		hitChance += (dexterity * ((double) 3/100)) * hitChance;

		ai.selectTarget(fighters).takeHit(new Hit(attackValue, hitChance));
	}

	public void takeHit(Hit hit) {
		recentlyDeployed = false;
		float hitChance = hit.hitChance();
		hitChance -= (dexterity * ((double) 3/100)) * hitChance;

		float damageValue = hit.attackValue();
		double random = Math.random();
		System.out.println(random + " <= " + hitChance + " ?");
		if(random <= hitChance) { //In that case he takes the hit
			System.err.println(this.getClass() + " took " + damageValue + " ! " + lifePoints + " lefts");
			damageValue -= (resistance * ((double) 5/100)) * damageValue;
			lifePoints -= damageValue;

			changeSupport.firePropertyChange(lifePoints > 0 ? "damage" : "dead", lifePoints + damageValue, lifePoints);
		}
	}
	
	public boolean isAlive() {
		return lifePoints > 0;
	}

	public void sendToField(FieldProperties field) {
		assignedField = field;
		recentlyDeployed = true;
	}

	public FieldProperties getAssignedField() {
		return assignedField;
	}

	/**
	 * Check if a soldier can be moved.
	 * A soldier can be moved if :
	 * - he hasn't been assigned yet
	 * - he belongs to the controller of a field & the field has at least 2 soldiers of the controller
	 * He cannot be moved if :
	 * - He died
	 * - The battle in the field he is in is still going
	 * @return true if he can change field, false otherwise
	 */
	public boolean canBeMoved() {
		if (assignedField == null || GameController.step < 3) return true;

		Field field = GameController.findFieldByProperties(assignedField);
		if (field == null || !isAlive()) return false;

		Player fieldController = field.getController();
		if (fieldController == null) { //If no controller, the battle is still going so the soldier cannot be moved
			return recentlyDeployed;
		} else { //If there is a controller, the soldier can move if it belongs to the controller and there is more than one (and he is still alive)
			List<Soldier> controllerSoldiers = field.getPlayerSoldiers(fieldController);
			return controllerSoldiers.contains(this) && controllerSoldiers.size() > 1;
		}
	}

	public void addObserver(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}
}
