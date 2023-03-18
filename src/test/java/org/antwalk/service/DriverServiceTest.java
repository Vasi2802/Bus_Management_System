package org.antwalk.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.antwalk.entity.Driver;
import org.antwalk.entity.Route;
import org.antwalk.entity.Stop;
import org.antwalk.entity.User;
import org.antwalk.repository.DriverRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class DriverServiceTest {
	
	@Autowired
	private DriverService driverService;
	
	@MockBean
	private DriverRepo driverRepo;
	
	private String driverName;
	private String driverContactNo;
	private User user;
	
	@BeforeEach
	void setUp(){
		driverName = "Rahul";
		driverContactNo = "1234567890";
		user = new User((long) 1, "rahul@driver.nrifintech.com", "fun123", "ROLE_DRIVER");
	}

	@Test
	void testInsertDriver() {
		Driver expected = new Driver(1, driverName, driverContactNo, user);
		when(driverRepo.save(expected)).thenReturn(expected);
		
		Driver actual = driverService.insertDriver(expected);
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllDrivers() {
		List<Driver> expected = new ArrayList<>();
		Driver d1 =new Driver(2, driverName, driverContactNo, user);
		Driver d2 = new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER"));
		expected.add(d1);
		expected.add(d2);

		when(driverRepo.findAll()).thenReturn(expected);
		
		List<Driver> actual = driverService.getAllDrivers();
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0), actual.get(0));
		assertEquals(expected.get(1), actual.get(1));
	}

	@Test
	void testGetDriverById() {
		long id = 5;
		Driver expected =new Driver(id, driverName, driverContactNo, user);
		when(driverRepo.findById(id)).thenReturn(Optional.of(expected));
		
		Driver actual = driverService.getDriverById(id);
		assertEquals(expected,actual);
	}

	@Test
	void testDeleteDriverById() {
		Driver d = new Driver(8, driverName, driverContactNo, user);
		driverService.deleteDriverById(8);
		
		verify(driverRepo, times(1)).deleteById((long) 8);
	}

	@Test
	void testUpdateDriverById() {
		
		String expected1 = "Driver Updated";
		String expected2 = "Driver exists but your input id does not match with the existing Driver id";
		String expected3 = "Driver does not exist";
		
		Driver d = new Driver(10, driverName, driverContactNo, user);
		List<Driver> driverList = new ArrayList<>();
		driverList.add(d);
		
		d.setDriverName("Kishore");
		
		when(driverRepo.findAll()).thenReturn(driverList);
		when(driverRepo.save(d)).thenReturn(d);
		
		String actual1 = driverService.updateDriverById(d, 10);
		assertEquals(expected1, actual1);
		
		when(driverRepo.save(new Driver(11, driverName, driverContactNo, user))).thenReturn(new Driver(11, driverName, driverContactNo, user));
		String actual2 = driverService.updateDriverById(new Driver(11, driverName, driverContactNo, user), 10);
		assertEquals(expected2, actual2);
		
		when(driverRepo.save(new Driver(20, driverName, driverContactNo, user))).thenReturn(new Driver(20, driverName, driverContactNo, user));
		String actual3 = driverService.updateDriverById(new Driver(20, driverName, driverContactNo, user), 20);
		assertEquals(expected3, actual3);
		
		
		
	}

}
