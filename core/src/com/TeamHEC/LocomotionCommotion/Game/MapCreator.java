package com.TeamHEC.LocomotionCommotion.Game;

import com.TeamHEC.LocomotionCommotion.Map.MapInstance;

public abstract class MapCreator {
	
	protected MapInstance generatedMap;
	
	public MapCreator() {
		generatedMap = new MapInstance();
	}
}
