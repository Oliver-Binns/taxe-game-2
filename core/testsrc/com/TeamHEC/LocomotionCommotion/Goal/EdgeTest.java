package com.TeamHEC.LocomotionCommotion.Goal;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.Goal.Graph.Node;
import com.TeamHEC.LocomotionCommotion.Goal.Graph.Edge;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;
@RunWith(GdxTestRunner.class)
public class EdgeTest {
	WorldMap wm;
	Station yo ;
	Node n  ;
	Edge e ;//had to change node to public for this test
	
	@Before
	public void setUp() throws Exception {
		 wm = WorldMap.getInstance();
		 yo = wm.AMSTERDAM;
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
