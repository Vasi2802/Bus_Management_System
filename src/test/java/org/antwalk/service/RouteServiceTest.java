package org.antwalk.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.antwalk.entity.Route;
import org.antwalk.entity.Stop;
import org.antwalk.repository.RouteRepo;
import org.antwalk.repository.StopRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class RouteServiceTest {

	@Autowired
	private StopService stopService;
	
	@Autowired
	private StopRepo stopRepo;
	
	@Autowired
	private RouteService routeService;
	
	@Autowired
	private RouteRepo routeRepo;
	
//	private Stop startGlobal = stopService.getStopById(4);
//	private Stop endGlobal = stopService.getStopById(7);
	
//	@BeforeEach
//	void setup() {
//		Stop start=new Stop(1L,"SaltLake");
//		Stop end=new Stop(2L,"abcd");
//		Route r=new Route(1,start,end,"yes");
//		Mockito.when(routeRepo.save(r)).thenReturn(r);
//	}
	
	@Test
	void testInsertRoute() {
		Stop start = stopService.getStopById(4);
		Stop end = stopService.getStopById(5);
		
		
//		Stop start=new Stop(1L,"SaltLake");
//		Stop end=new Stop(2L,"abcd");
//		System.out.println(start + "\n" + end);
//		Route actual = routeService.insertRoute(new Route(1,start, end, "yes"));
//		
//		System.out.println(actual.getActive());
		
		Route actual = routeService.insertRoute(new Route(start, end, "yes"));
		long id = actual.getRid();
		
		Route expected = new Route(id, start, end, "yes");
//		System.out.println(actual.getActive() + "\n" + expected.getActive());
		assertNotNull(actual);
		assertEquals(expected.getActive(), actual.getActive());
	}

//	@Test
//	void testGetAllRoutes() {
//		routeRepo.deleteAll();
//		
//		Stop start1 = stopService.getStopById(5);
//		Stop end1 = stopService.getStopById(6);
//		
//		Stop start2 = stopService.getStopById(6);
//		Stop end2 = stopService.getStopById(7);
//		
//		Route expected1 = new Route(start1, end1, "yes");
//		Route expected2 = new Route(start2, end2, "yes");
//		
//		routeService.insertRoute(expected1);
//		routeService.insertRoute(expected2);
//		
//		List<Route> actual = routeService.getAllRoutes();
//		assertEquals(actual.size(),2);
//		assertEquals(expected1, actual.get(0));
//		assertEquals(expected2, actual.get(1));
//	}
//
//	@Test
//	void testGetRouteById() {
//		Stop start = stopService.getStopById(7);
//		Stop end = stopService.getStopById(8);
//		
//		Route expected = new Route(start, end, "yes");
//		routeService.insertRoute(expected);
//		long id = expected.getRid();
//		
//		Route actual = routeService.getRouteById(id);
//		assertEquals(expected, actual);
//	}
//
//	@Test
//	void testDeleteRouteById() {
//		String expected = "Route Deleted";
//		
//		Stop start = stopService.getStopById(8);
//		Stop end = stopService.getStopById(9);
//		
//		Route r = new Route(start, end, "yes");
//		routeService.insertRoute(r);
//		long id = r.getRid();
//		
//		String actual = routeService.deleteRouteById(id);
//		assertEquals(expected, actual);
//	}
//
//	@Test
//	void testUpdateRouteById() {
//		String expected1 = "Route Updated";
//		String expected2 = "Route exists but your input id does not match with the existing route id";
//		String expected3 = "Route does not exist";
//		
//		Stop start = stopService.getStopById(4);
//		Stop end = stopService.getStopById(8);
//		
//		Stop startNew = stopService.getStopById(6);
//		Stop endNew = stopService.getStopById(9);
//		
//		Route r = new Route(start, end, "yes");
//		routeService.insertRoute(r);
//		long id = r.getRid();
//		
//		String actual1 = routeService.updateRouteById(new Route(id, startNew, endNew, "yes"), id);
//		String actual2 = routeService.updateRouteById(new Route(id+5, startNew, endNew, "yes"), id);
//		String actual3 = routeService.updateRouteById(new Route(id+100, startNew, endNew, "yes"), id+100);
//		
//		assertEquals(expected1, actual1);
//		assertEquals(expected2, actual2);
//		assertEquals(expected3, actual3);
//	}

}
