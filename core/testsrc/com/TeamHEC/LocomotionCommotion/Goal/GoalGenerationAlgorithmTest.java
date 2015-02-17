package com.TeamHEC.LocomotionCommotion.Goal;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.Goal.Graph.GoalGenerationAlgorithm;
import com.TeamHEC.LocomotionCommotion.Goal.Graph.Node;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;

/*
 * This test class makes use of the reflection testing pattern in order to test private 
 * methods of the class GoalGenerationAlgorithm
 */
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
		
		//Private method invoked using reflection
		try {
			Method method = gga.getClass().getDeclaredMethod("initialiseGraph");
			method.setAccessible(true);
			method.invoke(gga);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(gga.nodeList.length != 0 );  
		assertTrue(gga.nodeList.length == (map.stationsList.size() + 2));
	}
	
	@Test
	public void testLookUpNode() {
		Node n = null;
		
		//Private method invoked using reflection
		try{
			Method method = gga.getClass().getDeclaredMethod("lookUpNode", new Class[]{MapObj.class});
			method.setAccessible(true);
			n = (Node) method.invoke(gga, map.BERLIN);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		assertTrue(n!=null); 
		assertTrue(contains(gga.nodeList, n));
	}
	
	@Test
	public void testGetStartingNode(){
		Node n = null;
		
		//Private method invoked using reflection
		try {
			Method method = gga.getClass().getDeclaredMethod("getStartingNode");
			method.setAccessible(true);
			n = (Node) method.invoke(gga);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(stations.contains(n.mapobj));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetGoalPathNodeList(){
		gga = new GoalGenerationAlgorithm(5);
		ArrayList<Node> list = null;
		
		//Private method invoked using reflection
		try{
			Method method = gga.getClass().getDeclaredMethod("getGoalPathNodeList");
			method.setAccessible(true);
			list = (ArrayList<Node>) method.invoke(gga);
		}catch(Exception e){
			e.printStackTrace();
		}
		
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
