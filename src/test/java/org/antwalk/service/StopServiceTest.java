package org.antwalk.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
//		String stopName = "stop for insert method in service";
//		
//		Stop actual = stopService.insertStop(new Stop(stopName));
//		long id = actual.getSid();
//		Stop expected = new Stop(id,stopName);
//		assertNotNull(actual);
//		assertEquals(expected, actual);
		
		Stop expected = new Stop(10,"s10");
		when(stopRepo.save(expected)).thenReturn(expected);
		
		Stop actual = stopService.insertStop(expected);
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
		expected.add(new Stop(2,"s2"));

		
		when(stopRepo.findAll()).thenReturn(expected);
		
		List<Stop> actual = stopService.getAllStops();
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0), actual.get(0));
		assertEquals(expected.get(1), actual.get(1));
	}

	@Test
	void testGetStopById() {
//		String stopName = "stop for get stop by id";
//		Stop expected = new Stop(stopName);
//		stopService.insertStop(expected);
//		long id = expected.getSid();
//		
//		Stop actual = stopService.getStopById(id);
//		assertEquals(expected.getName(), actual.getName());
		
		
//		when(stopRepo.save(expected)).thenReturn(expected);
//		stopService.insertStop(expected);
		
		
		
		
//		long id = 1;
//		Stop expected = new Stop(id, "stop name");
//		stopService.insertStop(expected);
		
		long id = 5;
		Stop expected = new Stop(id,"stop name");
		when(stopRepo.findById(id)).thenReturn(Optional.of(expected));
		
		Stop actual = stopService.getStopById(id);
		
		assertEquals(expected,actual);
		
		
		

	}
//
	@Test
	void testGetStopByName() {
//		String stopName = "stop for get stop by name";
//
//		Stop expected = new Stop(stopName);
//		stopService.insertStop(expected);
//		
//		Stop actual = stopService.getStopByName(stopName);
//		assertEquals(expected.getSid(), actual.getSid());
	
		String name = "sector 5";
		Stop expected = new Stop(15,name);
		when(stopRepo.findByName(name)).thenReturn(expected);
		
		Stop actual = stopService.getStopByName(name);
		
		assertEquals(expected,actual);
	}
//
	@Test
	void testDeleteStopById() {
//		String expected = "Stop Deleted";
//		String stopName = "stop for delete stop by id";
//
//		Stop s = new Stop(stopName);
//		stopService.insertStop(s);
//		long id = s.getSid();
//		
//		String actual = stopService.deleteStopById(id);
//		assertEquals(expected, actual);
		
		Stop s = new Stop(25,"s25");
		stopService.deleteStopById(25);
		
		verify(stopRepo, times(1)).deleteById((long) 25);
	}
	
//
	@Test
	void testUpdateStopById() {
//		String expected1 = "Stop Updated";
//		String expected2 = "Stop exists but your input id does not match with the existing stop id";
//		String expected3 = "Stop does not exist";
//		
//		String stopName = "stop for update stop by id";
//		Stop s = new Stop(stopName);
//		stopService.insertStop(s);
//		long id=s.getSid();
//		
		
//		
//		assertEquals(expected1, actual1);
//		assertEquals(expected2, actual2);
//		assertEquals(expected3, actual3);
		
		Stop s = new Stop(30,"s30");
		s.setName("new name");
		
		
//		String actual1 = stopService.updateStopById(new Stop(id, "new name"), id);
//		String actual2 = stopService.updateStopById(new Stop(id+5, "new name"), id);
//		String actual3 = stopService.updateStopById(new Stop(id+100, "new name"), id+100);
		
		stopService.updateStopById(s, 30);
		
		verify(stopRepo, times(1)).save(s);
		
	}
//
//	@Test
//	void testUpdateStopByName() {
//		String expected1 = "Stop Updated";
//		String expected2 = "Stop does not exist";
//		
//		String stopName = "stop for update stop by name";
//		Stop s = new Stop(stopName);
//		stopService.insertStop(s);
//		long id=s.getSid();
//
//		
//		String actual1 = stopService.updateStopByName(new Stop(id, "updated by name"), stopName);
//		String actual2 = stopService.updateStopByName(new Stop(id, "updated by name"), "ABCDXYZ");
//		
//		assertEquals(expected1, actual1);
//		assertEquals(expected2, actual2);
//	}

}
