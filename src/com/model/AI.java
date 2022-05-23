package com.model;

import java.util.List;
import java.util.Random;

public interface AI { //TODO Implement some logic in AIs
	default Fighter selectTarget(List<Fighter> fighters) {
		return fighters.get(new Random().nextInt(fighters.size()));
	}
}
