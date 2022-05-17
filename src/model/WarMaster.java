package model;

public class WarMaster extends Soldier {
	
	public WarMaster(int strength, int dexterity, int resistance, int constitution, int initiative, AI ai) {
		super(strength, dexterity, resistance, constitution, initiative, ai);
		this.strength += 2;
		this.dexterity += 2;
		this.resistance += 2;
		
		this.constitution += 10;
		this.lifePoints += 10;
		
		this.initiative += 2;
	}

}
