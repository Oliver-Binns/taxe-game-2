package com.TeamHEC.LocomotionCommotion.Obstacle;

import com.TeamHEC.LocomotionCommotion.Train.Train;

/**
 * @author Alfio E. Fresta <aef517@york.ac.uk>
 */
public class Obstacle {

    private String name, description;
    private double speedFactor;

    private int turnsElapsed = 0;
    private int totalTurns   = 0;

    private boolean active   = false;
    private Train   train    = null;

    // Instantiates a new obstacle
    public Obstacle(String name, String description, double speedFactor, int noTurns) {
        this.name = name;
        this.description = description;
        this.speedFactor = speedFactor;
        this.totalTurns = noTurns;
    }

    // Instantiates a new obstacle and applies it to a train
    public Obstacle(String name, String description, double speedFactor, int noTurns, Train target) {
        this(name, description, speedFactor, noTurns);
        this.applyTo(target);
        // TODO apply obstacle to train
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    /*
        Applies the obstacle to a train.
        @param t    The train to apply the obstacle to.
     */
    public void applyTo(Train t) {
        this.active = true;
        this.train  = t;
        // TODO
    }

    /*
        Checks whether the obstacle has been assigned to a Train yet.
        @return     Yes or no.
     */
    public boolean isActive() {
        return (this.active && this.train != null);
    }

    /*
         Returns the number of turns left for this obstacle.
         @return    No. of turns left for the obstacle.
     */
    public int getTurnsLeft() {
        return this.totalTurns - this.turnsElapsed;
    }

    /*
        Returns the number of turns the obstacle has already been effective for
        @return     No. of turns the obstacle
     */
    public int getTurnsElapsed() {
        return this.turnsElapsed;
    }

    /*
         Returns the speed factor for the obstacle
         @return    A factor from 0 to 1 that should be multiplied by the train speed.
     */
    public double getSpeedFactor() {
        return this.speedFactor;
    }

    /*
        Increments the internal counter, eventually destroys itself and disassociate
        from the Train if the number of turn left is equal to zero.
     */
    public void endTurn() {
        if ( !this.isActive() ) {
            return;
        }
        this.turnsElapsed++;
        if ( this.turnsElapsed == this.totalTurns ) {
            this.active = false;
        }

        // TODO Remove from train
    }
}
