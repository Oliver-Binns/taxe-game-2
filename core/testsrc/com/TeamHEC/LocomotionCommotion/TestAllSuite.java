package com.TeamHEC.LocomotionCommotion;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.TeamHEC.LocomotionCommotion.Card.CardFactoryTest;
import com.TeamHEC.LocomotionCommotion.Card.CardTest;
import com.TeamHEC.LocomotionCommotion.Card.GoFasterStripesCardTest;
import com.TeamHEC.LocomotionCommotion.Card.GoldCardTest;
import com.TeamHEC.LocomotionCommotion.Card.ResourceCardTest;
import com.TeamHEC.LocomotionCommotion.Card.TeleportCardTest;
import com.TeamHEC.LocomotionCommotion.Game.CoreGameTest;
import com.TeamHEC.LocomotionCommotion.Goal.DijkstraTest;
import com.TeamHEC.LocomotionCommotion.Goal.EdgeTest;
import com.TeamHEC.LocomotionCommotion.Goal.GoalFactoryTest;
import com.TeamHEC.LocomotionCommotion.Goal.GoalGenerationAlgorithmTest;
import com.TeamHEC.LocomotionCommotion.Goal.GoalTest;
import com.TeamHEC.LocomotionCommotion.Goal.NodeTest;
import com.TeamHEC.LocomotionCommotion.Map.ConnectionTest;
import com.TeamHEC.LocomotionCommotion.Map.MapObjTest;
import com.TeamHEC.LocomotionCommotion.Map.StationTest;
import com.TeamHEC.LocomotionCommotion.Map.WorldMapTest;
import com.TeamHEC.LocomotionCommotion.Obstacle.ObstacleTest;
import com.TeamHEC.LocomotionCommotion.Player.PlayerTest;
import com.TeamHEC.LocomotionCommotion.Player.ShopTest;
import com.TeamHEC.LocomotionCommotion.Replay.ReplayTesting;
import com.TeamHEC.LocomotionCommotion.Train.RouteTest;
import com.TeamHEC.LocomotionCommotion.Train.TrainTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CardFactoryTest.class,
	CardTest.class,
	GoFasterStripesCardTest.class,
	GoldCardTest.class,
	ResourceCardTest.class,
	TeleportCardTest.class,
	CoreGameTest.class,
	DijkstraTest.class,
	EdgeTest.class,
	GoalFactoryTest.class,
	GoalGenerationAlgorithmTest.class,
	GoalTest.class,
	NodeTest.class,
	WorldMapTest.class,
	ConnectionTest.class,
	MapObjTest.class,
	StationTest.class,
	ObstacleTest.class,
	PlayerTest.class,
	ShopTest.class,
	RouteTest.class,
	TrainTest.class,
	ReplayTesting.class
})

public class TestAllSuite {
}
