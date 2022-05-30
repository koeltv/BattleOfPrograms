package com.model;

import java.util.Comparator;
import java.util.List;

/**
 * The type Offensive AI.
 * This AI will attack the enemy soldiers with the highest life points.
 */
public class OffensiveAI implements AI {
	@Override
	public Soldier selectTarget(List<Soldier> soldiers) {
		return soldiers.stream().max(Comparator.comparing(Soldier::getLifePoints)).orElse(null);
	}
}
