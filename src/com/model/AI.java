package com.model;

import java.util.List;

/**
 * The interface AI.
 */
public interface AI {
	/**
	 * Select the target of this soldiers attack. Will vary according to the implemented AI.
	 *
	 * @param soldiers the list to choose from
	 * @return the selected target
	 */
	Soldier selectTarget(List<Soldier> soldiers);
}
