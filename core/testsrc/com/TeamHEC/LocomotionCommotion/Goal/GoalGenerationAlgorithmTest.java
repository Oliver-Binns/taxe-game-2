package com.TeamHEC.LocomotionCommotion.Goal;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.Goal.Graph.GoalGenerationAlgorithm;
import com.TeamHEC.LocomotionCommotion.Goal.Graph.Node;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class GoalGenerationAlgorithmTest {
	WorldMap map = WorldMap.getInstance();
	private int pathLength;
	public static ArrayList<Station> stations;
	public Node[] nodeList;
	GoalGenerationAlgorithm gga;
	
	@Before
	public void setUp() throws Exception {
		this.pathLength = 5;
		stations = map.stationsList; 
		 gga = new GoalGenerationAlgorithm(pathLength);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGoalGenerationAlgorithm(){
		assertTrue(gga.nodeList.length == 22);
		assertTrue(stations == map.stationsList );
	}
	
	@Test
	public void testInitialiseGraph() {
		gga.initialiseGraph();
		assertTrue(gga.nodeList.length != 0 );  
		assertTrue(gga.nodeList.length == (map.stationsList.size() + 2));
	}
	
	@Test
	public void testLookUpNode() {  
		Node n = gga.lookUpNode(map.BERLIN);
		assertTrue(n!=null); 
		assertTrue(contains(gga.nodeList, n));
	}
	
	@Test
	public void testGetStartingNode(){
		Node n = gga.getStartingNode();
		assertTrue(stations.contains(n.mapobj));
	}
	
	@Test
	public void testGetGoalPathNodeList(){
		gga = new GoalGenerationAlgorithm(5);
		ArrayList<Node> list = gga.getGoalPathNodeList();
		assertTrue(stations.contains(list.get(0).mapobj));
		
		assertTrue(stations.contains(list.get(1).mapobj) 
				||	map.junction[0] == list.get(1).mapobj 
				|| map.junction[1] == list.get(1).mapobj);
		
		assertTrue(stations.contains(list.get(2).mapobj) 
				||	map.junction[0] == list.get(2).mapobj 
				|| map.junction[1] == list.get(2).mapobj);
		
		assertTrue(stations.contains(list.get(3).mapobj) 
				||	map.junction[0] == list.get(3).mapobj 
				|| map.junction[1] == list.get(3).mapobj);
		
		assertTrue(stations.contains(list.get(4).mapobj) 
				||	map.junction[0] == list.get(4).mapobj 
				|| map.junction[1] == list.get(4).mapobj);
		
		assertTrue(stations.contains(list.get(5).mapobj));
	}
	
	@Test
	public void testGenerateGoalPath(){
		gga = new GoalGenerationAlgorithm(5);
		ArrayList<Station> stationList = gga.generateGoalPath();
		for(Station s : stationList){
			assertTrue(stations.contains(s));
		}
		assertTrue(stationList.size()>1);
	}
	

	
	
	
	public boolean contains(Node[] nodelist , Node node ) {

		for (int i = 0; i < nodelist.length; i++) {

			if (node == nodelist[i]) {	        	
				return true;
			}
		}
		return false;
	}
	

}
