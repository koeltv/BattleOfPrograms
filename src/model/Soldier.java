package model;

public class Soldier {
	private boolean isReservist = false;
	
	protected int lifePoints = 30;
	protected int strength = 0;
	protected int dexterity = 0;
	protected int resistance = 0;
	protected int constitution = 0;
	protected int initiative = 0;
	protected final AI ai;
	
	public Soldier(int strenght, int dexterity, int resistance, int constitution, int initiative, AI ai) {
		this.strength = strenght;
		this.dexterity = dexterity;
		this.resistance = resistance;
		
		this.constitution = constitution;
		this.lifePoints += constitution;
		
		this.initiative = initiative;
		
		/**
		 * Defensive, offensive, random
		 */
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
			hitChance -= (resistance * ((double) 5/100)) * hitChance;
		}
	}
	
	public void rest() {
		
	}
	
	public boolean isDead() {
		return lifePoints <= 0;
	}
}
