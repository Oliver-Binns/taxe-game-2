package com.TeamHEC.LocomotionCommotion.Train;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.Card.Card;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Resource.Electric;
import com.TeamHEC.LocomotionCommotion.Resource.Gold;
import com.TeamHEC.LocomotionCommotion.Resource.Nuclear;
import com.TeamHEC.LocomotionCommotion.Resource.Oil;

@RunWith(GdxTestRunner.class)
public class TrainTest {

	CoalTrain coalTrain;
	OilTrain oilTrain;
	ElectricTrain electricTrain;
	NuclearTrain nuclearTrain;
	
	String coalTrainName;
	int coalTrainBaseSpeed;
	int coalValue;
	int coalFuelPerTurn;
	
	String oilTrainName;
	int oilTrainBaseSpeed;
	int oilValue;
	int oilFuelPerTurn;
	
	String electricTrainName; 
	int electricTrainBaseSpeed;
	int electricValue;
	int electricFuelPerTurn;
	
	String nuclearTrainName;
	int nuclearTrainBaseSpeed;
	int nuclearValue;
	int nuclearFuelPerTurn;
	
	int speedMod;
	boolean inStation;
	Route route;
	String fakeStationName;
	Player owner;
	
	@Before
	public void setUp()	{
	owner = new Player(
			"Alice", 
			0, 
			new Gold(1000), 
			new Coal(1000), 
			new Electric(1000), 
			new Nuclear(1000), 
			new Oil(1000), 
			new ArrayList<Card>(), 
			new ArrayList<Goal>(), 
			new ArrayList<Train>());	
	
	coalTrainName = "Steam Machine";
	coalTrainBaseSpeed = 70;
	coalValue = 200;
	coalFuelPerTurn = 10;
	
	oilTrainName = "Diesel Weasel";
	oilTrainBaseSpeed = 80;
	oilValue = 350;
	oilFuelPerTurn = 15;
	
	electricTrainName = "Electrix";
	electricTrainBaseSpeed = 100;
	electricValue = 500;
	electricFuelPerTurn = 20;
	
	nuclearTrainName = "Atom Bomb";
	nuclearTrainBaseSpeed = 120;
	nuclearValue = 750;
	nuclearFuelPerTurn = 25;
	
	fakeStationName = "aStation";
	speedMod = 0;
	inStation = true;
	route = new Route(new MapObj(0, 0, fakeStationName));

	coalTrain = new CoalTrain(0,true, route, owner);
	oilTrain = new OilTrain(0,true, route, owner);
	electricTrain = new ElectricTrain(0,true, route, owner);
	nuclearTrain = new NuclearTrain(0,true, route, owner);
	}
	
	@Test
	public void testTrain() throws Exception {
		//Coal
		assertTrue(
				"coalTrain name was not set correctly",
				coalTrain.getName() == coalTrainName);
		assertTrue(
				"coalTrain in station was not set correctly",
				coalTrain.isInStation() == inStation);
		assertTrue(
				"coalTrain route was not set correctly",
				coalTrain.getRoute() == route);
		assertTrue(
				"coalTrain player was not set correctly",
				coalTrain.getOwner() == owner);
		assertTrue(
				"coalTrain fuelPerTurn was not set correctly",
				coalTrain.getFuelPerTurn() == coalFuelPerTurn);
		assertTrue(
				"coalTrain speed was not set correctly",
				coalTrain.getSpeed() == coalTrainBaseSpeed + speedMod);
		assertTrue(
				"coalTrain value was not set correctly",
				coalTrain.getValue() == coalValue);
		
		//Oil
		assertTrue(
				"oilTrain name was not set correctly",
				oilTrain.getName() == oilTrainName);
		assertTrue(
				"oilTrain in station was not set correctly",
				oilTrain.isInStation() == inStation);
		assertTrue(
				"oilTrain route was not set correctly",
				oilTrain.getRoute() == route);
		assertTrue(
				"oilTrain player was not set correctly",
				oilTrain.getOwner() == owner);
		assertTrue(
				"oilTrain fuelPerTurn was not set correctly",
				oilTrain.getFuelPerTurn() == oilFuelPerTurn);
		assertTrue(
				"oilTrain speed was not set correctly",
				oilTrain.getSpeed() == oilTrainBaseSpeed + speedMod);
		assertTrue(
				"oilTrain value was not set correctly",
				oilTrain.getValue() == oilValue);
		
		//Electric
		assertTrue(
				"electricTrain name was not set correctly",
				electricTrain.getName() == electricTrainName);
		assertTrue(
				"electricTrain in station was not set correctly",
				electricTrain.isInStation() == inStation);
		assertTrue(
				"electricTrain route was not set correctly",
				electricTrain.getRoute() == route);
		assertTrue(
				"electricTrain player was not set correctly",
				electricTrain.getOwner() == owner);
		assertTrue(
				"electricTrain fuelPerTurn was not set correctly",
				electricTrain.getFuelPerTurn() == electricFuelPerTurn);
		assertTrue(
				"electricTrain speed was not set correctly",
				electricTrain.getSpeed() == electricTrainBaseSpeed + speedMod);
		assertTrue(
				"electricTrain value was not set correctly",
				electricTrain.getValue() == electricValue);
		
		//Nuclear
		assertTrue(
				"nuclearTrain name was not set correctly",
				nuclearTrain.getName() == nuclearTrainName);
		assertTrue(
				"nuclearTrain in station was not set correctly",
				nuclearTrain.isInStation() == inStation);
		assertTrue(
				"nuclearTrain route was not set correctly",
				nuclearTrain.getRoute() == route);
		assertTrue(
				"nuclearTrain player was not set correctly",
				nuclearTrain.getOwner() == owner);
		assertTrue(
				"nuclearTrain fuelPerTurn was not set correctly",
				nuclearTrain.getFuelPerTurn() == nuclearFuelPerTurn);
		assertTrue(
				"nuclearTrain speed was not set correctly",
				nuclearTrain.getSpeed() == nuclearTrainBaseSpeed + speedMod);
		assertTrue(
				"nuclearTrain value was not set correctly",
				nuclearTrain.getValue() == nuclearValue);
	}

	@Test
	public void testAddUpgrade() throws Exception {
		int speedUpgradeChange = 10;
		
		coalTrain.addUpgrade(new SpeedUpgrade(coalTrain));		
		assertTrue(
				"Upgrade did not get added correctly",
				coalTrain.getSpeedMod() == speedMod + speedUpgradeChange);
	}
	
	@Test
	public void testRemoveUpgrade() throws Exception {
		coalTrain.addUpgrade(new SpeedUpgrade(coalTrain));
		coalTrain.removeUpgrade(new SpeedUpgrade(coalTrain));		
		assertTrue(
				"Upgrade did not get removed correctly",
				coalTrain.getSpeedMod() == speedMod);		
	}
}
