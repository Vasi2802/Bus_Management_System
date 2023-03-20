package org.antwalk.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.entity.Route;
import org.antwalk.entity.Stop;
import org.antwalk.entity.User;
import org.antwalk.repository.BusRepo;
import org.antwalk.repository.RouteRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class BusServiceTest {

	@Autowired
	private BusService busService;
	
	@MockBean
	private BusRepo busRepo;
	
	private int totalSeats;
	private int availableSeats;
	private LocalTime startTime;
	private Driver d;
	private Route r;
	
	@BeforeEach
	void setUp() {
		totalSeats = 20;
		availableSeats = 10;
		startTime = LocalTime.of(7, 0);
		d = new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER"));
		r = new Route(2, new Stop(2,"s2"), new Stop(8, "s8"), "yes");
	}
	
	@Test
	void testInsertBus() {
		Bus expected = new Bus(1, totalSeats, availableSeats, startTime, d, r);
		when(busRepo.save(expected)).thenReturn(expected);
		
		Bus actual = busService.insertBus(expected);
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllBus() {
		List<Bus> expected = new ArrayList<>();
		Bus b1 = new Bus(2, totalSeats, availableSeats, startTime, d, r);
		Bus b2 = new Bus(3, 10, 8, LocalTime.of(7, 30), d, r);
		expected.add(b1);
		expected.add(b2);

		when(busRepo.findAll()).thenReturn(expected);
		
		List<Bus> actual = busService.getAllBus();
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0), actual.get(0));
		assertEquals(expected.get(1), actual.get(1));
	}

	@Test
	void testGetBusById() {
		long id = 5;
		Bus expected = new Bus(id, totalSeats, availableSeats, startTime, d, r);
		when(busRepo.findById(id)).thenReturn(Optional.of(expected));
		
		Bus actual = busService.getBusById(id);
		assertEquals(expected,actual);
	}

	@Test
	void testDeleteBusById() {
		Bus b = new Bus(7, totalSeats, availableSeats, startTime, d, r);
		busService.deleteBusById(7);
		
		verify(busRepo, times(1)).deleteById((long) 7);
	}

	@Test
	void testUpdateBusById() {
		String expected1 = "Bus Updated";
		String expected2 = "Bus exists but your input id does not match with the existing Bus id";
		String expected3 = "Bus does not exist";
		
		Bus b = new Bus(10, totalSeats, availableSeats, startTime, d, r);
		List<Bus> busList = new ArrayList<>();
		busList.add(b);
		
		b.setTotalSeats(15);
		
		when(busRepo.findAll()).thenReturn(busList);
		when(busRepo.save(b)).thenReturn(b);
		
		String actual1 = busService.updateBusById(b, 10);
		assertEquals(expected1, actual1);
		
		when(busRepo.save(new Bus(11, totalSeats, availableSeats, startTime, d, r))).thenReturn(new Bus(11, totalSeats, availableSeats, startTime, d, r));
		String actual2 = busService.updateBusById(new Bus(11, totalSeats, availableSeats, startTime, d, r), 10);
		assertEquals(expected2, actual2);
		
		when(busRepo.save(new Bus(19, totalSeats, availableSeats, startTime, d, r))).thenReturn(new Bus(19, totalSeats, availableSeats, startTime, d, r));
		String actual3 = busService.updateBusById(new Bus(19, totalSeats, availableSeats, startTime, d, r), 19);
		assertEquals(expected3, actual3);
	}

}
