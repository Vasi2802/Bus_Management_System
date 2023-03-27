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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UserRepoTest {
	
	@MockBean
	private UserRepo userRepo;
	
	private String userName;
	private String password;
	private String role;
	private Employee employee;
	
	@BeforeEach
	void setUp() {
		userName = "subhashreem@trainee.nrifintech.com";
		password = "fun123";
		role = "ROLE_EMPLOYEE";
		employee = new Employee(1, "Subhashree Mitra", "648464654554", new Bus(1, 20, 10, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8"))), new User((long) 6, "subhashreem@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE"));
	}

	@Test
	void testFindByIdLong() {
		User expected = new User(6L, userName, password, role, employee);
		when(userRepo.findById((long) 6)).thenReturn(Optional.of(expected));
		User actual= userRepo.findById((long) 6).get();
		assertEquals(expected, actual);

	}

	@Test
	void testDeleteByEmployee() {
		User emp = new User(6L, userName, password, role, employee);	
		userRepo.deleteByEmployee(employee);
		verify(userRepo, times(1)).deleteByEmployee(employee);
		
	}

}
