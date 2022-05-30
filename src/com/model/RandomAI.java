package com.model;

import java.util.List;
import java.util.Random;

/**
 * The type Random AI.
 * This AI will attack a random enemy soldier.
 */
public class RandomAI implements AI {
	@Override
	public Soldier selectTarget(List<Soldier> soldiers) {
		return soldiers.get(new Random().nextInt(soldiers.size()));
	}
}
