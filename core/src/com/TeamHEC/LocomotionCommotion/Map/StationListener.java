package com.TeamHEC.LocomotionCommotion.Map;

import com.TeamHEC.LocomotionCommotion.Player.Player;

/**
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 */

public interface StationListener {
	
	/**
	 * Any class than implements this will be notified of a change in ownership
	 * of the station
	 * @param station the station which has changed owner
	 * @param player the new owner of the station
	 */
	public void ownerChanged(Station station, Player player);
}
