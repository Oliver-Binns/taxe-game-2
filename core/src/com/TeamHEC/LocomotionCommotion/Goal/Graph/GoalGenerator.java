package com.TeamHEC.LocomotionCommotion.Goal.Graph;

import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Player.Player;


/**
 * @author Stefan Kokov
 * GoalGenerator class is used to generate new goals. It creates a graph of the stations in the game,
 * traverses it and finds a goal which is achieveable according to a players resources and trains 
 *
 */
public class GoalGenerator {
	private WorldMap map;
	private int fuelCost;
	private int fuel;
	public static ArrayList<Station> stations;
	public Node[] nodeList;
	
	
	public GoalGenerator(Player player) {
			map = WorldMap.getInstance();
			stations = map.stationsList;  
			
			initialiseGraph();
	}
	
	
	
	/**
	 * Everytime GoalGenerator is called a graph is created specifically for that instance. This function populates nodeList 
	 * by creating as many null nodes as there are stations/junctions it then steps through this list of null nodes and 
	 * populates it with a station from the worldmap.stationList. Once done it then adds edges to each newly created 
	 * node in correspondence to the connections of that station. 
	 */
	public void initialiseGraph()
	{
		ArrayList<MapObj> fullList = new ArrayList<MapObj>();
		fullList.addAll(stations);
		fullList.add(WorldMap.getInstance().junction[0]);
		fullList.add(WorldMap.getInstance().junction[1]);
		
		nodeList = new Node[map.stationsList.size() + map.junction.length];
	     
		for(int i = 0; i < fullList.size(); i++){         //populates empty nodes with a new station
			nodeList[i] = new Node(fullList.get(i));  //stepping through each node in array and assigning station to it.      
		}      

		for(Node n : nodeList){
			for(Connection c : n.mapobj.connections){                            //adds each connection to each node
				n.edges.add(new Edge(lookUpNode(c.getDestination()),(c.getLength()))); } } 

	}
	
	/**
	 * a lookup table that returns a Node instance for a given map obj, it should be noted that nodes are just extended 
	 * mapobj's. In theory, all stations/junctions will be inside nodeList 
	 * so this should not return an illegal argument exception. 
	 * @param mapObj
	 * @return Node
	 */
		public Node lookUpNode(MapObj mapObj)
		{
			for (Node n : nodeList){
				if (mapObj.getName() == n.mapobj.getName())
					return n;		
			}
			
			throw new IllegalArgumentException("The given mapObj does not exist in the nodeList");
		}

}
