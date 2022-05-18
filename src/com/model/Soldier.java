package com.model;

public class Soldier {
	protected boolean reservist = false;

	protected int lifePoints = 30;
	protected int strength;
	protected int dexterity;
	protected int resistance;
	protected int constitution;
	protected int initiative;

	/**
	 * Defensive, offensive, random
	 */
	public AI ai;

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
	
	public void heal() {
		
	}
	
	public void attack() {
		double attackValue = 1;
		attackValue += attackValue * ((double) strength / 10);
		
		double hitChance = 1;
		hitChance += (dexterity * ((double) 3/100)) * hitChance;
	}
	
	public void takeHit(double damageValue) {
		double hitChance = 1;
		hitChance -= (dexterity * ((double) 3/100)) * hitChance;
		
		if(Math.random() <= hitChance) { //In that case he takes the hit
			damageValue -= (resistance * ((double) 5/100)) * damageValue;
		}

		lifePoints -= damageValue;
	}


	
	public void rest() {
		
	}
	
	public boolean isDead() {
		return lifePoints <= 0;
	}
}