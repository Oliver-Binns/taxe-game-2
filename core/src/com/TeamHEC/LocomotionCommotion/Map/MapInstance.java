package com.TeamHEC.LocomotionCommotion.Map;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Resource.Electric;
import com.TeamHEC.LocomotionCommotion.Resource.Gold;
import com.TeamHEC.LocomotionCommotion.Resource.Nuclear;
import com.TeamHEC.LocomotionCommotion.Resource.Oil;
import com.TeamHEC.LocomotionCommotion.Resource.Resource;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;
import com.TeamJKG.LocomotionCommotion.Replay.Replay;

/**
 * @author Sam Watkins <sw1308@york.ac.uk>
 */

public class MapInstance {
	//HashMap of Station and Junction objects allowing easy reference to them elsewhere in the program.
	private Map<String, Station> stations;
	private Map<String, Junction> junctions;
	private String filePath;
	
	/**
	 * Instantiates an empty map instance for the purpose of generating a map file
	 */
	public MapInstance() {
		stations = new HashMap<String, Station>();
		junctions = new HashMap<String, Junction>();
	}
	
	/**
	 * Dynamically builds the map from a JSON file.
	 * @param filepath provides the path to the JSON file representing the map
	 */
	public MapInstance(String filepath) {
		stations = new HashMap<String, Station>();
		junctions = new HashMap<String, Junction>();
		filePath = filepath;
		
		JSONParser jParser = new JSONParser();
		
		try {
			JSONObject jMapObj = (JSONObject) jParser.parse(new FileReader(filepath));
			
			//Create a set of map features from the JSON file
			JSONArray jStations = (JSONArray) jMapObj.get("Stations");
			JSONArray jJunctions = (JSONArray) jMapObj.get("Junctions");
			JSONArray jConnections = (JSONArray) jMapObj.get("Connections");
			
			//Instantiate all Station objects from the set of Station features
			for(int i=0; i<jStations.size(); i++) {
				JSONObject jStation = (JSONObject) jStations.get(i);
				
				String name = (String) jStation.get("Name");
				Long baseValue = (Long) jStation.get("BaseValue");
				String resourceType = (String) ((JSONObject)((JSONArray) jStation.get("Resource")).get(0)).get("Type");
				Long resourceAmount = (Long) ((JSONObject) ((JSONArray) jStation.get("Resource")).get(0)).get("Amount");
				
				Resource resource;
				if(resourceType.equals("Coal")) {
					resource = new Coal(resourceAmount.intValue());
				} else if(resourceType.equals("Electric")) {
					resource = new Electric(resourceAmount.intValue());
				} else if(resourceType.equals("Gold")) {
					resource = new Gold(resourceAmount.intValue());
				} else if(resourceType.equals("Nuclear")) {
					resource = new Nuclear(resourceAmount.intValue());
				} else {
					resource = new Oil(resourceAmount.intValue());
				}
				
				Long baseFuelOut = (Long) jStation.get("BaseFuelOut");
				JSONArray jLines = (JSONArray) jStation.get("Lines");
				Line[] lines = new Line[jLines.size()];
				
				for(int j=0; j<lines.length; j++) {
					String s = (String) jLines.get(j);
					
					if(s.equals("Red")) {
						lines[j] = Line.Red;
					} else if(s.equals("Blue")) {
						lines[j] = Line.Blue;
					} else if(s.equals("Green")) {
						lines[j] = Line.Green;
					} else if(s.equals("Yellow")) {
						lines[j] = Line.Yellow;
					} else if(s.equals("Purple")) {
						lines[j] = Line.Purple;
					} else if(s.equals("Black")) {
						lines[j] = Line.Black;
					} else if(s.equals("Brown")) {
						lines[j] = Line.Brown;
					} else if(s.equals("Orange")) {
						lines[j] = Line.Orange;
					}
				}
				
				Long rent = (Long) jStation.get("Rent");
				Double xPos = (Double) ((JSONArray) jStation.get("Location")).get(0);
				Double yPos = (Double) ((JSONArray) jStation.get("Location")).get(1);
				boolean locked = (Boolean) jStation.get("Locked");
				
				stations.put(name, new Station(name, baseValue.intValue(), resource, baseFuelOut.intValue(), lines, rent.intValue(), xPos.floatValue(), yPos.floatValue(), locked));
			}
			
			//Initialise all Junction objects from the set of junction features
			for(int i=0; i<jJunctions.size(); i++) {
				JSONObject jJunction = (JSONObject) jJunctions.get(i);
				
				Double xPos = (Double) ((JSONArray) jJunction.get("Location")).get(0);
				Double yPos = (Double) ((JSONArray) jJunction.get("Location")).get(1);
				String name = (String) jJunction.get("Name");
				boolean locked = (Boolean) jJunction.get("Locked");
				
				junctions.put(name, new Junction(xPos.floatValue(), yPos.floatValue(), name, locked));
			}
			
			//Create the appropriate connections between all MapObj
			for(int i=0; i<jConnections.size(); i++) {
				JSONObject jConnection = (JSONObject) jConnections.get(i);
				
				String jStartPoint = (String) jConnection.get("StartPoint");
				MapObj startPoint;
				
				if(junctions.containsKey(jStartPoint)) {
					startPoint = junctions.get(jStartPoint);
				} else {
					startPoint = stations.get(jStartPoint);
				}
				
				JSONArray jEndPoints = (JSONArray) jConnection.get("EndPoints");
				
				MapObj[] endPoints = new MapObj[jEndPoints.size()];
				for(int j=0; j<endPoints.length; j++) {
					if(junctions.containsKey(jEndPoints.get(j))) {
						endPoints[j] = junctions.get(jEndPoints.get(j));
					} else {
						endPoints[j] = stations.get(jEndPoints.get(j));
					}
				}
				
				JSONArray jColours = (JSONArray) jConnection.get("Colours");
				Line[] colours = new Line[jColours.size()];
				
				for(int j=0; j<colours.length; j++) {
					String s = (String) jColours.get(j);
					
					if(s.equals("Red")) {
						colours[j] = Line.Red;
					} else if(s.equals("Blue")) {
						colours[j] = Line.Blue;
					} else if(s.equals("Green")) {
						colours[j] = Line.Green;
					} else if(s.equals("Yellow")) {
						colours[j] = Line.Yellow;
					} else if(s.equals("Purple")) {
						colours[j] = Line.Purple;
					} else if(s.equals("Black")) {
						colours[j] = Line.Black;
					} else if(s.equals("Brown")) {
						colours[j] = Line.Brown;
					} else if(s.equals("Orange")) {
						colours[j] = Line.Orange;
					}
				}
				
				createConnections(startPoint, endPoints, colours);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Resets the MapInstance to it's original state, used when you exit to the main menu
	 */
	public void reset() {
		stations = new HashMap<String, Station>();
		junctions = new HashMap<String, Junction>();
		
		JSONParser jParser = new JSONParser();
		
		try {
			JSONObject jMapObj = (JSONObject) jParser.parse(new FileReader(filePath));
			
			//Create a set of map features from the JSON file
			JSONArray jStations = (JSONArray) jMapObj.get("Stations");
			JSONArray jJunctions = (JSONArray) jMapObj.get("Junctions");
			JSONArray jConnections = (JSONArray) jMapObj.get("Connections");
			
			//Instantiate all Station objects from the set of Station features
			for(int i=0; i<jStations.size(); i++) {
				JSONObject jStation = (JSONObject) jStations.get(i);
				
				String name = (String) jStation.get("Name");
				Long baseValue = (Long) jStation.get("BaseValue");
				String resourceType = (String) ((JSONObject)((JSONArray) jStation.get("Resource")).get(0)).get("Type");
				Long resourceAmount = (Long) ((JSONObject) ((JSONArray) jStation.get("Resource")).get(0)).get("Amount");
				
				Resource resource;
				if(resourceType.equals("Coal")) {
					resource = new Coal(resourceAmount.intValue());
				} else if(resourceType.equals("Electric")) {
					resource = new Electric(resourceAmount.intValue());
				} else if(resourceType.equals("Gold")) {
					resource = new Gold(resourceAmount.intValue());
				} else if(resourceType.equals("Nuclear")) {
					resource = new Nuclear(resourceAmount.intValue());
				} else {
					resource = new Oil(resourceAmount.intValue());
				}
				
				Long baseFuelOut = (Long) jStation.get("BaseFuelOut");
				JSONArray jLines = (JSONArray) jStation.get("Lines");
				Line[] lines = new Line[jLines.size()];
				
				for(int j=0; j<lines.length; j++) {
					String s = (String) jLines.get(j);
					
					if(s.equals("Red")) {
						lines[j] = Line.Red;
					} else if(s.equals("Blue")) {
						lines[j] = Line.Blue;
					} else if(s.equals("Green")) {
						lines[j] = Line.Green;
					} else if(s.equals("Yellow")) {
						lines[j] = Line.Yellow;
					} else if(s.equals("Purple")) {
						lines[j] = Line.Purple;
					} else if(s.equals("Black")) {
						lines[j] = Line.Black;
					} else if(s.equals("Brown")) {
						lines[j] = Line.Brown;
					} else if(s.equals("Orange")) {
						lines[j] = Line.Orange;
					}
				}
				
				Long rent = (Long) jStation.get("Rent");
				Long xPos = (Long) ((JSONArray) jStation.get("Location")).get(0);
				Long yPos = (Long) ((JSONArray) jStation.get("Location")).get(1);
				boolean locked = (Boolean) jStation.get("Locked");
				
				stations.put(name, new Station(name, baseValue.intValue(), resource, baseFuelOut.intValue(), lines, rent.intValue(), xPos.floatValue(), yPos.floatValue(), locked));
			}
			
			//Initialise all Junction objects from the set of junction features
			for(int i=0; i<jJunctions.size(); i++) {
				JSONObject jJunction = (JSONObject) jJunctions.get(i);
				
				Long xPos = (Long) ((JSONArray) jJunction.get("Location")).get(0);
				Long yPos = (Long) ((JSONArray) jJunction.get("Location")).get(1);
				String name = (String) jJunction.get("Name");
				boolean locked = (Boolean) jJunction.get("Locked");
				
				junctions.put(name, new Junction(xPos.floatValue(), yPos.floatValue(), name, locked));
			}
			
			//Create the appropriate connections between all MapObj
			for(int i=0; i<jConnections.size(); i++) {
				JSONObject jConnection = (JSONObject) jConnections.get(i);
				
				String jStartPoint = (String) jConnection.get("StartPoint");
				MapObj startPoint;
				
				if(junctions.containsKey(jStartPoint)) {
					startPoint = junctions.get(jStartPoint);
				} else {
					startPoint = stations.get(jStartPoint);
				}
				
				JSONArray jEndPoints = (JSONArray) jConnection.get("EndPoints");
				
				MapObj[] endPoints = new MapObj[jEndPoints.size()];
				for(int j=0; j<endPoints.length; j++) {
					if(junctions.containsKey(jEndPoints.get(j))) {
						endPoints[j] = junctions.get(jEndPoints.get(j));
					} else {
						endPoints[j] = stations.get(jEndPoints.get(j));
					}
				}
				
				JSONArray jColours = (JSONArray) jConnection.get("Colours");
				Line[] colours = new Line[jColours.size()];
				
				for(int j=0; j<colours.length; j++) {
					String s = (String) jColours.get(j);
					
					if(s.equals("Red")) {
						colours[j] = Line.Red;
					} else if(s.equals("Blue")) {
						colours[j] = Line.Blue;
					} else if(s.equals("Green")) {
						colours[j] = Line.Green;
					} else if(s.equals("Yellow")) {
						colours[j] = Line.Yellow;
					} else if(s.equals("Purple")) {
						colours[j] = Line.Purple;
					} else if(s.equals("Black")) {
						colours[j] = Line.Black;
					} else if(s.equals("Brown")) {
						colours[j] = Line.Brown;
					} else if(s.equals("Orange")) {
						colours[j] = Line.Orange;
					}
				}
				
				createConnections(startPoint, endPoints, colours);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return Station object with a given name - null if no station exists.
	 */
	public Station getStationWithName(String name){
		return stations.get(name);
	}
	
	/**
	 * @return Junction object with a given name - null if no junction exists.
	 */
	public Junction getJunctionWithName(String name) {
		return junctions.get(name);
	}
	
	public Connection[] connectionList() {
		ArrayList<Connection> returnList = new ArrayList<Connection>();
		
		for(MapObj o : mapObjList()) {
			returnList.addAll(o.connections);
		}
		
		return returnList.toArray(new Connection[returnList.size()]);
	}
	/**
	 * @return an array of stations only
	 */
	public Station[] stationList() {
		Station[] returnList = new Station[stations.size()];
		return stations.values().toArray(returnList);
	}
	
	/**
	 * @return an array of junctions only
	 */
	public Junction[] junctionList() {
		Junction[] returnList = new Junction[junctions.size()];
		return junctions.values().toArray(returnList);
	}
	
	/**
	 * @return an array of all mapObj
	 */
	public MapObj[] mapObjList() {
		MapObj[] stationList = stationList();
		MapObj[] junctionList = junctionList();
		MapObj[] returnList = new MapObj[stationList.length + junctionList.length];
		
		for(int i=0; i<stationList.length; i++) {
			returnList[i] = stationList[i];
		}
		for(int i=stationList.length; i<returnList.length; i++) {
			returnList[i] = junctionList[i-stationList.length];
		}
		
		return returnList;
	}
	
	/**
	 * Adds an object to the map
	 * @param obj the MapObj to add to the map
	 */
	public void addMapObj(MapObj obj) {
		if(obj instanceof Station) {
			if(stations.containsKey(obj.getName())) {
				throw new RuntimeException();
			} else {
				stations.put(obj.getName(), (Station) obj);
				return;
			}
		} else {
			if(junctions.containsKey(obj.getName())) {
				throw new RuntimeException();
			} else {
				junctions.put(obj.getName(), (Junction) obj);
				return;
			}
		}
	}
	
	/**
	 * Adds a single connection to the map
	 * @param connection
	 * @return 
	 */
	public boolean addConnection(Connection connection) {
		ArrayList<Connection> cList = connection.getStartMapObj().connections;
		
		for(int i=0; i<cList.size(); i++) {
			if(cList.get(i).getDestination().getName() == connection.getDestination().getName()) {
				return false;
			}
		}
		
		connection.getStartMapObj().connections.add(connection);
		connection.getDestination().connections.add(new Connection(connection.getDestination(), connection.getStartMapObj(), connection.getColour()));
		return true;
	}
	
	/**
	 * Removes station from MapInstance
	 * @param station the station to be removed from the map
	 */
	public void removeStation(String station) {
		removeAllConnections(stations.get(station));
		stations.remove(station);
	}
	
	public void removeJunction(String junction) {
		removeAllConnections(junctions.get(junction));
		junctions.remove(junction);
	}
	
	public void removeAllConnections(MapObj startPoint) {
		while(!startPoint.connections.isEmpty()) {
			removeConnection(startPoint, startPoint.connections.get(0).getDestination());
		}
	}
	
	public void removeConnection(MapObj startPoint, MapObj endPoint) {
		for(Connection c : startPoint.connections) {
			if(c.getDestination().equals(endPoint)) {
				startPoint.connections.remove(c);
				break;
			}
		}
		
		for(Connection c : endPoint.connections) {
			if(c.getDestination().equals(startPoint)) {
				endPoint.connections.remove(c);
				break;
			}
		}
	}
	
	/**
	 * 
	 * @return json string for saving to map file
	 */
	public String generateJSON() {
		String jMap = "{\"Stations\" : [";
		
		for(Station s : stations.values()) {
			jMap = jMap + "{\"Name\" : \"" + s.getName() + "\",\"BaseValue\" : " + s.getBaseValue() + ",\"Resource\" : [{\"Type\" : \"" + s.getResourceString() + "\",\"Amount\" : " + s.getResourceType().getValue() + "}],\"BaseFuelOut\" : " + s.getBaseResourceOut() + ",\"Lines\" : [";
			for(Line l : s.getLineType()) {
				jMap = jMap + "\"" + l.toString() + "\",";
			}
			jMap = jMap.substring(0,  jMap.length() - 1) + "],\"Rent\" : " + s.getBaseRentValue() + ",\"Location\" : [" + s.x + "," + s.y + "],\"Locked\" : " + s.isLocked() + "}";
		}
		
		jMap = jMap + "],\"Junctions\" : [";
		
		for(Junction j : junctions.values()) {
			jMap = jMap + "{\"Location\" : [" + j.x + "," + j.y + "],\"Name\" : \"" + j.getName() + "\",\"Locked\" : " + j.isLocked() + "}";
		}
		
		jMap = jMap + "],\"Connections\" : [";
		
		for(MapObj m : mapObjList()) {
			jMap = jMap + "{\"StartPoint\" : \"" + m.getName() + "\",\"EndPoints\" : [";
			
			for(Connection c : m.connections) {
				jMap = jMap + "\"" + c.getDestination().getName() + "\",";
			}
			
			jMap = jMap.substring(0, jMap.length() - 1) + "],\"Colours\" : [";
			
			for(Connection c : m.connections) {
				jMap = jMap + "\"" + c.getColour() + "\",";
			}
			
			jMap = jMap.substring(0, jMap.length() - 1) + "]}";
		}
		jMap = jMap + "]}";
		
		return jMap;
	}
	
	/**
	 * Initialised the connections Arraylist in each MapObj with their adjacent stations
	 * @param mapObj the initial starting MapObj
	 * @param connection All it's adjacent MapObjs
	 */
	private void createConnections(MapObj mapObj, MapObj[] connections, Line[] colours)
	{
		for(int i = 0; i < connections.length; i++)
		{
			mapObj.connections.add(new Connection(mapObj, connections[i], colours[i]));
		}
	}
	
	/**
	 * Randomly cause faults within stations on the map
	 */
	public void generateFaults(Replay replay){
		for(int i = 0; i < stationList().length; i++){
			Random random = new Random();
			int randInt = random.nextInt(100);
			int faultRate = (int)(stationList()[i].getFaultRate() * 100); //make fault rate into a %age
			if(randInt <= (faultRate)){ 	//if fault rate is a lower number, faults are less likely
				stationList()[i].makeFaulty();			//faults become less likely as more stations become faulty
				replay.addFault(stationList()[i]);
			}
		}
	}

	public Connection getReverseConnection(Connection connection) {
		Connection[] cList = connectionList();
		
		for(int i=0; i<cList.length; i++) {
			if(cList[i].isReverseOf(connection)) {
				return cList[i];
			}
		}
		
		return null;
	}

	
	public boolean changeNameMapping(MapObj mapObj, String newName) {
		if(mapObj instanceof Station) {
			if(stations.containsKey(newName)) {
				return false;
			} else {
				stations.remove(mapObj.getName());
				stations.put(newName, (Station) mapObj);
			}
		} else if(mapObj instanceof Junction) {
			if(junctions.containsKey(newName)) {
				return false;
			} else {
				junctions.remove(mapObj.getName());
				junctions.put(newName, (Junction) mapObj);
			}
		}
		return true;
	}
}
