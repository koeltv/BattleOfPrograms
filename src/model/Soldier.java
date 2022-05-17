package model;

public class Soldier {
	private boolean isReservist = false;
	
	protected int lifePoints = 30;
	protected int strength;
	protected int dexterity;
	protected int resistance;
	protected int constitution;
	protected int initiative;

	/**
	 * Defensive, offensive, random
	 */
	protected final AI ai;
	
	public Soldier(int strength, int dexterity, int resistance, int constitution, int initiative, AI ai) {
		this.strength = strength;
		this.dexterity = dexterity;
		this.resistance = resistance;
		
		this.constitution = constitution;
		this.lifePoints += constitution;
		
		this.initiative = initiative;

		this.ai = ai;
	}
	
	public int getInitiative() {
		return initiative;
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
		
		if(Math.random() <= hitChance) { //In that case he take the hit
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
