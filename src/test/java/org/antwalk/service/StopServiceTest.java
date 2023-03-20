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
		
		Stop expected = new Stop(10,"s10");
		when(stopRepo.save(expected)).thenReturn(expected);
		
		Stop actual = stopService.insertStop(expected);
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllStops() {
		
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
		long id = 5;
		Stop expected = new Stop(id,"stop name");
		when(stopRepo.findById(id)).thenReturn(Optional.of(expected));
		
		Stop actual = stopService.getStopById(id);
		
		assertEquals(expected,actual);

	}

	@Test
	void testGetStopByName() {
		String name = "sector 5";
		Stop expected = new Stop(15,name);
		when(stopRepo.findByName(name)).thenReturn(expected);
		
		Stop actual = stopService.getStopByName(name);
		
		assertEquals(expected,actual);
	}

	@Test
	void testDeleteStopById() {
		Stop s = new Stop(25,"s25");
		stopService.deleteStopById(25);
		
		verify(stopRepo, times(1)).deleteById((long) 25);
	}
	

	@Test
	void testUpdateStopById() {
		String expected1 = "Stop Updated";
		String expected2 = "Stop exists but your input id does not match with the existing stop id";
		String expected3 = "Stop does not exist";
		
		Stop s = new Stop(30,"s30");
		List<Stop> stopList = new ArrayList<>();
		stopList.add(s);
		
		s.setName("new name");
		
		when(stopRepo.findAll()).thenReturn(stopList);
		when(stopRepo.save(s)).thenReturn(s);
		
		String actual1 = stopService.updateStopById(s, 30);
		assertEquals(expected1, actual1);
		
		when(stopRepo.save(new Stop(31, "new name"))).thenReturn(new Stop(31, "new name"));
		String actual2 = stopService.updateStopById(new Stop(31, "new name"),30);
		assertEquals(expected2, actual2);
		
		when(stopRepo.save(new Stop(50, "new name"))).thenReturn(new Stop(50, "new name"));
		String actual3 = stopService.updateStopById(new Stop(50, "new name"),50);
		assertEquals(expected3, actual3);
		
		
	}

	@Test
	void testUpdateStopByName() {
		String expected1 = "Stop Updated";
		String expected2 = "Stop does not exist";
		
		Stop s = new Stop(35,"s35");
		List<Stop> stopList = new ArrayList<>();
		stopList.add(s);
		
		when(stopRepo.findAll()).thenReturn(stopList);
		
		Stop update = new Stop(35, "new name");
		when(stopRepo.save(update)).thenReturn(update);
		
		
		String actual1 = stopService.updateStopByName(update, "s35");
		String actual2 = stopService.updateStopByName(update, "does not exist");
		
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
	}

}
