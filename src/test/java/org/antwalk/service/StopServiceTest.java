package org.antwalk.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antwalk.entity.Stop;
import org.antwalk.repository.StopRepo;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class StopServiceTest {
	
	@Autowired
	private StopService stopService;
	
	@MockBean
	private StopRepo stopRepo;

	@Test
	void testInsertStop() {
		String stopName = "stop for insert method in service";
		
		Stop actual = stopService.insertStop(new Stop(stopName));
		long id = actual.getSid();
		Stop expected = new Stop(id,stopName);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllStops() {
//		stopRepo.deleteAll();
//		Stop expected1 = new Stop("stop1 for get all stops");
//		Stop expected2 = new Stop("stop2 for get all stops");
//		
//		stopService.insertStop(expected1);
//		stopService.insertStop(expected2);
//		
//		List<Stop> actual = stopService.getAllStops();
//		assertEquals(2,actual.size());
//		assertEquals(expected1, actual.get(0));
//		assertEquals(expected2, actual.get(1));
		
		List<Stop> expected = new ArrayList<>();
		expected.add(new Stop(1,"s1"));
		expected.add(new Stop(1,"s1"));

		
		when(stopRepo.findAll()).thenReturn(expected);
	}

	@Test
	void testGetStopById() {
		String stopName = "stop for get stop by id";
		Stop expected = new Stop(stopName);
		stopService.insertStop(expected);
		long id = expected.getSid();
		
		Stop actual = stopService.getStopById(id);
		assertEquals(expected.getName(), actual.getName());

	}

	@Test
	void testGetStopByName() {
		String stopName = "stop for get stop by name";

		Stop expected = new Stop(stopName);
		stopService.insertStop(expected);
		
		Stop actual = stopService.getStopByName(stopName);
		assertEquals(expected.getSid(), actual.getSid());
	}

	@Test
	void testDeleteStopById() {
		String expected = "Stop Deleted";
		String stopName = "stop for delete stop by id";

		Stop s = new Stop(stopName);
		stopService.insertStop(s);
		long id = s.getSid();
		
		String actual = stopService.deleteStopById(id);
		assertEquals(expected, actual);
	}

	@Test
	void testUpdateStopById() {
		String expected1 = "Stop Updated";
		String expected2 = "Stop exists but your input id does not match with the existing stop id";
		String expected3 = "Stop does not exist";
		
		String stopName = "stop for update stop by id";
		Stop s = new Stop(stopName);
		stopService.insertStop(s);
		long id=s.getSid();
		
		String actual1 = stopService.updateStopById(new Stop(id, "new name"), id);
		String actual2 = stopService.updateStopById(new Stop(id+5, "new name"), id);
		String actual3 = stopService.updateStopById(new Stop(id+100, "new name"), id+100);
		
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
		assertEquals(expected3, actual3);
	}

	@Test
	void testUpdateStopByName() {
		String expected1 = "Stop Updated";
		String expected2 = "Stop does not exist";
		
		String stopName = "stop for update stop by name";
		Stop s = new Stop(stopName);
		stopService.insertStop(s);
		long id=s.getSid();

		
		String actual1 = stopService.updateStopByName(new Stop(id, "updated by name"), stopName);
		String actual2 = stopService.updateStopByName(new Stop(id, "updated by name"), "ABCDXYZ");
		
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
	}

}
