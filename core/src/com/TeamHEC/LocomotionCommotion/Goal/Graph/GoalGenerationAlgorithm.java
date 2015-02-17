package com.TeamHEC.LocomotionCommotion.Goal.Graph;

import java.util.ArrayList;
import java.util.Random;

import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;


/**
 * @author Stefan Kokov
 * GoalGenerationAlgorithm class is used to generate new goals. It creates a graph of the stations in the game,
 * traverses it and finds a goal. GoalGenerationAlgoritm should not be used to generate new goals directly.
 * GoalFactory should be used to generate goals instead. Goal factory uses GoalGenerationAlgorithm.
 * The pathLength parameter in the constructor should be set using one of the constant difficulty fields in
 * GoalFactory. Only the generateGoalPath method should be accessed  externally. 
 */
public class GoalGenerationAlgorithm {
	
	private WorldMap map;
	private int pathLength;
	public static ArrayList<Station> stations;
	public Node[] nodeList;
	
	
	/**
	 * @param pathLength - The length of the path this algorithm will produce using
	 * generateGoalPath method.
	 */
	public GoalGenerationAlgorithm(int pathLength) {
			this.pathLength = pathLength;
			
			//intialize world map graph
			map = WorldMap.getInstance();
			stations = map.stationsList;  
			initialiseGraph();
	}
	
	
	
	/**This method returns the starting node of the goal being generated.
	 * Starting station is picked randomly.
	 * @return startingNode
	 */
	private Node getStartingNode(){
		Random rand = new Random();
		int n = rand.nextInt(stations.size());// pick random station node from graph
		Node startNode = lookUpNode(stations.get(n)); 
		return startNode;
	}
	
	
	
	/**Generates a list of nodes which represents goal path. Will aim to produce a path with no
	 * repeating nodes, however it may fail. 
	 * @return ArrayList of nodes which is a path
	 */
	private ArrayList<Node> getGoalPathNodeList(){
		ArrayList<Node> goalPath = new ArrayList<Node>(); 	//List of Nodes which is the path to be returned
		ArrayList<Node> usedNodes = new ArrayList<Node>();	//List of Nodes - represents the Nodes already used in the path
		Random rand = new Random();							//Random number generator used to choose new edges
		goalPath.add(getStartingNode());
		Node currentNode = goalPath.get(0);
		Node nextNode;
		
		//Traverse the Graph pathLength number of times and build a list of Nodes of the same size
		for(int i=0; i<pathLength; i++){
			nextNode = currentNode.edges.get(rand.nextInt(currentNode.edges.size())).target;
			int count = 0;
			
			/*Check if the next node to be added to the path is already used. If the Node is used
			 * the algorithm will try 50 times to choose an unused one instead however this may be impossible*/
			while(usedNodes.contains(nextNode) && count<50){
				nextNode = currentNode.edges.get(rand.nextInt(currentNode.edges.size())).target;
				count++;
			}
			
			usedNodes.add(nextNode);
			goalPath.add(nextNode);
			currentNode = nextNode;
			
			if(!stations.contains(currentNode.mapobj) && i == pathLength -1)//If last station of a route is a junction iterate again.
			{
				goalPath.remove(currentNode);
				i--;
			}
		}
		
		return goalPath;
	}
	
	
	
	/**Uses getGoalPathNodeList to generate a list of Nodes, then converts it into a list
	 * of stations using a lookup table and returns it. Will aim to produce a path with no
	 * repeating nodes, however it may fail. 
	 * @return list of stations
	 */
	public ArrayList<Station> generateGoalPath(){
		ArrayList<Node> nodeList = getGoalPathNodeList();
		ArrayList<Station> stationList = new ArrayList<Station>();
		
		//Go through all Nodes and find corresponding station for each Node
		for(Node node : nodeList){
			for(Station station : stations){
				if(node.mapobj.getName() == station.getName()){
					stationList.add(station);
				}
			}
		}
		return stationList;
	}
	

	
	/**
	 * Every time GoalGeneratoionAlgorithm is called a graph is created specifically for that instance. This function populates nodeList 
	 * by creating as many null nodes as there are stations/junctions it then steps through this list of null nodes and 
	 * populates it with a station from the worldmap.stationList and worldMap.junction. Once done it then adds edges to each newly created 
	 * node in correspondence to the connections of that station. 
	 */
	private void initialiseGraph()
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
	 * a lookup table that returns a Node instance for a given mapObj, it should be noted that nodes are just extended 
	 * mapobj's. In theory, all stations/junctions will be inside nodeList 
	 * so this should not return an illegal argument exception. 
	 * @param mapObj
	 * @return Node
	 */
		private Node lookUpNode(MapObj mapObj)
		{
			for (Node n : nodeList){
				if (mapObj.getName() == n.mapobj.getName())
					return n;		
			}
			
			throw new IllegalArgumentException("The given mapObj does not exist in the nodeList");
		}
		
}
