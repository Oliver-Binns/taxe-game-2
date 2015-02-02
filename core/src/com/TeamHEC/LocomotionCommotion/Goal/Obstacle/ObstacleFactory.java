package com.TeamHEC.LocomotionCommotion.Goal.Obstacle;

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
public interface ObstacleFactory {

    public float        getProbability();           // Gets the current random obstacle probability value
    public void         setProbability(double p);   // Set a new random obstacle probability value

    public Obstacle     getObstacle(Player p);      // Gets a random obstacle

    /*
        Tests could be, for example, setting probability both to 0 and 1 and test you
        get null every time or an obstacle every time (e.g. by repeating 100 times).
     */

}
