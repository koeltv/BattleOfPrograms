package com.model;

/**
 * The model used to store data related to a player.
 */
public class Player {
	private final String name;
	private final String program;

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
