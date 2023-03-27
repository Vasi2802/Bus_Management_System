package org.antwalk.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.antwalk.entity.ArrivalTimeTable;
import org.antwalk.entity.Route;
import org.antwalk.entity.RouteStopId;
import org.antwalk.entity.Stop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ArrivalTimeRepoTest {

	@MockBean
	private ArrivalTimeRepo atRepo;
	
	private Route r;
	private Stop s;
	private LocalTime morning;
	private LocalTime evening;
	private RouteStopId composite;
	
	@BeforeEach
	void setUp() {
		r = new Route(1, new Stop(1, "s1"), new Stop(10, "s10"));
		s = new Stop(5, "s5");
		morning = LocalTime.of(7, 45);
		evening = LocalTime.of(18, 15);
		composite = new RouteStopId(r,s);
	}
	
	@Test
	void testFindAllByRouteStopId_Stop() {
		ArrivalTimeTable at1 = new ArrivalTimeTable(composite, morning, evening);
		ArrivalTimeTable at2 = new ArrivalTimeTable(new RouteStopId(new Route(2, new Stop(3, "s3"), new Stop(12, "s12")), new Stop(7, "s7")), LocalTime.of(7, 55), LocalTime.of(18, 05));
		List<ArrivalTimeTable> timeList = new ArrayList<>();
		timeList.add(at1);
		timeList.add(at2);
		
		Stop find = s;
		
		List<ArrivalTimeTable> expected = new ArrayList<>();
		
		for(ArrivalTimeTable obj: timeList) {
			if(obj.getRouteStopId().getStop().equals(find)) {
				expected.add(obj);
			}
		}
		
//		System.out.println("Expected " + expected.get(0).getRouteStopId().getStop().getSid());
		
		when(atRepo.findAllByRouteStopId_Stop(find)).thenReturn(expected);
		
		List<ArrivalTimeTable> actual = atRepo.findAllByRouteStopId_Stop(find);
		
		assertEquals(expected, actual);

	}

	@Test
	void testFindAllByRouteStopId_RouteOrderByMorningArrivalTime() {
		ArrivalTimeTable at1 = new ArrivalTimeTable(composite, morning, evening);
		ArrivalTimeTable at2 = new ArrivalTimeTable(new RouteStopId(r, new Stop(7, "s7")), LocalTime.of(7, 55), LocalTime.of(18, 05));
		List<ArrivalTimeTable> expected = new ArrayList<>();
		expected.add(at2);
		expected.add(at1);
		
		expected.sort(Comparator.comparing(ArrivalTimeTable::getMorningArrivalTime));
		
//		System.out.println("Expected 1 " + expected.get(0).getMorningArrivalTime());
//		System.out.println("Expected 2 " + expected.get(1).getMorningArrivalTime());

		when(atRepo.findAllByRouteStopId_RouteOrderByMorningArrivalTime(r)).thenReturn(expected);
		List<ArrivalTimeTable> actual = atRepo.findAllByRouteStopId_RouteOrderByMorningArrivalTime(r);
		assertEquals(expected, actual);
	
	}
//
	@Test
	void testFindAllByRouteStopId_RouteOrderByEveningArrivalTime() {
		ArrivalTimeTable at1 = new ArrivalTimeTable(composite, morning, evening);
		ArrivalTimeTable at2 = new ArrivalTimeTable(new RouteStopId(r, new Stop(7, "s7")), LocalTime.of(7, 55), LocalTime.of(18, 05));
		List<ArrivalTimeTable> expected = new ArrayList<>();
		expected.add(at1);
		expected.add(at2);
		
		expected.sort(Comparator.comparing(ArrivalTimeTable::getEveningArrivalTime));
		
//		System.out.println("Expected 1 " + expected.get(0).getEveningArrivalTime());
//		System.out.println("Expected 2 " + expected.get(1).getEveningArrivalTime());

		when(atRepo.findAllByRouteStopId_RouteOrderByEveningArrivalTime(r)).thenReturn(expected);
		List<ArrivalTimeTable> actual = atRepo.findAllByRouteStopId_RouteOrderByEveningArrivalTime(r);
		assertEquals(expected, actual);
	}
//
//	@Test
//	void testDeleteAllByRouteStopId_RouteOrderByMorningArrivalTime() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteAllByRouteStopId_Route() {
//		fail("Not yet implemented");
//	}
//
	@Test
	void testDeleteByRouteStopId_Route() {
		ArrivalTimeTable at = new ArrivalTimeTable(composite, morning, evening);
		atRepo.deleteByRouteStopId_Route(r);
		verify(atRepo, times(1)).deleteByRouteStopId_Route(r);
	}

}
