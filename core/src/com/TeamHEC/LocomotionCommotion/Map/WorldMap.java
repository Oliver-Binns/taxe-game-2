package com.TeamHEC.LocomotionCommotion.Map;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.TeamHEC.LocomotionCommotion.GameData;

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
//	public final Station AMSTERDAM = new Station("Amsterdam", 850, new Electric(500), 50, new Line[]{Line.Orange, Line.Orange, Line.Orange}, 50, 615f, 560f);
//	public final Station ATHENS = new Station("Athens", 850, new Coal(500), 100, new Line[]{Line.Brown, Line.Green, Line.Green}, 50,  1121f, 170f);
//	public final Station BERLIN = new Station("Berlin", 950, new Nuclear(500), 25, new Line[]{Line.Purple, Line.Red, Line.Red}, 50, 731f, 560f);
//	public final Station BERN = new Station("Bern", 950, new Nuclear(500), 25, new Line[]{Line.Purple, Line.Orange, Line.Orange}, 50, 731f, 300f);
//	public final Station DUBLIN = new Station("Dublin", 900, new Coal(500), 100, new Line[]{Line.Orange, Line.Black, Line.Black}, 50, 471f, 560f);
//	public final Station HELSINKI = new Station("Helsinki", 900, new Oil(500), 75, new Line[]{Line.Brown, Line.Blue, Line.Blue}, 50, 1121f, 820f);
//	public final Station LISBON = new Station("Lisbon", 850, new Electric(500), 50, new Line[]{Line.Yellow, Line.Green, Line.Green}, 50, 341f, 170f);
//	public final Station LONDON = new Station("London", 850, new Coal(500), 100, new Line[]{Line.Black, Line.Black, Line.Black}, 50, 471f, 430f);
//	public final Station MADRID = new Station("Madrid", 900, new Electric(500), 75, new Line[]{Line.Yellow, Line.Orange, Line.Orange}, 50, 471f, 300f);
//	public final Station MONACO = new Station("Monaco", 1500, new Gold(500), 150, new Line[]{Line.Black, Line.Orange, Line.Orange}, 50, 601f, 300f);
//	public final Station MOSCOW = new Station("Moscow", 850, new Nuclear(500), 25, new Line[]{Line.Blue, Line.Orange, Line.Orange}, 50, 1381f, 560f);
//	public final Station OSLO = new Station("Oslo", 900, new Oil(500), 75, new Line[]{Line.Purple, Line.Blue, Line.Blue}, 50, 731f, 820f);
//	public final Station PARIS = new Station("Paris", 950, new Electric(500), 50, new Line[]{Line.Yellow, Line.Black, Line.Black}, 50, 601f, 430f);
//	public final Station PRAGUE = new Station("Prague", 1000, new Oil(500), 75, new Line[]{Line.Orange, Line.Yellow, Line.Brown}, 50, 861f, 430f);
//	public final Station REYKJAVIK = new Station("Reykjavik", 850, new Electric(500), 50, new Line[]{Line.Blue, Line.Black, Line.Black}, 50, 211f, 820f);
//	public final Station ROME = new Station("Rome", 900, new Coal(500), 100, new Line[]{Line.Purple, Line.Green, Line.Green}, 50, 861f, 170f);
//	public final Station STOCKHOLM = new Station("Stockholm", 900, new Oil(500), 75, new Line[]{Line.Blue, Line.Orange, Line.Orange}, 50, 861f, 820f);
//	public final Station VIENNA = new Station("Vienna", 850, new Oil(500), 75, new Line[]{Line.Brown, Line.Brown, Line.Brown}, 50, 991f, 300f);
//	public final Station VILNIUS = new Station("Vilnuis", 850, new Oil(500), 75, new Line[]{Line.Brown, Line.Brown, Line.Brown}, 50, 1121f, 690f);
//	public final Station WARSAW = new Station("Warsaw", 950, new Coal(500), 100, new Line[]{Line.Red, Line.Orange, Line.Orange}, 50, 861f, 560f);
	public Map<String, MapInstance> mapList;
	
	// Creates Junction MapObjs with specified coordinates:
	public final Junction[] junction = new Junction[]{new Junction(731f, 430f, "Junction1"), new Junction(991f, 560f, "Junction2")};
	
	// Adds all the created stations to an ArrayList for later access:
//	public ArrayList<Station> stationsList = new ArrayList<Station>() {	 		 
//		private static final long serialVersionUID = 1L;
//	{ 
//		add(LONDON);
//		add(PARIS);
//		add(REYKJAVIK);
//		add(DUBLIN);
//		add(AMSTERDAM);
//		add(OSLO);
//		add(STOCKHOLM);
//		add(HELSINKI);
//		add(VILNIUS);
//		add(MOSCOW);
//		add(WARSAW);
//		add(PRAGUE);
//		add(VIENNA);
//		add(ROME);
//		add(MADRID);
//		add(LISBON);
//		add(MONACO);
//		add(ATHENS);
//		add(BERLIN);
//		add(BERN);
//	 }};
	
	public WorldMap()
	{
		// Creates a connection instance for each existing connection:
//		createConnections(REYKJAVIK, new MapObj[]{OSLO, DUBLIN}, new Line[]{Line.Blue, Line.Black});
//		createConnections(DUBLIN, new MapObj[]{REYKJAVIK, AMSTERDAM, LONDON}, new Line[]{Line.Black, Line.Orange, Line.Black});
//		createConnections(LONDON, new MapObj[]{DUBLIN, PARIS}, new Line[]{Line.Black, Line.Black});
//		createConnections(PARIS, new MapObj[]{LONDON, MONACO, MADRID, junction[0]}, new Line[]{Line.Black, Line.Black, Line.Yellow, Line.Yellow});
//		createConnections(MADRID, new MapObj[]{PARIS, MONACO, LISBON}, new Line[]{Line.Yellow, Line.Orange, Line.Yellow});
//		createConnections(LISBON, new MapObj[]{MADRID, ROME}, new Line[]{Line.Yellow, Line.Green});
//		createConnections(AMSTERDAM, new MapObj[]{DUBLIN, BERLIN}, new Line[]{Line.Red, Line.Red});
//		createConnections(BERLIN, new MapObj[]{AMSTERDAM, OSLO, WARSAW, junction[0]}, new Line[]{Line.Red, Line.Purple, Line.Red, Line.Purple});
//		createConnections(OSLO, new MapObj[]{STOCKHOLM, REYKJAVIK, BERLIN}, new Line[]{Line.Blue, Line.Blue, Line.Purple});
//		createConnections(WARSAW, new MapObj[]{BERLIN, STOCKHOLM, junction[1], PRAGUE}, new Line[]{Line.Red, Line.Orange, Line.Red, Line.Orange});
//		createConnections(STOCKHOLM, new MapObj[]{OSLO, WARSAW, HELSINKI}, new Line[]{Line.Blue, Line.Orange, Line.Blue});
//		createConnections(HELSINKI, new MapObj[]{STOCKHOLM, VILNIUS, MOSCOW}, new Line[]{Line.Blue, Line.Brown, Line.Blue});
//		createConnections(MOSCOW, new MapObj[]{HELSINKI, junction[1]}, new Line[]{Line.Blue, Line.Red});
//		createConnections(PRAGUE, new MapObj[]{WARSAW, junction[0], junction[1], BERN, VIENNA}, new Line[]{Line.Orange, Line.Yellow, Line.Brown, Line.Orange, Line.Brown});
//		createConnections(VIENNA, new MapObj[]{PRAGUE, ATHENS}, new Line[]{Line.Brown, Line.Brown});
//		createConnections(ROME, new MapObj[]{LISBON, BERN, ATHENS}, new Line[]{Line.Green, Line.Purple, Line.Green});
//		createConnections(MONACO, new MapObj[]{MADRID, PARIS, BERN}, new Line[]{Line.Orange, Line.Black, Line.Orange});
//		createConnections(BERN, new MapObj[]{MONACO, junction[0], PRAGUE, ROME}, new Line[]{Line.Orange, Line.Purple, Line.Orange, Line.Purple});
//		createConnections(VILNIUS, new MapObj[]{HELSINKI, junction[1]}, new Line[]{Line.Brown, Line.Brown});
//		createConnections(junction[1], new MapObj[]{WARSAW, VILNIUS, MOSCOW, PRAGUE}, new Line[]{Line.Red, Line.Brown, Line.Red, Line.Brown});
//		createConnections(junction[0], new MapObj[]{PARIS, BERLIN, PRAGUE, BERN}, new Line[]{Line.Yellow, Line.Purple, Line.Yellow, Line.Purple});
//		createConnections(ATHENS, new MapObj[]{ROME, VIENNA}, new Line[]{Line.Green, Line.Brown});
		mapList = new HashMap<String, MapInstance>();
		File mapFolder = new File(GameData.GAME_FOLDER + "Maps");
		boolean created = false;
		
		if(!mapFolder.exists()) {
			try {
				created = mapFolder.mkdirs();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			if(created) {
				System.out.println("Folder created. Populating with default map file.");
				createDefaultFile();
			} else {
				System.out.println("Folder not created.");
			}
		} else {
			System.out.println("Game folder already exists, scanning for existing maps...");
		}
		
		String[] mapFiles = mapFolder.list();
		for(String mapFile : mapFiles) {
			String mapName = mapFile.substring(0, mapFile.length()-5);
			mapList.put(mapName, new MapInstance(GameData.GAME_FOLDER + "Maps" + System.getProperty("file.separator") + mapFile));
		}
	}
	
	private void createDefaultFile() {
		try {
			PrintWriter out = new PrintWriter(GameData.GAME_FOLDER + "Maps" + System.getProperty("file.separator") + "Map1.json");
			out.println(GameData.DEFAULT_MAP_STRING);
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
//	/**
//	 * Initialised the connections Arraylist in each MapObj with their adjacent stations
//	 * @param mapObj the initial starting MapObj
//	 * @param connection All it's adjacent MapObjs
//	 */
//	private void createConnections(MapObj mapObj, MapObj[] connections, Line[] colours)
//	{
//		for(int i = 0; i < connections.length; i++)
//		{
//			mapObj.connections.add(new Connection(mapObj, connections[i], colours[i]));
//		}
//	}
//	/**
//	 * 
//	 */
//	public void generateFaults(){
//		for(int i = 0; i < stationsList.size(); i++){
//			Random random = new Random();
//			int randInt = random.nextInt(100);
//			int faultRate = (int)(stationsList.get(i).getFaultRate() * 100);
//			if(randInt <= faultRate){
//				stationsList.get(i).makeFaulty();
//			}
//		}
//	}
}
