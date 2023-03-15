package org.antwalk.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.antwalk.entity.Stop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StopServiceTest {
	
	@Autowired
	private StopService stopService;

	@Test
	void testInsertStop() {
		Stop expected = new Stop(2, "testing stop 2");
		
		Stop actual = stopService.insertStop(new Stop(2, "testing stop 2"));
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllStops() {
		Stop expected1 = new Stop(5,"s5");
		Stop expected2 = new Stop(6,"s6");
		
		stopService.insertStop(expected1);
		stopService.insertStop(expected2);
		
		List<Stop> actual = stopService.getAllStops();
		assertEquals(actual.size(),2);
		assertEquals(expected1, actual.get(0));
		assertEquals(expected2, actual.get(1));
	}

	@Test
	void testGetStopById() {
		Stop expected = new Stop(10,"s10");
		stopService.insertStop(expected);
		
		Stop actual = stopService.getStopById(10);
		assertEquals(expected.getName(), actual.getName());

	}

	@Test
	void testGetStopByName() {
		Stop expected = new Stop(15,"s15");
		stopService.insertStop(expected);
		
		Stop actual = stopService.getStopByName("s15");
		assertEquals(expected.getSid(), actual.getSid());
	}

	@Test
	void testDeleteStopById() {
		String expected = "Stop Deleted";
		Stop s = new Stop(18,"s18");
		stopService.insertStop(s);
		
		String actual = stopService.deleteStopById(18);
		assertEquals(expected, actual);
	}

	@Test
	void testUpdateStopById() {
		String expected1 = "Stop Updated";
		String expected2 = "Stop exists but your input id does not match with the existing stop id";
		String expected3 = "Stop does not exist";
		
		Stop s = new Stop(20,"s20");
		stopService.insertStop(s);
		
		String actual1 = stopService.updateStopById(new Stop(20, "new name"), 20);
		String actual2 = stopService.updateStopById(new Stop(22, "new name"), 20);
		String actual3 = stopService.updateStopById(new Stop(20, "new name"), 50);
		
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
		assertEquals(expected3, actual3);
	}

	@Test
	void testUpdateStopByName() {
		String expected1 = "Stop Updated";
		String expected2 = "Stop does not exist";
		
		Stop s = new Stop(25,"s25");
		stopService.insertStop(s);
		
		String actual1 = stopService.updateStopByName(new Stop(25, "updated by name"), "s25");
		String actual2 = stopService.updateStopByName(new Stop(25, "updated by name"), "ABCD");
		
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
	}

}
