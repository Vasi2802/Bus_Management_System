package org.antwalk.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.antwalk.entity.ArrivalTimeTable;
import org.antwalk.entity.Route;
import org.antwalk.entity.RouteStopId;
import org.antwalk.entity.Stop;
import org.antwalk.repository.ArrivalTimeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
class ArrivalTimeServiceTest {

	@Autowired
	private ArrivalTimeService atService;
	
	@MockBean
	private ArrivalTimeRepo atRepo;
	
	Route r;
	Stop s;
	LocalTime morning;
	LocalTime evening;
	RouteStopId composite;
	
	@BeforeEach
	void setUp() {
		r = new Route(1, new Stop(1, "s1"), new Stop(10, "s10"), "yes");
		s = new Stop(5, "s5");
		morning = LocalTime.of(7, 45);
		evening = LocalTime.of(18, 15);
		composite = new RouteStopId(r,s);
	}
	
	@Test
	void testInsertArrivalTime() {
		ArrivalTimeTable expected = new ArrivalTimeTable(composite, morning, evening);
		when(atRepo.save(expected)).thenReturn(expected);
		
		ArrivalTimeTable actual = atService.insertArrivalTime(expected);
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllArrivalTimes() {
		List<ArrivalTimeTable> expected = new ArrayList<>();
		ArrivalTimeTable at1 = new ArrivalTimeTable(composite, morning, evening);
		ArrivalTimeTable at2 = new ArrivalTimeTable(new RouteStopId(new Route(2, new Stop(2, "s2"), new Stop(15, "s15"), "yes"), new Stop(7,"s7")),  LocalTime.of(7, 30), LocalTime.of(18, 30));
		expected.add(at1);
		expected.add(at2);

		when(atRepo.findAll()).thenReturn(expected);
		
		List<ArrivalTimeTable> actual = atService.getAllArrivalTimes();
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0), actual.get(0));
		assertEquals(expected.get(1), actual.get(1));
	}

	@Test
	void testGetArrivalTimeById() {
		long rid = 1;
		long sid= 5;
		ArrivalTimeTable expected = new ArrivalTimeTable(composite, morning, evening);
		when(atRepo.findById(composite)).thenReturn(Optional.of(expected));
		
		ArrivalTimeTable actual = atService.getArrivalTimeById(rid, sid);
		assertEquals(expected,actual);
	}

	@Test
	void testDeleteArrivalTimeById() {
		long rid = 1;
		long sid= 5;
		ArrivalTimeTable at = new ArrivalTimeTable(composite, morning, evening);
		atService.deleteArrivalTimeById(rid, sid);
		
		verify(atRepo, times(1)).deleteById(composite);
	}
//
//	@Test
//	void testUpdateArrivalTimeById() {
//		fail("Not yet implemented");
//	}

}
