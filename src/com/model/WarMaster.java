package com.model;

/**
 * The type War master.
 */
public class WarMaster extends Soldier {

	/**
	 * Instantiates a new War master.
	 */
	public WarMaster() {
		super();
		super.strength += 2;
		super.dexterity += 2;
		super.resistance += 2;

		super.constitution += 10;
		super.lifePoints += 10;

		super.initiative += 2;
	}

	@Override
	public boolean setStrength(int strength) {
		if (strength > 1) return super.setStrength(strength);
		else return false;
	}

	@Override
	public boolean setDexterity(int dexterity) {
		if (dexterity > 1) return super.setDexterity(dexterity);
		else return false;
	}

	@Override
	public boolean setResistance(int resistance) {
		if (resistance > 1) return super.setResistance(resistance);
		else return false;
	}

	@Override
	public boolean setConstitution(int constitution) {
		if (constitution > 9) return super.setConstitution(constitution);
		else return false;
	}

	@Override
	public boolean setInitiative(int initiative) {
		if (initiative > 1) return super.setInitiative(initiative);
		else return false;
	}

}
