package com.model;

import com.view.component.FieldProperties;
import controller.GameController;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * This class represent a soldier.
 */
public class Soldier {
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	/**
	 * Whether the soldier is a reservist or not.
	 */
	protected boolean reservist = false;
	/**
	 * The maximum amount of life points.
	 */
	protected final int maxLifePoints = 30;
	/**
	 * The current life points.
	 */
	protected float lifePoints = maxLifePoints;
	/**
	 * The Strength.
	 */
	protected int strength;
	/**
	 * The Dexterity.
	 */
	protected int dexterity;
	/**
	 * The Resistance.
	 */
	protected int resistance;
	/**
	 * The Constitution.
	 */
	protected int constitution;
	/**
	 * The Initiative.
	 */
	protected int initiative;

	/**
	 * The Assigned field.
	 */
	public FieldProperties assignedField;

	private boolean recentlyDeployed = false;
	/**
	 * The assigned AI. Can be defensive, offensive or random.
	 */
	private AI ai;

	/**
	 * Instantiates a new Soldier.
	 */
	public Soldier () { //TODO When a new round start, if he wasn't moved, heal 5pts (rest)

	}

	///////////////////////////////////////////////////////////////////////////
	// Getters and setters
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Check if the soldier is a reservist.
	 *
	 * @return the boolean
	 */
	public boolean isReservist() {
		return reservist;
	}

	/**
	 * Sets reservist.
	 *
	 * @param reservist the reservist
	 */
	public void setReservist(boolean reservist) {
		this.reservist = reservist;
	}

	/**
	 * Gets strength.
	 *
	 * @return the strength
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * Sets strength.
	 *
	 * @param strength the strength
	 * @return the strength
	 */
	public boolean setStrength(int strength) {
		this.strength = strength;
		return true;
	}

	/**
	 * Gets dexterity.
	 *
	 * @return the dexterity
	 */
	public int getDexterity() {
		return dexterity;
	}

	/**
	 * Sets dexterity.
	 *
	 * @param dexterity the dexterity
	 * @return the dexterity
	 */
	public boolean setDexterity(int dexterity) {
		this.dexterity = dexterity;
		return true;
	}

	/**
	 * Gets resistance.
	 *
	 * @return the resistance
	 */
	public int getResistance() {
		return resistance;
	}

	/**
	 * Sets resistance.
	 *
	 * @param resistance the resistance
	 * @return the resistance
	 */
	public boolean setResistance(int resistance) {
		this.resistance = resistance;
		return true;
	}

	/**
	 * Gets constitution.
	 *
	 * @return the constitution
	 */
	public int getConstitution() {
		return constitution;
	}

	/**
	 * Sets constitution.
	 *
	 * @param constitution the constitution
	 * @return the constitution
	 */
	public boolean setConstitution(int constitution) {
		this.constitution = constitution;
		return true;
	}

	/**
	 * Gets initiative.
	 *
	 * @return the initiative
	 */
	public int getInitiative() {
		return initiative;
	}

	/**
	 * Sets initiative.
	 *
	 * @param initiative the initiative
	 * @return the initiative
	 */
	public boolean setInitiative(int initiative) {
		this.initiative = initiative;
		return true;
	}

	/**
	 * Gets ai.
	 *
	 * @return the ai
	 */
	public AI getAi() {
		return ai;
	}

	/**
	 * Sets ai.
	 *
	 * @param ai the ai
	 */
	public void setAi(AI ai) {
		this.ai = ai;
	}

	/**
	 * Gets max life points.
	 *
	 * @return the max life points
	 */
	public int getMaxLifePoints() {
		return maxLifePoints;
	}

	/**
	 * Gets current life points.
	 *
	 * @return the life points
	 */
	public float getLifePoints() {
		return lifePoints;
	}

	///////////////////////////////////////////////////////////////////////////
	// Other methods
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Attack.
	 *
	 * @param soldiers the list of soldiers to choose a target from
	 */
	public void attack(List<Soldier> soldiers) {
		recentlyDeployed = false;
		float attackValue = 1;
		attackValue += attackValue * ((double) strength / 10);
		
		float hitChance = 1;
		hitChance += (dexterity * ((double) 3/100)) * hitChance;

		ai.selectTarget(soldiers).takeHit(new Hit(attackValue, hitChance));
	}

	/**
	 * Receive a hit from another soldier's attack.
	 *
	 * @param hit the hit received
	 */
	public void takeHit(Hit hit) {
		recentlyDeployed = false;
		float hitChance = hit.hitChance();
		hitChance -= (dexterity * ((double) 3/100)) * hitChance;

		float damageValue = hit.attackValue();
		double random = Math.random();
		if(random <= hitChance) { //In that case he takes the hit
			damageValue -= (resistance * ((double) 5/100)) * damageValue;
			lifePoints -= damageValue;

			changeSupport.firePropertyChange(lifePoints > 0 ? "damage" : "dead", lifePoints + damageValue, lifePoints);
		}
	}

	/**
	 * Is alive boolean.
	 *
	 * @return the boolean
	 */
	public boolean isAlive() {
		return lifePoints > 0;
	}

	/**
	 * Send to field.
	 *
	 * @param field the field
	 */
	public void sendToField(FieldProperties field) {
		assignedField = field;
		recentlyDeployed = true;
	}

	/**
	 * Gets assigned field.
	 *
	 * @return the assigned field
	 */
	public FieldProperties getAssignedField() {
		return assignedField;
	}

	/**
	 * Check if a soldier can be moved.
	 * A soldier can be moved if :
	 * - he hasn't been assigned yet
	 * - he belongs to the controller of a field & the field has at least 2 soldiers of the controller
	 * He cannot be moved if :
	 * - He died
	 * - The battle in the field he is in is still going
	 *
	 * @return true if he can change field, false otherwise
	 */
	public boolean canBeMoved() {
		if (assignedField == null || GameController.getStep() < 3) return true;

		Field field = GameController.findFieldByProperties(assignedField);
		if (field == null || !isAlive()) return false;

		Player fieldController = field.getController();
		if (fieldController == null) { //If no controller, the battle is still going so the soldier cannot be moved
			return recentlyDeployed;
		} else { //If there is a controller, the soldier can move if it belongs to the controller and there is more than one (and he is still alive)
			List<Soldier> controllerSoldiers = field.getPlayerSoldiers(fieldController);
			return controllerSoldiers.contains(this) && controllerSoldiers.size() > 1;
		}
	}

	///////////////////////////////////////////////////////////////////////////
	// PropertyChange methods
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Add observer.
	 *
	 * @param listener the listener
	 */
	public void addObserver(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public void addObserver(String property, PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(property, listener);
	}

	public void removeObserver(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}
}
