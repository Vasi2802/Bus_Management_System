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
import org.antwalk.entity.Employee;
import org.antwalk.entity.Route;
import org.antwalk.entity.Stop;
import org.antwalk.entity.User;
import org.antwalk.entity.WaitingList;
import org.antwalk.repository.RouteRepo;
import org.antwalk.repository.WaitingListRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class WaitingListServiceTest {

	@Autowired
	private WaitingListService wlService;
	
	@MockBean
	private WaitingListRepo wlRepo;
	
	private Employee e;
	private Bus b;
	
	@BeforeEach
	void setUp() {
		e = new Employee(1, "Achyut Madhawan", "56456454150", new Bus(1, 20, 10, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"), "yes")), new User((long) 7, "achyutm@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE"));
		b = new Bus(3, 10, 7, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"), "yes"));
	}
	
	@Test
	void testInsertWaitingList() {
		WaitingList expected = new WaitingList(1,e,b);
		when(wlRepo.save(expected)).thenReturn(expected);
		
		WaitingList actual = wlService.insertWaitingList(expected);
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllWaitingList() {
		List<WaitingList> expected = new ArrayList<>();
		WaitingList wl1 = new WaitingList(2,e,b);
		WaitingList wl2 = new WaitingList(4,e,b);
		expected.add(wl1);
		expected.add(wl2);

		when(wlRepo.findAll()).thenReturn(expected);
		
		List<WaitingList> actual = wlService.getAllWaitingList();
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0), actual.get(0));
		assertEquals(expected.get(1), actual.get(1));
	}

	@Test
	void testGetWaitingListById() {
		long id = 5;
		WaitingList expected = new WaitingList(id,e,b);
		when(wlRepo.findById(id)).thenReturn(Optional.of(expected));
		
		WaitingList actual = wlService.getWaitingListById(id);
		assertEquals(expected,actual);
	}

	@Test
	void testDeleteWaitingListById() {
		WaitingList wl = new WaitingList(7,e,b);
		wlService.deleteWaitingListById(7);
		
		verify(wlRepo, times(1)).deleteById((long) 7);
	}

	@Test
	void testUpdateWaitingListById() {
		String expected1 = "Waiting List Updated";
		String expected2 = "Waiting List exists but your input id does not match with the existing Waiting List id";
		String expected3 = "Waiting List does not exist";
		
		WaitingList wl = new WaitingList(10,e,b);
		List<WaitingList> wlList = new ArrayList<>();
		wlList.add(wl);
		
		wl.setB(new Bus(3, 20, 12, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"), "yes")));
		
		when(wlRepo.findAll()).thenReturn(wlList);
		when(wlRepo.save(wl)).thenReturn(wl);
		
		String actual1 = wlService.updateWaitingListById(wl, 10);
		assertEquals(expected1, actual1);
		
		when(wlRepo.save(new WaitingList(11,e,b))).thenReturn(new WaitingList(11,e,b));
		String actual2 = wlService.updateWaitingListById(new WaitingList(11,e,b), 10);
		assertEquals(expected2, actual2);
		
		when(wlRepo.save(new WaitingList(15,e,b))).thenReturn(new WaitingList(15,e,b));
		String actual3 = wlService.updateWaitingListById(new WaitingList(15,e,b), 15);
		assertEquals(expected3, actual3);
	}

}
