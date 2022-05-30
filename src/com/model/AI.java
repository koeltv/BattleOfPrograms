package com.model;

import java.util.List;

public interface AI {
	Soldier selectTarget(List<Soldier> soldiers);
}
