package com.model;

public class Player {
	public final String name;
	public final String program;

	public final Soldier[] soldiers = new Soldier[20];
	
	public Player(String name, String program) {
		this.name = name;
		this.program = program;
	}
}
