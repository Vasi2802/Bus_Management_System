package org.antwalk.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.antwalk.entity.ArrivalTimeTable;
import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.entity.Employee;
import org.antwalk.entity.Route;
import org.antwalk.entity.Stop;
import org.antwalk.entity.User;
import org.antwalk.entity.WaitingList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class WaitingListRepoTest {

	@MockBean
	private WaitingListRepo wlRepo;
	
	private Employee e;
	private Bus b;
	
	@BeforeEach
	void setUp() {
		e = new Employee(1, "Achyut Madhawan", "56456454150", new Bus(1, 20, 10, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"))), new User((long) 7, "achyutm@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE"));
		b = new Bus(3, 10, 7, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8")));
	}
	
	@Test
	void testFindAllByE() {
		WaitingList wl1 = new WaitingList(1,e,b);
		WaitingList wl2 = new WaitingList(2,e,new Bus(4, 15, 0, LocalTime.of(7, 0), new Driver(5, "Pintu", "2345678901", new User((long) 1, "pintu@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"))));
		
		List<WaitingList> expected = new ArrayList<>();
		expected.add(wl1);
		expected.add(wl2);
		
		when(wlRepo.findAllByE(e)).thenReturn(expected);
		List<WaitingList> actual = wlRepo.findAllByE(e);
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0), actual.get(0));
		assertEquals(expected.get(1), actual.get(1));

	}

	@Test
	void testFindByE() {
		WaitingList expected = new WaitingList(1,e,b);
		when(wlRepo.findByE(e)).thenReturn(Optional.of(expected));
		WaitingList actual = wlRepo.findByE(e).get();
		assertEquals(expected, actual);

	}

	@Test
	void testDeleteByE() {
		WaitingList wl = new WaitingList(1,e,b);
		long expected = 1;
		when(wlRepo.deleteByE(e)).thenReturn((long) 1);
		long actual = wlRepo.deleteByE(e);
		assertEquals(expected, actual);

	}

	@Test
	void testFindAllByBOrderByWid() {
		WaitingList wl1 = new WaitingList(1,e,b);
		WaitingList wl2 = new WaitingList(2,new Employee(2, "Shreyansh Sahu", "56456454150", new Bus(1, 20, 10, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"))), new User((long) 7, "achyutm@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE")),b);
		
		List<WaitingList> expected = new ArrayList<>();
		expected.add(wl2);
		expected.add(wl1);
		
		expected.sort(Comparator.comparing(WaitingList::getWid));
		
		when(wlRepo.findAllByBOrderByWid(b)).thenReturn(expected);
		
		List<WaitingList> actual = wlRepo.findAllByBOrderByWid(b);
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0), actual.get(0));
		assertEquals(expected.get(1), actual.get(1));
	}
//
//	@Test
//	void testCountByBus() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByBIn() {
//		fail("Not yet implemented");
//	}
//
	@Test
	void testFindAllByB() {
		WaitingList wl1 = new WaitingList(1,e,b);
		WaitingList wl2 = new WaitingList(2,new Employee(2, "Shreyansh Sahu", "56456454150", new Bus(1, 20, 10, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"))), new User((long) 7, "achyutm@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE")),b);
		
		List<WaitingList> expected = new ArrayList<>();
		expected.add(wl1);
		expected.add(wl2);
		when(wlRepo.findAllByB(b)).thenReturn(expected);
		
		List<WaitingList> actual = wlRepo.findAllByB(b);
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0), actual.get(0));
		assertEquals(expected.get(1), actual.get(1));

		
		
	}
//
	@Test
	void testDeleteByB() {
		WaitingList wl = new WaitingList(1,e,b);
		wlRepo.deleteByB(b);
		verify(wlRepo, times(1)).deleteByB(b);

	}

}
