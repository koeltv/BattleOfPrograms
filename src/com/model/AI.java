package com.model;

import java.util.List;
import java.util.Random;

public interface AI { //TODO Implement some logic in AIs
	/**
	 * Select the target of this soldiers attack. Will vary according to the implemented AI.
	 *
	 * @param soldiers the list to choose from
	 * @return the selected target
	 */
	default Soldier selectTarget(List<Soldier> soldiers) {
		return soldiers.get(new Random().nextInt(soldiers.size()));
	}
}
