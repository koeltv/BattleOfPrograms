package com.model;

public class EliteSoldier extends Soldier {
	
	public EliteSoldier(int strength, int dexterity, int resistance, int constitution, int initiative, AI ai) {
		super(strength, dexterity, resistance, constitution, initiative, ai);
		super.strength += 1;
		super.dexterity += 1;
		super.resistance += 1;

		super.constitution += 5;
		super.lifePoints += 5;

		super.initiative += 1;
	}
}
