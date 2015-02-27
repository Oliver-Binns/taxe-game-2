package com.TeamHEC.LocomotionCommotion.Map;

import static org.junit.Assert.*;

import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class ConnectionTest {

	Connection tester;
	String error;
	boolean success;
	
	@Before
	public void setUp() throws Exception {
		error = "";
		success = false;
		
		try
		{
			tester = new Connection(WorldMap.getInstance().AMSTERDAM, WorldMap.getInstance().BERLIN);
			success = true;
		}
		catch (Exception ex)
		{
			error = ex.getMessage();
			success = false;
		}
		
	}

	@Test
	public void testConnection() {
		assertTrue("Connection failed to initialise: " + error, success);
		assertTrue("Length was not initialised correctly", tester.getLength() != 0);	
		assertTrue("Vector was not initialised correctly", tester.getVector() != null);
		assertTrue("StartMapObj was not stored correctly", tester.getStartMapObj() == WorldMap.getInstance().AMSTERDAM);
		assertTrue("EndMapObj was not stored correctly", tester.getDestination() == WorldMap.getInstance().BERLIN);
		assertTrue("Route blips were not set correctly - null", tester.getRouteBlips() != null);
		assertTrue("Route blips were not set correctly - null", tester.getRouteBlips().size != 0);
	}

	@Test
	public void testIsReverseOf() {
		//True
		assertTrue("Connection did not recognise it's reverse", 
				tester.isReverseOf(new Connection(
						WorldMap.getInstance().BERLIN,
						WorldMap.getInstance().AMSTERDAM)));
		
		//False
		assertTrue("Connection mistakenly recognised a random connection as it's reverse", 
				!tester.isReverseOf(new Connection(
						WorldMap.getInstance().DUBLIN,
						WorldMap.getInstance().ATHENS)));
	}

}
