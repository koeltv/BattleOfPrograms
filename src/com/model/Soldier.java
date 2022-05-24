package com.model;

import com.view.component.FieldProperties;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class Soldier implements Fighter {
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
	
	public void attack(List<Fighter> fighters) {
		float attackValue = 1;
		attackValue += attackValue * ((double) strength / 10);
		
		float hitChance = 1;
		hitChance += (dexterity * ((double) 3/100)) * hitChance;

		ai.selectTarget(fighters).takeHit(new Hit(attackValue, hitChance));
	}

	@Override
	public boolean takeHit(Hit hit) {
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
			return true;
		}
		return false;
	}


	
	public void rest() {
		
	}

	public void heal() {

	}
	
	public boolean isAlive() {
		return lifePoints > 0;
	}

	public void sendToField(FieldProperties field) {
		assignedField = field;
	}

	public void addObserver(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}
}
