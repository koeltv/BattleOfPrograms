package model;

public class EliteSoldier extends Soldier {
	
	public EliteSoldier(int strenght, int dexterity, int resistance, int constitution, int initiative, AI ai) {
		super(strenght, dexterity, resistance, constitution, initiative, ai);
		this.strength += 1;
		this.dexterity += 1;
		this.resistance += 1;
		
		this.constitution += 5;
		this.lifePoints += 5;
		
		this.initiative += 1;
	}
}
