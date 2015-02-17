package com.TeamHEC.LocomotionCommotion.Obstacle;

import com.TeamHEC.LocomotionCommotion.Player.Player;

import java.util.Random;

/**
 * @author Alfio E. Fresta <aef517@york.ac.uk>
 *
 * A class used to generate obstacles.
 * Usage example:
 *
 *  ObstacleFactory f = new ObstacleFactory();
 *  f.setProbability(.3); // 30% chance of getting an obstacle
 *
 *  Obstacle o = f.getObstacle(player);
 *  if ( o == null ) {
 *      System.out.println("You got lucky.");
 *  } else {
 *      System.out.println("Oh no, something bad happened.");
 *  }
 */
public class ObstacleFactory {

    private double probability = 0;

    private static int maxNoOfTurns = 5;
    private static String[] possibleNames = {
            "Oh snap, that's unfortunate!",
            "BBC forecasts warn about possible ice on tracks",
            "That Junction's definitely broken",
            "Those are Yetis, for real!",
            "There's been an accident at a Junction.",
            "Something is wrong with this train.",
            "'I don't like the sound of the engine.'",
            "Kids on the tracks... Again.",
            "That cow thinks to be train.",
            "That's a Junction fault.",
    };

    public ObstacleFactory() {
        this.setProbability(0.5);
    }

    /*
        Gets the current probability ratio for the Generation of an obstacle.
     */
    public double getProbability() {
        return this.probability;
    }

    /*
        Sets a new probability ratio for the generation of an obstacle.
        @param p    The new probability (between 0 and 1).
     */
    public void         setProbability(double p) {
        if ( p < 0 || p > 1 ) {
            return;
        }
        this.probability = p;
    }

    /*
        (Probably) get an obstacle. Use the probability ratio
        set for this obstacle factory.
        @param p    The player.
        @return     Either an Obstacle or null, if you're lucky.
     */
    public Obstacle getObstacle(Player p) {

        // If you're lucky, a random number x | 0 <= x < 1,
        // will be smaller than your probability window.
        double luck = Math.random();
        if ( luck > this.probability ) {
            return null;
        }

        @SuppressWarnings("static-access")
		String name = this.possibleNames[new Random().nextInt(this.possibleNames.length)];
        double speedFactor = Math.random();
        int numberOfTurns = (int) (1 + (Math.random() * maxNoOfTurns));
        int readableSpedFactor = (int) (speedFactor * 100);
        String description = "slow down to " + readableSpedFactor
                + "%  of its normal speed\n for " + numberOfTurns + " turns.";

        return new Obstacle(name, description, speedFactor, numberOfTurns);

    }


}
