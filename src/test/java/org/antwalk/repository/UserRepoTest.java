package org.antwalk.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.Optional;

import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.entity.Employee;
import org.antwalk.entity.Route;
import org.antwalk.entity.RouteStopId;
import org.antwalk.entity.Stop;
import org.antwalk.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UserRepoTest {
	
//	@MockBean
	
	@Autowired
	private UserRepo userRepo;
	
//	@Autowired
//	private EmployeeRepo empRepo;
	
//	private String userName;
//	private String password;
//	private String role;
//	private Employee employee;
//	
//	@BeforeEach
//	void setUp() {
//		userName = "subhashreem@trainee.nrifintech.com";
//		password = "fun123";
//		role = "ROLE_EMPLOYEE";
//		employee = new Employee(1, "Subhashree Mitra", "648464654554", new Bus(1, 20, 10, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"))), new User((long) 6, "subhashreem@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE"));
//	}

//	@Test
//	void testFindByIdLong() {
//		User expected = new User(6L, userName, password, role, employee);
//		when(userRepo.findById((long) 6)).thenReturn(Optional.of(expected));
//		User actual= userRepo.findById((long) 6).get();
//		assertEquals(expected, actual);
//
//	}
	
	@Test
	void testFindByIdLong() {
		User expected = new User(1L, "driver1@driver.nrifintech.com", "$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K", "ROLE_DRIVER");
		User actual= userRepo.findById((long) 1).get();
		assertEquals(expected.getId(), actual.getId());

	}

//	@Test
//	void testDeleteByEmployee() {
//		User emp = new User(6L, userName, password, role, employee);	
//		userRepo.deleteByEmployee(employee);
//		verify(userRepo, times(1)).deleteByEmployee(employee);
//		
//	}
	
//	@Test
//	void testDeleteByEmployee() {
////		Employee e = new Employee(22L, "Sagnik Sinha", "4567890123", null, new User(32L, "sagniks@trainee.nrifintech.com", "$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K", "ROLE_EMPLOYEE"));
//		Employee e = empRepo.findById(22L).get();
////		User emp = new User(6L, userName, password, role, employee);	
//		userRepo.deleteByEmployee(e);
////		System.out.println(userRepo.findById(32L);
//		assertEquals(false, userRepo.findById(32L).isPresent());
////		verify(userRepo, times(1)).deleteByEmployee(e);
//		
//	}

}
