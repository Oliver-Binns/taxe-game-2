package com.TeamHEC.LocomotionCommotion.Map;

import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.Resource.*;

/**
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 */

public class WorldMap {

	private final static WorldMap INSTANCE = new WorldMap();
	public static WorldMap getInstance()
	{
		return INSTANCE;
	}
	
	//Line colour takes an array of 3 colours, if a station needs less than 3 set the first one(s) to the colour you want and repeat the last unique colour
	//e.g. for a station on a black and blue line the second and third slots of the array must be the same (order of colours is otherwise irrelevant)
	
	// Hardcoded stations used in the map:
	public final Station AMSTERDAM = new Station("Amsterdam", 850, new Electric(500), 50, new Line[]{Line.Orange, Line.Orange, Line.Orange}, 50, 615f, 560f);
	public final Station ATHENS = new Station("Athens", 850, new Coal(500), 100, new Line[]{Line.Brown, Line.Green, Line.Green}, 50,  1121f, 170f);
	public final Station BERLIN = new Station("Berlin", 950, new Nuclear(500), 25, new Line[]{Line.Purple, Line.Red, Line.Red}, 50, 731f, 560f);
	public final Station BERN = new Station("Bern", 950, new Nuclear(500), 25, new Line[]{Line.Purple, Line.Orange, Line.Orange}, 50, 731f, 300f);
	public final Station DUBLIN = new Station("Dublin", 900, new Coal(500), 100, new Line[]{Line.Orange, Line.Black, Line.Black}, 50, 471f, 560f);
	public final Station HELSINKI = new Station("Helsinki", 900, new Oil(500), 75, new Line[]{Line.Brown, Line.Blue, Line.Blue}, 50, 1121f, 820f);
	public final Station LISBON = new Station("Lisbon", 850, new Electric(500), 50, new Line[]{Line.Yellow, Line.Green, Line.Green}, 50, 341f, 170f);
	public final Station LONDON = new Station("London", 850, new Coal(500), 100, new Line[]{Line.Black, Line.Black, Line.Black}, 50, 471f, 430f);
	public final Station MADRID = new Station("Madrid", 900, new Electric(500), 75, new Line[]{Line.Yellow, Line.Orange, Line.Orange}, 50, 471f, 300f);
	public final Station MONACO = new Station("Monaco", 1500, new Gold(500), 150, new Line[]{Line.Black, Line.Orange, Line.Orange}, 50, 601f, 300f);
	public final Station MOSCOW = new Station("Moscow", 850, new Nuclear(500), 25, new Line[]{Line.Blue, Line.Orange, Line.Orange}, 50, 1381f, 560f);
	public final Station OSLO = new Station("Oslo", 900, new Oil(500), 75, new Line[]{Line.Purple, Line.Blue, Line.Blue}, 50, 731f, 820f);
	public final Station PARIS = new Station("Paris", 950, new Electric(500), 50, new Line[]{Line.Yellow, Line.Black, Line.Black}, 50, 601f, 430f);
	public final Station PRAGUE = new Station("Prague", 1000, new Oil(500), 75, new Line[]{Line.Orange, Line.Yellow, Line.Brown}, 50, 861f, 430f);
	public final Station REYKJAVIK = new Station("Reykjavik", 850, new Electric(500), 50, new Line[]{Line.Blue, Line.Black, Line.Black}, 50, 211f, 820f);
	public final Station ROME = new Station("Rome", 900, new Coal(500), 100, new Line[]{Line.Purple, Line.Green, Line.Green}, 50, 861f, 170f);
	public final Station STOCKHOLM = new Station("Stockholm", 900, new Oil(500), 75, new Line[]{Line.Blue, Line.Orange, Line.Orange}, 50, 861f, 820f);
	public final Station VIENNA = new Station("Vienna", 850, new Oil(500), 75, new Line[]{Line.Brown, Line.Brown, Line.Brown}, 50, 991f, 300f);
	public final Station VILNIUS = new Station("Vilnuis", 850, new Oil(500), 75, new Line[]{Line.Brown, Line.Brown, Line.Brown}, 50, 1121f, 690f);
	public final Station WARSAW = new Station("Warsaw", 950, new Coal(500), 100, new Line[]{Line.Red, Line.Orange, Line.Orange}, 50, 861f, 560f);
	
	// Creates Junction MapObjs with specified coordinates:
	public final Junction[] junction = new Junction[]{new Junction(731f, 430f, "Junction1"), new Junction(991f, 560f, "Junction2")};
	
	// Adds all the created stations to an ArrayList for later access:
	public ArrayList<Station> stationsList = new ArrayList<Station>() {	 		 
		private static final long serialVersionUID = 1L;
	{ 
		add(LONDON);
		add(PARIS);
		add(REYKJAVIK);
		add(DUBLIN);
		add(AMSTERDAM);
		add(OSLO);
		add(STOCKHOLM);
		add(HELSINKI);
		add(VILNIUS);
		add(MOSCOW);
		add(WARSAW);
		add(PRAGUE);
		add(VIENNA);
		add(ROME);
		add(MADRID);
		add(LISBON);
		add(MONACO);
		add(ATHENS);
		add(BERLIN);
		add(BERN);
	 }};
	
	public WorldMap()
	{
		// Creates a connection instance for each existing connection:
		createConnections(REYKJAVIK, new MapObj[]{OSLO, DUBLIN});
		createConnections(DUBLIN, new MapObj[]{REYKJAVIK, AMSTERDAM, LONDON});
		createConnections(LONDON, new MapObj[]{DUBLIN, PARIS});
		createConnections(PARIS, new MapObj[]{LONDON, MONACO, MADRID, junction[0]});
		createConnections(MADRID, new MapObj[]{PARIS, MONACO, LISBON});
		createConnections(LISBON, new MapObj[]{MADRID, ROME});
		createConnections(AMSTERDAM, new MapObj[]{DUBLIN, BERLIN});
		createConnections(BERLIN, new MapObj[]{AMSTERDAM, OSLO, WARSAW, junction[0]});
		createConnections(OSLO, new MapObj[]{STOCKHOLM, REYKJAVIK, BERLIN});
		createConnections(WARSAW, new MapObj[]{BERLIN, STOCKHOLM, junction[1], PRAGUE});
		createConnections(STOCKHOLM, new MapObj[]{OSLO, WARSAW, HELSINKI});
		createConnections(HELSINKI, new MapObj[]{STOCKHOLM, VILNIUS, MOSCOW});
		createConnections(MOSCOW, new MapObj[]{HELSINKI, junction[1]});
		createConnections(PRAGUE, new MapObj[]{WARSAW, junction[0], junction[1], BERN, VIENNA});
		createConnections(VIENNA, new MapObj[]{PRAGUE, ATHENS});
		createConnections(ROME, new MapObj[]{LISBON, BERN, ATHENS});
		createConnections(MONACO, new MapObj[]{MADRID, PARIS, BERN});
		createConnections(BERN, new MapObj[]{MONACO, junction[0], PRAGUE, ROME});
		createConnections(VILNIUS, new MapObj[]{HELSINKI, junction[1]});
		createConnections(junction[1], new MapObj[]{WARSAW, VILNIUS, MOSCOW, PRAGUE});
		createConnections(junction[0], new MapObj[]{PARIS, BERLIN, PRAGUE, BERN});
		createConnections(ATHENS, new MapObj[]{ROME, VIENNA});
	}
	
	/**
	 * Initialised the connections Arraylist in each MapObj with their adjacent stations
	 * @param mapObj the initial starting MapObj
	 * @param connection All it's adjacent MapObjs
	 */
	private void createConnections(MapObj mapObj, MapObj[] connection)
	{
		for(int i = 0; i < connection.length; i++)
		{
			mapObj.connections.add(new Connection(mapObj, connection[i]));
		}
	}
}
