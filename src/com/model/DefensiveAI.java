package com.model;

import java.util.Comparator;
import java.util.List;

/**
 * The type Defensive AI.
 * This AI will attack the enemy soldiers with the lowest life points.
 */
public class DefensiveAI implements AI {
	@Override
	public Soldier selectTarget(List<Soldier> soldiers) {
		return soldiers.stream().min(Comparator.comparing(Soldier::getLifePoints)).orElse(null);
	}
}
