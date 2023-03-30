package org.antwalk.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.antwalk.entity.ArrivalTimeTable;
import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.entity.Route;
import org.antwalk.entity.Stop;
import org.antwalk.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class BusRepoTest {

//	@MockBean
	
	@Autowired
	private BusRepo busRepo;
	
	@Autowired
	private RouteRepo routeRepo;
	
	@Autowired
	private DriverRepo driverRepo;
	
//	private int totalSeats;
//	private int availableSeats;
//	private LocalTime startTime;
//	private Driver d;
//	private Route r;
//	
//	@BeforeEach
//	void setUp() {
//		totalSeats = 20;
//		availableSeats = 10;
//		startTime = LocalTime.of(7, 0);
//		d = new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER"));
//		r = new Route(2, new Stop(2,"s2"), new Stop(8, "s8"));
//	}
	
//	@Test
//	void testFindAllByR() {
//		Bus b1 = new Bus(1, totalSeats, availableSeats, startTime, d, r);
//		Bus b2 = new Bus(2, 15, 5, LocalTime.of(7, 10), new Driver(4, "Rahul", "2345678901", new User((long) 2, "rahul@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(3, new Stop(5,"s5"), new Stop(13, "s13")));
//		
//		List<Bus> busList = new ArrayList<>();
//		busList.add(b1);
//		busList.add(b2);
//		
//		List<Bus> expected = new ArrayList<>();
//		
//		Route find = r;
//		
//		for(Bus obj: busList) {
//			if(obj.getR().equals(find)) {
//				expected.add(obj);
//			}
//		}
//		
//		when(busRepo.findAllByR(find)).thenReturn(expected);
//		
//		List<Bus> actual = busRepo.findAllByR(find);
//		
//		assertEquals(expected, actual);
//		
//	}
	
	@Test
	void testFindAllByR() {
		List<Bus> actual = busRepo.findAllByR(routeRepo.findById(1L).get());
		for(Bus obj:actual) {
			assertEquals(1, obj.getR().getRid());
		}
		
	}

//	@Test
//	void testFindByD() {
//		Bus expected = new Bus(1, totalSeats, availableSeats, startTime, d, r);
//		when(busRepo.findByD(d)).thenReturn(Optional.of(expected));
//		Bus actual = busRepo.findByD(d).get();
//		assertEquals(expected, actual);
//
//		
//	}
	
	@Test
	void testFindByD() {
		Bus b = busRepo.findByD(driverRepo.findById(1L).get()).get();
		assertEquals(1, b.getD().getDid());
	}

//	@Test
//	void testDeleteByR() {
//		Bus expected = new Bus(1, totalSeats, availableSeats, startTime, d, r);
//		busRepo.deleteByR(r);
//		
//		verify(busRepo, times(1)).deleteByR(r);
//	}
	
//	@Test
//	void testDeleteByR() {
//		busRepo.deleteByR(routeRepo.findById(4L).get());
//		List<Bus> busList = busRepo.findAllByR(routeRepo.findById(4L).get());
//		assertEquals(0, busList.size());
//	}

}
