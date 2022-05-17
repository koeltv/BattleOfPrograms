package com.model;

public class WarMaster extends Soldier {
	
	public WarMaster(int strength, int dexterity, int resistance, int constitution, int initiative, AI ai) {
		super(strength, dexterity, resistance, constitution, initiative, ai);
		super.strength += 2;
		super.dexterity += 2;
		super.resistance += 2;

		super.constitution += 10;
		super.lifePoints += 10;

		super.initiative += 2;
	}

}
