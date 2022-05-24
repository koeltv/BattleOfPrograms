package com.model;

import com.view.component.FieldProperties;

import java.util.List;

public interface Fighter {
	boolean isReservist();

	void setReservist(boolean reservist);

	int getStrength();

	boolean setStrength(int strength);

	int getDexterity();

	boolean setDexterity(int dexterity);

	int getResistance();

	boolean setResistance(int resistance);

	int getConstitution();

	boolean setConstitution(int constitution);

	int getInitiative();

	boolean setInitiative(int initiative);

	AI getAi();

	void setAi(AI ai);

	void heal();

	void attack(List<Fighter> fighters);

	boolean takeHit(Hit hit);

	void rest();

	boolean isAlive();

	void sendToField(FieldProperties field);
}
