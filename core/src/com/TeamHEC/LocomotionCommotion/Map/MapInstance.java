package com.TeamHEC.LocomotionCommotion.Map;

import java.io.FileReader;
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

/**
 * @author Sam Watkins <sw1308@york.ac.uk>
 */

public class MapInstance {
	private Map<String, Station> stations;
	private Map<String, Junction> junctions;
	
	public MapInstance(String filepath) {
		stations = new HashMap<String, Station>();
		junctions = new HashMap<String, Junction>();
		JSONParser jParser = new JSONParser();
		
		try {
			JSONObject jMapObj = (JSONObject) jParser.parse(new FileReader(filepath));
			
			JSONArray jStations = (JSONArray) jMapObj.get("Stations");
			JSONArray jJunctions = (JSONArray) jMapObj.get("Junctions");
			JSONArray jConnections = (JSONArray) jMapObj.get("Connections");
			
			for(int i=0; i<jStations.size(); i++) {
				JSONObject jStation = (JSONObject) jStations.get(i);
				
				String name = (String) jStation.get("Name");
				Long baseValue = (Long) jStation.get("BaseValue");
				String resourceType = (String) ((JSONObject)((JSONArray) jStation.get("Resource")).get(0)).get("Type");
				Long resourceAmount = (Long) ((JSONObject) ((JSONArray) jStation.get("Resource")).get(0)).get("Amount");
				
				Resource resource;
				if(resourceType == "Coal") {
					resource = new Coal(resourceAmount.intValue());
				} else if(resourceType == "Electric") {
					resource = new Electric(resourceAmount.intValue());
				} else if(resourceType == "Gold") {
					resource = new Gold(resourceAmount.intValue());
				} else if(resourceType == "Nuclear") {
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
			
			for(int i=0; i<jJunctions.size(); i++) {
				JSONObject jJunction = (JSONObject) jJunctions.get(i);
				
				Long xPos = (Long) ((JSONArray) jJunction.get("Location")).get(0);
				Long yPos = (Long) ((JSONArray) jJunction.get("Location")).get(1);
				String name = (String) jJunction.get("Name");
				boolean locked = (Boolean) jJunction.get("Locked");
				
				junctions.put(name, new Junction(xPos.floatValue(), yPos.floatValue(), name, locked));
			}
			
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
	 * Returns an array of stations only
	 */
	public Station[] stationList() {
		Station[] returnList = new Station[stations.size()];
		return stations.values().toArray(returnList);
	}
	
	/**
	 * Returns an array of junctions only
	 */
	public Junction[] junctionList() {
		Junction[] returnList = new Junction[junctions.size()];
		return junctions.values().toArray(returnList);
	}
	
	/**
	 * Returns an array of all mapObj
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
	public void generateFaults(){
		for(String station : stations.keySet()){
			Random random = new Random();
			int randInt = random.nextInt(100);
			int faultRate = (int)(stations.get(station).getFaultRate() * 100);
			if(randInt <= faultRate){
				stations.get(station).makeFaulty();
			}
		}
	}
}
