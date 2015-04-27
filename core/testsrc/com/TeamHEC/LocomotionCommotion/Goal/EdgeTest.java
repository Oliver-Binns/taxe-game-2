package com.TeamHEC.LocomotionCommotion.Goal;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Goal.Graph.Node;
import com.TeamHEC.LocomotionCommotion.Goal.Graph.Edge;
import com.TeamHEC.LocomotionCommotion.Map.MapInstance;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;
@RunWith(GdxTestRunner.class)
public class EdgeTest {
	MapInstance wm;
	Station yo ;
	Node n  ;
	Edge e ;//had to change node to public for this test
	
	@Before
	public void setUp() throws Exception {
		GameData.TEST_CASE = true;
		wm = WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP);
		yo = wm.stationList()[0];
		n = new Node(yo);
		e = new Edge(n, 37);
	}

	@After
	public void tearDown() throws Exception {
	}



	@Test
	public void testEdge() {
		assertTrue(e.target != null);
		assertTrue(e.weight == 37);		
		
		
		
		
	}

}
