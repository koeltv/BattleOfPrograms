package com.model;

import java.util.Arrays;

/**
 * The model used to store data related to a player.
 */
public class Player {
	/**
	 * The Name.
	 */
	private final String name;
	/**
	 * The Program.
	 */
	private final String program;

	/**
	 * The Soldiers.
	 */
	private final Soldier[] soldiers = new Soldier[20];

	/**
	 * Instantiates a new Player.
	 *
	 * @param name    the name
	 * @param program the program
	 */
	public Player(String name, String program) {
		this.name = name;
		this.program = program;
	}

	/**
	 * The Soldiers.
	 *
	 * @return the players soldiers
	 */
	public Soldier[] getSoldiers() {
		return soldiers;
	}

	/**
	 * Check if a soldier belong to the player.
	 *
	 * @param soldier the soldier
	 * @return true if he belongs to the player, false otherwise
	 */
	public boolean containsSoldier(Soldier soldier) {
		return Arrays.stream(soldiers).anyMatch(s -> s == soldier);
	}

	/**
	 * The Name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * The Program.
	 *
	 * @return the program
	 */
	public String getProgram() {
		return program;
	}
}
