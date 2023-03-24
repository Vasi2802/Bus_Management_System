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
import org.antwalk.repository.RouteRepo;
import org.antwalk.repository.StopRepo;
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
	
	@MockBean
	private RouteRepo routeRepo;

	@MockBean
	private StopRepo stopRepo;
	
	Route r;
	Stop s;
	LocalTime morning;
	LocalTime evening;
	RouteStopId composite;
	
	@BeforeEach
	void setUp() {
		r = new Route(1, new Stop(1, "s1"), new Stop(10, "s10"));
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
		ArrivalTimeTable at2 = new ArrivalTimeTable(new RouteStopId(new Route(2, new Stop(2, "s2"), new Stop(15, "s15")), new Stop(7,"s7")),  LocalTime.of(7, 30), LocalTime.of(18, 30));
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
		
		when(routeRepo.findById(rid)).thenReturn(Optional.of(r));
		when(stopRepo.findById(sid)).thenReturn(Optional.of(s));
		when(atRepo.findById(composite)).thenReturn(Optional.of(expected));
		
		ArrivalTimeTable actual = atService.getArrivalTimeById(rid, sid);
		assertEquals(expected,actual);
	}

	@Test
	void testDeleteArrivalTimeById() {
		long rid = 1;
		long sid= 5;
		
		ArrivalTimeTable at = new ArrivalTimeTable(composite, morning, evening);
		when(routeRepo.findById(rid)).thenReturn(Optional.of(r));
		when(stopRepo.findById(sid)).thenReturn(Optional.of(s));
		atService.deleteArrivalTimeById(rid, sid);
		
		verify(atRepo, times(1)).deleteById(composite);
	}

	@Test
	void testUpdateArrivalTimeById() {
		String expected1 = "Arrival Time Updated";
		String expected2 = "Arrival Time exists but your input id does not match with the existing Arrival Time id";
		String expected3 = "Arrival Time does not exist";
		
		long rid = 1;
		long sid= 5;
		
		ArrivalTimeTable at = new ArrivalTimeTable(composite, morning, evening);
		List<ArrivalTimeTable> atList = new ArrayList<>();
		atList.add(at);
		
		at.setEveningArrivalTime(LocalTime.of(18, 25));
		
		when(routeRepo.findById(rid)).thenReturn(Optional.of(r));
		when(stopRepo.findById(sid)).thenReturn(Optional.of(s));
		
		when(atRepo.findAll()).thenReturn(atList);
		when(atRepo.save(at)).thenReturn(at);
		
		String actual1 = atService.updateArrivalTimeById(at, rid, sid);
		assertEquals(expected1, actual1);
		
		Route newRoute = new Route(5, new Stop(3, "s3"), new Stop(12, "s12"));
		Stop newStop = new Stop(8, "s8");
		LocalTime newMorning = LocalTime.of(7, 50);
		LocalTime newEvening = LocalTime.of(18, 20);
		RouteStopId newcomposite = new RouteStopId(newRoute,newStop);
		ArrivalTimeTable newAt = new ArrivalTimeTable(newcomposite, newMorning, newEvening);
		
		when(routeRepo.findById((long) 5)).thenReturn(Optional.of(newRoute));
		when(stopRepo.findById((long) 8)).thenReturn(Optional.of(newStop));
		
		when(atRepo.save(newAt)).thenReturn(newAt);
		
		String actual2 = atService.updateArrivalTimeById(newAt, rid, sid);
		assertEquals(expected2, actual2);
	
		String actual3 = atService.updateArrivalTimeById(newAt, 5, 8);
		assertEquals(expected3, actual3);
	}

}
