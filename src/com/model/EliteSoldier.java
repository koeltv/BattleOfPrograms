package com.model;

/**
 * The type Elite soldier.
 */
public class EliteSoldier extends Soldier {

	/**
	 * Instantiates a new Elite soldier.
	 */
	public EliteSoldier() {
		super();
		super.strength += 1;
		super.dexterity += 1;
		super.resistance += 1;

		super.constitution += 5;
		super.lifePoints += 5;

		super.initiative += 1;
	}

	@Override
	public boolean setStrength(int strength) {
		if (strength > 0) return super.setStrength(strength);
		else return false;
	}

	@Override
	public boolean setDexterity(int dexterity) {
		if (dexterity > 0) return super.setDexterity(dexterity);
		else return false;
	}

	@Override
	public boolean setResistance(int resistance) {
		if (resistance > 0) return super.setResistance(resistance);
		else return false;
	}

	@Override
	public boolean setConstitution(int constitution) {
		if (constitution > 4) return super.setConstitution(constitution);
		else return false;
	}

	@Override
	public boolean setInitiative(int initiative) {
		if (initiative > 0) return super.setInitiative(initiative);
		else return false;
	}
}
