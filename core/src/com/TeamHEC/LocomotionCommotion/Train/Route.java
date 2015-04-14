package com.TeamHEC.LocomotionCommotion.Train;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.UI_Elements.GameScreenUI;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 */

public class Route{
	
	private ArrayList<Connection> path = new ArrayList<Connection>();
	
	// Progress through route ArrayList
	private int routeIndex = 0;
	private float connectionTravelled = 0;	
	private MapObj currentMapObj;	
	private boolean isComplete = false;
	
	public Train train;
	
	// Safe to remove while using:
	private CopyOnWriteArrayList<RouteListener> listeners = new CopyOnWriteArrayList<RouteListener>();
	
	
	public String toJSON(){
		String json = "{\"connections\" : [";
		for(int i = 0; i < path.size(); i++){
			json += path.get(i).toJSON();
			if(i+1 != path.size()){
				json += ",";
			}
		}
		json += "]}";
		return json;
	}
	
	/**
	 * Creates an arrayList of connections (a route) for the train to eventually follow
	 * @param startingPos the starting position of the route
	 */
	public Route(MapObj startingPos)
	{
		currentMapObj = startingPos;
		routeIndex = 0;
		connectionTravelled = 0;
	}
	/**
	 * Used to reload an existing route
	 * @param path current route of the train
	 * @param routeIndex The index used to track progress through an array of connections
	 * @param connectionTravelled The distance travelled through that connection 
	 */
	public Route(ArrayList<Connection> path, int routeIndex, float connectionTravelled)
	{
		this.path = path;
		if(!path.isEmpty())
			currentMapObj = path.get(routeIndex).getStartMapObj();
		this.routeIndex = routeIndex;
		this.connectionTravelled = connectionTravelled;
	}
	
	/**
	 * @return An Arraylist of connections within the route
	 */
	public ArrayList<Connection> getRoute()
	{
		return path;
	}
	
	/**
	 * @return The current position of the train within the connections ArrayList
	 */
	public int getRouteIndex()
	{
		return routeIndex;
	}
	
	/**
	 * Set to true when the route reaches an end in update method
	 * @return if the route is complete
	 */
	public boolean isComplete()
	{
		return isComplete;
	}
	
	/**
	 * @return The amount a train has progress through the current connection in a route
	 */
	public float getConnectionTravelled()
	{
		return connectionTravelled;
	}
	
	/**
	 * Can be used to change the position of the train within the route
	 * @param index the index of the connection to move the train to
	 */
	public void setRouteIndex(int index)
	{
		routeIndex = index;
	}
	/**
	 * Can be used to change the position of the train within the route
	 * @param The position of the train in the current connection
	 */
	public void setConnectionTravelled(float travelled)
	{
		connectionTravelled = travelled;
	}
	
	/**
	 * The currentMapObj the train is positioned at
	 */
	public void setCurrentMapObj(MapObj current)
	{
		currentMapObj = current;
	}
	
	/**
	 * Can be used to see which  adjacent connections are available to add to an existing route
	 * @return an ArrayList of adjacent connections to the latest connection in the route.
	 */
	public ArrayList<Connection> getAdjacentConnections()
	{
		if(path.isEmpty())
		{
			return currentMapObj.connections;
		}
		else
			return path.get(path.size()-1).getDestination().connections;
	}

	/**
	 * Makes all the white route blips between connections visible, the red dot indicates
	 * direction
	 */
	public void showRouteBlips()
	{
		Stage stage = train.getActor().getStage();
		for(Connection c : getRoute())
		{
			for(Actor a : c.getRouteBlips())
			{
				a.setVisible(true);
				stage.addActor(a);
			}
		}
	}
	/**
	 * Removes route UI blips from the screen
	 */
	public void hideRouteBlips()
	{
		Stage stage = train.getActor().getStage();
		for(Connection c : getRoute())
		{
			for(Actor a : c.getRouteBlips())
			{
				a.setVisible(false);
				stage.getActors().removeValue(a, true);
			}
		}
	}
	
	/**
	 * Creates route UI dots for this connection
	 * @param connection the connection to add UI blips to
	 */
	public void showConnectionBlips(Connection connection)
	{
		if(train.getActor() == null) //Must be for testing.
		{			
		}
		else
		{
			Stage stage = train.getActor().getStage();
			for(Actor a : connection.getRouteBlips())
			{
				a.setVisible(true);
				stage.addActor(a);
			}
		}
		
	}
	/**
	 * @param connection The connection to hide UI blips for
	 */
	public void hideConnectionBlips(Connection connection)
	{
		Stage stage = train.getActor().getStage();
		for(Actor a : connection.getRouteBlips())
		{
			a.setVisible(false);
			stage.getActors().removeValue(a, true);
		}
	}
	
	/**
	 * Adds a new connection to the end of the route, if the player has enough fuel
	 * Usually one of the connections return from getAdjacentConnections()
	 * @param connection The connection to be added
	 */
	public void addConnection(Connection connection)
	{
		// Charging the player for the fuel needed or displaying an error message if insufficient:
		int fuelCost = train.getFuelLengthCost(connection.getLength());
		if( fuelCost <= train.getOwner().getFuel(train.getFuelType()))
		{
			train.getOwner().subFuel(train.getFuelType(), fuelCost);
			
			//Discards old selections so they cannot be clicked on:
			ArrayList<Connection> oldConnections = connection.getStartMapObj().connections;
			for(Connection c : oldConnections)
			{
				c.getDestination().getActor().setRouteAvailable(false);
				c.getDestination().getActor().toggleHighlight(false);
			}
			
			// Adds the connection to route ArrayList:
			path.add(connection);
			
			// Makes the new adjacent connections clickable:
			ArrayList<Connection> adj = getAdjacentConnections();	
			for(Connection c: adj)
			{
				c.getDestination().getActor().setRouteAvailable(train, c);
				c.getDestination().getActor().toggleHighlight(true);
			}
			
			// Shows the UI blips for that connection:
			showConnectionBlips(connection);
			
			// Sets booleans to false so the train does not move and the route
			//is not complete:
			isComplete = false;
			if(train.getActor() != null) //Stops problems when testing
			{
				train.getActor().canMove = false;			
				updateRouteText();
			}
		}
		else
		{
			// Player has insufficient fuel to add connection to route, Warning message:
			WarningMessage.fireWarningWindow("INSUFFICIENT FUEL!", "You need " + fuelCost
					+ " more " + train.getFuelType());
		}
	}

	/**
	 * Removes the latest connection from the route.
	 * Used to undo latest addition.
	 * @return returns true if remove successful
	 */
	public boolean removeConnection()
	{
		if(!path.isEmpty())
		{	
			// If the route trying to be removed has already been traversed...
			if((path.get(path.size() - 1) == path.get(routeIndex) && connectionTravelled == 0) || path.get(path.size() - 1) != path.get(routeIndex))
			{
				hideConnectionBlips(path.get(path.size() - 1));
				
				ArrayList<Connection> currentConnection = getAdjacentConnections();	
				
				// Removes the possible adjacent connections: 
				for(Connection c : currentConnection)
				{
					c.getDestination().getActor().setRouteAvailable(false);
					c.getDestination().getActor().toggleHighlight(false);
					
					//train.route.hideConnectionBlips(c);
				}
				
				// Refund player:
				
				int fuelCost = train.getFuelLengthCost(path.get(path.size() - 1).getLength());
				train.getOwner().addFuel(train.getFuelType(), fuelCost);
				
				// Remove the connection from the route:
				path.remove(path.size() - 1);
				
				updateRouteText();
				
				//Toggles the current selection for the new route:
				currentConnection = getAdjacentConnections();	
				for(Connection c: currentConnection)
				{
					c.getDestination().getActor().setRouteAvailable(train, c);
					c.getDestination().getActor().toggleHighlight(true);
					
					//train.route.showConnectionBlips(c);
				}
				return true;
			}
			return false;
		}
		else
			return false;
	}
	
	/**
	 * Removes all connections from the route and resets the train position to the last station it past.
	 * Can be used to reset a train when it collides with another or when a route is completely aborted.
	 */
	public void abortRoute()
	{	
		currentMapObj = path.get(routeIndex).getStartMapObj();
		hideRouteBlips();
		updateRouteText();
		
		while(removeConnection()){}
		
		routeIndex = 0;
		connectionTravelled = 0;
	}
	
	/**
	 * A repeated undo
	 */
	public void cancelRoute()
	{
		if(path.isEmpty())
			GameScreenUI.exitRoutingMode();
		
		hideRouteBlips();
				
		while(removeConnection()){}
				
		showRouteBlips();
		updateRouteText();
	}
	
	/**
	 * Updates the text displaying the route information:
	 */
	public void updateRouteText()
	{
		GameScreenUI.routeLength.setText(String.format("Route length: %.1f", getTotalLength()));
		GameScreenUI.routeRemaining.setText(String.format("Route remaining: %.1f", getLengthRemaining()));
		GameScreenUI.routeFuelCost.setText(String.format("Fuel cost (%s): %d", train.getFuelType(), train.getFuelRouteCost()));
	}
	
	/**
	 * Returns Vector2 containing x and y position of the Train in the Route. Calculated using
	 * the route index and connectionTravelled by scaling the direction vector within a connection
	 * @return a Vector containing the coordinates of the Train on the map
	 */
	public Vector2 getTrainPos()
	{
		if(path.isEmpty())
		{
			return new Vector2(currentMapObj.x, currentMapObj.y);
		}
		else
		{
			MapObj startMapObj = path.get(routeIndex).getStartMapObj();
			
			// Gets the coordinates of the starting station of the current connection:
			Vector2 pos = new Vector2(startMapObj.x, startMapObj.y);
			
			// Copies the vector for the connection direction so we can scale:
			Vector2 vect = path.get(routeIndex).getVector().cpy();
			
			// Scales the vector by the connectionTravelled
			vect.scl(connectionTravelled);
			
			// Adds the starting startion and scaled vector to get exact train position:
			pos.add(vect);

			return pos;
		}
	}
	
	/**
	 * @return The length of the route from start to end
	 */
	public float getTotalLength()
	{
		float length = 0;
		for(int i = 0; i < path.size(); i++)
		{
			length += path.get(i).getLength();
		}
		return length;
	}
	
	/**
	 * @return The length of the route remaining
	 */
	public float getLengthRemaining()
	{
		if(path.isEmpty())
		{
			return 0;
		}
		else
		{
			float currentLength = path.get(routeIndex).getLength(); 
			float length = currentLength - connectionTravelled;

			for(int i = routeIndex + 1; i < path.size(); i++)
			{
				length += path.get(i).getLength();
			}
			return length;
		}
	}
	
	/**
	 * @return if the train is currently in a station
	 */
	public boolean inStation()
	{
		if(path.isEmpty())
		{
			return true;
		}
		else
		{
			Connection currentConnection = path.get(routeIndex);
			float connectionLength = currentConnection.getLength();
			
			if(connectionTravelled == 0 || connectionTravelled == connectionLength)
				return true;
			else
				return false;
		}
	}
	
	/**
	 * @return The station instance of the station the Train is in. Null if between stations
	 */
	public Station getStation()
	{
		if(inStation())
		{
			if(path.isEmpty())
			{
				return currentMapObj.getStation();
			}
			else
			{
				float connectionLength = path.get(routeIndex).getLength();
				
				if(connectionTravelled == 0)
					return path.get(routeIndex).getStartMapObj().getStation();
				else if(connectionTravelled == connectionLength)
					return path.get(routeIndex).getDestination().getStation();
			}
		}
		return null;
	}
	
	/**
	 * Progressed a train in the route by certain amount - normally it's speed
	 * @param moveBy how much to progress the Train through the route by
	 */
	public void update(float moveBy)
	{
		if(inStation()){
			if(getStation() != null){	//determines that it is not a junction
				if(getStation().isFaulty()){
					WarningMessage.fireWarningWindow("Sorry", getStation().getName() + " Station is faulty. You must repair it to continue!");
					abortRoute();
					path.clear();
					routeIndex = 0;
					isComplete = true;
					return;
				}
			}
		}
		System.out.println(routeIndex);
		
		// gets the length of the current connection:
		float connectionLength = path.get(routeIndex).getLength();
		
		// If the train is still on the same connection, update conenctionTravelled:
		if(connectionTravelled + moveBy <= connectionLength)
		{
			connectionTravelled += moveBy;
			currentMapObj = path.get(routeIndex).getStartMapObj();
		}
		else
		{
			// Completes the current connection and progresses onto the next using
			// the route index, which then progresses the difference of that connection:
			
			float diff = Math.abs(connectionTravelled + moveBy - connectionLength);
			currentMapObj = path.get(routeIndex).getDestination();
			
			routeIndex++;
			connectionTravelled = 0;
			
			// Triggers a listener so we can implement station tax and Goal completion validation:
			notifyStationPassed();
			
			// If route not complete, recursively call itself with a new distance to travel:
			if(routeIndex < path.size())
				update(diff);
			else
			{
				// ROUTE FINISHED
				path.clear();
				routeIndex = 0;
				isComplete = true;
			}
		}
	}

	/**
	 * Adds an object to the listener array
	 */
	public void register(RouteListener newListener)
	{
		if(newListener != null)
			listeners.add(newListener);
	}
	/**
	 * Removes an object from the Listener array
	 */
	public void unregister(RouteListener removeListener)
	{
		if(listeners.contains(removeListener))
			listeners.remove(removeListener);
	}

	/**
	 * Notifies all listeners that a station has been passed by a train while completely it's route.
	 * Can be used to tax trains for passing rival stations
	 */
	public void notifyStationPassed()
	{
		for(RouteListener listener: listeners)
		{
			if(currentMapObj.getStation() != null)
				listener.stationPassed(currentMapObj.getStation(), train);
		}
	}
}