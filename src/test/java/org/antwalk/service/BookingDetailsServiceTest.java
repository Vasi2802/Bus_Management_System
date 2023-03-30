package org.antwalk.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.antwalk.entity.BookingDetails;
import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.entity.Employee;
import org.antwalk.entity.Route;
import org.antwalk.entity.Stop;
import org.antwalk.entity.User;
import org.antwalk.repository.BookingDetailsRepo;
import org.antwalk.repository.RouteRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class BookingDetailsServiceTest {
	
	@Autowired
	private BookingDetailsService bdService;
	
	@MockBean
	private BookingDetailsRepo bdRepo;
	
	private Employee e;
	private Bus b;
	private Date bookingForMonth;
	
	@BeforeEach
	void setUp() {
		e = new Employee(1, "Achyut Madhawan", "56456454150", new Bus(1, 20, 10, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"))), new User((long) 7, "achyutm@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE"));
		b = new Bus(3, 10, 7, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8")));

		bookingForMonth = Date.valueOf("2023-03-20");
	}

	@Test
	void testInsertBookingDetails() {
		BookingDetails expected = new BookingDetails(1, e,b,bookingForMonth);
		when(bdRepo.save(expected)).thenReturn(expected);
		
		BookingDetails actual = bdService.insertBookingDetails(expected);
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllBookingDetails() {
		List<BookingDetails> expected = new ArrayList<>();
		BookingDetails bd1 = new BookingDetails(2, e,b,bookingForMonth);
		BookingDetails bd2 = new BookingDetails(3, e,b,bookingForMonth);
		expected.add(bd1);
		expected.add(bd2);

		when(bdRepo.findAll()).thenReturn(expected);
		
		List<BookingDetails> actual = bdService.getAllBookingDetails();
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0), actual.get(0));
		assertEquals(expected.get(1), actual.get(1));
	}

	@Test
	void testGetBookingDetailsById() {
		long id = 5;
		BookingDetails expected = new BookingDetails(id, e,b,bookingForMonth);
		when(bdRepo.findById(id)).thenReturn(Optional.of(expected));
		
		BookingDetails actual = bdService.getBookingDetailsById(id);
		assertEquals(expected,actual);
	}

	@Test
	void testDeleteBookingDetailsById() {
		BookingDetails bd = new BookingDetails(10, e,b,bookingForMonth);
		bdService.deleteBookingDetailsById(10);
		
		verify(bdRepo, times(1)).deleteById((long) 10);
	}

	@Test
	void testUpdateBookingDetailsById() {
		String expected1 = "Booking Details Updated";
		String expected2 = "Booking Details exists but your input id does not match with the existing Booking Details id";
		String expected3 = "Booking Details does not exist";
		
		BookingDetails bd = new BookingDetails(15, e,b,bookingForMonth);
		List<BookingDetails> bdList = new ArrayList<>();
		bdList.add(bd);
		
		bd.setBookingForMonth(Date.valueOf("2023-04-12"));
		
		when(bdRepo.findAll()).thenReturn(bdList);
		when(bdRepo.save(bd)).thenReturn(bd);
		
		String actual1 = bdService.updateBookingDetailsById(bd, 15);
		assertEquals(expected1, actual1);
		
		when(bdRepo.save(new BookingDetails(17, e,b,bookingForMonth))).thenReturn(new BookingDetails(17, e,b,bookingForMonth));
		String actual2 = bdService.updateBookingDetailsById(new BookingDetails(17, e,b,bookingForMonth), 15);
		assertEquals(expected2, actual2);
		
		when(bdRepo.save(new BookingDetails(20, e,b,bookingForMonth))).thenReturn(new BookingDetails(20, e,b,bookingForMonth));
		String actual3 = bdService.updateBookingDetailsById(new BookingDetails(20, e,b,bookingForMonth), 20);
		assertEquals(expected3, actual3);
	}

}
