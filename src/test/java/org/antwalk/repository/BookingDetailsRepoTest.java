package org.antwalk.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.antwalk.entity.BookingDetails;
import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.entity.Employee;
import org.antwalk.entity.Route;
import org.antwalk.entity.Stop;
import org.antwalk.entity.User;
import org.antwalk.entity.WaitingList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingDetailsRepoTest {

//	@MockBean
	
	@Autowired
	private BookingDetailsRepo bdRepo;
	
	@Autowired
	private EmployeeRepo empRepo;
	
	@Autowired
	private BusRepo busRepo;
	
//	private Employee e;
//	private Bus b;
//	private Date bookingForMonth;
//	private Stop stop;
//	
//	@BeforeEach
//	void setUp() {
//		e = new Employee(1, "Achyut Madhawan", "56456454150", new Bus(1, 20, 10, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"))), new User((long) 7, "achyutm@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE"));
//		b = new Bus(3, 10, 7, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8")));
//		bookingForMonth = Date.valueOf("2023-03-20");
//		stop = new Stop(2,"s2");
//	}
	
//	@Test
//	void testFindAllByEAndBookingForMonthGreaterThanEqual() {
//		BookingDetails b1 = new BookingDetails(1,e,b,Date.valueOf("2023-02-23"), stop);
//		BookingDetails b2 = new BookingDetails(2,e,b,bookingForMonth, stop);
//		BookingDetails b3 = new BookingDetails(3,e,b,Date.valueOf("2023-04-22"), stop);
//		BookingDetails b4 = new BookingDetails(4,e,b,Date.valueOf("2023-05-25"), stop);
//		
//		List<BookingDetails> bookingList = new ArrayList<>();
//		bookingList.add(b1);
//		bookingList.add(b2);
//		bookingList.add(b3);
//		bookingList.add(b4);
//		
//		List<BookingDetails> expected = new ArrayList<>();
//		
//		for(BookingDetails obj:bookingList) {
//			if(obj.getBookingForMonth().compareTo(bookingForMonth)>=0) {
//				expected.add(obj);
////				System.out.println(obj.getBookingForMonth());
//			}
//		}
//		
//		when(bdRepo.findAllByEAndBookingForMonthGreaterThanEqual(e,bookingForMonth)).thenReturn(expected);
//		
//		List<BookingDetails> actual = bdRepo.findAllByEAndBookingForMonthGreaterThanEqual(e,bookingForMonth);
//		
//		assertEquals(expected.size(), actual.size());
//		
//		for(int i=0; i<expected.size(); i++) {
//			assertEquals(expected.get(i), actual.get(i));
//		}
//		
//	}
	
	@Test
	@Order(1)
	void testFindAllByEAndBookingForMonthGreaterThanEqual() {
		List<BookingDetails> actual = bdRepo.findAllByEAndBookingForMonthGreaterThanEqual(empRepo.findById(1L).get(), Date.valueOf("2023-03-25"));
		for(int i=0; i<actual.size()-1; i++) {
//			System.out.println(actual.get(i).getBookingForMonth());
			assertTrue(actual.get(i).getBookingForMonth().compareTo(actual.get(i+1).getBookingForMonth()) <= 0);
		}
		
	}

//	@Test
//	void testFindAllByBookingForMonthGreaterThanEqualOrderByBookingForMonth() {
//		BookingDetails b1 = new BookingDetails(1, new Employee(2, "Shreya Rai", "56456454150", new Bus(1, 20, 10, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"))), new User((long) 9, "shreyar@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE")),b,Date.valueOf("2023-02-23"), stop);
//		BookingDetails b2 = new BookingDetails(2,e,b,bookingForMonth, stop);
//		BookingDetails b3 = new BookingDetails(3,new Employee(4, "Subhashree Mitra", "1234567890", new Bus(2, 15, 7, LocalTime.of(7, 0), new Driver(4, "Pintu", "4654651555200", new User((long) 2, "pintu@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"))), new User((long) 10, "subhashreem@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE")),b,Date.valueOf("2023-04-22"), stop);
//		BookingDetails b4 = new BookingDetails(4,new Employee(5, "Shreyansh Sahu", "9876543210", new Bus(1, 20, 10, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"))), new User((long) 11, "shreyanshs@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE")),b,Date.valueOf("2023-05-25"), stop);
//		
//		List<BookingDetails> bookingList = new ArrayList<>();
//		bookingList.add(b4);
//		bookingList.add(b3);
//		bookingList.add(b2);
//		bookingList.add(b1);
//		
//		List<BookingDetails> expected = new ArrayList<>();
//		
//		for(BookingDetails obj:bookingList) {
//			if(obj.getBookingForMonth().compareTo(bookingForMonth)>=0) {
//				expected.add(obj);
////				System.out.println(obj.getBookingForMonth() + "       " + obj.getE().getName());
//			}
//		}
//		
////		System.out.println();
//		
//		Collections.sort(expected);
//		
////		for(BookingDetails obj:expected) {
////				System.out.println(obj.getBookingForMonth() + "       " + obj.getE().getName());
////			
////		}
//		
//		
//		when(bdRepo.findAllByBookingForMonthGreaterThanEqualOrderByBookingForMonth(bookingForMonth)).thenReturn(expected);
//		
//		List<BookingDetails> actual = bdRepo.findAllByBookingForMonthGreaterThanEqualOrderByBookingForMonth(bookingForMonth);
//		
//		assertEquals(expected.size(), actual.size());
//		
//		for(int i=0; i<expected.size(); i++) {
//			assertEquals(expected.get(i), actual.get(i));
//		}
//		
//	}
	
	@Test
	@Order(2)
	void testFindAllByBookingForMonthGreaterThanEqualOrderByBookingForMonth() {
		List<BookingDetails> actual = bdRepo.findAllByBookingForMonthGreaterThanEqualOrderByBookingForMonth(Date.valueOf("2023-03-25"));
		for(int i=0; i<actual.size()-1; i++) {
//			System.out.println(actual.get(i).getBookingForMonth());
			assertTrue(actual.get(i).getBookingForMonth().compareTo(actual.get(i+1).getBookingForMonth()) <= 0);
		}
	}
	
	
//
//	@Test
//	void testFindAllByEAndBookingForMonth() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByEAndBookingForMonth() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByEAndBOrderByBookingForMonthAsc() {
//		fail("Not yet implemented");
//	}
//
	
	
	
//	@Test
//	void testFindByEAndBOrderByBookingIdDesc() {
//		BookingDetails b1 = new BookingDetails(1, e, b, Date.valueOf("2023-02-23"), stop);
//		BookingDetails b2 = new BookingDetails(2, e, b, bookingForMonth, stop);
//		BookingDetails b3 = new BookingDetails(3, e, b, Date.valueOf("2023-04-22"), stop);
//		BookingDetails b4 = new BookingDetails(4, e, b, Date.valueOf("2023-05-25"), stop);
//		
//		List<BookingDetails> expected = new ArrayList<>();
//		expected.add(b1);
//		expected.add(b2);
//		expected.add(b3);
//		expected.add(b4);
//		
//		expected.sort(Comparator.comparing(BookingDetails::getBookingId).reversed());
//		
////		for(BookingDetails obj:expected) {
////			System.out.println(obj.getBookingId());
////		
////		}
//		
//		when(bdRepo.findByEAndBOrderByBookingIdDesc(e,b)).thenReturn(expected);
//		
//		List<BookingDetails> actual = bdRepo.findByEAndBOrderByBookingIdDesc(e,b);
//		
//		assertEquals(expected.size(), actual.size());
//		
//		for(int i=0; i<expected.size(); i++) {
//			assertEquals(expected.get(i), actual.get(i));
//		}
//		
//	}
	
	@Test
	@Order(3)
	void testFindByEAndBOrderByBookingIdDesc() {
		List<BookingDetails> actual = bdRepo.findByEAndBOrderByBookingIdDesc(empRepo.findById(1L).get(), busRepo.findById(1L).get());
		for(int i=0; i<actual.size()-1; i++) {
//			System.out.println(actual.get(i).getBookingId());
			assertTrue(actual.get(i).getBookingId() > actual.get(i+1).getBookingId());
		}
		
	}
	
	
//
//	@Test
//	void testFindByEAndBOrderByBookingForMonthDesc() {
//		fail("Not yet implemented");
//	}
//
	
	
//	@Test
//	void testDeleteByB() {
//		BookingDetails bd = new BookingDetails(2, e, b, bookingForMonth, stop);
//		bdRepo.deleteByB(b);
//		verify(bdRepo, times(1)).deleteByB(b);
//		
//	}
	
	@Test
	@Order(4)
	void testDeleteByB() {
		bdRepo.deleteByB(busRepo.findById(1L).get());
		List<BookingDetails> empty = bdRepo.findAllByB(busRepo.findById(1L).get());
		assertEquals(0, empty.size());
		
	}

//	@Test
//	void testDeleteByE() {
//		BookingDetails bd = new BookingDetails(2, e, b, bookingForMonth, stop);
//		bdRepo.deleteByE(e);
//		verify(bdRepo, times(1)).deleteByE(e);
//	}
	
	@Test
	@Order(5)
	void testDeleteByE() {
		bdRepo.deleteByE(empRepo.findById(4L).get());
		List<BookingDetails> empty = bdRepo.findAllByE(empRepo.findById(4L).get());
		assertEquals(0, empty.size());
	}
	
//	@Test
//	void testFindAllByB() {
//		BookingDetails b1 = new BookingDetails(1, new Employee(2, "Shreya Rai", "56456454150", new Bus(1, 20, 10, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"))), new User((long) 9, "shreyar@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE")),b,Date.valueOf("2023-02-23"), stop);
//		BookingDetails b2 = new BookingDetails(2,e,b,bookingForMonth, stop);
//		BookingDetails b3 = new BookingDetails(3,new Employee(4, "Subhashree Mitra", "1234567890", new Bus(2, 15, 7, LocalTime.of(7, 0), new Driver(4, "Pintu", "4654651555200", new User((long) 2, "pintu@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"))), new User((long) 10, "subhashreem@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE")),b,Date.valueOf("2023-04-22"), stop);
//		BookingDetails b4 = new BookingDetails(4,new Employee(5, "Shreyansh Sahu", "9876543210", new Bus(1, 20, 10, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"))), new User((long) 11, "shreyanshs@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE")),b,Date.valueOf("2023-05-25"), stop);
//		
//		List<BookingDetails> expected = new ArrayList<>();
//		expected.add(b1);
//		expected.add(b2);
//		expected.add(b3);
//		expected.add(b4);
//		
//		when(bdRepo.findAllByB(b)).thenReturn(expected);
//		
//		List<BookingDetails> actual = bdRepo.findAllByB(b);
//		
//		assertEquals(expected.size(), actual.size());
//		
//		for(int i=0; i<expected.size(); i++) {
//			assertEquals(expected.get(i), actual.get(i));
//		}
//		
//		
//	}
	
	@Test
	@Order(6)
	void testFindAllByB() {
		List<BookingDetails> actual = bdRepo.findAllByB(busRepo.findById(3L).get());
		for(BookingDetails obj: actual) {
			assertEquals(3, obj.getB().getBid());
		}
		
	}
	
	@Test
	@Order(7)
	void testFindAllByE() {
		List<BookingDetails> actual = bdRepo.findAllByE(empRepo.findById(5L).get());
		for(BookingDetails obj: actual) {
			assertEquals(5, obj.getE().getEid());
		}
		
	}

}
