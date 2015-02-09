package com.TeamHEC.LocomotionCommotion.Obstacle;

import com.TeamHEC.LocomotionCommotion.Train.Train;

/**
 * @author Alfio E. Fresta <aef517@york.ac.uk>
 */
public interface Obstacle {

    // Instantiates a new obstacle
    public void Obstacle(String name, String description, double speedFactor, int noTurns);

    // Instantiates a new obstacle and applies it to a train
    public void Obstacle(String name, String description, double speedFactor, int noTurns, Train target);

    public void appyTo(Train t);    // Applies the obstacle to the train
    public boolean isActive();      // Returns whether the Obstacle is applied to a train ATM
    public int getTurnsLeft();      // Returns the number of turns left for this obstacle
    public int getTurnsElapsed();   // Returns the number of turns the obstacle has already been effective for
    public double getSpeedFactor(); // Returns the speed factor for the obstacle

    public void endTurn();          // Increments the internal counter, eventually destroys itself and disassociate
                                    // from the Train if the number of turn left is equal to zero.

}
