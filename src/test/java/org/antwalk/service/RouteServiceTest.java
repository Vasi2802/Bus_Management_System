package org.antwalk.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.antwalk.entity.Route;
import org.antwalk.entity.Stop;
import org.antwalk.repository.RouteRepo;
import org.antwalk.repository.StopRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class RouteServiceTest {

	@Autowired
	private RouteService routeService;
	
	@MockBean
	private RouteRepo routeRepo;
	
	private Stop start;
	private Stop end;
	private String active;
	
	@BeforeEach
	void setUp() {
		start = new Stop(1,"s1");
		end = new Stop(10, "s10");
		active = "yes";
	}
	
	@Test
	void testInsertRoute() {
		Route expected = new Route(1, start, end,active);
		when(routeRepo.save(expected)).thenReturn(expected);
		
		Route actual = routeService.insertRoute(expected);
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllRoutes() {
		List<Route> expected = new ArrayList<>();
		Route r1 = new Route(1, start, end,active);
		Route r2 = new Route(2, new Stop(2,"s2"), new Stop(8, "s8"),active);
		expected.add(r1);
		expected.add(r2);

		when(routeRepo.findAll()).thenReturn(expected);
		
		List<Route> actual = routeService.getAllRoutes();
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0), actual.get(0));
		assertEquals(expected.get(1), actual.get(1));
	}

	@Test
	void testGetRouteById() {
		long id = 5;
		Route expected = new Route(id, start, end,active);
		when(routeRepo.findById(id)).thenReturn(Optional.of(expected));
		
		Route actual = routeService.getRouteById(id);
		assertEquals(expected,actual);
	}

	@Test
	void testDeleteRouteById() {
		Route r = new Route(8, start, end,active);
		routeService.deleteRouteById(8);
		
		verify(routeRepo, times(1)).deleteById((long) 8);
	}

	@Test
	void testUpdateRouteById() {
		String expected1 = "Route Updated";
		String expected2 = "Route exists but your input id does not match with the existing route id";
		String expected3 = "Route does not exist";
		
		Route r = new Route(15, start, end,active);
		List<Route> routeList = new ArrayList<>();
		routeList.add(r);
		
		r.setActive("no");
		
		when(routeRepo.findAll()).thenReturn(routeList);
		when(routeRepo.save(r)).thenReturn(r);
		
		String actual1 = routeService.updateRouteById(r, 15);
		assertEquals(expected1, actual1);
		
		when(routeRepo.save(new Route(17, start, end,active))).thenReturn(new Route(17, start, end,active));
		String actual2 = routeService.updateRouteById(new Route(17, start, end,active), 15);
		assertEquals(expected2, actual2);
		
		when(routeRepo.save(new Route(20, start, end,active))).thenReturn(new Route(20, start, end,active));
		String actual3 = routeService.updateRouteById(new Route(20, start, end,active), 20);
		assertEquals(expected3, actual3);
	}

}
