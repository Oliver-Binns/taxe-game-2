package com.TeamHEC.LocomotionCommotion.Train;

import com.TeamHEC.LocomotionCommotion.Map.Station;

public interface RouteListener {
	
	public void stationPassed(Station station, Train train);

}
