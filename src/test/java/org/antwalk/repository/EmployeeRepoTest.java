package org.antwalk.repository;

import static org.junit.jupiter.api.Assertions.*;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class EmployeeRepoTest {

	@MockBean
	private EmployeeRepo empRepo;
	
	private String name;
	private String contactNo;
	private Bus b;
	private User user;
	
	@BeforeEach
	void setUp() {
		name = "Subhashree Mitra";
		contactNo = "1234567890";
		b = new Bus(1, 20, 10, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8")));
		user = new User((long) 5, "subhashreem@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE");
	}
	
	@Test
	void testFindAllByB() {
		Employee e1 = new Employee(1, name, contactNo, b, user);
		Employee e2 = new Employee(2, "Anupam Adhikari", "648464654554", b, new User((long) 6, "anupama@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE"));
		Employee e3 = new Employee(3, "Ankita Gupta", "84671896978", b, new User((long) 7, "ankitag@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE"));

		List<Employee> expected = new ArrayList<>();
		expected.add(e1);
		expected.add(e2);
		expected.add(e3);
		
		when(empRepo.findAllByB(b)).thenReturn(expected);
		
		List<Employee> actual = empRepo.findAllByB(b);
		assertEquals(expected, actual);


	}

	@Test
	void testFindByEid() {
		Employee expected = new Employee(1, name, contactNo, b, user);
		when(empRepo.findByEid(1)).thenReturn(Optional.of(expected));
		
		Employee actual = empRepo.findByEid(1).get();
		assertEquals(expected, actual);

	}

}
