/* In Initiazer error which is breaking all of the tests need to be fixed first before this is completed*/
package com.TeamHEC.LocomotionCommotion.Map;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;
import com.badlogic.gdx.Gdx;

@RunWith(GdxTestRunner.class)
public class WorldMapTest {
	WorldMap tester;

	@Test							//Tests if the World Map initializes or instantiates correctly
	public void setUp() {
		tester = new WorldMap(); 
		
	}
	
	@Test 							//Case 1 : No Map File Present in MapFolder Directory
	public void createDefaultFileTest() {		
		String Created_Map_Path = GameData.MAP_FOLDER + System.getProperty("file.separator") + GameData.CURRENT_MAP;
		File CreatedMapInFolder = new File(Created_Map_Path);	
		assertTrue("The Default map file has been created successfully in the Map Folder", Gdx.files.internal("Map1.json").readString().equals(CreatedMapInFolder.toString()));
		
		System.out.println("Case when the mapfolder directory is not present or doesn't contain any map files");
		System.out.println(WorldMap.getInstance().mapList.keySet()); //Map Instance of the Default map file has been correctly instantiated and loaded into the mapList
	}
	
	@Test 							//Case 2 : Some Map Files Present in MapFolder Directory 
	public void testLoadingIntoMapList() {
		System.out.println("Case when one or more maps are present in the MapFolder Directory");	
		System.out.println(WorldMap.getInstance().mapList.keySet()); //Map-Instances from all the map files have been correctly instantiated and loaded into the mapList 
	}

}
