package com.TeamHEC.LocomotionCommotion.Obstacle;

import static org.junit.Assert.*;

import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.Card.Card;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Train.Route;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Resource.Electric;
import com.TeamHEC.LocomotionCommotion.Resource.Gold;
import com.TeamHEC.LocomotionCommotion.Resource.Nuclear;
import com.TeamHEC.LocomotionCommotion.Resource.Oil;
import com.TeamHEC.LocomotionCommotion.Train.CoalTrain;

@RunWith(GdxTestRunner.class)
public class ObstacleTest {

    Player player;
    int baseGold;
    Train train;

    @Before
    public void setUp() throws Exception {
        baseGold = 500;
        player = new Player(
                "Alice",
                0,
                new Gold(baseGold),
                new Coal(500),
                new Electric(500),
                new Nuclear(500),
                new Oil(500),
                new ArrayList<Card>(),
                new ArrayList<Goal>(),
                new ArrayList<Train>()
        );

        String fakeStationName = "aStation";
        Route route = new Route(new MapObj(0, 0, fakeStationName));
        train = new CoalTrain(0, true, route, player);

    }

    @Test
    public void testProbability() {

        ObstacleFactory f = new ObstacleFactory();
        int n = 1000; // How many times to try (10x)

        // Test 100% probability
        f.setProbability(1);
        for ( int i = 0; i < n; i++ ) {
            Obstacle o = f.getObstacle(this.player);
            assertNotNull("I expected an obstacle, but got none.", o);
        }

        // Test 0% probability
        f.setProbability(0);
        for ( int i = 0; i < n; i++ ) {
            Obstacle o = f.getObstacle(this.player);
            assertNull("I didn't expect any obstacle here.", o);
        }

    }
    @Test
    public void testObstacleCreation() {
        ObstacleFactory f = new ObstacleFactory();
        f.setProbability(1);
        Obstacle o = f.getObstacle(player);

        assertTrue("Obstacle speed factor should be positive.",
                o.getSpeedFactor() >= 0.0
        );

        assertTrue("Obstacle speed factor should be at most 1.",
                o.getSpeedFactor() <= 1.0
        );

        assertTrue("No turn has finished yet.",
                o.getTurnsElapsed() == 0
        );

        assertTrue("Got an obstacle for zero turns?",
                o.getTurnsLeft() > 0
        );

        assertFalse("The obstacle should not be active yet.",
                o.isActive()
        );

        assertFalse("Train should have no obstacles yet.",
                this.train.hasObstacle()
        );

        int s = train.getSpeed();
        o.applyTo(this.train);

        assertTrue("Train should now have obstacle.",
                this.train.hasObstacle()
        );

        assertTrue("The obstacle should be active now.",
                o.isActive()
        );

        assertTrue("The speed of the train should be affected",
                train.getSpeed() == (int) (s * o.getSpeedFactor())
        );

        int x = o.getTurnsLeft();
        o.startTurn();

        assertTrue("A turn should be passed, now.",
                o.getTurnsElapsed() == 1
        );

        assertTrue("Turns left should be decremented.",
                o.getTurnsLeft() == x-1
        );

        int t = o.getTurnsLeft();

        for ( int i = 0; i < t; i++ ) {
            assertTrue("Obstacle should be still alive.",
                    o.isActive()
            );


            o.startTurn();
        }

        assertFalse("Ok, now everything finished.",
                o.isActive()
        );

        assertTrue("Train speed should now be back to normal",
                train.getSpeed() == s
        );

        assertFalse("Train should have no obstacles anymore.",
                this.train.hasObstacle()
        );

    }

}
